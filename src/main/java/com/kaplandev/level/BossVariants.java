package com.kaplandev.level;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;



public class BossVariants {

    public static boolean tryAssignCustomZombieBoss(ZombieEntity zombie, LivingEntity living, World world) {
        float roll = zombie.getRandom().nextFloat();

        if (roll < 0.01f) {
            return applyBoss(zombie, living, world, "§4GİANT BOSS", 1000, ArmorSet.NETHERITE, Items.NETHERITE_SWORD, 9, 20);
        } else if (roll < 0.06f) {
            return applyBoss(zombie, living, world, "§4SİNİRLİ MİRAN BABA", 38, ArmorSet.NETHERITE, Items.NETHERITE_SWORD, 9, 40);
        } else if (roll < 0.11f) {
            return applyBoss(zombie, living, world, "§4ARABİC TOAST BOSS", 29, ArmorSet.DIAMOND, Items.NETHERITE_SWORD, 9, 20);
        } else if (roll < 0.20f) {
            return applyBoss(zombie, living, world, "§4MİNİ BOSS", 12, ArmorSet.IRON, Items.IRON_SWORD, 9, 2);
        }

        return false;
    }



    private static boolean applyBoss(ZombieEntity zombie, LivingEntity living, World world,
                                     String name, int level, ArmorSet armorSet,
                                     net.minecraft.item.Item swordItem, int sharp, int knockback) {

        living.setCustomName(Text.literal("§c[Seviye: " + level + "] " + name));
        living.setCustomNameVisible(true);

        // HP
        if (living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH) != null) {
            double base = living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
            double total = base + level * 1.2;
            living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(total);
            living.setHealth((float) total);
        }

        // Damage
        if (living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null) {
            double dmg = living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue();
            living.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(dmg + level * 0.2);
        }

        // Zırh
        ItemStack[] armor = armorSet.getArmor();
        zombie.equipStack(EquipmentSlot.HEAD, armor[0]);
        zombie.equipStack(EquipmentSlot.CHEST, armor[1]);
        zombie.equipStack(EquipmentSlot.LEGS, armor[2]);
        zombie.equipStack(EquipmentSlot.FEET, armor[3]);

        // Silah
        ItemStack sword = new ItemStack(swordItem);
        RegistryEntry<Enchantment> sharpnessEntry = world.getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(Identifier.of("minecraft", "sharpness")).orElseThrow();

        RegistryEntry<Enchantment> knockbackEntry = world.getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(Identifier.of("minecraft", "knockback")).orElseThrow();

        sword.addEnchantment(sharpnessEntry, sharp);
        sword.addEnchantment(knockbackEntry, knockback);
        zombie.equipStack(EquipmentSlot.MAINHAND, sword);

        // Düşmesin
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            zombie.setEquipmentDropChance(slot, 0f);
        }

        return true;
    }
}
