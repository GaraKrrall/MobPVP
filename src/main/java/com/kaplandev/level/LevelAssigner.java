package com.kaplandev.level;

import com.kaplandev.data.LevelData;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelAssigner {

    private static final Pattern NAME_PATTERN = Pattern.compile("§c\\[Level: \\d+] §f(.+) §7- .*");

    private static LevelData getData() {
        return AutoConfig.getConfigHolder(LevelData.class).getConfig();
    }

    public static void assignLevel(LivingEntity entity, String mobId, int level) {
        getData().setLevel(entity.getUuid(), level);
        AutoConfig.getConfigHolder(LevelData.class).save();
    }

    public static boolean hasLevel(LivingEntity entity) {
        return getData().hasLevel(entity.getUuid());
    }

    public static int getLevel(LivingEntity entity) {
        return getData().getLevel(entity.getUuid());
    }

    public static String getMobId(LivingEntity entity) {
        return Registries.ENTITY_TYPE.getId(entity.getType()).getPath();
    }

    public static String getDefaultTranslatedName(LivingEntity entity) {
        return entity.getType().getName().getString();
    }

    public static String extractBaseName(String formatted) {
        Matcher m = NAME_PATTERN.matcher(formatted);
        if (m.matches()) {
            return m.group(1);
        }
        return null;
    }

    public static String buildDisplayName(LivingEntity entity) {
        int level = getLevel(entity);
        float currentHealth = entity.getHealth();
        float maxHealth = entity.getMaxHealth();

        String baseName;

        if (entity.hasCustomName() && entity.getCustomName() != null) {
            String rawName = entity.getCustomName().getString();

            if (rawName.startsWith("§c[Level:")) {
                // Bizim format → orijinal ismi çek
                String extracted = extractBaseName(rawName);
                baseName = (extracted != null) ? extracted : getDefaultTranslatedName(entity);
            } else {
                // Oyuncu kendi özel ismini vermiş
                baseName = rawName;
            }
        } else {
            // Default isim Minecraft'ın dil sisteminden gelir
            baseName = getDefaultTranslatedName(entity);
        }

        return String.format(
                "§c[Level: %d] §f%s §7- §a%.1f§7/§c%.0f ❤",
                level,
                baseName,
                currentHealth,
                maxHealth
        );
    }
}
