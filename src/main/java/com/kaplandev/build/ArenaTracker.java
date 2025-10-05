
package com.kaplandev.build;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class ArenaTracker {
    private static final List<BlockPos> ARENAS = new ArrayList<>();

    public static void add(BlockPos pos) {
        ARENAS.add(pos);
    }

    public static List<BlockPos> getAll() {
        return ARENAS;
    }

    public static BlockPos getNearest(BlockPos from) {
        return ARENAS.stream()
                .min((a, b) -> Double.compare(a.getSquaredDistance(from), b.getSquaredDistance(from)))
                .orElse(null);
    }
}
