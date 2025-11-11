package mc.garakrral.client.renderer.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import mc.garakrral.entity.passive.TheGreatProtectorGolemEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class TheGreatProtectorGolemModel extends EntityModel<TheGreatProtectorGolemEntity> {
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart right_arm;
    private final ModelPart right_arm2;
    private final ModelPart left_arm;
    private final ModelPart left_arm2;
    private final ModelPart body;
    private final ModelPart head;

    public TheGreatProtectorGolemModel(ModelPart root) {
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
        this.right_arm = root.getChild("right_arm");
        this.right_arm2 = root.getChild("right_arm2");
        this.left_arm = root.getChild("left_arm");
        this.left_arm2 = root.getChild("left_arm2");
        this.body = root.getChild("body");
        this.head = root.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create()
                        .uv(82, 127).cuboid(-17.0F, -35.0F, 21.0F, 20.0F, 35.0F, 24.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-9.0F, 24.0F, -33.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create()
                        .uv(170, 127).cuboid(-10.0F, -35.0F, 17.0F, 20.0F, 35.0F, 24.0F, new Dilation(0.0F)),
                ModelTransform.pivot(16.0F, 24.0F, -29.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create()
                        .uv(82, 186).cuboid(-12.0F, -23.0F, 9.0F, 19.0F, 42.0F, 22.0F, new Dilation(0.0F))
                        .uv(230, 49).cuboid(-9.0F, -27.0F, 13.0F, 13.0F, 2.0F, 14.0F, new Dilation(0.0F))
                        .uv(196, 93).cuboid(-11.0F, -25.0F, 11.0F, 17.0F, 2.0F, 18.0F, new Dilation(0.0F)),
                ModelTransform.pivot(43.0F, -44.0F, -20.0F));

        ModelPartData right_arm2 = modelPartData.addChild("right_arm2", ModelPartBuilder.create()
                        .uv(0, 174).cuboid(-19.0F, -39.0F, 12.0F, 19.0F, 42.0F, 22.0F, new Dilation(0.0F)),
                ModelTransform.pivot(50.0F, 14.0F, -23.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create()
                        .uv(0, 67).cuboid(-2.0F, -36.0F, 18.0F, 19.0F, 43.0F, 22.0F, new Dilation(0.0F))
                        .uv(164, 186).cuboid(-1.0F, -38.0F, 20.0F, 17.0F, 2.0F, 18.0F, new Dilation(0.0F))
                        .uv(176, 49).cuboid(1.0F, -40.0F, 22.0F, 13.0F, 2.0F, 14.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-48.0F, -32.0F, -29.0F));

        ModelPartData left_arm2 = modelPartData.addChild("left_arm2", ModelPartBuilder.create()
                        .uv(0, 67).cuboid(-11.0F, -28.0F, 15.0F, 19.0F, 42.0F, 22.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-39.0F, 3.0F, -26.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create()
                        .uv(82, 93).cuboid(-17.0F, 5.0F, 24.0F, 35.0F, 12.0F, 22.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-31.0F, -35.0F, 22.0F, 62.0F, 41.0F, 26.0F, new Dilation(0.0F))
                        .uv(82, 67).cuboid(-27.0F, -39.0F, 24.0F, 54.0F, 4.0F, 22.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, -28.0F, -35.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
                        .uv(176, 0).cuboid(-13.0F, 4.0F, -11.0F, 26.0F, 26.0F, 23.0F, new Dilation(0.0F))
                        .uv(164, 206).cuboid(-4.0F, 1.0F, -10.0F, 8.0F, 3.0F, 22.0F, new Dilation(0.0F))
                        .uv(164, 231).cuboid(-17.0F, 10.0F, -4.0F, 4.0F, 19.0F, 15.0F, new Dilation(0.0F))
                        .uv(224, 206).cuboid(13.0F, 10.0F, -4.0F, 4.0F, 19.0F, 15.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, -78.0F, -25.0F));

        return TexturedModelData.of(modelData, 512, 512);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        left_leg.render(matrices, vertices, light, overlay);
        right_leg.render(matrices, vertices, light, overlay);
        right_arm.render(matrices, vertices, light, overlay);
        right_arm2.render(matrices, vertices, light, overlay);
        left_arm.render(matrices, vertices, light, overlay);
        left_arm2.render(matrices, vertices, light, overlay);
        body.render(matrices, vertices, light, overlay);
        head.render(matrices, vertices, light, overlay);
    }

    @Override
    public void setAngles(TheGreatProtectorGolemEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // Baş (derece -> radyan)
        this.head.yaw = (float) (headYaw * (Math.PI / 180.0F));
        this.head.pitch = (float) (headPitch * (Math.PI / 180.0F));

        // Animation parametreleri (daha 'ağır' bir yürüyüş için speed < 1)
        float speed = 0.8F;      // animasyon hızı
        float degree = 0.35F;    // genlik (küçültüldü)
        float swing = MathHelper.clamp(limbDistance, 0.0F, 0.9F); // aşırı değerleri kes

        // --- Bacak hedefleri (daha kontrollü, halay hissi yok) ---
        float rightLegTarget = (float) (Math.cos(limbAngle * speed) * degree * swing);
        float leftLegTarget  = (float) (Math.cos(limbAngle * speed + Math.PI) * degree * swing);

        // Smooth (lerp) ile geçiş: 0.6F -> nispeten hızlı ama yumuşak uyum
        this.right_leg.pitch = MathHelper.lerp(0.6F, this.right_leg.pitch, rightLegTarget);
        this.left_leg.pitch  = MathHelper.lerp(0.6F, this.left_leg.pitch, leftLegTarget);

        // Bacak yaw küçük ve limbAngle'e bağlı (dönme hissini kaldırıcak kadar küçük)
        float legYawTargetR = (float) (Math.sin(limbAngle * speed) * 0.02F * swing);
        float legYawTargetL = (float) (-Math.sin(limbAngle * speed) * 0.02F * swing);
        this.right_leg.yaw = MathHelper.lerp(0.6F, this.right_leg.yaw, legYawTargetR);
        this.left_leg.yaw  = MathHelper.lerp(0.6F, this.left_leg.yaw, legYawTargetL);

        // --- Gövde: hafif öne eğilme, dönme çok küçük ---
        float bodyPitchTarget = (float) (Math.cos(limbAngle * speed * 0.5F) * 0.03F * swing);
        float bodyYawTarget   = (float) (Math.sin(limbAngle * speed * 0.25F) * 0.01F * swing);
        this.body.pitch = MathHelper.lerp(0.5F, this.body.pitch, bodyPitchTarget);
        this.body.yaw   = MathHelper.lerp(0.5F, this.body.yaw, bodyYawTarget);

        // --- Kollar: bacaklarla zıt faz, daha küçük genlik ---
        float armDegree = degree * 0.75F;
        float rightArmTarget = (float) (Math.cos(limbAngle * speed + Math.PI) * armDegree * swing) - 0.12F;
        float leftArmTarget  = (float) (Math.cos(limbAngle * speed) * armDegree * swing) - 0.12F;

        // Kolları da yumuşat
        this.right_arm.pitch = MathHelper.lerp(0.55F, this.right_arm.pitch, rightArmTarget);
        this.left_arm.pitch  = MathHelper.lerp(0.55F, this.left_arm.pitch, leftArmTarget);

        // Kol roll (çok küçük) -> animationProgress'e bağlı sürekli dönüş yok
        float rightArmRollTarget = (float) (Math.sin(limbAngle * speed) * 0.02F * swing);
        float leftArmRollTarget  = (float) (-Math.sin(limbAngle * speed) * 0.02F * swing);
        this.right_arm.roll = MathHelper.lerp(0.6F, this.right_arm.roll, rightArmRollTarget);
        this.left_arm.roll  = MathHelper.lerp(0.6F, this.left_arm.roll, leftArmRollTarget);

        // --- Alt kollar üst kola bağlı takipci (yüksek korelasyon) ---
        float rightArm2Target = this.right_arm.pitch * 0.92F + (float) (Math.cos(limbAngle * speed + 0.5F) * 0.02F * swing) - 0.06F;
        float leftArm2Target  = this.left_arm.pitch  * 0.92F + (float) (Math.cos(limbAngle * speed + 0.5F + Math.PI) * 0.02F * swing) - 0.06F;

        this.right_arm2.pitch = MathHelper.lerp(0.7F, this.right_arm2.pitch, rightArm2Target);
        this.left_arm2.pitch  = MathHelper.lerp(0.7F, this.left_arm2.pitch, leftArm2Target);

        // Alt kol roll/yaw üst kolun küçük bir kesri olsun
        this.right_arm2.roll = MathHelper.lerp(0.7F, this.right_arm2.roll, this.right_arm.roll * 0.6F);
        this.left_arm2.roll  = MathHelper.lerp(0.7F, this.left_arm2.roll, this.left_arm.roll * 0.6F);

        this.right_arm2.yaw = MathHelper.lerp(0.7F, this.right_arm2.yaw, this.right_arm.yaw * 0.55F);
        this.left_arm2.yaw  = MathHelper.lerp(0.7F, this.left_arm2.yaw, this.left_arm.yaw * 0.55F);

        // --- Hız arttıkça (örneğin sprint tarzı) hafif artış ---
        if (swing > 0.85F) {
            float boost = (swing - 0.85F) * 3.0F;
            this.right_leg.pitch += 0.04F * boost;
            this.left_leg.pitch  += 0.04F * boost;
            this.right_arm.pitch += 0.03F * boost;
            this.left_arm.pitch  += 0.03F * boost;
        }

        // --- Son düzeltmeler: başın ve gövdenin aşırı dönmesini engelle ---
        this.head.yaw = MathHelper.clamp(this.head.yaw, -1.2F, 1.2F);
        this.head.pitch = MathHelper.clamp(this.head.pitch, -0.9F, 0.9F);
        this.body.yaw = MathHelper.clamp(this.body.yaw, -0.25F, 0.25F);
    }
}
