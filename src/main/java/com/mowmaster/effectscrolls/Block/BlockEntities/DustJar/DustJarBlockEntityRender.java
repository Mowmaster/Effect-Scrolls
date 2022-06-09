package com.mowmaster.effectscrolls.Block.BlockEntities.DustJar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mowmaster.dust.Block.BlockEntities.CustomDustBlock.CustomPowderedBlockEntity;
import com.mowmaster.dust.Capabilities.Dust.DustMagic;
import com.mowmaster.dust.DeferredRegistery.DeferredRegisterBlocks;
import com.mowmaster.dust.References.ColorReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.mowmaster.dust.References.Constants.MODID;

public class DustJarBlockEntityRender implements BlockEntityRenderer<DustJarBlockEntity> {

    public DustJarBlockEntityRender(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(DustJarBlockEntity p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) {
        if (!p_112307_.isRemoved())
        {
            Level level = p_112307_.getLevel();
            BlockPos pos = p_112307_.getPos();
            DustMagic magic = p_112307_.getStoredDust();
            double amount = (float)magic.getDustAmount();
            double capacity = (float)p_112307_.getDustCapacity();
            double heightRenderMultiplier = (double)(10.0 * (amount/capacity));
            List<Integer> colorList = ColorReference.getTrueColorFromInt(magic.getDustColor());

            @SuppressWarnings("deprecation")
            TextureAtlasSprite dustSprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(MODID, "util/crystal_dust"));

            if(p_112307_.hasDust())
            {
                //Render  height somewhere between 0.6875 (11/16) and 0.0625 (1/16) (this is 10/16ths gap)
                AABB aabb = new AABB(pos.getX() + 0.25, pos.getY() + 0.0625, pos.getZ() + 0.25,pos.getX() + 0.75, pos.getY() + (0.0625 + (double)(heightRenderMultiplier * 0.0625)), pos.getZ() + 0.75);
                renderFaces(dustSprite,pos,aabb,p_112309_, p_112310_.getBuffer(Sheets.translucentCullBlockSheet()), p_112307_, colorList.get(0),colorList.get(1),colorList.get(2), 255);
            }
        }
    }

