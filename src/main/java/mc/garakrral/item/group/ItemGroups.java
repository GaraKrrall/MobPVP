package mc.garakrral.item.group;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import mc.garakrral.item.ItemType;

import com.kaplanlib.api.version.BetaVersions;

import static com.kaplanlib.util.path.Paths.TAB_MOBPVP_ITEMS_KEY;
import static mc.garakrral.mobpvp.MOD_ID;

public class ItemGroups {
    public static final RegistryKey<ItemGroup> MOBPVP_GROUP_KEY;
    public static final ItemGroup MOBPVP_GROUP;

    public static void init() {
        if (BetaVersions.IS_BETA) {
            BetaItemGroups.init();
        }
    }

    static {
        MOBPVP_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, TAB_MOBPVP_ITEMS_KEY));
        MOBPVP_GROUP = Registry.register(Registries.ITEM_GROUP, MOBPVP_GROUP_KEY, FabricItemGroup.builder().icon(() -> new ItemStack(ItemType.KALP_ITEM)).displayName(Text.translatable("itemGroup.mobpvp")).entries((ctx, entries) -> {
        }).build());

    }
}
