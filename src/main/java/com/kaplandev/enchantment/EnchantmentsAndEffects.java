package com.kaplandev.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.kaplandev.mobpvp.MOD_ID;

public class EnchantmentsAndEffects {

    public static final Enchantment LIGHTNING_STRIKE = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(MOD_ID, "lightning_strike"),
            new LightningStrikeEnchantment()
    );

    public static final Enchantment CERTAINTY_STRIKE = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(MOD_ID, "certainty_strike"),
            new CertaintyStrikeEnchantment()
    );

    public static final Enchantment MAGMATIZATION_STRIKE = Registry.register(
            Registries.ENCHANTMENT,
            new Identifier(MOD_ID, "magmatization_strike"),
            new MagmatizationEnchantment()
    );

    public static void registerModEnchantments() {
        System.out.println("Registered enchantments for " + MOD_ID);
    }
}
