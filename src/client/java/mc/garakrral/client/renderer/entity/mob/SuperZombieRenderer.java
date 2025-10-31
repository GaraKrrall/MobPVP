package mc.garakrral.client.renderer.entity.mob;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.util.Identifier;

import static mc.garakrral.mobpvp.MOD_ID;
import static com.kaplanlib.util.path.Paths.SUPER_ZOMBIE_TEXTURE;

public class SuperZombieRenderer extends ZombieEntityRenderer {
    private static final Identifier SUPER_ZOMBIE_TEXTURE_RENDERER =
            Identifier.of(MOD_ID, SUPER_ZOMBIE_TEXTURE );

    public SuperZombieRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(net.minecraft.entity.mob.ZombieEntity entity) {
        return SUPER_ZOMBIE_TEXTURE_RENDERER;
    }
}
