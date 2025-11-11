package mc.garakrral.client.renderer.entity.model.layer;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static mc.garakrral.mobpvp.MOD_ID;

public class EntityModelLayers {
    public static final EntityModelLayer SNOT_BALL_LAYER =
            new EntityModelLayer(Identifier.of(MOD_ID, "snot_ball"), "main");
    public static final EntityModelLayer GOLEM_LAYER =
            new EntityModelLayer(Identifier.of(MOD_ID, "the_great_protector_golem_model"), "main");
}
