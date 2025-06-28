package com.kaplandev.build;



import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ArenaFeature extends Feature<DefaultFeatureConfig> {

    public ArenaFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos origin = context.getOrigin();
        WorldAccess world = context.getWorld();
        Random random = context.getRandom();

        int radius = 10;

        // Zemin
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz <= radius * radius) {
                    BlockPos pos = origin.add(dx, 0, dz);
                    float r = random.nextFloat();
                    if (r < 0.15f)
                        world.setBlockState(pos, Blocks.CRACKED_STONE_BRICKS.getDefaultState(), 3);
                    else if (r < 0.3f)
                        world.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3);
                    else if (r < 0.4f)
                        world.setBlockState(pos, Blocks.MOSS_BLOCK.getDefaultState(), 3);
                    else
                        world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState(), 3);
                }
            }
        }

        // Çimen yayılımı (doğal görünüm)
        for (int i = 0; i < 8; i++) {
            int seedX = origin.getX() + random.nextBetween(-radius + 2, radius - 2);
            int seedZ = origin.getZ() + random.nextBetween(-radius + 2, radius - 2);

            for (int j = 0; j < 3 + random.nextInt(3); j++) {
                int offsetX = seedX + random.nextBetween(-1, 1);
                int offsetZ = seedZ + random.nextBetween(-1, 1);
                BlockPos pos = new BlockPos(offsetX, origin.getY() + 1, offsetZ);

                if (world.getBlockState(pos.down()).isOpaque()) {
                    float variant = random.nextFloat();
                    if (variant < 0.2f)
                        world.setBlockState(pos, Blocks.TALL_GRASS.getDefaultState(), 3);
                    else if (variant < 0.4f)
                        world.setBlockState(pos, Blocks.LARGE_FERN.getDefaultState(), 3);
                    else if (variant < 0.6f)
                        world.setBlockState(pos, Blocks.FERN.getDefaultState(), 3);
                    else
                        world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState(), 3);
                }
            }
        }

        // Duvarlar
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int distSq = dx * dx + dz * dz;
                if (distSq >= (radius - 1) * (radius - 1) && distSq <= (radius + 1) * (radius + 1)) {
                    if (random.nextFloat() < 0.4f) continue;
                    int wallHeight = 2 + random.nextInt(2);
                    for (int dy = 1; dy <= wallHeight; dy++) {
                        BlockPos pos = origin.add(dx, dy, dz);
                        float r = random.nextFloat();
                        if (r < 0.33f)
                            world.setBlockState(pos, Blocks.COBBLESTONE_WALL.getDefaultState(), 3);
                        else if (r < 0.66f)
                            world.setBlockState(pos, Blocks.MOSSY_COBBLESTONE_WALL.getDefaultState(), 3);
                        else
                            world.setBlockState(pos, Blocks.STONE_BRICK_WALL.getDefaultState(), 3);
                    }

                    if (random.nextFloat() < 0.2f) {
                        world.setBlockState(origin.add(dx, wallHeight + 1, dz), Blocks.VINE.getDefaultState(), 3);
                    }
                }
            }
        }

        // Enkaz (kırık sütun parçaları)
        for (int i = 0; i < 10; i++) {
            int dx = random.nextBetween(-radius + 2, radius - 2);
            int dz = random.nextBetween(-radius + 2, radius - 2);
            if (Math.sqrt(dx * dx + dz * dz) < radius - 2) {
                int height = 1 + random.nextInt(2);
                for (int dy = 1; dy <= height; dy++) {
                    BlockPos pos = origin.add(dx, dy, dz);
                    if (random.nextFloat() < 0.5f)
                        world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState(), 3);
                    else
                        world.setBlockState(pos, Blocks.POLISHED_ANDESITE.getDefaultState(), 3);
                }
            }
        }

        // --- YÜKSEK SÜTUN (5 blok boyunda, yerin üstünde) ---

        int columnHeight = 5;

        for (int i = 0; i < columnHeight; i++) {
            BlockPos pos = origin.up(i);
            if (i == columnHeight - 1) {
                world.setBlockState(pos, Blocks.CHISELED_POLISHED_BLACKSTONE.getDefaultState(), 3);
            } else if (i == 0) {
                world.setBlockState(pos, Blocks.DEEPSLATE_BRICKS.getDefaultState(), 3);
            } else if (i == 1 || i == 2) {
                world.setBlockState(pos, Blocks.POLISHED_DEEPSLATE.getDefaultState(), 3);
            } else {
                world.setBlockState(pos, Blocks.CHISELED_STONE_BRICKS.getDefaultState(), 3);
            }
        }

        // Üst platform 3x3 taban
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos topPlate = origin.up(columnHeight - 1).add(dx, 1, dz);
                if (dx == 0 && dz == 0) continue; // Ortaya cevher gelecek
                world.setBlockState(topPlate, Blocks.POLISHED_ANDESITE.getDefaultState(), 3);
            }
        }

        // Özel cevher bloğu en üste
        BlockPos orePos = origin.up(columnHeight);
        world.setBlockState(orePos, com.kaplandev.block.Blocks.CRUDE_ACIDIC_ORE.getDefaultState(), 3);

        // Yaratıklar (Zombi + İskelet)
        if (world instanceof ServerWorld serverWorld) {
            BlockPos spawn1 = origin.add(5, 1, 0);
            BlockPos spawn2 = origin.add(-5, 1, 0);

            ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, serverWorld);
            zombie.refreshPositionAndAngles(spawn1, 0.0f, 0.0f);
            serverWorld.spawnEntity(zombie);

            SkeletonEntity skeleton = new SkeletonEntity(EntityType.SKELETON, serverWorld);
            skeleton.refreshPositionAndAngles(spawn2, 0.0f, 0.0f);
            serverWorld.spawnEntity(skeleton);
        }

        ArenaTracker.add(origin);
        System.out.println("Arena üretildi: " + origin);
        return true;
    }
}
