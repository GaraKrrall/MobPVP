package com.kaplandev.level.player;

import com.kaplandev.event.level.player.PlayerLevelEvents;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class PlayerLevelData {
    private static final int MAX_LEVEL = 120;

    public static int getLevel(UUID uuid) {
        return PlayerLevelSaveHandler.get(uuid).level;
    }

    public static int getXp(UUID uuid) {
        return PlayerLevelSaveHandler.get(uuid).xp;
    }

    public static int getXpToLevelUp(int level) {
        if(level >= MAX_LEVEL) {
            return Integer.MAX_VALUE; // Max seviyede XP kazanamaz
        }
        return 100 + level * 20;
    }

    public static void addXp(ServerPlayerEntity player, int amount) {
        UUID uuid = player.getUuid();
        PlayerLevel levelData = PlayerLevelSaveHandler.get(uuid);

        // Max seviyeyi kontrol et
        if(levelData.level >= MAX_LEVEL) {
            levelData.xp = 0;
            PlayerLevelSaveHandler.set(uuid, levelData);
            return;
        }

        levelData.xp += amount;

        // Birden fazla seviye atlama desteği
        while(levelData.xp >= getXpToLevelUp(levelData.level) && levelData.level < MAX_LEVEL) {
            levelData.xp -= getXpToLevelUp(levelData.level);
            levelData.level++;
            PlayerLevelEvents.onLevelUp(player, levelData.level);
        }

        PlayerLevelSaveHandler.set(uuid, levelData);
    }
}