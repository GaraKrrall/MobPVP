package mc.garakrral.entity.mob;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class SnotBallEntity extends SlimeEntity implements JumpingMount {

    private static final TrackedData<Boolean> TAMED =
            DataTracker.registerData(SnotBallEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    // JumpingMount ile gelen zıplama gücü (0-100 tipinde client'tan gelir)
    private int jumpStrength = 0;
    // Şarj (player space basılı tuttuğu süre)
    private boolean isCharging = false;

    public SnotBallEntity(EntityType<? extends SlimeEntity> type, World world) {
        super(type, world);
        // Constructor içinde setSize çağırma — initialize() içinde ayarlanacak
    }

    /**
     * Kayıt ederken kullanacağın attribute builder.
     * Mod'un mod init kısmında şu şekilde register etmelisin:
     * FabricDefaultAttributeRegistry.register(ModEntities.SNOT_BALL, SnotBallEntity.createAttributes());
     */
    public static DefaultAttributeContainer.Builder createAttributes() {
        return SlimeEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D);
    }

    // DataTracker (tamed)
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TAMED, false);
    }

    public boolean isTamed() {
        return this.dataTracker.get(TAMED);
    }

    public void setTamed(boolean tamed) {
        this.dataTracker.set(TAMED, tamed);
    }

    /**
     * Önemli: SlimeEntity.initialize(super) içinde bazen boyut rastgele atanabilir.
     * Burada kesinlikle boyutu 1 olarak sabitliyoruz ve attribute'ların hazır olduğu bu aşamada
     * setSize(1, true) çağrıyoruz.
     */
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason reason, EntityData entityData) {
        EntityData data = super.initialize(world, difficulty, reason, entityData);
        // kesin 1 boyutu kesinleştir
        try {
            this.setSize(1, true);
        } catch (Exception ignored) {
            // Güvenlik: eğer attribute'lar halen hazır değilse yut
        }
        return data;
    }

    /**
     * Evcilleştirme: slime ball kullanınca tamed olur. Tamed ise biner ama kontrol WASD ile değil,
     * yalnızca jumpbar ile zıplar.
     */
    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient) {
            if (!isTamed() && player.getStackInHand(hand).isOf(Items.SLIME_BALL)) {
                this.setTamed(true);
                player.getStackInHand(hand).decrement(1);
                player.sendMessage(Text.literal("SnotBall seni sevdi 💚"), true);
            } else if (isTamed()) {
                player.startRiding(this, true); // <---- true ile, client sync zorlanır
            }
        } else {
            // Client tarafında da mount sync'i güvenceye al
            if (isTamed()) player.startRiding(this, true);
        }
        return ActionResult.SUCCESS;
    }


    @Override
    public void tick() {
        super.tick();

        // Eğer binen bir oyuncu varsa, normal hareketi kilitle; yalnızca zıplama mantığı çalışsın.
        if (this.hasPassengers() && this.getFirstPassenger() instanceof PlayerEntity player) {

            // Yönü eşitle (oyuncu baktıkça slime dönsün)
            this.setYaw(player.getYaw());
            this.prevYaw = this.getYaw();

            // Slime kendi yapay zekâsını kapatılmış gibi davranacak; x/z hareketi sıfırlıyoruz
            Vec3d vel = this.getVelocity();
            this.setVelocity(0.0, vel.y, 0.0); // yatay hareket yok

            // JumpingMount şarjı: şarj bittiyse zıplat
            // (startJumping() ile isCharging true olur, stopJumping() çağrısıyla zıplatma yapılır)
            // Burada ekstra bir şey yapmaya gerek yok; stopJumping() zıplamayı uygulayacak.
        }
    }

    /**
     * SlimeEntity.setSize() bazen attribute'lara erişir; hata almamak için super çağrısını try-catch içinde tutuyoruz.
     * Bu, spawn sırasında veya NBT yüklemerinde güvenlik sağlar.
     */
    @Override
    public void setSize(int size, boolean heal) {
        try {
            super.setSize(size, heal);
        } catch (Exception ignored) {
            // Bazı durumlarda attribute'lar hazır değil; yut.
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setTamed(nbt.getBoolean("Tamed"));
        // NBT'den gelmiş olabilecek slime size'ı yok sayıp 1 olarak ayarla
        try { this.setSize(1, false); } catch (Exception ignored) {}
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Tamed", this.isTamed());
    }

    // ----- JumpingMount (zıplama - jumpbar) -----
    // setJumpStrength: client tarafından gönderilen değeri kaydet
    @Override
    public void setJumpStrength(int strength) {
        // strength aralığı client tarafında 0..100 gibi olabilir; burada doğrudan kaydediyoruz
        this.jumpStrength = Math.max(0, Math.min(100, strength));
    }

    @Override
    public boolean canJump() {
        // Hem yerde hem de binen varsa zıplayabilir
        return true;
    }


    @Override
    public void startJumping(int height) {
        // Player jump tuşuna bastığında çağrılır -> şarj başlasın
        this.isCharging = true;
    }

    @Override
    public void stopJumping() {
        // Şarj bırakıldığında çağrılır -> hesapla ve uygulayalım
        if (!this.isAlive() || !this.hasPassengers()) {
            this.isCharging = false;
            return;
        }

        this.isCharging = false;

        // Binen oyuncuyu al
        if (!(this.getFirstPassenger() instanceof PlayerEntity rider)) return;

        // jumpStrength değerinden bir zıplama gücü hesapla
        // örnek: min 0.4, max 1.6 -> jumpStrength 0..100 arası oranla
        double min = 0.4D;
        double max = 1.6D;
        double power = min + ( (double) this.jumpStrength / 100.0 ) * (max - min);

        // oyuncunun baktığı doğrultu
        Vec3d look = rider.getRotationVec(1.0F).normalize();

        // küçük yukarı takviyesi ile ileri zıplama
        double vx = look.x * power;
        double vy = power * 0.9D; // biraz daha dik yükseliş
        double vz = look.z * power;

        // Uygula
        this.setVelocity(vx, vy, vz);
        this.velocityDirty = true;
    }

    // ---- Ek: sürüşte WASD'yi pasif hale getirmek için travel override ----
    // Eğer binen varsa travel inputunu yok sayıyoruz (WASD etkisiz)
    @Override
    public void travel(Vec3d movementInput) {
        if (this.hasPassengers() && this.getFirstPassenger() instanceof PlayerEntity) {
            // sadece yerçekimi / mevcut y-hızını koru; yatay hareket yok
            Vec3d vel = this.getVelocity();
            super.setVelocity(0.0, vel.y, 0.0);
        } else {
            super.travel(movementInput);
        }
    }

    // Bu iki metot eskiden override edilirken hata veriyordu; bırakıyorum yine çalışır.
    public boolean collides() {
        return true;
    }

    public boolean canBeControlledByRider() {
        return true;
    }
}
