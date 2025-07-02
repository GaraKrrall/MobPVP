package com.kaplandev.block.util;

import com.kaplandev.block.behavior.BlockBehavior;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockCreator {
    public static Block createCustomBlock(AbstractBlock.Settings settings, BlockBehavior behavior, IntProvider xpProvider) {
        return new ExperienceDroppingBlock(xpProvider, settings) {
            @Override
            protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
                super.onStacksDropped(state, world, pos, tool, dropXp);
                behavior.onStacksDropped(state, world, pos, tool, dropXp);
            }
        };
    }


}
