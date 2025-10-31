package mc.garakrral.block.behavior;

import mc.garakrral.item.ItemType;
import com.kaplanlib.api.behavior.BlockBehavior;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class DamagedPvpSpawnerMaxBlockBehavior implements BlockBehavior {
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
        if (!world.isClient) {
            // Kalp iteminden 2 adet düşür
            Block.dropStack(world, pos, new ItemStack(ItemType.KALP_ITEM, 4));

            // Ekstra başka bir item daha düşür (örnek: enerji itemi)
            Block.dropStack(world, pos, new ItemStack(net.minecraft.item.Items.DIAMOND, 2));


        }
    }
}
