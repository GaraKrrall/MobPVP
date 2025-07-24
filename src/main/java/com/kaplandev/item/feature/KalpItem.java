package com.kaplandev.item.feature;

import com.kaplandev.block.Blocks;
import com.kaplandev.entity.EntityType;
import com.kaplandev.entity.boss.BulwarkEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.world.World;

import java.util.List;

public class KalpItem extends Item implements ItemFeature {
    public KalpItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return onUseOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        return onUse(world, player, hand);
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        addTooltip(stack, world, tooltip, context);
    }

    @Override
    public ActionResult onUseOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();

        if (!world.isClient && world.getBlockState(pos).getBlock() == Blocks.CRUDE_ACIDIC_ORE) {
            BulwarkEntity bulwark = new BulwarkEntity(EntityType.BULWARK, world);
            bulwark.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
            world.spawnEntity(bulwark);

            world.breakBlock(pos, false);
            stack.decrement(1);
            player.playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> onUse(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            StatusEffectInstance current = player.getStatusEffect(StatusEffects.HEALTH_BOOST);
            int duration = 36000;
            int amplifier = 0;

            if (current != null) {
                player.removeStatusEffect(StatusEffects.HEALTH_BOOST);
            }

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, duration, amplifier, false, true));
            player.sendMessage(Text.literal("§a+2 Kalp (30 dakika)"), true);
            player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            stack.decrement(1);
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void addTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§7Sağ tıklayınca +2 Kalp verir (30 dakika)"));
        tooltip.add(Text.literal("§bElmas bloğuna sağ tıklarsan bir zombi çağırır!"));
    }
}
