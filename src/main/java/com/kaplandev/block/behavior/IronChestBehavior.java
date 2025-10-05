package com.kaplandev.block.behavior;

/*import com.kaplandev.block.BlockEntityTypes;
import com.kaplandev.entity.block.IronChestBlockEntity;
import com.kaplandev.api.behavior.BlockBehavior;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IronChestBehavior extends ChestBlock {

    public IronChestBehavior(Settings settings) {
        super(settings, () -> BlockEntityTypes.IRON_CHEST_ENTITY_TYPE); // en az değişiklik

    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof IronChestBlockEntity chest) {
            return chest;
        }
        return null;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IronChestBlockEntity(pos, state);
    }
}
*/