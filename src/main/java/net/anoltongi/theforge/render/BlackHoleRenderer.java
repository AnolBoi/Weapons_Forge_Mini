package net.anoltongi.theforge.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.anoltongi.theforge.TheForgeMod;
import net.anoltongi.theforge.entity.BlackHoleEntity;
import net.anoltongi.theforge.client.model.black_hole;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BlackHoleRenderer extends EntityRenderer<BlackHoleEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TheForgeMod.MOD_ID, "textures/entity/black_hole.png");
    private final black_hole<BlackHoleEntity> model;

    public BlackHoleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new black_hole<>(context.bakeLayer(black_hole.LAYER_LOCATION));
    }

    @Override
    public void render(BlackHoleEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        var vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackHoleEntity entity) {
        return TEXTURE;
    }
}