package com.kaplandev.levels;

import java.util.HashMap;
import java.util.Map;

public class MobLevelRegistry {
    private static final Map<String, Integer[]> LEVEL_RANGES = new HashMap<>();

    static {
        LEVEL_RANGES.put("zombie", new Integer[]{1, 5});
        LEVEL_RANGES.put("skeleton", new Integer[]{1, 5});
        LEVEL_RANGES.put("husk", new Integer[]{1, 5});
        LEVEL_RANGES.put("ghast", new Integer[]{5, 10});
        LEVEL_RANGES.put("zombie_villager", new Integer[]{2, 6});
        LEVEL_RANGES.put("piglin", new Integer[]{5, 12});
        LEVEL_RANGES.put("creeper", new Integer[]{4, 10});
        LEVEL_RANGES.put("witch", new Integer[]{10, 20});
        LEVEL_RANGES.put("magma_cube", new Integer[]{8, 16});
        LEVEL_RANGES.put("piglin_brute", new Integer[]{10, 20});
        LEVEL_RANGES.put("wither_skeleton", new Integer[]{40, 64});
        LEVEL_RANGES.put("wither", new Integer[]{100, 250});
        LEVEL_RANGES.put("warden", new Integer[]{250, 300});
        LEVEL_RANGES.put("ender_dragon", new Integer[]{500, 545});
        LEVEL_RANGES.put("enderman", new Integer[]{2, 11});
        LEVEL_RANGES.put("spider", new Integer[]{3, 6});
        LEVEL_RANGES.put("cave_spider", new Integer[]{4, 8});
        // Pasif moblar
        LEVEL_RANGES.put("sheep", new Integer[]{2, 5});
        LEVEL_RANGES.put("cow", new Integer[]{1, 5});
        LEVEL_RANGES.put("pig", new Integer[]{1, 5});
        LEVEL_RANGES.put("chicken", new Integer[]{1, 5});
        // Özel moblar
        LEVEL_RANGES.put("custom_skeleton", new Integer[]{1, 10});

        //boss
        //  LEVEL_RANGES.put("bulwark", new Integer[]{100, 112}); //TODO: Başka bir yönteme aldık bunu
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
