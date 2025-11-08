package mc.garakrral.client.renderer.entity.mob;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.util.Identifier;

import static mc.garakrral.mobpvp.MOD_ID;

public class GoblinRenderer extends ZombieEntityRenderer {
    private static final Identifier GOBLIN_TEXTURE_RENDERER =
            Identifier.of(MOD_ID, "textures/entity/goblin/goblin.png");

    public GoblinRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(net.minecraft.entity.mob.ZombieEntity entity) {
        return GOBLIN_TEXTURE_RENDERER;
    }
}