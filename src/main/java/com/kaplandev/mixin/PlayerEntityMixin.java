package com.kaplandev.mixin;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private static final UUID EXTRA_HEALTH_MODIFIER_ID = UUID.fromString("2a6d2b35-91a4-4cb7-9e5a-b6a04bbfbe12");

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        EntityAttributeInstance inst = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (inst == null) return;

        // Daha önce eklendiyse eklemeyi atla
        if (inst.getModifier(EXTRA_HEALTH_MODIFIER_ID) == null) {
            EntityAttributeModifier modifier = new EntityAttributeModifier(
                    EXTRA_HEALTH_MODIFIER_ID,
                    "extra_health_bonus",
                    2.0,
                    EntityAttributeModifier.Operation.ADDITION // 1.20.1'deki doğru enum
            );
            inst.addPersistentModifier(modifier);
        }
    }
}
