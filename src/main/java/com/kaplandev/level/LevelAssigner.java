package com.kaplandev.level;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.kaplanlib.util.NameUtils.toTr;



public class LevelAssigner {

    private static final Map<UUID, Integer> LEVELS = new HashMap<>();

    public static void assignLevel(LivingEntity entity, String mobId, int level) {
        LEVELS.put(entity.getUuid(), level);
        updateDisplay(entity);
    }

    public static void updateDisplay(LivingEntity entity) {
        float currentHealth = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        int level = LEVELS.getOrDefault(entity.getUuid(), 1);

        String mobId = Registries.ENTITY_TYPE.getId(entity.getType()).getPath();

        String displayName = String.format(
                "§c[Level: %d] §f%s §7- §a%.1f§7/§c%.0f ❤",
                level,
                toTr(mobId),
                currentHealth,
                maxHealth
        );

        entity.setCustomName(Text.literal(displayName));
        entity.setCustomNameVisible(true);
    }


    public static boolean hasLevel(LivingEntity entity) {
        return LEVELS.containsKey(entity.getUuid());
    }

    public static String getMobId(LivingEntity entity) {
        return entity.getType().getTranslationKey(); // Alternatif: Registries.ENTITY_TYPE.getId(entity.getType()).getPath();
    }
}
