package com.kaplandev.item.feature;

import com.kaplandev.block.Blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class UltraHeathItem extends Item implements ItemFeature {
    public UltraHeathItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    @Override
    public ActionResult onUseOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        return onUse(world, player, hand);
    }

    @Override
    public TypedActionResult<ItemStack> onUse(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) return TypedActionResult.pass(player.getStackInHand(hand));

        ItemStack stack = player.getStackInHand(hand);
        BlockPos center = player.getBlockPos();
        int radius = 2;

        List<BlockPos> circlePositions = getCirclePositions(center, radius);

        // Uygunluk kontrolü
        for (BlockPos pos : circlePositions) {
            BlockPos below = pos;
            BlockPos above = pos.up();

            if (!world.getBlockState(below).isAir() || !world.getBlockState(above).isAir()) {
                player.sendMessage(Text.translatable("error.mobpvp.alan"), true);
                return TypedActionResult.fail(stack);
            }

            BlockPos ground = below.down();
            if (world.getBlockState(ground).isAir()) {
                player.sendMessage(Text.translatable("error.mobpvp.block"), true);
                return TypedActionResult.fail(stack);
            }
        }

        // Blok yerleştir
        for (BlockPos pos : circlePositions) {
            world.setBlockState(pos, net.minecraft.block.Blocks.SMOOTH_STONE.getDefaultState()); // Alt blok
            world.setBlockState(pos.up(), Blocks.PVP_SPAWNER.getDefaultState()); // Üst blok
        }

        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE, player.getSoundCategory(), 1f, 1f);
        stack.decrement(1);
        return TypedActionResult.success(stack);
    }

    private List<BlockPos> getCirclePositions(BlockPos center, int radius) {
        List<BlockPos> positions = new ArrayList<>();
        int cx = center.getX();
        int cy = center.getY();
        int cz = center.getZ();

        BlockPos doorPos = new BlockPos(cx + radius, cy, cz); // Doğu yönünde kapı

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double distance = Math.sqrt(x * x + z * z);
                if (distance >= radius - 0.5 && distance <= radius + 0.5) {
                    BlockPos pos = new BlockPos(cx + x, cy, cz + z);
                    if (pos.equals(doorPos)) continue; // Kapı boşluğu
                    positions.add(pos);
                }
            }
        }
        return positions;
    }




    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        addTooltip(stack, world, tooltip, context);
    }

    @Override
    public void addTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        // Boş bırakabilirsin veya info yazabilirsin
    }
}
