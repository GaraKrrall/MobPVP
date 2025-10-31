package mc.garakrral.entity.goal;

import mc.garakrral.entity.mob.MadZombieEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;

public class DashAtTargetGoal extends Goal {

    private final ZombieEntity zombie;
    private final double dashSpeed;
    private PlayerEntity target;

    public DashAtTargetGoal(ZombieEntity zombie, double dashSpeed) {
        this.zombie = zombie;
        this.dashSpeed = dashSpeed;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (!(zombie instanceof MadZombieEntity customZombie)) return false;

        this.target = zombie.getTarget() instanceof PlayerEntity player ? player : null;

        return target != null && target.isAlive() && !customZombie.hasDashed()
                && zombie.squaredDistanceTo(target) < 16; // Yakındaysa dash yapsın
    }

    @Override
    public void start() {
        if (zombie instanceof MadZombieEntity customZombie) {
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
