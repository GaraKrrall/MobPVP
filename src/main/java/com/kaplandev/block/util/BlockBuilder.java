package com.kaplandev.block.util;

import com.kaplandev.block.BlockEntityTypes;
import com.kaplandev.block.behavior.BlockBehavior;

import com.kaplandev.block.behavior.IronChestBehavior;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;

import static com.kaplandev.mobpvp.MOD_ID;

public class BlockBuilder {
    public static BlockEntityType<?> registeredEntity;
    public static Block registeredBlock;

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
    public static <T extends BlockEntity> Block RegisterCreatedBlockWithEntity(
            String name,
            Block block,
            BlockEntityType.BlockEntityFactory<T> factory
    ) {
        // 1. Bloğu kaydet
        Identifier id = Identifier.of(MOD_ID, name);
        Block registeredBlock = Registry.register(Registries.BLOCK, id, block);

        // 2. BlockEntityType oluştur ve kaydet
        BlockEntityType<T> blockEntityType = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                id,
                BlockEntityType.Builder.create(factory, registeredBlock).build(null)
        );

        return registeredBlock;
    }

    @Deprecated
    public static <T extends BlockEntity> Block RegisterCreatedBlockWithEntityType2(
            String name,
            Block block,
            BlockEntityType.BlockEntityFactory<T> factory
    ) {
        Identifier id = Identifier.of(MOD_ID, name);

        registeredBlock = Registry.register(Registries.BLOCK, id, block);
        registeredEntity = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                id,
                BlockEntityType.Builder.create(factory, registeredBlock).build(null)
        );

        return registeredBlock;

    }


}
