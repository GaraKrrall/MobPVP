package com.kaplandev.enchantment;

import com.kaplandev.enchantment.effect.CertaintyEffect;
import com.kaplandev.enchantment.effect.LightningStrikeEffect;
import com.kaplandev.enchantment.effect.MagmatizationEffect;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static com.kaplandev.mobpvp.MOD_ID;
import static net.minecraft.registry.Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE;

public class EnchantmentsAndEffects {
    public static final RegistryKey<Enchantment> LIGHTNING_STRIKE = of("lightning_strike");
    public static MapCodec<LightningStrikeEffect> LIGHTNING_EFFECT = register("lightning_effect", LightningStrikeEffect.CODEC);
    public static final RegistryKey<Enchantment> CERTAINTYSTRIKE = of("certainty_strike");
    public static MapCodec<CertaintyEffect> CERTAINTY_EFFECT = register("certainty_effect", CertaintyEffect.CODEC);
    public static final RegistryKey<Enchantment> MAGMATIZATION_STRIKE = of("magmatization_strike");
    public static MapCodec<MagmatizationEffect> MAGMATIZATION_EFFECT = register("magmatization_effect", MagmatizationEffect.CODEC);

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(ENCHANTMENT_ENTITY_EFFECT_TYPE,
                Identifier.of(MOD_ID, id), codec);
    }

    public static void registerModEnchantmentEffects() {
        System.out.println("Registered Enchantments");
    }

}
