package com.kaplandev.block.util;

import com.kaplandev.block.behavior.BlockBehavior;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;

import static com.kaplandev.mobpvp.MOD_ID;

public class BlockBuilder {
    public static Block BuildBlockAttribute(AbstractBlock.Settings settings, BlockBehavior behavior, IntProvider xpProvider) {
        return new ExperienceDroppingBlock(xpProvider, settings) {
            @Override
            protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
                super.onStacksDropped(state, world, pos, tool, dropXp);
                behavior.onStacksDropped(state, world, pos, tool, dropXp);
            }
        };
    }
    public static Block RegisterCreatedBlock (String name, Block block) {
        // Bloğu kaydet
        Block registeredBlock = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
        return registeredBlock;
    }
}
