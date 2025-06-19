// CustomZombieRenderer.java
package com.kaplandev.client.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.util.Identifier;

public class CustomZombieRenderer extends ZombieEntityRenderer {
    private static final Identifier CUSTOM_ZOMBIE_TEXTURE =
            Identifier.of("mobpvp", "textures/entity/zombie/zombie.png");

    public CustomZombieRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(net.minecraft.entity.mob.ZombieEntity entity) {
        return CUSTOM_ZOMBIE_TEXTURE;
    }
}