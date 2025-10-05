package com.kaplandev.build;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class StructureBuilder {

    public static void buildStarterHouse(ServerWorld world, BlockPos pos) {
        int width = 5;
        int height = 4;
        int depth = 5;

        // Zemin
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                world.setBlockState(pos.add(x, 0, z), Blocks.STONE.getDefaultState());
            }
        }

        // Duvarlar ve tavan
        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < depth; z++) {
                    boolean isWall = x == 0 || x == width - 1 || z == 0 || z == depth - 1;
                    if (isWall) {
                        world.setBlockState(pos.add(x, y, z), Blocks.OAK_PLANKS.getDefaultState());
                    }
                }
            }
        }

        // Tavan (roof)
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                world.setBlockState(pos.add(x, height, z), Blocks.STONE.getDefaultState());
            }
        }

        // Kapı açıklığı (ön yüzey z=0)
        world.setBlockState(pos.add(2, 1, 0), Blocks.AIR.getDefaultState());
        world.setBlockState(pos.add(2, 2, 0), Blocks.AIR.getDefaultState());
    }

    public static void buildRuinedArena(ServerWorld world, BlockPos center) {
        int radius = 3;
        Random random = new Random();

        // Zemin
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz <= radius * radius) {
                    BlockPos pos = center.add(dx, 0, dz);
                    world.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
                }
            }
        }

        // Duvarlar (kısmen yıkık)
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int distSq = dx * dx + dz * dz;
                if (distSq >= (radius - 1) * (radius - 1) && distSq <= (radius + 1) * (radius + 1)) {
                    if (random.nextFloat() < 0.6f) { // %60 ihtimalle yıkık
                        continue;
                    }
                    int wallHeight = 1 + random.nextInt(2); // 1–2 blok yüksekliğinde
                    for (int dy = 1; dy <= wallHeight; dy++) {
                        BlockPos pos = center.add(dx, dy, dz);
                        world.setBlockState(pos, Blocks.COBBLESTONE_WALL.getDefaultState());
                    }
                }
            }
        }

        // Ortaya yıkık sütun veya çimen
        if (random.nextBoolean()) {
            world.setBlockState(center.up(1), Blocks.COBBLESTONE_WALL.getDefaultState());
        } else {
            world.setBlockState(center.up(1), Blocks.GRASS_BLOCK.getDefaultState());
        }
    }

}