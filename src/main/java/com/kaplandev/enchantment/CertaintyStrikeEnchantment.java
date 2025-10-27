package com.kaplandev.enchantment;

import com.kaplandev.item.Items;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CertaintyStrikeEnchantment extends Enchantment {

    public CertaintyStrikeEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void onTargetDamaged(LivingEntity user, net.minecraft.entity.Entity target, int level) {
        World world = user.getWorld();
        if (!world.isClient && target instanceof LivingEntity living) {
            if (world.random.nextFloat() < 0.2f) { // %20 ihtimal
                ItemStack drop = new ItemStack(Items.KALP_ITEM, 1);
                ItemEntity itemEntity = new ItemEntity(world, target.getX(), target.getY(), target.getZ(), drop);
                world.spawnEntity(itemEntity);
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
