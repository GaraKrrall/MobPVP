package mc.garakrral.client.renderer.entity.boss;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import mc.garakrral.client.renderer.entity.model.TheGreatProtectorGolemModel;
import mc.garakrral.client.renderer.entity.model.layer.EntityModelLayers;
import mc.garakrral.entity.boss.TheGreatProtectorGolemEntity;

import static mc.garakrral.mobpvp.MOD_ID;

@Environment(EnvType.CLIENT)
public class TheGreatProtectorGolemRenderer extends MobEntityRenderer<TheGreatProtectorGolemEntity, TheGreatProtectorGolemModel> {
    private static final Identifier TEXTURE = Identifier.of(MOD_ID,"textures/entity/a/test.png");

    public TheGreatProtectorGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new TheGreatProtectorGolemModel(context.getPart(EntityModelLayers.GOLEM_LAYER)), 1.4F);
    }

    @Override
    public Identifier getTexture(TheGreatProtectorGolemEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(TheGreatProtectorGolemEntity entity, MatrixStack matrices, float f, float g, float h, float i) {
        super.setupTransforms(entity, matrices, f, g, h, i);
        if (!(entity.limbAnimator.getSpeed() < 0.01F)) {
            float cycle = 13.0F;
            float pos = entity.limbAnimator.getPos(h) + 6.0F;
            float swing = (Math.abs(pos % cycle - 6.5F) - 3.25F) / 3.25F;
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(6.5F * swing));
        }

        matrices.scale(2.0F, 2.0F, 2.0F);
    }
}
