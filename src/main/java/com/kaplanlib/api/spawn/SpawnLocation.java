package com.kaplanlib.api.spawn;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;


public class SpawnLocation implements net.minecraft.entity.SpawnLocation {
    public static final SpawnLocation ON_GROUND = new SpawnLocation();

    @Override
    public boolean isSpawnPositionOk(WorldView world, BlockPos pos, @Nullable EntityType<?> entityType) {
        // Zombi ve iskeletler gibi sadece üstü boş ve altı sağlam bloksa doğsun
        return world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
    }

    @Override
    public BlockPos adjustPosition(WorldView world, BlockPos pos) {
        return pos;
    }
}
