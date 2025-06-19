package com.kaplandev.items.ItemsFeatures;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class KalpItem extends Item {
    public KalpItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            StatusEffectInstance current = player.getStatusEffect(StatusEffects.HEALTH_BOOST);
            int duration = 36000; // 30 dakika
            int amplifier = 0;    // seviye 1 (+2 kalp)

            // Aynı efekt varsa, süresini yenile
            if (current != null) {
                player.removeStatusEffect(StatusEffects.HEALTH_BOOST);
            }

            StatusEffectInstance newEffect = new StatusEffectInstance(
                    StatusEffects.HEALTH_BOOST,
                    duration,
                    amplifier,
                    false,
                    true
            );

            player.addStatusEffect(newEffect);
            player.sendMessage(Text.literal("§a+2 Kalp (30 dakika)"), true);
            player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            stack.decrement(1);
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§7Sağ tıklayınca +2 Kalp verir (30 dakika)"));
    }
}
