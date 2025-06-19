package com.kaplandev.client.renderer.entity.zombie;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.util.Identifier;

public class SuperZombieRenderer extends ZombieEntityRenderer {
    private static final Identifier SUPER_ZOMBIE_TEXTURE =
            Identifier.of("mobpvp", "textures/entity/zombie/superzombie.png");

    public SuperZombieRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(net.minecraft.entity.mob.ZombieEntity entity) {
        return SUPER_ZOMBIE_TEXTURE;
    }
}
