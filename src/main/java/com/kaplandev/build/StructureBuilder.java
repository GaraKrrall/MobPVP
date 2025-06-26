package com.kaplandev.build;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

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
}