package com.kaplandev.entity.passive;


import com.kaplandev.entity.goal.FindIngotAndHealGolemGoal;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiniIronGolemEntity extends IronGolemEntity {

    public MiniIronGolemEntity(EntityType<? extends IronGolemEntity> type, World world) {
        super(type, world);
        this.setPlayerCreated(true);               // NPC say, köylülere saldırmasın
    }

    /** İstatistikler */
    public static DefaultAttributeContainer.Builder createAttributes() {
        return IronGolemEntity.createIronGolemAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)     // 5  ❤️
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D);  // Saldırı yapmasın
    }

    /** SADECE ihtiyaç duyduğumuz hedefler */
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FindIngotAndHealGolemGoal(this));  // Yeni eklenen goal
        //this.goalSelector.add(2, new GolemHealGoal(this));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.6D));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }


    /** Adım sesi */
    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, 0.15F, 1.0F);
    }
}
