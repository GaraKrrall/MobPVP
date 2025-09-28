package com.kaplandev.data;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Config(name = "mod_level_data")
public class LevelData implements ConfigData {
    public Map<String, Integer> levels = new HashMap<>();

    public void setLevel(UUID uuid, int level) {
        levels.put(uuid.toString(), level);
    }

    public boolean hasLevel(UUID uuid) {
        return levels.containsKey(uuid.toString());
    }

    public int getLevel(UUID uuid) {
        return levels.getOrDefault(uuid.toString(), 1);
    }
}
