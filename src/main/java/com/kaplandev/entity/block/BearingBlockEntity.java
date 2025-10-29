package com.kaplandev.entity.block;

/*import com.kaplandev.entity.EntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BearingBlockEntity extends BlockEntity {
    private boolean rotating = false;
    private final List<BlockPos> attachedBlocks = new ArrayList<>();

    public BearingBlockEntity(BlockPos pos, BlockState state) {
        super(EntityType.BEARING, pos, state);
    }

    public void startRotation() {
        if (rotating) return;
        rotating = true;

        collectAttachedBlocks();
        convertToEntities();
    }

    public void stopRotation() {
        if (!rotating) return;
        rotating = false;

        restoreBlocks();
    }

    private void collectAttachedBlocks() {
        attachedBlocks.clear();
        // Basit flood fill algoritması — Bearing'e bağlı blokları bulur
        World world = getWorld();
        if (world == null) return;

        BlockPos start = getPos().up();
        floodFill(world, start);
    }

    private void floodFill(World world, BlockPos pos) {
        if (attachedBlocks.contains(pos)) return;
        if (world.isAir(pos)) return;
        attachedBlocks.add(pos);

        for (Direction dir : Direction.values()) {
            floodFill(world, pos.offset(dir));
        }
    }

    private void convertToEntities() {
        if (world == null || world.isClient) return;
        for (BlockPos pos : attachedBlocks) {
            BlockState state = world.getBlockState(pos);
            world.removeBlock(pos, false);

            var entity = new RotatingBlockEntity(world, pos, state);
            world.spawnEntity(entity);

        }
    }

    private void restoreBlocks() {
        if (world == null || world.isClient) return;

        // Entity’leri yok edip blokları geri koy
        for (BlockPos pos : attachedBlocks) {
            // Bu örnekte sadece dummy şekilde geri koyar
            // Gerçek senaryoda entity'den state bilgisi alınabilir
            world.setBlockState(pos, getCachedState());
        }

        attachedBlocks.clear();
    }

    public void dropAttachedBlocks() {
        if (world == null || world.isClient) return;
        attachedBlocks.forEach(p -> world.breakBlock(p, true));
        attachedBlocks.clear();
    }
}
*/