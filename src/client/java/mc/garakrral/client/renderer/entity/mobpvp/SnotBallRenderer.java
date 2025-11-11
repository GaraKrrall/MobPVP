package mc.garakrral.client.renderer.entity.mobpvp;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import mc.garakrral.client.renderer.entity.model.SnotBallModel;
import mc.garakrral.client.renderer.entity.model.layer.EntityModelLayers;
import mc.garakrral.entity.mob.SnotBallEntity;

import static com.kaplanlib.util.path.Paths.MOBPVP;

public class SnotBallRenderer extends MobEntityRenderer<SnotBallEntity, SnotBallModel> {

    private static final Identifier TEXTURE = Identifier.of(MOBPVP, "textures/entity/snot_ball/a2.png");

    public SnotBallRenderer(EntityRendererFactory.Context context) {
        super(context, new SnotBallModel(context.getPart(EntityModelLayers.SNOT_BALL_LAYER)), 0.3f);
    }

    @Override
    public Identifier getTexture(SnotBallEntity entity) {
        return TEXTURE;
    }
}
