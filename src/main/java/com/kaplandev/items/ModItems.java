package com.kaplandev.items;

import com.kaplandev.items.blocks.CrudeAcidicLayerRockOre;
import com.kaplandev.items.tab.TabMobPVP;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static void init() {

        //items
        KalpItem.init();
        a.register();


        //blocks
        CrudeAcidicLayerRockOre.register();

    }

}
