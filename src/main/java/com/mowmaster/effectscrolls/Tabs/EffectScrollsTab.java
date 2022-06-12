package com.mowmaster.effectscrolls.Tabs;

import com.mowmaster.effectscrolls.Registry.DeferredRegisterItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EffectScrollsTab extends CreativeModeTab
{
    public EffectScrollsTab() {
        super("tab_effectscrollstab");
    }

    public static final EffectScrollsTab TAB_EFFECTSCROLLS = new EffectScrollsTab() {};

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(DeferredRegisterItems.EFFECT_SCROLL.get());
    }
}
