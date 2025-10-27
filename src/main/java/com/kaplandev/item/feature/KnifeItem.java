package com.kaplandev.item.feature;

import com.kaplandev.item.Items;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

public class KnifeItem extends Item {
    private final float baseDamage;
    private final float attackSpeed;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public KnifeItem(Settings settings, float baseDamage, float attackSpeed) {
        super(settings);
        this.baseDamage = baseDamage;
        this.attackSpeed = attackSpeed;

        // Attribute sistemi (1.20.1)
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", baseDamage, EntityAttributeModifier.Operation.ADDITION)
        );
        builder.put(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION)
        );
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient && attacker instanceof PlayerEntity player) {
            float damage = baseDamage;

            // Arkadan saldırı kontrolü
            Vec3d targetFacing = target.getRotationVec(1.0F).normalize();
            Vec3d attackerVec = attacker.getPos().subtract(target.getPos()).normalize();
            double dot = targetFacing.dotProduct(attackerVec);

            if (dot > 0.7) {
                damage *= 2.0f; // arkadan vurduysa 2 kat hasar
                attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, attacker.getSoundCategory(), 1.0f, 1.0f);
            }

            target.damage(attacker.getDamageSources().playerAttack(player), damage);
        }

        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.REINFORCED_COPPER_INGOT);
    }
}
