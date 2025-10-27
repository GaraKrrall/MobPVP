package com.kaplandev.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightningStrikeEnchantment extends Enchantment {

    public LightningStrikeEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void onTargetDamaged(LivingEntity user, net.minecraft.entity.Entity target, int level) {
        World world = user.getWorld();
        if (!world.isClient && world.isThundering() && target.isAlive()) {
            ServerWorld serverWorld = (ServerWorld) world;
            BlockPos pos = target.getBlockPos();
            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(serverWorld);
            if (lightning != null) {
                lightning.refreshPositionAfterTeleport(pos.getX(), pos.getY(), pos.getZ());
                serverWorld.spawnEntity(lightning);
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
