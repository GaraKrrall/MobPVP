package mc.garakrral.entity.block;

import mc.garakrral.block.BlockType;
import mc.garakrral.entity.EntityType;


import mc.garakrral.entity.mob.MadSkeletonEntity;
import mc.garakrral.entity.mob.MadZombieEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class PvpSpawnerMaxBlockEntity extends BlockEntity {
    private static final int SPAWN_RADIUS = 8;
    private static final int BASE_WAVE_SIZE = 5;
    private static final int MAX_WAVES = 5;
    private static final double WAVE_SCALE_FACTOR = 1.3;

    private int waveCount = 0;
    private final Set<UUID> aliveEntities = new HashSet<>();

    public PvpSpawnerMaxBlockEntity(BlockPos pos, BlockState state) {
        super(EntityType.PVP_SPAWNER_MAX, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PvpSpawnerMaxBlockEntity blockEntity) {
        if (world.isClient || !(world instanceof ServerWorld serverWorld)) return;

        // Yakındaki oyuncular
        List<ServerPlayerEntity> playersNearby = serverWorld.getPlayers(p ->
                p.getBlockPos().isWithinDistance(pos, SPAWN_RADIUS)
        );

        // Eğer hiç oyuncu yoksa veya herkes çok uzaksa → resetle
        Optional<ServerPlayerEntity> nearest = serverWorld.getPlayers().stream()
                .min(Comparator.comparingDouble(p -> p.squaredDistanceTo(Vec3d.ofCenter(pos))));
        if (nearest.isEmpty() || nearest.get().squaredDistanceTo(Vec3d.ofCenter(pos)) > 48 * 48) { // 20 bloktan uzak
            if (blockEntity.waveCount > 0 || !blockEntity.aliveEntities.isEmpty()) {
                blockEntity.waveCount = 0;
                blockEntity.aliveEntities.clear();
                serverWorld.setBlockState(pos, BlockType.PVP_SPAWNER_MAX.getDefaultState()); // geri eski haline dön
            }
            return;
        }


        // Clean up dead entities
        blockEntity.aliveEntities.removeIf(uuid -> {
            Entity e = serverWorld.getEntity(uuid);
            return e == null || !e.isAlive();
        });

        if (!blockEntity.aliveEntities.isEmpty()) return;

        // Max waves reached
        if (blockEntity.waveCount >= MAX_WAVES) {
            serverWorld.setBlockState(pos, BlockType.DAMAGED_PVP_SPAWNER_MAX.getDefaultState());
            return;
        }

        // Spawn wave with increasing difficulty
        int waveSize = (int) (BASE_WAVE_SIZE * Math.pow(WAVE_SCALE_FACTOR, blockEntity.waveCount));
        int mobVariety = Math.min(blockEntity.waveCount + 2, 6); // Increase variety each wave

        for (int i = 0; i < waveSize; i++) {
            BlockPos spawnPos = pos.add(
                    world.getRandom().nextBetween(-3, 3),
                    1,
                    world.getRandom().nextBetween(-3, 3)
            );

            Entity mob = createWaveMob(serverWorld, blockEntity.waveCount, mobVariety);

            if (mob != null) {
                mob.refreshPositionAndAngles(Vec3d.ofCenter(spawnPos), 0, 0);
                // Scale mob health and damage based on wave
                serverWorld.spawnEntity(mob);
                blockEntity.aliveEntities.add(mob.getUuid());
            }
        }

        blockEntity.waveCount++;
    }

    private static Entity createWaveMob(ServerWorld world, int wave, int variety) {
        int choice = world.getRandom().nextInt(variety);

        switch (choice) {
            case 0: return new MadZombieEntity(EntityType.MAD_ZOMBIE, world);
            case 1: return new MadSkeletonEntity(EntityType.MAD_SKELETON, world);
            case 2: return new ZombieEntity(net.minecraft.entity.EntityType.ZOMBIE, world);
            case 3: return new SkeletonEntity(net.minecraft.entity.EntityType.SKELETON, world);
            case 4:
                if (wave > 1) return new CreeperEntity(net.minecraft.entity.EntityType.CREEPER, world);
                else return new ZombieEntity(net.minecraft.entity.EntityType.ZOMBIE, world);
            case 5:
                if (wave > 2) return new SpiderEntity(net.minecraft.entity.EntityType.SPIDER, world);
                else return new SkeletonEntity(net.minecraft.entity.EntityType.SKELETON, world);
            default:
                return wave > 0 ? new MadZombieEntity(EntityType.MAD_ZOMBIE, world)
                        : new ZombieEntity(net.minecraft.entity.EntityType.ZOMBIE, world);
        }
    }

}