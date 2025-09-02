package com.kaplandev.entity.item;

import com.kaplandev.item.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class IronReinforcedCopperBallEntity extends ThrownItemEntity {
    public IronReinforcedCopperBallEntity(EntityType<? extends IronReinforcedCopperBallEntity> type, World world) {
        super(type, world);
    }

    public IronReinforcedCopperBallEntity(World world, LivingEntity owner) {
        super(com.kaplandev.entity.EntityType.IRON_REINFORCED_COPPER_BALL, owner, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.getWorld().isClient) {
            if (entityHitResult.getEntity() instanceof LivingEntity target) {
                target.damage(this.getDamageSources().thrown(this, this.getOwner()), 10.0F);
            }
            this.discard();
        }
    }


    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.REINFORCED_COPPER_BALL; // senin itemin
    }
}
