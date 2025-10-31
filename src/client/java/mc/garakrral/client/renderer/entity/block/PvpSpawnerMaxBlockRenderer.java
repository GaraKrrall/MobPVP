package mc.garakrral.client.renderer.entity.block;


import mc.garakrral.entity.block.PvpSpawnerMaxBlockEntity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;



public class PvpSpawnerMaxBlockRenderer implements BlockEntityRenderer<PvpSpawnerMaxBlockEntity> {

    private final MinecraftClient client = MinecraftClient.getInstance();

    public PvpSpawnerMaxBlockRenderer(BlockEntityRendererFactory.Context context) {
        // Renderer için context alınır, gerekli ise burada render dispatcher gibi şeyler alınabilir
    }

    @Override
    public void render(
            PvpSpawnerMaxBlockEntity entity,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {
        if (entity.getWorld() == null) return;
    }
}
