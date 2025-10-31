package mc.garakrral.block.behavior;

/*import com.kaplandev.item.ItemType;
import com.kaplanlib.api.behavior.BlockBehavior;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BearingBlockBehaivor extends BlockWithEntity implements BlockBehavior {
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    public BearingBlockBehaivor(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BearingBlockEntity(pos, state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isClient) return;

        boolean powered = world.isReceivingRedstonePower(pos);
        boolean currentlyPowered = state.get(POWERED);

        if (powered != currentlyPowered) {
            world.setBlockState(pos, state.with(POWERED, powered), Block.NOTIFY_ALL);
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof BearingBlockEntity bearing) {
                if (powered) bearing.startRotation();
                else bearing.stopRotation();
            }
        }
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
        if (!world.isClient) Block.dropStack(world, pos, new ItemStack(ItemType.BEARING_ITEM));
    }
}
*/