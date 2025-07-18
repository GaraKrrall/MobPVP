package com.kaplandev.level;

import net.minecraft.entity.EntityType;
import java.util.Map;
import java.util.HashMap;

import com.kaplandev.entity.EntitiyRegister;

public class MobXpValues {
    private static final Map<EntityType<?>, Integer> XP_VALUES = new HashMap<>();

    static {
        //BOSS
        XP_VALUES.put(EntityType.ENDER_DRAGON, 2500);  // Ender Dragon (1 seviye ~50-60)
        XP_VALUES.put(EntityType.WITHER, 400);        // Wither (1 seviye ~30-40)
        XP_VALUES.put(EntityType.WARDEN, 250);

        // Hostile Mobs
        XP_VALUES.put(EntityType.ZOMBIE, 5);
        XP_VALUES.put(EntityType.SKELETON, 7);
        XP_VALUES.put(EntityType.CREEPER, 8);
        XP_VALUES.put(EntityType.ENDERMAN, 15);
        XP_VALUES.put(EntityType.SPIDER, 6);
        XP_VALUES.put(EntityType.CAVE_SPIDER, 7);
        XP_VALUES.put(EntityType.WITCH, 12);
        XP_VALUES.put(EntityType.BLAZE, 10);
        XP_VALUES.put(EntityType.GHAST, 20);
        XP_VALUES.put(EntityType.SLIME, 3);
        XP_VALUES.put(EntityType.MAGMA_CUBE, 4);
        XP_VALUES.put(EntityType.PHANTOM, 10);
        XP_VALUES.put(EntityType.DROWNED, 5);
        XP_VALUES.put(EntityType.HUSK, 6);
        XP_VALUES.put(EntityType.STRAY, 8);
        XP_VALUES.put(EntityType.VEX, 5);
        XP_VALUES.put(EntityType.VINDICATOR, 10);
        XP_VALUES.put(EntityType.EVOKER, 15);
        XP_VALUES.put(EntityType.ILLUSIONER, 15);
        XP_VALUES.put(EntityType.PILLAGER, 8);
        XP_VALUES.put(EntityType.RAVAGER, 25);
        XP_VALUES.put(EntityType.GUARDIAN, 10);
        XP_VALUES.put(EntityType.ELDER_GUARDIAN, 30);
        XP_VALUES.put(EntityType.SHULKER, 10);
        XP_VALUES.put(EntityType.ENDERMITE, 3);
        XP_VALUES.put(EntityType.SILVERFISH, 3);
        XP_VALUES.put(EntityType.WITHER_SKELETON, 15);
        XP_VALUES.put(EntityType.HOGLIN, 10);
        XP_VALUES.put(EntityType.ZOGLIN, 10);
        XP_VALUES.put(EntityType.PIGLIN, 5);
        XP_VALUES.put(EntityType.PIGLIN_BRUTE, 15);
        XP_VALUES.put(EntityType.ZOMBIFIED_PIGLIN, 5);

        // Neutral Mobs
        XP_VALUES.put(EntityType.IRON_GOLEM, 10);
        XP_VALUES.put(EntityType.SNOW_GOLEM, 1);
        XP_VALUES.put(EntityType.BEE, 1);
        XP_VALUES.put(EntityType.DOLPHIN, 3);
        XP_VALUES.put(EntityType.LLAMA, 3);
        XP_VALUES.put(EntityType.TRADER_LLAMA, 3);
        XP_VALUES.put(EntityType.PANDA, 2);
        XP_VALUES.put(EntityType.POLAR_BEAR, 3);
        XP_VALUES.put(EntityType.WOLF, 2);
        XP_VALUES.put(EntityType.FOX, 2);
        XP_VALUES.put(EntityType.CAT, 1);
        XP_VALUES.put(EntityType.OCELOT, 1);
        XP_VALUES.put(EntityType.PARROT, 1);

        // Passive Mobs
        XP_VALUES.put(EntityType.COW, 1);
        XP_VALUES.put(EntityType.PIG, 1);
        XP_VALUES.put(EntityType.SHEEP, 1);
        XP_VALUES.put(EntityType.CHICKEN, 1);
        XP_VALUES.put(EntityType.RABBIT, 1);
        XP_VALUES.put(EntityType.TURTLE, 1);
        XP_VALUES.put(EntityType.COD, 1);
        XP_VALUES.put(EntityType.SALMON, 1);
        XP_VALUES.put(EntityType.PUFFERFISH, 1);
        XP_VALUES.put(EntityType.TROPICAL_FISH, 1);
        XP_VALUES.put(EntityType.SQUID, 1);
        XP_VALUES.put(EntityType.GLOW_SQUID, 2);
        XP_VALUES.put(EntityType.AXOLOTL, 2);
        XP_VALUES.put(EntityType.BAT, 1);
        XP_VALUES.put(EntityType.MOOSHROOM, 1);
        XP_VALUES.put(EntityType.DONKEY, 1);
        XP_VALUES.put(EntityType.HORSE, 1);
        XP_VALUES.put(EntityType.MULE, 1);
        XP_VALUES.put(EntityType.SKELETON_HORSE, 5);
        XP_VALUES.put(EntityType.ZOMBIE_HORSE, 5);
        XP_VALUES.put(EntityType.STRIDER, 2);
        XP_VALUES.put(EntityType.GOAT, 2);

        //mobpvp
        XP_VALUES.put(EntitiyRegister.BULWARK, 25);
        XP_VALUES.put(EntitiyRegister.MAD_SKELETON, 5);
        XP_VALUES.put(EntitiyRegister.MAD_ZOMBIE, 1);
        XP_VALUES.put(EntitiyRegister.MINIGOLEM, 2);

    }

    public static int getXp(EntityType<?> type) {
        return XP_VALUES.getOrDefault(type, 1); // Default: 1 XP for any unlisted entity
    }
}