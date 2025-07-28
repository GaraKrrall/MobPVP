package com.kaplandev.entity.block;


import com.kaplandev.block.BlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;

public class IronChestBlockEntity extends ChestBlockEntity {
    public IronChestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypes.IRON_CHEST_ENTITY_TYPE, pos, state); // kendi BlockEntityType'ını burada veriyorsun
    }
}

