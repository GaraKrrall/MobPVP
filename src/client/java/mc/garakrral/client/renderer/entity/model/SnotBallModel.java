package mc.garakrral.client.renderer.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import mc.garakrral.entity.mob.SnotBallEntity;

public class SnotBallModel extends EntityModel<SnotBallEntity> {
    private final ModelPart bb_main;

    public SnotBallModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("bb_main", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-7.0F, -10.0F, -7.0F, 14.0F, 2.0F, 14.0F)
                        .uv(0, 16).cuboid(-6.0F, -12.0F, -6.0F, 12.0F, 2.0F, 12.0F)
                        .uv(0, 30).cuboid(-5.0F, -14.0F, -5.0F, 10.0F, 2.0F, 10.0F)
                        .uv(20, 0).cuboid(-4.0F, -16.0F, -4.0F, 8.0F, 2.0F, 8.0F)
                        .uv(40, 0).cuboid(-3.0F, -18.0F, -3.0F, 6.0F, 2.0F, 6.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 32);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        bb_main.render(matrices, vertices, light, overlay);
    }

    @Override
    public void setAngles(SnotBallEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
