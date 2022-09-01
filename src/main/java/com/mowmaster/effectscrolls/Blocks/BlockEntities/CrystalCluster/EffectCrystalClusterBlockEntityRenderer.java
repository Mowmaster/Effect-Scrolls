package com.mowmaster.effectscrolls.Blocks.BlockEntities.CrystalCluster;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterBlocks;
import com.mowmaster.mowlib.Blocks.BaseBlocks.BaseColoredBlock;
import com.mowmaster.mowlib.MowLibUtils.MowLibColorReference;
import com.mowmaster.mowlib.api.IColorableBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


import static com.mowmaster.effectscrolls.Blocks.BlockEntities.CrystalCluster.EffectCrystalClusterBlock.FACING;

public class EffectCrystalClusterBlockEntityRenderer implements BlockEntityRenderer<EffectCrystalClusterBlockEntity> {

    public EffectCrystalClusterBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(EffectCrystalClusterBlockEntity p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) {
        if (!p_112307_.isRemoved())
        {
            Direction facing = p_112307_.getBlockState().getValue(FACING);
            ItemStack baseBlock = p_112307_.getBaseBlock();
            int crystalCount = p_112307_.crystalCount();
            int clusterColor = p_112307_.getCurrentColor();
            BlockState getClusterState = getCrystalToRender(crystalCount, clusterColor);
            Level level = p_112307_.getLevel();
            BlockPos pos = p_112307_.getPos();

            if(facing== Direction.UP)
            {
                renderBase(level,pos,baseBlock,p_112309_,p_112310_,0.09375f, 0.0f, 0.09375f, 0.0f,p_112311_,p_112312_);
                renderCrystal(level,pos,clusterColor,getClusterState,p_112309_,p_112310_,0.0f, 0.125f, 0.0f, 0.0f,p_112311_,p_112312_);
            }
            if(facing== Direction.DOWN) {
                p_112309_.mulPose(Vector3f.ZP.rotationDegrees(180));
                p_112309_.translate(0, -1, 0);
                p_112309_.translate(-1, 0, 0);
                renderBase(level,pos,baseBlock,p_112309_,p_112310_,0.09375f, 0.0f, 0.09375f, 0.0f,p_112311_,p_112312_);
                renderCrystal(level,pos, clusterColor,getClusterState,p_112309_,p_112310_,0.0f, 0.125f, 0.0f, 0.0f,p_112311_,p_112312_);
            }
            if(facing== Direction.NORTH) {
                p_112309_.mulPose(Vector3f.XP.rotationDegrees(270));
                p_112309_.translate(0, -1, 0);
                renderBase(level,pos,baseBlock,p_112309_,p_112310_,0.09375f, 0.0f, 0.09375f, 0.0f,p_112311_,p_112312_);
                renderCrystal(level,pos,clusterColor,getClusterState,p_112309_,p_112310_,0.0f, 0.125f, 0.0f, 0.0f,p_112311_,p_112312_);
            }
            if(facing== Direction.EAST) {
                p_112309_.mulPose(Vector3f.ZP.rotationDegrees(270));
                p_112309_.translate(-1, 0, 0);
                renderBase(level,pos,baseBlock,p_112309_,p_112310_,0.09375f, 0.0f, 0.09375f, 0.0f,p_112311_,p_112312_);
                renderCrystal(level, pos,clusterColor,getClusterState,p_112309_,p_112310_,0.0f, 0.125f, 0.0f, 0.0f,p_112311_,p_112312_);
            }
            if(facing== Direction.SOUTH) {
                p_112309_.mulPose(Vector3f.XP.rotationDegrees(90));
                p_112309_.translate(0, 0, -1);
                renderBase(level,pos,baseBlock,p_112309_,p_112310_,0.09375f, 0.0f, 0.09375f, 0.0f,p_112311_,p_112312_);
                renderCrystal(level,pos,clusterColor,getClusterState,p_112309_,p_112310_,0.0f, 0.125f, 0.0f, 0.0f,p_112311_,p_112312_);
            }
            if(facing== Direction.WEST) {
                p_112309_.mulPose(Vector3f.ZP.rotationDegrees(90));
                p_112309_.translate(0, -1, 0);
                renderBase(level,pos,baseBlock,p_112309_,p_112310_,0.09375f, 0.0f, 0.09375f, 0.0f,p_112311_,p_112312_);
                renderCrystal(level,pos,clusterColor,getClusterState,p_112309_,p_112310_,0.0f, 0.125f, 0.0f, 0.0f,p_112311_,p_112312_);
            }
        }
    }

