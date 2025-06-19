package com.kaplandev.entity.zombie.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;

public class CustomDashAtTargetGoal extends Goal {

    private final ZombieEntity zombie;
    private final double dashSpeed;
    private PlayerEntity target;

    public CustomDashAtTargetGoal(ZombieEntity zombie, double dashSpeed) {
        this.zombie = zombie;
        this.dashSpeed = dashSpeed;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (!(zombie instanceof com.kaplandev.entity.zombie.CustomZombieEntity customZombie)) return false;

        this.target = zombie.getTarget() instanceof PlayerEntity player ? player : null;

        return target != null && target.isAlive() && !customZombie.hasDashed()
                && zombie.squaredDistanceTo(target) < 16; // Yakındaysa dash yapsın
    }

    @Override
    public void start() {
        if (zombie instanceof com.kaplandev.entity.zombie.CustomZombieEntity customZombie) {
            customZombie.setDashed(true);
        }

        if (target != null) {
            zombie.getNavigation().startMovingTo(target, dashSpeed);
        }
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }
}
