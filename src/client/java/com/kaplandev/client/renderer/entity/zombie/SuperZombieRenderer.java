package com.kaplandev.client.renderer.entity.zombie;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.util.Identifier;

import static com.kaplandev.mobpvp.MOD_ID;
import static com.kaplandev.strings.path.Paths.SUPER_ZOMBIE_TEXTURE;

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
