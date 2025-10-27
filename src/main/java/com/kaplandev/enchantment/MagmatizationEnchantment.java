package com.kaplandev.enchantment;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MagmatizationEnchantment extends Enchantment {

    private static final Map<ServerWorld, Map<BlockPos, Integer>> scheduledReverts = new HashMap<>();

    public MagmatizationEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void onTargetDamaged(LivingEntity user, net.minecraft.entity.Entity target, int level) {
        if (!(target instanceof LivingEntity living)) return;
        if (!living.isOnGround()) return;

        ServerWorld world = (ServerWorld) living.getWorld();
        BlockPos basePos = living.getBlockPos().down();

        for (int dx = -2; dx <= 1; dx++) {
            for (int dz = -2; dz <= 1; dz++) {
                BlockPos blockPos = basePos.add(dx, 0, dz);
                BlockState state = world.getBlockState(blockPos);

                if (state.isOf(Blocks.LAVA)) {
                    world.setBlockState(blockPos, Blocks.MAGMA_BLOCK.getDefaultState());
                    scheduledReverts.computeIfAbsent(world, w -> new HashMap<>()).put(blockPos.toImmutable(), 100);
                }
            }
        }

        super.onTargetDamaged(user, target, level);
    }

    // Tick metodu: magmayı 5 saniye (100 tick) sonra tekrar lava çevirir
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
}
