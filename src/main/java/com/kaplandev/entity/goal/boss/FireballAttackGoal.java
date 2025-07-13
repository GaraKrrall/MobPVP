package com.kaplandev.entity.goal.boss;

import com.kaplandev.entity.boss.BulwarkEntity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.entity.EntityType;

import java.util.EnumSet;

public class FireballAttackGoal extends Goal {
    private final BulwarkEntity boss;
    private int cooldown = 0;

    public FireballAttackGoal(BulwarkEntity boss) {
        this.boss = boss;
        this.setControls(EnumSet.of(Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return boss.getTarget() instanceof PlayerEntity;
    }

    @Override
    public void tick() {
        PlayerEntity target = (PlayerEntity) boss.getTarget();
        if (target == null) return;

        World world = boss.getWorld();
        boss.getLookControl().lookAt(target, 30.0f, 30.0f);

        if (cooldown <= 0) {
            Vec3d dir = target.getPos().subtract(boss.getPos()).normalize();


            for (int i = 0; i < 6; i++) {
                FireballEntity fireball = new FireballEntity(
                        EntityType.FIREBALL,
                        world
                );
                fireball.setVelocity(dir.x, dir.y, dir.z, 2.5f, 0.0f);
                fireball.setPosition(boss.getX(), boss.getEyeY(), boss.getZ());

                world.spawnEntity(fireball);
            }



            cooldown = 100;
        } else {
            cooldown--;
        }
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }

}
