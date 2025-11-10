package mc.garakrral.client.renderer.entity.mob;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.util.Identifier;

import mc.garakrral.entity.mob.GoblinHorseEntity;

@Environment(EnvType.CLIENT)
public class GoblinHorseRenderer extends MobEntityRenderer<GoblinHorseEntity, HorseEntityModel<GoblinHorseEntity>> {
    private static final Identifier TEXTURE = com.kaplanlib.api.identifier.Identifier.ofMod("textures/entity/goblin_horse/goblin_horse.png");

    //   // private static final Identifier TEXTURE = com.kaplanlib.api.identifier.Identifier.ofMod("textures/entity/goblin_horse/goblin_horse.png");

    public GoblinHorseRenderer(EntityRendererFactory.Context context) {
        super(context, new HorseEntityModel<>(context.getPart(EntityModelLayers.HORSE)), 0.7F);
    }

    public Identifier getTexture(GoblinHorseEntity hoglinEntity) {
        return TEXTURE;
    }
}
