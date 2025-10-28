package com.kaplandev.item.feature;

import com.kaplandev.item.ItemType;
import com.kaplandev.item.Items;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.*;
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

import java.util.List;

public class KnifeItem extends Item {
    private final float baseDamage;
    private final float attackSpeed;

    public KnifeItem(Settings settings, float baseDamage, float attackSpeed) {
        super(settings);
        this.baseDamage = baseDamage;
        this.attackSpeed = attackSpeed;
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, baseDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    public static ToolComponent createToolComponent() {
        return new ToolComponent(List.of(), 1.0F, 1);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

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
                damage *= 2.0f; // Arkadan vurduysa 2 kat hasar
                attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, attacker.getSoundCategory(), 1.0f, 1.0f);
            }

            target.damage(attacker.getDamageSources().playerAttack(player), damage);
        }

        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND); // Canı eksiltir
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(ItemType.REINFORCED_COPPER_INGOT);
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        // İsteğe bağlı: ekstra arkadan hasar burada da eklenebilir
        return 0;
    }
}
