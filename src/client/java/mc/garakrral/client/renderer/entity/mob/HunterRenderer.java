package mc.garakrral.client.renderer.entity.mob;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.util.Identifier;

import mc.garakrral.entity.mob.HunterEntity;

@Environment(EnvType.CLIENT)
public class HunterRenderer extends IllagerEntityRenderer<HunterEntity> {
    private static final Identifier TEXTURE = com.kaplanlib.api.identifier.Identifier.ofMod("textures/entity/hunter/hunter.png");

    public HunterRenderer(EntityRendererFactory.Context context) {
        super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.PILLAGER)), 0.5F);
        this.addFeature(new HeldItemFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    public Identifier getTexture(HunterEntity hunterEntity) {
        return TEXTURE;
    }
}
