package mc.garakrral.entity.block;

import mc.garakrral.block.BlockType;
import mc.garakrral.entity.EntityType;
import mc.garakrral.entity.mob.MadSkeletonEntity;
import mc.garakrral.entity.mob.MadZombieEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

public class PvpSpawnerBlockEntity extends BlockEntity {
    private static final int SPAWN_RADIUS = 5;
    private static final int WAVE_SIZE = 5;
    private static final int MAX_WAVES = 3;

    private int waveCount = 0;
    private final Set<UUID> aliveEntities = new HashSet<>();

    public PvpSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(EntityType.PVP_SPAWNER, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PvpSpawnerBlockEntity blockEntity) {
        if (world.isClient || !(world instanceof ServerWorld serverWorld)) return;

        // Yakındaki oyuncular
        List<ServerPlayerEntity> playersNearby = serverWorld.getPlayers(p ->
                p.getBlockPos().isWithinDistance(pos, SPAWN_RADIUS)
        );

        // Eğer hiç oyuncu yoksa veya herkes çok uzaksa → resetle
        Optional<ServerPlayerEntity> nearest = serverWorld.getPlayers().stream()
                .min(Comparator.comparingDouble(p -> p.squaredDistanceTo(Vec3d.ofCenter(pos))));
        if (nearest.isEmpty() || nearest.get().squaredDistanceTo(Vec3d.ofCenter(pos)) > 48 * 48) {// 48 bloktan uzak
            if (blockEntity.waveCount > 0 || !blockEntity.aliveEntities.isEmpty()) {
                blockEntity.waveCount = 0;
                blockEntity.aliveEntities.clear();
                serverWorld.setBlockState(pos, BlockType.PVP_SPAWNER.getDefaultState()); // geri eski haline dön
            }
            return;
        }

        // Ölüleri temizle
        blockEntity.aliveEntities.removeIf(uuid -> {
            Entity e = serverWorld.getEntity(uuid);
            return e == null || !e.isAlive();
        });

        if (!blockEntity.aliveEntities.isEmpty()) return;

        // Dalga sınırı dolduysa
        if (blockEntity.waveCount >= MAX_WAVES) {
            serverWorld.setBlockState(pos, BlockType.DAMAGED_PVP_SPAWNER.getDefaultState());
            return;
        }

        for (int i = 0; i < WAVE_SIZE; i++) {
            BlockPos spawnPos = pos.add(world.getRandom().nextBetween(-2, 2), 1, world.getRandom().nextBetween(-2, 2));
            Entity mob = switch (world.getRandom().nextInt(3)) {
                case 0 -> new MadZombieEntity(EntityType.MAD_ZOMBIE, serverWorld);
                case 1 -> new MadSkeletonEntity(EntityType.MAD_SKELETON, serverWorld);
                default -> new ZombieEntity(net.minecraft.entity.EntityType.ZOMBIE, serverWorld);
            };

            if (mob != null) {
                mob.refreshPositionAndAngles(Vec3d.ofCenter(spawnPos), 0, 0);
                serverWorld.spawnEntity(mob);
                blockEntity.aliveEntities.add(mob.getUuid());
            }
        }

        blockEntity.waveCount++;
    }
}