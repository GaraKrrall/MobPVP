package mc.garakrral.level.player;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerLevelSaveHandler {
    private static final Gson GSON = new Gson();
    private static final Type TYPE = new TypeToken<Map<UUID, PlayerLevel>>() {}.getType();
    private static final Map<UUID, PlayerLevel> DATA = new HashMap<>();
    private static File saveFile;

    public static void init(MinecraftServer server) {
        File dataDir = new File(server.getSaveProperties().getLevelName(), "player_levels");
        if (!dataDir.exists() && !dataDir.mkdirs()) {
            System.err.println("§cKlasör oluşturulamadı: " + dataDir.getAbsolutePath());
        }

        saveFile = new File(dataDir, "levels.json");

        if (saveFile.exists()) {
            try (FileReader reader = new FileReader(saveFile)) {
                Map<UUID, PlayerLevel> loaded = GSON.fromJson(reader, TYPE);
                if (loaded != null) {
                    DATA.clear();
                    DATA.putAll(loaded);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void save() {
        if (saveFile == null) return;
        try (FileWriter writer = new FileWriter(saveFile)) {
            GSON.toJson(DATA, TYPE, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PlayerLevel get(UUID uuid) {
        PlayerLevel levelData = DATA.computeIfAbsent(uuid, k -> new PlayerLevel());

        if (levelData.level > 120) {
            throw new RuntimeException("§cHile tespit edildi: Oyuncunun seviyesi 120'yi geçti: " + levelData.level);
        }

        return levelData;
    }


    public static void set(UUID uuid, PlayerLevel level) {
        if (level.level > 120) {
            throw new RuntimeException("Oyuncunun level'i 120'den büyük olamaz! (" + level.level + ")");
        }
        DATA.put(uuid, level);
    }

}

