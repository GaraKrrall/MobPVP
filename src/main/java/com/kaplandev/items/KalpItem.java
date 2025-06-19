package com.kaplandev.items;

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
import net.minecraft.util.Identifier;
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
            // 30 dakika = 30 * 60 * 20 = 36000 tick
            StatusEffectInstance effect = new StatusEffectInstance(
                    StatusEffects.HEALTH_BOOST, // ekstra kalp verir
                    36000,                      // 30 dakika
                    0,                          // seviye 1 (+4 sağlık = 2 kalp)
                    false,
                    true
            );

            player.addStatusEffect(effect);
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
