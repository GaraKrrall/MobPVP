package mc.garakrral.client.renderer.entity.mobpvp;

import mc.garakrral.entity.mob.MiniCopperGolemEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.kaplanlib.util.path.Paths.MOBPVP;

public class MiniCopperGolemRenderer
        extends MobEntityRenderer<MiniCopperGolemEntity, IronGolemEntityModel<MiniCopperGolemEntity>> {


    private static final Identifier TEXTURE =
            Identifier.of(MOBPVP, "textures/entity/mini_copper_golem/copper-golem.png");

    public MiniCopperGolemRenderer(EntityRendererFactory.Context ctx) {
        super(ctx,
                new IronGolemEntityModel<>(ctx.getModelLoader()
                        .getModelPart(EntityModelLayers.IRON_GOLEM)),
                0.3F);                     // küçük gölge yarıçapı
    }

    @Override
    public Identifier getTexture(MiniCopperGolemEntity entity) {
        return TEXTURE;                  // vanilla doku
    }

    @Override
    protected void scale(MiniCopperGolemEntity entity, MatrixStack matrices, float tickDelta) {
        matrices.scale(0.5F, 0.5F, 0.5F);
        super.scale(entity, matrices, tickDelta);
    }
}