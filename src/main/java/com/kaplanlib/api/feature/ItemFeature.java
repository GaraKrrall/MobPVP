package com.kaplanlib.api.feature;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public interface ItemFeature {
    // Elmas bloğuna sağ tıklama davranışı
    ActionResult onUseOnBlock(ItemUsageContext context);

    // Havaya sağ tıklama davranışı
    TypedActionResult<ItemStack> onUse(World world, PlayerEntity player, Hand hand);

    // Tooltip davranışı
    void addTooltip(ItemStack stack, World world, List<Text> tooltip, Item.TooltipContext context);
}
