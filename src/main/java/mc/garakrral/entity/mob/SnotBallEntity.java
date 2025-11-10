package mc.garakrral.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
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
import net.minecraft.world.World;

public class SnotBallEntity extends SlimeEntity {

    private static final TrackedData<Boolean> TAMED =
            DataTracker.registerData(SnotBallEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private boolean isCharging = false;

    public SnotBallEntity(EntityType<? extends SlimeEntity> type, World world) {
        super(type, world);
        this.setSize(1, true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return SlimeEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TAMED, false);
    }

    public boolean isTamed() {
        return this.dataTracker.get(TAMED);
    }

    public void setTamed(boolean tamed) {
        this.dataTracker.set(TAMED, tamed);
    }

    // Evcilleştirme ve binme
    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient) {
            if (!isTamed() && player.getStackInHand(hand).getItem() == Items.SLIME_BALL) {
                this.setTamed(true);
                player.getStackInHand(hand).decrement(1);
                player.sendMessage(Text.literal("SnotBall seni sevdi 💚"), true);
                return ActionResult.SUCCESS;
            } else if (isTamed()) {
                player.startRiding(this);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasPassengers() && this.getFirstPassenger() instanceof PlayerEntity player) {

            // Oyuncunun yönünü slime’a uygula
            this.setYaw(player.getYaw());
            this.prevYaw = this.getYaw();

            // Zıplama kontrolü (client'ta değil, server tarafında güvenli)
            if (player.isOnGround() && this.isOnGround()) {
                // Oyuncu zıplamayı isterse (space basılıysa)
                if (player.input != null && player.input.jumping) {
                    isCharging = true;
                } else if (isCharging) {
                    // Tuş bırakıldığında zıpla
                    isCharging = false;
                    Vec3d look = player.getRotationVec(1.0F);
                    double jumpPower = 0.8D;
                    this.addVelocity(look.x * 0.8, jumpPower, look.z * 0.8);
                    this.velocityDirty = true;
                }
            }
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
    }

    @Override
    public void setSize(int size, boolean heal) {
        super.setSize(1, heal);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSize(1, false);
        this.setTamed(nbt.getBoolean("Tamed"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Tamed", this.isTamed());
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public boolean canBeControlledByRider() {
        return true;
    }
}
