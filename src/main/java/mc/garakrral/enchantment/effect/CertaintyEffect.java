package mc.garakrral.enchantment.effect;

import mc.garakrral.item.ItemType;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record CertaintyEffect(EnchantmentLevelBasedValue amount)
        implements EnchantmentEntityEffect {

    public static final MapCodec<CertaintyEffect> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            EnchantmentLevelBasedValue.CODEC.fieldOf("amount")
                                    .forGetter(CertaintyEffect::amount)
                    ).apply(instance, CertaintyEffect::new)
            );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (target instanceof LivingEntity livingEntity) {
            if (world.random.nextFloat() < 0.2f) {
                ItemStack drop = new ItemStack(ItemType.KALP_ITEM, 1);

                ItemEntity itemEntity = new ItemEntity(world, pos.x, pos.y, pos.z, drop);
                world.spawnEntity(itemEntity);
            }
        }
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

}
