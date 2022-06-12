package com.mowmaster.effectscrolls.Registry;


import com.mowmaster.effectscrolls.Blocks.BlockEntities.DustJar.DustJarBlockItem;
import com.mowmaster.effectscrolls.Items.ScrollBase;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;

public class ItemModelPropertiesEffectScrolls
{
    public static void effectscrollsItemModes(Item item)
    {
        ItemProperties.register(item, new ResourceLocation(MODID + ":scroll_mode"),(p_174625_, p_174626_, p_174627_, p_174628_) -> {
            return ScrollBase.getRenderType(p_174625_);});


        ItemProperties.register(item, new ResourceLocation(MODID + ":jar_filled"),(p_174625_, p_174626_, p_174627_, p_174628_) -> {
            return DustJarBlockItem.getFillLevel(p_174625_);});
    }
}