    public static void renderBase(Level level, BlockPos pos,ItemStack baseBlockStack, PoseStack p_112309_, MultiBufferSource p_112310_, float x, float y, float z, float angle, int p_112311_, int p_112312_) {
        p_112309_.pushPose();
        p_112309_.translate(x, y, z);
        p_112309_.scale(0.8125f, 0.125f, 0.8125f);
        p_112309_.mulPose(Vector3f.YP.rotationDegrees(angle));
        BlockRenderDispatcher renderBaseBlock = Minecraft.getInstance().getBlockRenderer();
        BlockState getBlockBaseState = Block.byItem(baseBlockStack.getItem()).defaultBlockState();
        if(getBlockBaseState.getBlock() instanceof BaseColoredBlock)
        {
            //MowLibColorReference.getColorFromItemStackInt(baseBlockStack)
            int colorValue = MowLibColorReference.getColorFromItemStackInt(baseBlockStack);
            VertexConsumer color = p_112310_.getBuffer(RenderType.translucent()).color(colorValue);
            renderBaseBlock.renderBatched(MowLibColorReference.addColorToBlockState(getBlockBaseState,colorValue),pos,level,p_112309_,color,true, RandomSource.create());
        }
        else { renderBaseBlock.renderSingleBlock(getBlockBaseState,p_112309_,p_112310_,p_112311_,p_112312_); }
        //BakedModel baked = renderBaseBlock.getBlockModel(Blocks.STONE.defaultBlockState());
        p_112309_.popPose();
    }

    public static void renderCrystal(Level level, BlockPos pos,int intcolor, BlockState stateBaseBlock, PoseStack p_112309_, MultiBufferSource p_112310_, float x, float y, float z, float angle, int p_112311_, int p_112312_) {
        p_112309_.pushPose();
        p_112309_.translate(x, y, z);
        p_112309_.scale(1.0f, 1.0f, 1.0f);
        p_112309_.mulPose(Vector3f.YP.rotationDegrees(angle));
        BlockRenderDispatcher renderBaseBlock = Minecraft.getInstance().getBlockRenderer();
        VertexConsumer color = p_112310_.getBuffer(RenderType.translucent()).color(intcolor);
        renderBaseBlock.renderBatched(stateBaseBlock,pos,level,p_112309_,color,true, RandomSource.create());
        //renderBaseBlock.renderSingleBlock(stateBaseBlock,p_112309_,p_112310_,p_112311_,p_112312_);
        p_112309_.popPose();
    }

    public BlockState getCrystalToRender(int crystalCount, int color)
    {
        switch(crystalCount)
        {
            case 1: return MowLibColorReference.addColorToBlockState(DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get().defaultBlockState(),color);
            case 2: return MowLibColorReference.addColorToBlockState(DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get().defaultBlockState(),color);
            case 3: return MowLibColorReference.addColorToBlockState(DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get().defaultBlockState(),color);
            case 4: return MowLibColorReference.addColorToBlockState(DeferredRegisterBlocks.CRYSTAL_CLUSTER_FULL.get().defaultBlockState(),color);
            default: return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(EffectCrystalClusterBlockEntity p_112306_) {
        return BlockEntityRenderer.super.shouldRenderOffScreen(p_112306_);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

    @Override
    public boolean shouldRender(EffectCrystalClusterBlockEntity p_173568_, Vec3 p_173569_) {
        return BlockEntityRenderer.super.shouldRender(p_173568_, p_173569_);
    }
}
