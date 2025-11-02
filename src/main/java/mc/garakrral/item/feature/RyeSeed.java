package mc.garakrral.item.feature;

import mc.garakrral.block.BlockType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RyeSeed extends Item {
    public RyeSeed(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos().up();

        // sadece farmland üzerinde çalışsın
        if (!world.getBlockState(context.getBlockPos()).isOf(net.minecraft.block.Blocks.FARMLAND)) {
            return ActionResult.PASS;
        }

        if (!world.isClient) {
            world.setBlockState(pos, BlockType.RYE_CROP.getDefaultState());
            world.playSound(null, pos, SoundEvents.ITEM_CROP_PLANT, SoundCategory.BLOCKS, 1.0f, 1.0f);
            context.getStack().decrement(1);
        }

        return ActionResult.SUCCESS;
    }
}
