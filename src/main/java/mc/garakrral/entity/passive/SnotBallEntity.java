package mc.garakrral.entity.passive;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import mc.garakrral.xpjump.XpJump;

public class SnotBallEntity extends SlimeEntity implements XpJump {

    private static final TrackedData<Boolean> TAMED =
            DataTracker.registerData(SnotBallEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    // XP Jump sistemi
    private int jumpStrength = 0;
    private boolean isCharging = false;
    private float currentSpeed = 0.0F;           // anlık yatay hız katsayısı
    private boolean riderForward = false;
    private boolean riderSprintBrake = false;

    public SnotBallEntity(EntityType<? extends SlimeEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return SlimeEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TAMED, false);
    }

    public boolean isTamed() {
        return this.dataTracker.get(TAMED);
    }

    public void setTamed(boolean b) {
        this.dataTracker.set(TAMED, b);
    }

    public void setRiderForward(boolean f) {
        this.riderForward = f;
    }

    public void setRiderSprintBrake(boolean b) {
        this.riderSprintBrake = b;
    }

    @Override
    public EntityData initialize(ServerWorldAccess w, LocalDifficulty d, SpawnReason r, EntityData data) {
        EntityData d0 = super.initialize(w, d, r, data);
        try {
            this.setSize(1, true);
        } catch (Exception ignored) {
        }
        return d0;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient) {
            if (!isTamed() && player.getStackInHand(hand).isOf(Items.SLIME_BALL)) {
                setTamed(true);
                player.getStackInHand(hand).decrement(1);
                player.sendMessage(Text.literal("SnotBall loves you now!"), true);
            } else if (isTamed()) {
                player.startRiding(this, true);
            }
        } else {
            if (isTamed()) player.startRiding(this, true);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasPassengers() && this.getFirstPassenger() instanceof PlayerEntity player) {

            // yön kilidi
            this.setYaw(player.getYaw());
            this.prevYaw = this.getYaw();

            // yatay hareketi sıfırla
            Vec3d v = this.getVelocity();
            this.setVelocity(0, v.y, 0);

            player.fallDistance = 0.0F; //fall damage kapalı
        }
    }


    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setTamed(nbt.getBoolean("Tamed"));
        try {
            setSize(1, false);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Tamed", isTamed());
    }

    @Override
    public void setJumpStrength(int strength) {
        this.jumpStrength = Math.max(0, Math.min(100, strength));
    }

    @Override
    public boolean canJump() {
        return this.isOnGround();
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false; // kapalı
    }

    @Override
    public void startJumping(int height) {
        this.isCharging = true;
    }

    @Override
    public void stopJumping() {
        if (!this.isAlive() || !this.hasPassengers()) {
            isCharging = false;
            return;
        }

        isCharging = false;

        PlayerEntity rider = (PlayerEntity) this.getFirstPassenger();

        if (rider != null) {
            rider.fallDistance = 0.0F;
        }

        double min = 0.4D;
        double max = 1.6D;
        double power = min + ((double) jumpStrength / 100.0) * (max - min);

        Vec3d look = rider.getRotationVec(1.0F).normalize();

        double vx = look.x * power;
        double vy = power * 0.9;
        double vz = look.z * power;

        this.setVelocity(vx, vy, vz);
        this.velocityDirty = true;

        if (rider != null) {
            rider.fallDistance = 0.0F;
        }
    }

    @Override
    public void jump() {
        // kapandı
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.hasPassengers() && this.getFirstPassenger() instanceof PlayerEntity rider) {

            // yaw'ı rider'a göre kilitle (görüş)
            this.setYaw(rider.getYaw());
            this.prevYaw = this.getYaw();

            // parametreler: hedef hız katsayıları
            final float maxWalkSpeed = 0.35F;   // hız
            final float accel = 0.02F;         // hızlanma hızı (artış per tick)
            final float decel = 0.01F;         // yavaşlama per tick
            final float sprintBrakeFactor = 0.06F; // shift ile daha hızlı yavaşlama

            // Hedef hız: eğer riderForward true ise maxWalkSpeed, değilse 0
            float target = this.riderForward ? maxWalkSpeed : 0f;

            if (!this.riderForward) {
                // eğer shift ile frenleniyorsa daha hızlı frenle
                if (this.riderSprintBrake) {
                    // sprintBrake: güçlü fren
                    if (currentSpeed > 0f) currentSpeed = Math.max(0f, currentSpeed - sprintBrakeFactor);
                    else currentSpeed = Math.min(0f, currentSpeed + sprintBrakeFactor);
                } else {
                    // normal yavaşlama
                    if (currentSpeed > target) currentSpeed = Math.max(target, currentSpeed - decel);
                    else currentSpeed = Math.min(target, currentSpeed + accel);
                }
            } else {
                // riderForward true ise kademeli hızlan
                if (currentSpeed < target) {
                    currentSpeed = Math.min(target, currentSpeed + accel);
                } else if (currentSpeed > target) {
                    currentSpeed = Math.max(target, currentSpeed - decel);
                }
            }

            // apply horizontal velocity according to currentSpeed and rider look direction
            Vec3d look = rider.getRotationVec(1.0F).normalize();
            Vec3d vel = this.getVelocity();
            double vx = look.x * currentSpeed;
            double vz = look.z * currentSpeed;

            // koru düşey hız bileşeni
            this.setVelocity(vx, vel.y, vz);

            // passenger control: normal entity physics ile çakışmaması için
            super.travel(Vec3d.ZERO);
            return;
        }

        super.travel(movementInput);
    }


    public boolean collides() {
        return true;
    }

    public boolean canBeControlledByRider() {
        return true;
    }
}
