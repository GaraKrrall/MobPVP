package mc.garakrral.block.behavior;

import mc.garakrral.entity.EntityType;
import mc.garakrral.entity.block.UpgradedHopperBlockEntity;
import mc.garakrral.item.ItemType;
import com.kaplanlib.api.behavior.BlockBehavior;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class UpgradedHopperBlockBehavior extends BlockWithEntity implements BlockBehavior {
    public static final DirectionProperty FACING = Properties.FACING;

    public UpgradedHopperBlockBehavior(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.DOWN));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new UpgradedHopperBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getSide().getOpposite());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
        if (!world.isClient) Block.dropStack(world, pos, new ItemStack(ItemType.UPGREADED_HOPPER_ITEM));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkEntityType(type, EntityType.UPGREADED_HOPPER, UpgradedHopperBlockEntity::tick);
    }

    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkEntityType(BlockEntityType<A> given, BlockEntityType<E> expected, BlockEntityTicker<? super E> ticker) {
        // Klasik Fabric helper fonksiyonu
        return expected == given ? (BlockEntityTicker<A>) ticker : null;
    }
}
