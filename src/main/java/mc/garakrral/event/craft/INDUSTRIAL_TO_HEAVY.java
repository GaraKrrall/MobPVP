package mc.garakrral.event.craft;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mc.garakrral.block.BlockType;

public class INDUSTRIAL_TO_HEAVY {
    public static ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (world.isClient()) return ActionResult.PASS;
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return ActionResult.PASS;
        }
        BlockPos pos = hitResult.getBlockPos();
        if (!world.getBlockState(pos).isOf(BlockType.INDUSTRIAL_OVEN_BLOCK)) {
            return ActionResult.PASS;
        }
        if (!(serverPlayer.getStackInHand(hand).getItem() instanceof AxeItem)) {
            return ActionResult.PASS;
        }
        world.setBlockState(pos, BlockType.HEAVY_CRUSHER_HEAD.getDefaultState());
        return ActionResult.SUCCESS;
    }
}
