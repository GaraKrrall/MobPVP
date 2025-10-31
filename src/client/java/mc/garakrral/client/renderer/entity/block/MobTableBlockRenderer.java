package mc.garakrral.client.renderer.entity.block;

import mc.garakrral.entity.block.MobTableBlockEntity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;


public class MobTableBlockRenderer implements BlockEntityRenderer<MobTableBlockEntity> {

    private final MinecraftClient client = MinecraftClient.getInstance();

    public MobTableBlockRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(
            MobTableBlockEntity entity,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {
        if (entity.getWorld() == null) return;
    }
}
