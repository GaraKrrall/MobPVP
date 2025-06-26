package com.kaplandev.items;

import com.kaplandev.items.blocks.CrudeAcidicLayerRockOre;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CrudeAcidicLayerRockOreItem {

    public static void register() {
        Registry.register(Registries.ITEM, Identifier.of("mobpvp", "ore"),
                new BlockItem(CrudeAcidicLayerRockOre.ORE, new Item.Settings()));
    }
}
