package com.kaplandev.entity.goal.boss;

import com.kaplandev.entity.boss.BulwarkEntity;

import net.minecraft.entity.TntEntity;
import net.minecraft.entity.ai.goal.Goal;

public class TntSpawnGoal extends Goal {
    private final BulwarkEntity boss;
    private int tntCooldown = 0;

    public TntSpawnGoal(BulwarkEntity boss) {
        this.boss = boss;
    }

    @Override
    public boolean canStart() {
        return boss.getTarget() != null;
    }

    @Override
    public void tick() {
        if (tntCooldown <= 0) {
            TntEntity tnt = new TntEntity(boss.getWorld(), boss.getX(), boss.getY(), boss.getZ(), null);
            tnt.setFuse(60);
            tnt.setVelocity(0, 0.1, 0);
            tnt.setNoGravity(true);
            tnt.setInvulnerable(true);

            boss.getWorld().spawnEntity(tnt);

            tntCooldown = 100;
        } else {
            tntCooldown--;
        }
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }

}

