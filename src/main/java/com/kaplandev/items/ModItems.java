package com.kaplandev.items;

import com.kaplandev.items.blocks.CrudeAcidicLayerRockOre;
import com.kaplandev.items.eggs.SkeletonEggItem;
import com.kaplandev.items.eggs.ZombieEggItem;

public class ModItems {

    public static void init() {

        //items
        KalpItem.init();
        CrudeAcidicLayerRockOreItem.register();

        //eggs
        SkeletonEggItem.register();
        ZombieEggItem.register();


        //blocks
        CrudeAcidicLayerRockOre.register();

    }

}
