package com.kaplandev.entity.block;

import com.kaplandev.entity.EntityType;
import com.kaplandev.inventory.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class MobTableBlockEntity extends BlockEntity implements ImplementedInventory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int ticksRemaining = 0;

    public MobTableBlockEntity(BlockPos pos, BlockState state) {
        super(EntityType.MOB_TABLE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public void startProcessing() {
        if (ticksRemaining <= 0) {
            ticksRemaining = 20 * 60; // 60 saniye
        }
    }

    public boolean isProcessing() {
        return ticksRemaining > 0;
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public void tick() {
        if (ticksRemaining > 0) {
            ticksRemaining--;
            markDirty();
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.readNbt(nbt, lookup);
        Inventories.readNbt(nbt, items, lookup);
        ticksRemaining = nbt.getInt("TicksRemaining");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.writeNbt(nbt, lookup);
        Inventories.writeNbt(nbt, items, lookup);
        nbt.putInt("TicksRemaining", ticksRemaining);
    }
}
