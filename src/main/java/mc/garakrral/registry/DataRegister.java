package mc.garakrral.registry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import mc.garakrral.data.LevelData;
import mc.garakrral.data.OvenData;

public class DataRegister {
    public static void registerAllData() {
        AutoConfig.register(LevelData.class, GsonConfigSerializer::new);
        AutoConfig.register(OvenData.class, GsonConfigSerializer::new);
    }
}
