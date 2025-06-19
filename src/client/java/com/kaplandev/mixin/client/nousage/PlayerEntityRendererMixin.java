package com.kaplandev.mixin.client.nousage;

/*import com.kaplandev.client.skin.SkinApplyManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void overridePlayerSkin(AbstractClientPlayerEntity player, CallbackInfoReturnable<Identifier> cir) {
        // Artık Session yerine doğrudan player UUID kullanıyoruz
        UUID currentPlayerUUID = MinecraftClient.getInstance().player.getUuid();

        if (player.getUuid().equals(currentPlayerUUID) && SkinApplyManager.currentSkin != null) {
            cir.setReturnValue(SkinApplyManager.currentSkin);
        }
    }
}
*/