package com.kaplandev.entity.goal;


import com.kaplandev.entity.passive.MiniIronGolemEntity;
import net.minecraft.entity.ai.goal.Goal;

import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;

import java.util.EnumSet;
import java.util.List;

@Deprecated
public class GolemHealGoal extends Goal {
    @Deprecated
    private final MiniIronGolemEntity mini;
    private IronGolemEntity target;

    public GolemHealGoal(MiniIronGolemEntity mini) {
        this.mini = mini;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        List<IronGolemEntity> list = mini.getWorld().getEntitiesByClass(
                IronGolemEntity.class,
                mini.getBoundingBox().expand(10.0D),
                g -> g.isAlive() && g.getHealth() < g.getMaxHealth()
        );
        if (!list.isEmpty()) {
            target = list.get(0);
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        mini.getNavigation().startMovingTo(target, 1.2D);
    }

    @Override
    public void tick() {
        if (target == null || !target.isAlive()) return;

        // hedefe tekrar yönel
        mini.getNavigation().startMovingTo(target, 1.2D);

        if (mini.squaredDistanceTo(target) < 2.5D) {
            // 2 kalp iyileştir
            target.heal(4.0F);

            // Efekt ve ses
            ((ServerWorld) mini.getWorld()).spawnParticles(
                    ParticleTypes.HEART,
                    mini.getX(), mini.getY() + 1, mini.getZ(),
                    8, 0.3, 0.3, 0.3, 0.0
            );
            mini.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, 1.0F);

            // Kendini yok et
            mini.discard();
        }
    }

    @Override
    public boolean shouldContinue() {
        return target != null && target.isAlive();
    }
}
