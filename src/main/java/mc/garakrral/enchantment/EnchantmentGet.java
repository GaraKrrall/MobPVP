package mc.garakrral.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import mc.garakrral.enchantment.list.EnchantmentList;

import static mc.garakrral.mobpvp.MOD_ID;

public class EnchantmentGet {

    public static final RegistryKey<Enchantment> THUNDERING;
    public static final RegistryKey<Enchantment> CERTAINTY;
    public static final RegistryKey<Enchantment> MAGMATIZATION;

    public static void init() {
    }

    static {
        CERTAINTY = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(MOD_ID, EnchantmentList.CERTAINTY.getName()));
        THUNDERING = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(MOD_ID, EnchantmentList.THUNDERING.getName()));
        MAGMATIZATION = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(MOD_ID, EnchantmentList.MAGMATIZATION.getName()));
    }
}
