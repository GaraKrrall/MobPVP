package mc.garakrral.client.renderer.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import mc.garakrral.entity.mob.SnotBallEntity;

@Environment(EnvType.CLIENT)
public class SnotBallModel extends EntityModel<SnotBallEntity> {
    private final ModelPart bb_main;

    public SnotBallModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -10.0F, -7.0F, 14.0F, 2.0F, 14.0F, new Dilation(0.0F))
                .uv(48, 36).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(48, 16).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 44).cuboid(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(-6.0F, -12.0F, -6.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 30).cuboid(-6.0F, -8.0F, -6.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F))
                .uv(48, 26).cuboid(-4.0F, -16.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 56).cuboid(-3.0F, -18.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(40, 44).cuboid(-5.0F, -14.0F, -5.0F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(56, 0).cuboid(-3.0F, -18.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        bb_main.render(matrices, vertices, light, overlay);
    }

    @Override
    public void setAngles(SnotBallEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
