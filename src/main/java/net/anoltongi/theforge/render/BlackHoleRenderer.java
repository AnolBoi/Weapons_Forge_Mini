package net.anoltongi.theforge.render;

import net.anoltongi.theforge.entity.BlackHoleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

public class BlackHoleRenderer extends EntityRenderer<BlackHoleEntity> {
    // Using a vanilla texture as a placeholder
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/block/white_wool.png");

    public BlackHoleRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BlackHoleEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();

        // Rotate around Y-axis based on entityYaw
        matrixStack.mulPose(Axis.YP.rotationDegrees(-entityYaw));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);

        var renderType = RenderType.entityCutoutNoCull(TEXTURE);
        var vertexBuilder = buffer.getBuffer(renderType);

        float size = 1.0F;
        float half = size / 2.0F;

        var pose = matrixStack.last().pose();

        // A simple quad
        vertexBuilder.vertex(pose, -half, half, 0.0F)
                .color(1.0F, 1.0F, 1.0F, 1.0F)
                .uv(0.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(0.0F, 1.0F, 0.0F)
                .endVertex();

        vertexBuilder.vertex(pose, half, half, 0.0F)
                .color(1.0F, 1.0F, 1.0F, 1.0F)
                .uv(1.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(0.0F, 1.0F, 0.0F)
                .endVertex();

        vertexBuilder.vertex(pose, half, -half, 0.0F)
                .color(1.0F, 1.0F, 1.0F, 1.0F)
                .uv(1.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(0.0F, 1.0F, 0.0F)
                .endVertex();

        vertexBuilder.vertex(pose, -half, -half, 0.0F)
                .color(1.0F, 1.0F, 1.0F, 1.0F)
                .uv(0.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(0.0F, 1.0F, 0.0F)
                .endVertex();

        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackHoleEntity entity) {
        return TEXTURE;
    }
}