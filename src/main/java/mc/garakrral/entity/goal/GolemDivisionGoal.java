package mc.garakrral.entity.goal;

import mc.garakrral.entity.boss.TheGreatProtectorGolemEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class GolemDivisionGoal extends Goal {
    private final TheGreatProtectorGolemEntity golem;
    private boolean hasSpawned = false;

    public GolemDivisionGoal(TheGreatProtectorGolemEntity golem) {
        this.golem = golem;
    }

    @Override
    public boolean canStart() {
        if (golem.getWorld().isClient()) return false;
        double health = golem.getHealth();
        return health <= 40.0D && !hasSpawned; // 20 kalp altı
    }

    @Override
    public void start() {
        if (!(golem.getWorld() instanceof ServerWorld serverWorld)) return;

        // 20 kalp altına düştüğünde 2 golem doğur
        spawnIronGolems(serverWorld);
        hasSpawned = true;

        // Ölümde de aynı şeyi yap
        golem.setOnDeath(() -> spawnIronGolems(serverWorld));
    }

    private void spawnIronGolems(ServerWorld serverWorld) {
        BlockPos pos = golem.getBlockPos();

        IronGolemEntity golem1 = EntityType.IRON_GOLEM.create(serverWorld);
        IronGolemEntity golem2 = EntityType.IRON_GOLEM.create(serverWorld);

        if (golem1 != null && golem2 != null) {
            golem1.refreshPositionAndAngles(pos.add(2, 0, 0), golem.getYaw(), 0.0F);
            golem2.refreshPositionAndAngles(pos.add(-2, 0, 0), golem.getYaw(), 0.0F);

            golem1.setPlayerCreated(false);
            golem2.setPlayerCreated(false);

            serverWorld.spawnEntity(golem1);
            serverWorld.spawnEntity(golem2);
        }
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }

    @Override
    public void tick() {
        // Eğer canı yeniden fullenirse tekrar çağırma sıfırlansın
        if (golem.getHealth() >= golem.getMaxHealth()) {
            hasSpawned = false;
        }
    }
}
