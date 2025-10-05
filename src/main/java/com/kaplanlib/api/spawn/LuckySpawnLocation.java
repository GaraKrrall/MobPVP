package com.kaplanlib.api.spawn;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@Deprecated(forRemoval = true)
public class LuckySpawnLocation implements net.minecraft.entity.SpawnLocation {
    public static final SpawnLocation LUCKY_SPAWN = new SpawnLocation();
    private static final Random random = new Random();

    @Deprecated
    @Override
    public boolean isSpawnPositionOk(WorldView world, BlockPos pos, @Nullable EntityType<?> entityType) {
        // Alt blok sağlam mı?
        boolean solidGround = world.getBlockState(pos.down()).isSolidBlock(world, pos.down());

        // %10 şans (1/10)
        boolean lucky = random.nextInt(10) == 0;

        return solidGround && lucky;
    }
    @Deprecated
    @Override
    public BlockPos adjustPosition(WorldView world, BlockPos pos) { return pos; }


}