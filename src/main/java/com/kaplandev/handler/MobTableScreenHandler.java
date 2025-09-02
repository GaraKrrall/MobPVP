package com.kaplandev.handler;


import com.kaplandev.handler.type.ScreenHandlerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class MobTableScreenHandler extends ScreenHandler {
    public MobTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenHandlerTypes.MOB_TABLE, syncId);

        // Slotlar buraya eklenir, şu an boş
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY; // şimdilik boş bırakabilirsin
    }

}