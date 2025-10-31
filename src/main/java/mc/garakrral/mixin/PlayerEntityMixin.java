package mc.garakrral.mixin;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private static final Identifier MOD_ID = Identifier.tryParse("extra_health:bonus");

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        EntityAttributeInstance inst = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (inst == null) return;

        // Daha önce eklendiyse eklemeyi atla
        if (inst.getModifier(MOD_ID) == null) {
            // ADD_VALUE sabiti Fabric 1.21.1'de +2.0 değer için kullanılır :contentReference[oaicite:1]{index=1}
            EntityAttributeModifier modifier = new EntityAttributeModifier(
                    MOD_ID,
                    2.0,
                    EntityAttributeModifier.Operation.ADD_VALUE
            );
            inst.addPersistentModifier(modifier);
        }
    }
}
