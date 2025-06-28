package com.kaplandev.item.ItemFeatures;

import com.kaplandev.block.Blocks;
import com.kaplandev.entity.EntitiyRegister;
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

public class KalpItemFeatures extends Item {
    public KalpItemFeatures(Settings settings) {
        super(settings);
    }

    // Elmas bloğuna SAĞ TIKLANINCA çağrılan metod
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();

        if (!world.isClient && world.getBlockState(pos).getBlock() == Blocks.CRUDE_ACIDIC_ORE) {
            // Zombi çağır
            BulwarkEntity bulwark = new BulwarkEntity(EntitiyRegister.BULWARK, world);
            bulwark.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
            world.spawnEntity(bulwark);

            // bloğu sil
            world.breakBlock(pos, false);

            // Itemi 1 azalt
            stack.decrement(1);

            // Ses çal
            player.playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    // Havada sağ tıklanınca: +2 kalp verir
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            StatusEffectInstance current = player.getStatusEffect(StatusEffects.HEALTH_BOOST);
            int duration = 36000; // 30 dakika
            int amplifier = 0;    // +2 kalp

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

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§7Sağ tıklayınca +2 Kalp verir (30 dakika)"));
        tooltip.add(Text.literal("§bElmas bloğuna sağ tıklarsan bir zombi çağırır!"));
    }
}
