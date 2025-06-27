package com.kaplandev.build;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class IsGrass {
    public static boolean isValidGrassPlatform(ServerWorld world, BlockPos center, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos checkPos = center.add(dx, -1, dz); // altındaki blok
                if (!world.getBlockState(checkPos).isOf(Blocks.GRASS_BLOCK)) {
                    return false;
                }
            }
        }
        return true;
    }

}
