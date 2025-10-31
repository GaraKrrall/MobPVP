package mc.garakrral.entity.goal;

import mc.garakrral.entity.boss.BulwarkEntity;
import mc.garakrral.entity.mob.MiniCopperGolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;

import java.util.EnumSet;
import java.util.List;

public class FollowAndProtectGolemGoal extends Goal {

    private final MiniCopperGolemEntity miniGolem;
    private IronGolemEntity targetGolem; // Asla unutmayacağı golem

    public FollowAndProtectGolemGoal(MiniCopperGolemEntity golem) {
        this.miniGolem = golem;
        this.setControls(EnumSet.of(Control.MOVE, Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (targetGolem == null || !targetGolem.isAlive()) {
            List<IronGolemEntity> golems = miniGolem.getWorld().getEntitiesByClass(
                    IronGolemEntity.class,
                    miniGolem.getBoundingBox().expand(2), // sadece 2 blok yakınındakiler
                    g -> !(g instanceof MiniCopperGolemEntity)
            );

            if (!golems.isEmpty()) {
                targetGolem = golems.get(0); // ilk bulduğu golemi seç
            }
        }
        return targetGolem != null && targetGolem.isAlive();
    }

    @Override
    public boolean shouldContinue() {
        return targetGolem != null && targetGolem.isAlive();
    }

    @Override
    public void tick() {

        List<LivingEntity> enemies = miniGolem.getWorld().getEntitiesByClass(
                LivingEntity.class,
                miniGolem.getBoundingBox().expand(8), // 8 blok içinde bak
                e -> (e instanceof Monster || e instanceof BulwarkEntity) && e.isAlive()
        );

        if (!enemies.isEmpty()) {
            LivingEntity enemy = enemies.get(0);
            miniGolem.setTarget(enemy);
            // Saldırmayı MeleeAttackGoal halleder
            return;
        }

        // Düşman yoksa goleme odaklan
        miniGolem.setTarget(null);

        double distSq = miniGolem.squaredDistanceTo(targetGolem);

        // Eğer çok uzaktaysa (3 bloktan fazla) → yaklaş
        if (distSq > 9.0D) { // 3 blok
            miniGolem.getNavigation().startMovingTo(targetGolem, 1.0D);
        }
        // Eğer yeterince yakınsa (1.5 bloktan az) → dur
        else if (distSq < 2.25D) { // 1.5 blok
            miniGolem.getNavigation().stop();
        }
        // Arada ise → dokunma (olduğu yerde kalır, çakışma yapmaz)
    }

}
