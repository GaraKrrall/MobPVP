package com.kaplanlib.api.builder;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import static com.kaplanlib.api.builder.BlockBuilder.MOD_ID;


public class GroupItemBuilder {

    private final String name;
    private Item item;
    private RegistryKey<ItemGroup> itemGroup;
    private Item registeredItem;

    private GroupItemBuilder(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    public static GroupItemBuilder create(String name, Item item) {
        return new GroupItemBuilder(name, item);
    }

    public GroupItemBuilder addToGroup(RegistryKey<ItemGroup> group) {
        this.itemGroup = group;
        return this;
    }

    public Item register() {
        registeredItem = Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);

        if (itemGroup != null) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup)
                    .register(entries -> entries.add(registeredItem));
        }

        return registeredItem;
    }

    public Item getRegisteredItem() {
        return registeredItem;
    }

    // Eski metotları da tutmak istersen
    @Deprecated(forRemoval = true)
    public static void AddGroup(Item item, RegistryKey<ItemGroup> tab) {
        ItemGroupEvents.modifyEntriesEvent(tab)
                .register(entries -> entries.add(item));
    }
    @Deprecated(forRemoval = true)
    public static Item BuildItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
    }
}
