package com.kaplandev.level;

import java.util.HashMap;
import java.util.Map;

public class MobLevelRegistry {
    private static final Map<String, Integer[]> LEVEL_RANGES = new HashMap<>();

    static {
        LEVEL_RANGES.put("zombie", new Integer[]{1, 20});
        LEVEL_RANGES.put("skeleton", new Integer[]{1, 3});
        LEVEL_RANGES.put("husk", new Integer[]{1, 3});
        LEVEL_RANGES.put("ghast", new Integer[]{3, 6});
        LEVEL_RANGES.put("zombie_villager", new Integer[]{1, 4});
        LEVEL_RANGES.put("piglin", new Integer[]{3, 7});
        LEVEL_RANGES.put("creeper", new Integer[]{3, 6});
        LEVEL_RANGES.put("witch", new Integer[]{6, 12});
        LEVEL_RANGES.put("magma_cube", new Integer[]{5, 10});
        LEVEL_RANGES.put("piglin_brute", new Integer[]{6, 12});
        LEVEL_RANGES.put("wither_skeleton", new Integer[]{20, 32});
        LEVEL_RANGES.put("wither", new Integer[]{60, 120});
        LEVEL_RANGES.put("warden", new Integer[]{120, 150});
        LEVEL_RANGES.put("ender_dragon", new Integer[]{200, 250});
        LEVEL_RANGES.put("enderman", new Integer[]{1, 6});
        LEVEL_RANGES.put("spider", new Integer[]{2, 4});
        LEVEL_RANGES.put("cave_spider", new Integer[]{3, 5});
        LEVEL_RANGES.put("villager", new Integer[]{1, 2});

        // Pasif moblar (çok az seviye farkı olsun)
        LEVEL_RANGES.put("sheep", new Integer[]{1, 2});
        LEVEL_RANGES.put("cow", new Integer[]{1, 2});
        LEVEL_RANGES.put("pig", new Integer[]{1, 2});
        LEVEL_RANGES.put("chicken", new Integer[]{1, 2});

        // Özel mob
        LEVEL_RANGES.put("custom_skeleton", new Integer[]{2, 6});

        LEVEL_RANGES.put("mini_iron_golem", new Integer[]{1, 2});
    }

    public static Integer[] getLevelRange(String mobId) {
        return LEVEL_RANGES.get(mobId);
    }

    public static Integer[] getLevelRangeOrDefault(String mobId, Integer[] defaultRange) {
        return LEVEL_RANGES.getOrDefault(mobId, defaultRange);
    }


    public static boolean hasMob(String mobId) {
        return LEVEL_RANGES.containsKey(mobId);
    }

    public static Map<String, Integer[]> getAll() {
        return LEVEL_RANGES;
    }
}
