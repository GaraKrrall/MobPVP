package com.kaplandev.block.behavior;

import com.kaplanlib.api.behavior.BlockBehavior;
import com.kaplandev.entity.EntityType;
import com.kaplandev.entity.block.PvpSpawnerBlockEntity;
import com.kaplandev.item.Items;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PvpSpawnerBlockBehavior extends BlockWithEntity implements BlockBehavior {
    public PvpSpawnerBlockBehavior(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PvpSpawnerBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, EntityType.PVP_SPAWNER, PvpSpawnerBlockEntity::tick);
    }



    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
        if (!world.isClient) Block.dropStack(world, pos, new ItemStack(Items.TEST_ITEM));
    }
    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true; // alt/arka yüzler render edilsin
    }
}
