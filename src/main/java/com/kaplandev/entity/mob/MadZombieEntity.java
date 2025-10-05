package com.kaplandev.entity.mob;

import com.kaplandev.entity.boss.BulwarkEntity;
import com.kaplandev.entity.goal.DashAtTargetGoal;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class MadZombieEntity extends ZombieEntity {

    private boolean dashed = false;

    public MadZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createCustomZombieAttributes() {
        return ZombieEntity.createZombieAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35) // Hızlı ama kaçılmaz değil
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0);

    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new DashAtTargetGoal(this, 1.5)); // özel dash
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));

        // Oyuncuya direkt saldırı
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

        // Diğer her şeye saldır, ama kendine, kendi türüne ve boss'a değil
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PathAwareEntity.class, 10, true, true,
                entity -> entity != this
                        && !(entity instanceof MadZombieEntity)
                        && !(entity instanceof BulwarkEntity)
                        && !(entity instanceof MadSkeletonEntity)

        ));
    }


    public boolean hasDashed() {
        return dashed;
    }

    public void setDashed(boolean dashed) {
        this.dashed = dashed;
    }
}
