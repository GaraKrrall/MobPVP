package mc.garakrral.client.renderer.entity.mob;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HoglinEntityModel;
import net.minecraft.util.Identifier;

import mc.garakrral.entity.mob.GoblinHorseEntity;

@Environment(EnvType.CLIENT)
public class GoblinHorseRenderer extends MobEntityRenderer<GoblinHorseEntity, HoglinEntityModel<GoblinHorseEntity>> {
    private static final Identifier TEXTURE = com.kaplanlib.api.identifier.Identifier.ofMod("eklenecek");

    public GoblinHorseRenderer(EntityRendererFactory.Context context) {
        super(context, new HoglinEntityModel(context.getPart(EntityModelLayers.HOGLIN)), 0.7F);
    }

    public Identifier getTexture(GoblinHorseEntity hoglinEntity) {
        return TEXTURE;
    }

    protected boolean isShaking(GoblinHorseEntity hoglinEntity) {
        return super.isShaking(hoglinEntity) || hoglinEntity.canConvert();
    }
}
