package mc.garakrral.entity.mob;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.CrossbowAttackGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mc.garakrral.sound.SoundType;

public class HunterEntity extends PillagerEntity {
    public HunterEntity(EntityType<? extends PillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createHunterAttributes() {
        return PillagerEntity.createPillagerAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35F)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(3, new CrossbowAttackGoal(this, (double) 1.0F, 8.0F));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MerchantEntity.class, false));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, VillagerEntity.class, false));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, PiglinEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, SheepEntity.class, true));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, ChickenEntity.class, true));
        this.targetSelector.add(8, new ActiveTargetGoal<>(this, CowEntity.class, true));
    }

    @Override
    public boolean canJoinRaid() {
        return true;
    }

    @Override
    public void setPatrolLeader(boolean leader) {
    }

    @Override
    public boolean hasActiveRaid() {
        return this.getRaid() != null && this.getRaid().isActive();
    }

    @Override
    public boolean isPatrolLeader() {
        return false;
    }

    @Override
    public boolean canLead() {
        return false;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundType.WALK_GRASS, 0.15F, 1.0F);
    }
}
