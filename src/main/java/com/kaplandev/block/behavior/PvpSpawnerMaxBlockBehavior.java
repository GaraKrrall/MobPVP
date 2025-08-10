package com.kaplandev.block.behavior;

import com.kaplandev.api.behavior.BlockBehavior;
import com.kaplandev.entity.EntityType;
import com.kaplandev.entity.block.PvpSpawnerMaxBlockEntity;

import com.kaplandev.item.Items;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PvpSpawnerMaxBlockBehavior extends BlockWithEntity implements BlockBehavior {
    public PvpSpawnerMaxBlockBehavior(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PvpSpawnerMaxBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : BlockWithEntity.validateTicker(type, EntityType.PVP_SPAWNER_MAX, PvpSpawnerMaxBlockEntity::tick);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null; // geçici çözüm — crash riskine dikkat
    }
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp){
        if (!world.isClient) Block.dropStack(world, pos, new ItemStack(Items.TEST_ITEM));
    }
}
