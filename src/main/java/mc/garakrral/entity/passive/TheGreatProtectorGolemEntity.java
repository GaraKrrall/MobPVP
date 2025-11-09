package mc.garakrral.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import mc.garakrral.entity.goal.GolemDivisionGoal;
import mc.garakrral.sound.SoundType;

public class TheGreatProtectorGolemEntity extends MiniIronGolemEntity {
    private Runnable onDeathCallback;

    public TheGreatProtectorGolemEntity(EntityType<? extends MiniIronGolemEntity> type, World world) {
        super(type, world);
        this.setPlayerCreated(false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return IronGolemEntity.createIronGolemAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 250.0D) // 125 kalp
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 25.0D); // güçlü vurur
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new GolemDivisionGoal(this)); // özel hedefimiz
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new WanderNearTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.add(4, new WanderAroundPointOfInterestGoal(this, 0.6D, false));
        this.goalSelector.add(5, new IronGolemWanderAroundGoal(this, 0.6D));
        this.goalSelector.add(6, new IronGolemLookGoal(this));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackIronGolemTargetGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false,
                (LivingEntity entity) -> entity instanceof Monster));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, CreeperEntity.class, true));
        this.targetSelector.add(6, new UniversalAngerGoal<>(this, false));
    }

    // ölüm anı geri çağırması
    public void setOnDeath(Runnable runnable) {
        this.onDeathCallback = runnable;
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (this.onDeathCallback != null && !this.getWorld().isClient()) {
            this.onDeathCallback.run();
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundType.WALK_GRASS, 0.15F, 1.0F);
    }
}
