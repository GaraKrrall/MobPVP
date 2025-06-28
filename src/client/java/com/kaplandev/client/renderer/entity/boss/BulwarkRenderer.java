package com.kaplandev.client.renderer.entity.boss;

import com.kaplandev.entity.boss.BulwarkEntity;

import static com.kaplandev.mobpvp.MOD_ID;
import static com.kaplandev.strings.path.Paths.BULWARK_TEXTURE;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;



public class BulwarkRenderer extends MobEntityRenderer<BulwarkEntity, BipedEntityModel<BulwarkEntity>> {

    private static final Identifier TEXTURE = Identifier.of(MOD_ID, BULWARK_TEXTURE);

    public BulwarkRenderer(EntityRendererFactory.Context context) {
        super(context, new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER)), 0.7f);
    }

    @Override
    public Identifier getTexture(BulwarkEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(BulwarkEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(2.5f, 2.5f, 2.5f);
        super.scale(entity, matrices, amount);
    }
}
