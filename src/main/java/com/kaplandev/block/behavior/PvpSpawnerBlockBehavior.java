package com.kaplandev.block.behavior;

import com.kaplandev.item.Items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class PvpSpawnerBlockBehavior implements BlockBehavior {
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
        if (!world.isClient) Block.dropStack(world, pos, new ItemStack(Items.PVP_SPAWNER_ITEM));
    }

}
