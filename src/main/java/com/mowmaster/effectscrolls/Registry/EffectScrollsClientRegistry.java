package com.mowmaster.effectscrolls.Registry;

import com.mowmaster.effectscrolls.Blocks.BlockEntities.CrystalCluster.EffectCrystalClusterBlockEntityRenderer;
import com.mowmaster.effectscrolls.Blocks.BlockEntities.DustJar.DustJarBlockEntityRender;
import com.mowmaster.effectscrolls.Blocks.BlockEntities.DustJar.DustJarBlockItem;
import com.mowmaster.effectscrolls.Blocks.BlockEntities.ScrollCrafter.ScrollCrafterBlockEntityRender_T15;
import com.mowmaster.effectscrolls.EffectScrollsUtils.References;
import com.mowmaster.effectscrolls.Items.ScrollBase;
import com.mowmaster.mowlib.MowLibUtils.ColorReference;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.NamedRenderTypeManager;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "effectscrolls", bus = Mod.EventBusSubscriber.Bus.MOD)
public class EffectScrollsClientRegistry
{

    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {

        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterItems.COLORED_CRYSTAL.get());
        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterItems.COLORED_CRYSTAL_DUST.get());



        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_NODE.get());
        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_CLUSTER_FULL.get());
        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get());
        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get());
        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get());

        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_BLOCK.get());
        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_BLOCK_COMPACT.get());

        event.getItemColors().register((stack, color) ->
        {
            if (color == 1) {
                return ScrollBase.getColorRender(stack);
            }
            else if (color == 2) {
                return ScrollBase.getColorRenderRibbon(stack);
            }
            else if (color == 3) {
                return ScrollBase.getColorRenderCoin(stack);
            }
            else {return -1;}

        }, DeferredRegisterItems.EFFECT_SCROLL.get());


        ItemModelPropertiesEffectScrolls.effectscrollsItemModes(DeferredRegisterItems.EFFECT_SCROLL.get());

        ItemModelPropertiesEffectScrolls.effectscrollsItemModes(DeferredRegisterTileBlocks.BLOCK_DUST_JAR.get().asItem());


        /*
        *
        * TILE ENTITY BLOCKS HERE
        *
         */
        event.getItemColors().register((stack, color) ->
        {
            if (color == 1) {
                return ColorReference.getColorFromItemStackInt(stack);
            }
            else if (color == 2) {
                return DustJarBlockItem.getFillColor(stack);
            }
            else {return -1;}
        }, DeferredRegisterTileBlocks.BLOCK_DUST_JAR.get());
        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterTileBlocks.BLOCK_CRAFTER_SCROLL_T15.get());
        event.getItemColors().register((stack, color) ->
        {if (color == 1) {return ColorReference.getColorFromItemStackInt(stack);} else {return -1;}}, DeferredRegisterBlocks.BASE_WORKSTATION_BLOCK.get());

    }

    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {

        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_NODE.get());

        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_CLUSTER_FULL.get());
        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get());
        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get());
        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get());

        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_BLOCK.get());
        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterBlocks.CRYSTAL_BLOCK_COMPACT.get());

        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterBlocks.BASE_WORKSTATION_BLOCK.get());

        /*
         *
         * TILE ENTITY BLOCKS HERE
         *
         */

        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterTileBlocks.BLOCK_DUST_JAR.get());

        event.getBlockColors().register((blockstate, blockReader, blockPos, color) ->
        {if (color == 1) {return ColorReference.getColorFromStateInt(blockstate);} else {return -1;}}, DeferredRegisterTileBlocks.BLOCK_CRAFTER_SCROLL_T15.get());

    }

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public static void textureStitchPreEvent(TextureStitchEvent.Pre event)
    {

        ResourceLocation location = event.getAtlas().location();

        if(location.equals(TextureAtlas.LOCATION_BLOCKS))
        {
            event.addSprite(new ResourceLocation(References.MODID, "util/crystal_dust"));
        }

    }

    /*@SubscribeEvent
    public static void registerLayers(FMLClientSetupEvent event)
    {
        *//*NamedRenderTypeManager.preRegisterVanillaRenderTypes()
        blockRenderTypes.put(new ResourceLocation("solid"), new RenderTypeGroup(RenderType.solid(), ForgeRenderTypes.ITEM_LAYERED_SOLID.get()));
        blockRenderTypes.put(new ResourceLocation("cutout"), new RenderTypeGroup(RenderType.cutout(), ForgeRenderTypes.ITEM_LAYERED_CUTOUT.get()));
        blockRenderTypes.put(new ResourceLocation("cutout_mipped"), new RenderTypeGroup(RenderType.cutoutMipped(), ForgeRenderTypes.ITEM_LAYERED_CUTOUT_MIPPED.get()));
        blockRenderTypes.put(new ResourceLocation("translucent"), new RenderTypeGroup(RenderType.translucent(), ForgeRenderTypes.ITEM_LAYERED_TRANSLUCENT.get()));
        blockRenderTypes.put(new ResourceLocation("tripwire"), new RenderTypeGroup(RenderType.tripwire(), ForgeRenderTypes.ITEM_LAYERED_TRANSLUCENT.get()));*//*
        ItemBlockRenderTypes.setRenderLayer(DeferredRegisterBlocks.BASE_WORKSTATION_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DeferredRegisterTileBlocks.BLOCK_CRAFTER_SCROLL_T15.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DeferredRegisterTileBlocks.BLOCK_DUST_JAR.get(), RenderType.cutout());
    }*/

    public static void registerBlockEntityRenderers()
    {
        BlockEntityRenderers.register(DeferredBlockEntityTypes.CLUSTER.get(), EffectCrystalClusterBlockEntityRenderer::new);
        BlockEntityRenderers.register(DeferredBlockEntityTypes.DUST_JAR.get(), DustJarBlockEntityRender::new);
        BlockEntityRenderers.register(DeferredBlockEntityTypes.CRAFTER_SCROLL_T15.get(), ScrollCrafterBlockEntityRender_T15::new);
    }
}
