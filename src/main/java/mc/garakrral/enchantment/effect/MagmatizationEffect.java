package mc.garakrral.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public record MagmatizationEffect(EnchantmentLevelBasedValue amount)
        implements EnchantmentEntityEffect {

    public static final MapCodec<MagmatizationEffect> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            EnchantmentLevelBasedValue.CODEC.fieldOf("amount")
                                    .forGetter(MagmatizationEffect::amount)
                    ).apply(instance, MagmatizationEffect::new)
            );

    private static final Map<ServerWorld, Map<BlockPos, Integer>> scheduledReverts = new HashMap<>();


    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (!(target instanceof LivingEntity living)) return;
        if (!living.isOnGround()) return;

        BlockPos basePos = BlockPos.ofFloored(pos.x, pos.y - 1, pos.z);

        // 4x4 alanı tara
        for (int dx = -2; dx <= 1; dx++) {
            for (int dz = -2; dz <= 1; dz++) {
                BlockPos blockPos = basePos.add(dx, 0, dz);
                BlockState state = world.getBlockState(blockPos);

                if (state.isOf(Blocks.LAVA)) {
                    world.setBlockState(blockPos, Blocks.MAGMA_BLOCK.getDefaultState());
                    scheduledReverts
                            .computeIfAbsent(world, w -> new HashMap<>())
                            .put(blockPos.toImmutable(), 100);
                }
            }
        }
    }

    public static void tick(ServerWorld world) {
        Map<BlockPos, Integer> worldReverts = scheduledReverts.get(world);
        if (worldReverts == null) return;

        Iterator<Map.Entry<BlockPos, Integer>> it = worldReverts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<BlockPos, Integer> entry = it.next();
            int remaining = entry.getValue() - 1;

            if (remaining <= 0) {
                BlockPos pos = entry.getKey();
                if (world.getBlockState(pos).isOf(Blocks.MAGMA_BLOCK)) {
                    world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                }
                it.remove();
            } else {
                entry.setValue(remaining);
            }
        }
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

}
