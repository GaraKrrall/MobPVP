package com.kaplandev.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;


public record LightningStrikeEffect(EnchantmentLevelBasedValue amount)
        implements EnchantmentEntityEffect {

    public static final MapCodec<LightningStrikeEffect> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            EnchantmentLevelBasedValue.CODEC.fieldOf("amount")
                                    .forGetter(LightningStrikeEffect::amount)
                    ).apply(instance, LightningStrikeEffect::new)
            );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (world.isThundering()) {
            BlockPos p = target.getBlockPos();
            EntityType.LIGHTNING_BOLT.spawn(world, p, SpawnReason.TRIGGERED);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

}