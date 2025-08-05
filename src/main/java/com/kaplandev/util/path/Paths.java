package com.kaplandev.util.path;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.kaplandev.mobpvp.MOD_ID;

public class Paths {
    public static final String BULWARK_TEXTURE = "textures/entity/zombie/superzombie.png";
    public static final String CUSTOM_ZOMBIE_TEXTURE = "textures/entity/zombie/zombie.png";
    @Deprecated(forRemoval = true) public static final String CUSTOM_SKELETON_TEXTURE = "";
    public static final String SUPER_ZOMBIE_TEXTURE = "textures/entity/zombie/superzombie.png";
    @Deprecated static final String GOLEM_TEXTURE = "textures/entity/iron_golem/iron_golem.png";
    public static final String KALP_ITEM_KEY = "kalp";
    public static final String ORE_ITEM_KEY = "ore";
    public static final String MOB_TABLE_ITEM_KEY = "mobtable_item";
    public static final String EGG_SKELETON_KEY = "skeleton_egg";
    public static final String EGG_SUPER_ZOMBIE_KEY = "zombie_egg";
    public static final String EGG_MINI_GOLEM_KEY = "mini_golem_egg";
    public static final String EGG_BULWARK_KEY = "bulwark_egg";
    public static final String HEARTH_PART_KEY = "hearth_part";
    public static final String COPPER_STICK_KEY = "copper_stick";
    public static final String REINFORCED_COPPER_INGOT_KEY = "reinforced_copper_ingot";
    public static final String REINFORCED_COPPER_BLOCK_KEY = "reinforced_copper_block";
    public static final String ULTRA_HEARTH_KEY = "ultra_hearth";
    public static final String CRUDE_ACIDIC_ORE_KEY = "ore";
    public static final String MOB_TABLE_KEY = "mob_table";
    public static final String TAB_MOBPVP_ITEMS_KEY = "itemsmobpvp";
    public static final String TAB_MOBPVP_EGGS_KEY = "eggsmobpvp";
    public static final String MOBPVP = "mobpvp";
    public static final String MC = "minecraft";
    public static final String BINGO = "§6§lBİNGO! §fNadir ganimet kazandın!";
    public static final String MAD_ZOMBIE_KEY = "mad_zombie";
    public static final String MAD_SKELETON_KEY = "mad_skeleton";
    public static final String MINIGOLEM = "mini_iron_golem";
    public static final String PVP_SPAWNER_KEY = "pvp_spawner";
    public static final String PVP_SPAWNER_MAX_KEY = "pvp_spawner_max";
    public static final String PVP_SPAWNER_ITEM_KEY = "pvp_spawner_item";
    public static final String PVP_SPAWNER_MAX_ITEM_KEY = "pvp_spawner_max_item";
    public static final String IRON_CHEST_KEY = "iron_chest";
    public static final String IRON_CHEST_ITEM_KEY = "iron_chest_item";
    public static final String DAMAGED_PVP_SPAWNER_KEY = "damaged_pvp_spawner";
    public static final String DAMAGED_PVP_SPAWNER_MAX_KEY = "damaged_pvp_spawner_max";
    public static final String DAMAGED_PVP_SPAWNER_ITEM_KEY = "damaged_pvp_spawner_item";
    public static final String DAMAGED_PVP_SPAWNER_MAX_ITEM_KEY = "damaged_pvp_spawner_max_item";
    public static final String BULWARK = "bulwark";
    public static final String MINI_IRON_GOLEM_TEXTURE = "textures/entity/mini_iron_golem/mini_iron_golem.png";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Identifier STARTUP_SOUND_ID = Identifier.of(MOD_ID, "startup");
    public static final SoundEvent STARTUP_SOUND_EVENT = SoundEvent.of(STARTUP_SOUND_ID);
    private Paths() {}
}
