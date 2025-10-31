package mc.garakrral.item.feature;

import mc.garakrral.item.ItemType;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class MaceItem extends Item {
    private final float attackDamage;
    private final float attackSpeed;
    private final float knockbackRange;
    private final float knockbackPower;

    public MaceItem(Settings settings, float attackDamage, float attackSpeed, float knockbackRange, float knockbackPower) {
        super(settings);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.knockbackRange = knockbackRange;
        this.knockbackPower = knockbackPower;
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, this.attackDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, this.attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    public static ToolComponent createToolComponent() {
        return new ToolComponent(List.of(), 1.0F, 2);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public int getEnchantability() {
        return 15;
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity serverPlayer) {
            if (shouldDealAdditionalDamage(serverPlayer)) {
                ServerWorld world = (ServerWorld) serverPlayer.getWorld();

                serverPlayer.setIgnoreFallDamageFromCurrentExplosion(true);
                serverPlayer.setVelocity(serverPlayer.getVelocity().withAxis(Axis.Y, 0.01));
                serverPlayer.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayer));

                SoundEvent sound = target.isOnGround() ?
                        (serverPlayer.fallDistance > 5.0F ? SoundEvents.ITEM_MACE_SMASH_GROUND_HEAVY : SoundEvents.ITEM_MACE_SMASH_GROUND) :
                        SoundEvents.ITEM_MACE_SMASH_AIR;

                world.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), sound, serverPlayer.getSoundCategory(), 1.0F, 1.0F);
                knockbackNearbyEntities(world, serverPlayer, target);
            }
        }
        return true;
    }

    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        if (shouldDealAdditionalDamage(attacker)) {
            attacker.onLanding();
        }
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(ItemType.REINFORCED_COPPER_INGOT); // özelleştirilebilir
    }

    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        Entity attackerEntity = damageSource.getSource();
        if (attackerEntity instanceof LivingEntity attacker && shouldDealAdditionalDamage(attacker)) {
            float fall = attacker.fallDistance;
            float extra = (fall <= 3) ? (4 * fall) : (fall <= 8) ? (12 + 2 * (fall - 3)) : (22 + fall - 8);
            if (attacker.getWorld() instanceof ServerWorld serverWorld) {
                return extra + EnchantmentHelper.getSmashDamagePerFallenBlock(serverWorld, attacker.getWeaponStack(), target, damageSource, 0) * fall;
            }
            return extra;
        }
        return 0;
    }

    private void knockbackNearbyEntities(World world, PlayerEntity player, Entity attacked) {
        world.syncWorldEvent(2013, attacked.getSteppingPos(), 750);
        world.getEntitiesByClass(LivingEntity.class, attacked.getBoundingBox().expand(knockbackRange), getKnockbackPredicate(player, attacked)).forEach(entity -> {
            Vec3d offset = entity.getPos().subtract(attacked.getPos());
            double knockback = getKnockback(player, entity, offset);
            Vec3d motion = offset.normalize().multiply(knockback);
            if (knockback > 0) {
                entity.addVelocity(motion.x, knockbackPower, motion.z);
                if (entity instanceof ServerPlayerEntity serverEntity) {
                    serverEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverEntity));
                }
            }
        });
    }

    private Predicate<LivingEntity> getKnockbackPredicate(PlayerEntity player, Entity attacked) {
        return entity -> {
            boolean valid = !entity.isSpectator()
                    && entity != player
                    && entity != attacked
                    && !player.isTeammate(entity)
                    && (!(entity instanceof TameableEntity tame) || !tame.isTamed() || !player.getUuid().equals(tame.getOwnerUuid()))
                    && (!(entity instanceof ArmorStandEntity armor) || !armor.isMarker())
                    && attacked.squaredDistanceTo(entity) <= knockbackRange * knockbackRange;
            return valid;
        };
    }

    private double getKnockback(PlayerEntity player, LivingEntity attacked, Vec3d distance) {
        return (knockbackRange - distance.length()) * knockbackPower *
                (player.fallDistance > 5 ? 2 : 1) *
                (1.0 - attacked.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
    }

    public static boolean shouldDealAdditionalDamage(LivingEntity attacker) {
        return attacker.fallDistance > 1.5F && !attacker.isFallFlying();
    }
}
