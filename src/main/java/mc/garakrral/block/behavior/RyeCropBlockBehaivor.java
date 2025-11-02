package mc.garakrral.block.behavior;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import mc.garakrral.item.ItemType;

import com.kaplanlib.api.behavior.BlockBehavior;

public class RyeCropBlockBehaivor implements BlockBehavior {
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
        if (world.isClient) return;

        int age = state.get(Properties.AGE_7);
        Random random = world.getRandom();

        if (age < 7) {
            // Olgunlaşmamışsa sadece tohum
            Block.dropStack(world, pos, new ItemStack(ItemType.RYE_SEED, 1 + random.nextInt(1)));
        } else {
            // Olgunlaşmışsa ürün + bazen tohum
            Block.dropStack(world, pos, new ItemStack(ItemType.RYE, 1 + random.nextInt(2)));
            Block.dropStack(world, pos, new ItemStack(ItemType.RYE_SEED));
        }

        world.playSound(null, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 0.8f, 1.0f);
    }
}
