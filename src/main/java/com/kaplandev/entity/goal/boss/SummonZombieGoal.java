package com.kaplandev.entity.goal.boss;

import com.kaplandev.entity.EntitiyRegister;
import com.kaplandev.entity.boss.BulwarkEntity;
import com.kaplandev.entity.mob.MadZombieEntity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class SummonZombieGoal extends Goal {
    private final BulwarkEntity boss;
    private int cooldown = 0;

    public SummonZombieGoal(BulwarkEntity boss) {
        this.boss = boss;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return boss.getTarget() != null;
    }

    @Override
    public void tick() {
        if (cooldown <= 0 && !boss.getWorld().isClient) {
            ServerWorld world = (ServerWorld) boss.getWorld();

            for (int i = 0; i < 6; i++) {
                MadZombieEntity customZombieEntity = new MadZombieEntity(EntitiyRegister.MAD_ZOMBIE, world);
                BlockPos spawnPos = boss.getBlockPos().add(boss.getRandom().nextInt(3) - 1, 0, boss.getRandom().nextInt(3) - 1);
                customZombieEntity.refreshPositionAndAngles(spawnPos, 0.0f, 0.0f);
                world.spawnEntity(customZombieEntity);
            }



            cooldown = 100; // 5 saniye
        } else {
            cooldown--;
        }
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }
}
