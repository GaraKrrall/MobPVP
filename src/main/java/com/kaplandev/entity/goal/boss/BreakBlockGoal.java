package com.kaplandev.entity.goal.boss;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;

public class BreakBlockGoal extends Goal {
    private final LivingEntity entity;
    private final World world;
    private int breakCooldown = 0;

    public BreakBlockGoal(LivingEntity entity) {
        this.entity = entity;
        this.world = entity.getEntityWorld();
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        BlockPos frontPos = entity.getBlockPos().offset(entity.getHorizontalFacing());
        BlockState blockState = world.getBlockState(frontPos);
        return !blockState.isAir();
    }

    @Override
    public void tick() {
        if (breakCooldown-- > 0) return;

        BlockPos frontPos = entity.getBlockPos().offset(entity.getHorizontalFacing());
        BlockState blockState = world.getBlockState(frontPos);

        if (!blockState.isAir()) {
            world.breakBlock(frontPos, true, entity);
            breakCooldown = 10; // 0.5 saniye bekle
        }
    }
}
