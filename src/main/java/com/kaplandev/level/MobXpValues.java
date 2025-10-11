package com.kaplandev.level;

import com.kaplandev.entity.EntityType;

import java.util.Map;
import java.util.HashMap;


public class MobXpValues {
    private static final Map<net.minecraft.entity.EntityType<?>, Integer> XP_VALUES = new HashMap<>();

    static {
        //BOSS
        XP_VALUES.put(net.minecraft.entity.EntityType.ENDER_DRAGON, 2500);  // Ender Dragon (1 seviye ~50-60)
        XP_VALUES.put(net.minecraft.entity.EntityType.WITHER, 400);        // Wither (1 seviye ~30-40)
        XP_VALUES.put(net.minecraft.entity.EntityType.WARDEN, 250);

        // Hostile Mobs
        XP_VALUES.put(net.minecraft.entity.EntityType.ZOMBIE, 5);
        XP_VALUES.put(net.minecraft.entity.EntityType.SKELETON, 7);
        XP_VALUES.put(net.minecraft.entity.EntityType.CREEPER, 8);
        XP_VALUES.put(net.minecraft.entity.EntityType.ENDERMAN, 15);
        XP_VALUES.put(net.minecraft.entity.EntityType.SPIDER, 6);
        XP_VALUES.put(net.minecraft.entity.EntityType.CAVE_SPIDER, 7);
        XP_VALUES.put(net.minecraft.entity.EntityType.WITCH, 12);
        XP_VALUES.put(net.minecraft.entity.EntityType.BLAZE, 10);
        XP_VALUES.put(net.minecraft.entity.EntityType.GHAST, 20);
        XP_VALUES.put(net.minecraft.entity.EntityType.SLIME, 3);
        XP_VALUES.put(net.minecraft.entity.EntityType.MAGMA_CUBE, 4);
        XP_VALUES.put(net.minecraft.entity.EntityType.PHANTOM, 10);
        XP_VALUES.put(net.minecraft.entity.EntityType.DROWNED, 5);
        XP_VALUES.put(net.minecraft.entity.EntityType.HUSK, 6);
        XP_VALUES.put(net.minecraft.entity.EntityType.STRAY, 8);
        XP_VALUES.put(net.minecraft.entity.EntityType.VEX, 5);
        XP_VALUES.put(net.minecraft.entity.EntityType.VINDICATOR, 10);
        XP_VALUES.put(net.minecraft.entity.EntityType.EVOKER, 15);
        XP_VALUES.put(net.minecraft.entity.EntityType.ILLUSIONER, 15);
        XP_VALUES.put(net.minecraft.entity.EntityType.PILLAGER, 8);
        XP_VALUES.put(net.minecraft.entity.EntityType.RAVAGER, 25);
        XP_VALUES.put(net.minecraft.entity.EntityType.GUARDIAN, 10);
        XP_VALUES.put(net.minecraft.entity.EntityType.ELDER_GUARDIAN, 30);
        XP_VALUES.put(net.minecraft.entity.EntityType.SHULKER, 10);
        XP_VALUES.put(net.minecraft.entity.EntityType.ENDERMITE, 3);
        XP_VALUES.put(net.minecraft.entity.EntityType.SILVERFISH, 3);
        XP_VALUES.put(net.minecraft.entity.EntityType.WITHER_SKELETON, 15);
        XP_VALUES.put(net.minecraft.entity.EntityType.HOGLIN, 10);
        XP_VALUES.put(net.minecraft.entity.EntityType.ZOGLIN, 10);
        XP_VALUES.put(net.minecraft.entity.EntityType.PIGLIN, 5);
        XP_VALUES.put(net.minecraft.entity.EntityType.PIGLIN_BRUTE, 15);
        XP_VALUES.put(net.minecraft.entity.EntityType.ZOMBIFIED_PIGLIN, 5);

        // Neutral Mobs
        XP_VALUES.put(net.minecraft.entity.EntityType.IRON_GOLEM, 10);
        XP_VALUES.put(net.minecraft.entity.EntityType.SNOW_GOLEM, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.BEE, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.DOLPHIN, 3);
        XP_VALUES.put(net.minecraft.entity.EntityType.LLAMA, 3);
        XP_VALUES.put(net.minecraft.entity.EntityType.TRADER_LLAMA, 3);
        XP_VALUES.put(net.minecraft.entity.EntityType.PANDA, 2);
        XP_VALUES.put(net.minecraft.entity.EntityType.POLAR_BEAR, 3);
        XP_VALUES.put(net.minecraft.entity.EntityType.WOLF, 2);
        XP_VALUES.put(net.minecraft.entity.EntityType.FOX, 2);
        XP_VALUES.put(net.minecraft.entity.EntityType.CAT, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.OCELOT, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.PARROT, 1);

        // Passive Mobs
        XP_VALUES.put(net.minecraft.entity.EntityType.COW, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.PIG, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.SHEEP, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.CHICKEN, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.RABBIT, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.TURTLE, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.COD, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.SALMON, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.PUFFERFISH, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.TROPICAL_FISH, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.SQUID, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.GLOW_SQUID, 2);
        XP_VALUES.put(net.minecraft.entity.EntityType.AXOLOTL, 2);
        XP_VALUES.put(net.minecraft.entity.EntityType.BAT, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.MOOSHROOM, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.DONKEY, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.HORSE, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.MULE, 1);
        XP_VALUES.put(net.minecraft.entity.EntityType.SKELETON_HORSE, 5);
        XP_VALUES.put(net.minecraft.entity.EntityType.ZOMBIE_HORSE, 5);
        XP_VALUES.put(net.minecraft.entity.EntityType.STRIDER, 2);
        XP_VALUES.put(net.minecraft.entity.EntityType.GOAT, 2);

        //mobpvp
        XP_VALUES.put(EntityType.BULWARK, 25);
        XP_VALUES.put(EntityType.MAD_SKELETON, 5);
        XP_VALUES.put(EntityType.MAD_ZOMBIE, 1);
        XP_VALUES.put(EntityType.MINIGOLEM, 2);
        XP_VALUES.put(EntityType.MINIGOLEM_COPPER, 4);

    }

    public static int getXp(net.minecraft.entity.EntityType<?> type) {
        return XP_VALUES.getOrDefault(type, 1); // Default: 1 XP for any unlisted entity
    }
}