    public void renderFaces(TextureAtlasSprite sprite, BlockPos pos, AABB aabb, PoseStack matrixStack, VertexConsumer buffer, DustJarBlockEntity blockEntity, int red, int green, int blue, int alpha)
    {

        Matrix4f matrix4f = matrixStack.last().pose();

        float minX = (float)(aabb.minX - pos.getX());
        float minY = (float)(aabb.minY - pos.getY());
        float minZ = (float)(aabb.minZ - pos.getZ());


        float maxX = (float)(aabb.maxX - pos.getX());
        float maxY = (float)(aabb.maxY - pos.getY());
        float maxZ = (float)(aabb.maxZ - pos.getZ());


        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();

        int uvBrightness = 240;

        //West
        buffer.vertex(matrix4f, minX , minY, maxZ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(-1, 0, 0).endVertex();
        buffer.vertex(matrix4f, minX , maxY, maxZ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(-1, 0, 0).endVertex();
        buffer.vertex(matrix4f, minX , maxY, minZ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(-1, 0, 0).endVertex();
        buffer.vertex(matrix4f, minX , minY, minZ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(-1, 0, 0).endVertex();

        buffer.vertex(matrix4f, minX , minY, minZ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(1, 0, 0).endVertex();
        buffer.vertex(matrix4f, minX , maxY, minZ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(1, 0, 0).endVertex();
        buffer.vertex(matrix4f, minX , maxY, maxZ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(1, 0, 0).endVertex();
        buffer.vertex(matrix4f, minX , minY, maxZ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(1, 0, 0).endVertex();


        //East
        buffer.vertex(matrix4f, maxX , minY, minZ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(1, 0, 0).endVertex();
        buffer.vertex(matrix4f, maxX , maxY, minZ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(1, 0, 0).endVertex();
        buffer.vertex(matrix4f, maxX , maxY, maxZ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(1, 0, 0).endVertex();
        buffer.vertex(matrix4f, maxX , minY, maxZ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(1, 0, 0).endVertex();

        buffer.vertex(matrix4f, maxX , minY, maxZ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(-1, 0, 0).endVertex();
        buffer.vertex(matrix4f, maxX , maxY, maxZ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(-1, 0, 0).endVertex();
        buffer.vertex(matrix4f, maxX , maxY, minZ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(-1, 0, 0).endVertex();
        buffer.vertex(matrix4f, maxX , minY, minZ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(-1, 0, 0).endVertex();


        //North
        buffer.vertex(matrix4f, minX, maxY, minZ ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, -1).endVertex();
        buffer.vertex(matrix4f, maxX, maxY, minZ ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, -1).endVertex();
        buffer.vertex(matrix4f, maxX, minY, minZ ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, -1).endVertex();
        buffer.vertex(matrix4f, minX, minY, minZ ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, -1).endVertex();

        buffer.vertex(matrix4f, minX, minY, minZ ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, 1).endVertex();
        buffer.vertex(matrix4f, maxX, minY, minZ ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, 1).endVertex();
        buffer.vertex(matrix4f, maxX, maxY, minZ ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, 1).endVertex();
        buffer.vertex(matrix4f, minX, maxY, minZ ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, 1).endVertex();


        //South
        buffer.vertex(matrix4f, minX, minY, maxZ ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, 1).endVertex();
        buffer.vertex(matrix4f, maxX, minY, maxZ ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, 1).endVertex();
        buffer.vertex(matrix4f, maxX, maxY, maxZ ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, 1).endVertex();
        buffer.vertex(matrix4f, minX, maxY, maxZ ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, 1).endVertex();

        buffer.vertex(matrix4f, minX, maxY, maxZ ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, -1).endVertex();
        buffer.vertex(matrix4f, maxX, maxY, maxZ ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, -1).endVertex();
        buffer.vertex(matrix4f, maxX, minY, maxZ ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, -1).endVertex();
        buffer.vertex(matrix4f, minX, minY, maxZ ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 0, -1).endVertex();


        //Bottom
        buffer.vertex(matrix4f, maxX, minY , minZ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, -1, 0).endVertex();
        buffer.vertex(matrix4f, maxX, minY , maxZ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, -1, 0).endVertex();
        buffer.vertex(matrix4f, minX, minY , maxZ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, -1, 0).endVertex();
        buffer.vertex(matrix4f, minX, minY , minZ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, -1, 0).endVertex();

        buffer.vertex(matrix4f, minX, minY , minZ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 1, 0).endVertex();
        buffer.vertex(matrix4f, minX, minY , maxZ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 1, 0).endVertex();
        buffer.vertex(matrix4f, maxX, minY , maxZ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 1, 0).endVertex();
        buffer.vertex(matrix4f, maxX, minY , minZ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 1, 0).endVertex();


        //Top
        buffer.vertex(matrix4f, minX, maxY , minZ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 1, 0).endVertex();
        buffer.vertex(matrix4f, minX, maxY , maxZ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 1, 0).endVertex();
        buffer.vertex(matrix4f, maxX, maxY , maxZ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 1, 0).endVertex();
        buffer.vertex(matrix4f, maxX, maxY , minZ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, 1, 0).endVertex();

        buffer.vertex(matrix4f, maxX, maxY , minZ).color(red, green, blue, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, -1, 0).endVertex();
        buffer.vertex(matrix4f, maxX, maxY , maxZ).color(red, green, blue, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, -1, 0).endVertex();
        buffer.vertex(matrix4f, minX, maxY , maxZ).color(red, green, blue, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, -1, 0).endVertex();
        buffer.vertex(matrix4f, minX, maxY , minZ).color(red, green, blue, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(uvBrightness).normal(0, -1, 0).endVertex();
    }

    @Override
    public boolean shouldRenderOffScreen(DustJarBlockEntity p_112306_) {
        return BlockEntityRenderer.super.shouldRenderOffScreen(p_112306_);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

    @Override
    public boolean shouldRender(DustJarBlockEntity p_173568_, Vec3 p_173569_) {
        return BlockEntityRenderer.super.shouldRender(p_173568_, p_173569_);
    }
}
