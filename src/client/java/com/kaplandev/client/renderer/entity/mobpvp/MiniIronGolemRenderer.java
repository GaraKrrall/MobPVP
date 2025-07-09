package com.kaplandev.client.renderer.entity.mobpvp;


import com.kaplandev.entity.mobpvp.MiniIronGolemEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MiniIronGolemRenderer
        extends MobEntityRenderer<MiniIronGolemEntity, IronGolemEntityModel<MiniIronGolemEntity>> {

    /** Vanilla demir golem dokusu – namespace mutlaka "minecraft" olmalı */
    private static final Identifier TEXTURE =
            Identifier.of("minecraft", "textures/entity/iron_golem/iron_golem.png");

    public MiniIronGolemRenderer(EntityRendererFactory.Context ctx) {
        super(ctx,
                new IronGolemEntityModel<>(ctx.getModelLoader()
                        .getModelPart(EntityModelLayers.IRON_GOLEM)),
                0.3F);                     // küçük gölge yarıçapı
    }

    @Override
    public Identifier getTexture(MiniIronGolemEntity entity) {
        return TEXTURE;                  // vanilla doku
    }

    @Override
    protected void scale(MiniIronGolemEntity entity, MatrixStack matrices, float tickDelta) {
        matrices.scale(0.5F, 0.5F, 0.5F); // %50 boyut
        super.scale(entity, matrices, tickDelta);
    }
}
