package com.kaplanlib.api.builder;

import com.kaplanlib.api.behavior.BlockBehavior;
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


public class BlockBuilder<T extends BlockEntity> {

    private final String name;
    private AbstractBlock.Settings settings;
    private BlockBehavior behavior;
    private IntProvider xpProvider;
    private BlockEntityType.BlockEntityFactory<T> entityFactory;
    public static final String MOD_ID = "mobpvp";

    private Block registeredBlock;
    private BlockEntityType<?> registeredEntity;

    private BlockBuilder(String name, AbstractBlock.Settings settings) {
        this.name = name;
        this.settings = settings;
    }

    public static <T extends BlockEntity> BlockBuilder<T> create(String name, AbstractBlock.Settings settings) {
        return new BlockBuilder<>(name, settings);
    }


    public BlockBuilder<T> behavior(BlockBehavior behavior) {
        this.behavior = behavior;
        return this;
    }


    public BlockBuilder<T> xpDrop(IntProvider xpProvider) {
        this.xpProvider = xpProvider;
        return this;
    }

    public BlockBuilder<T> withEntity(BlockEntityType.BlockEntityFactory<T> factory) {
        this.entityFactory = factory;
        return this;
    }

    public Block register() {
        Block block;
        if (xpProvider != null) {
            block = new ExperienceDroppingBlock(xpProvider, settings) {
                @Override
                protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
                    super.onStacksDropped(state, world, pos, tool, dropXp);
                    if (behavior != null) {
                        behavior.onStacksDropped(state, world, pos, tool, dropXp);
                    }
                }
            };
        } else {
            block = new Block(settings) {
                @Override
                protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
                    super.onStacksDropped(state, world, pos, tool, dropXp);
                    if (behavior != null) {
                        behavior.onStacksDropped(state, world, pos, tool, dropXp);
                    }
                }
            };
        }

        Identifier id = Identifier.of(MOD_ID, name);
        registeredBlock = Registry.register(Registries.BLOCK, id, block);

        if (entityFactory != null) {
            registeredEntity = Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    id,
                    BlockEntityType.Builder.create(entityFactory, registeredBlock).build(null)
            );
        }

        return registeredBlock;
    }

    public Block getRegisteredBlock() {
        return registeredBlock;
    }

    public BlockEntityType<?> getRegisteredEntity() {
        return registeredEntity;
    }
    @Deprecated
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
    public static Block RegisterCreatedBlock (String name, Block block) {
        // Bloğu kaydet
        Block registeredBlock = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
        return registeredBlock;
    }
    @Deprecated
    public static Block BuildBlockAttribute(AbstractBlock.Settings settings, BlockBehavior behavior, IntProvider xpProvider) {
        return new ExperienceDroppingBlock(xpProvider, settings) {
            @Override
            protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
                super.onStacksDropped(state, world, pos, tool, dropXp);
                behavior.onStacksDropped(state, world, pos, tool, dropXp);
            }
        };
    }

}
