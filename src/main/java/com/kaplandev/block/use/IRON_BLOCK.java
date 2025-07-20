package com.kaplandev.block.use;

import com.kaplandev.entity.EntityType;
import com.kaplandev.entity.passive.MiniIronGolemEntity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class IRON_BLOCK {

    public static ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        BlockPos pos = hit.getBlockPos();

        if (!world.isClient
                && (stack.isOf(Items.CARVED_PUMPKIN) || stack.isOf(Items.JACK_O_LANTERN))
                && hit.getSide() == Direction.UP
                && world.getBlockState(pos).isOf(Blocks.IRON_BLOCK)
                && world.getBlockState(pos.up()).isAir()

                // SADECE 1 BLOK demir bloğu varsa çalış
                && world.getBlockState(pos.north()).isAir()
                && world.getBlockState(pos.south()).isAir()
                && world.getBlockState(pos.east()).isAir()
                && world.getBlockState(pos.west()).isAir()
        ) {


            if (!player.isCreative()) stack.decrement(1);

            world.breakBlock(pos, false); // 👈 Demir bloğu sil

            BlockPos spawnPos = pos.up();
            for (int i = 0; i < 2; i++) {
                MiniIronGolemEntity mini = new MiniIronGolemEntity(EntityType.MINIGOLEM, world);
                mini.refreshPositionAndAngles(
                        spawnPos.getX() + 0.5,
                        spawnPos.getY(),
                        spawnPos.getZ() + 0.5,
                        world.random.nextFloat() * 360F,
                        0
                );
                world.spawnEntity(mini);
            }

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

}
