package mc.garakrral.event.craft;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mc.garakrral.block.BlockType;

public class IRON_TO_INDUSTRIAL {

    public static ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        // Client tarafında çalışmasın
        if (world.isClient()) return ActionResult.PASS;

        // Sadece server player kullanacağız (güvenli dönüşüm)
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return ActionResult.PASS;
        }

        BlockPos pos = hitResult.getBlockPos();

        // Demir blok değilse işlem yapılmaz
        if (!world.getBlockState(pos).isOf(Blocks.IRON_BLOCK)) {
            return ActionResult.PASS;
        }

        // Oyuncunun elinde demir ingot yoksa işlem olmaz
        if (!serverPlayer.getStackInHand(hand).isOf(Items.IRON_INGOT)) {
            return ActionResult.PASS;
        }

        // Iron ingot düşür
        serverPlayer.getStackInHand(hand).decrement(1);

        // Demir bloğu başka bir bloğa çevir (örnek: altın blok)
        world.setBlockState(pos, BlockType.INDUSTRIAL_OVEN_BLOCK.getDefaultState());

        return ActionResult.SUCCESS;
    }
}
