package com.kaplandev.mixin;

import com.kaplandev.level.MobXpValues;
import com.kaplandev.level.player.PlayerLevelData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin2 {
    @Shadow
    public abstract float getHealth();

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;

        // Bu hasar ölümcül mü?
        if (!self.getWorld().isClient && self.getHealth() <= amount) {
            Entity attacker = source.getAttacker();

            if (attacker instanceof ServerPlayerEntity player) {
                if (self.isDead()) return;
                int xp = MobXpValues.getXp(self.getType());
                PlayerLevelData.addXp(player, xp);
            }
        }
    }
}
