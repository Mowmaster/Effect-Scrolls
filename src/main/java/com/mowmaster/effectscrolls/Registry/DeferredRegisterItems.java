package com.mowmaster.effectscrolls.Registry;

import com.mowmaster.effectscrolls.Items.ColoredCrystalBase;
import com.mowmaster.effectscrolls.Items.ColoredCrystalDustBase;
import com.mowmaster.effectscrolls.Items.ScrollBase;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;
import static com.mowmaster.effectscrolls.Tabs.EffectScrollsTab.TAB_EFFECTSCROLLS;

public class DeferredRegisterItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> COLORED_CRYSTAL = ITEMS.register("item_crystal",
            () -> new ColoredCrystalBase(new Item.Properties().stacksTo(64).tab(TAB_EFFECTSCROLLS)));
    public static final RegistryObject<Item> COLORED_CRYSTAL_DUST = ITEMS.register("item_crystal_dust",
            () -> new ColoredCrystalDustBase(new Item.Properties().stacksTo(64).tab(TAB_EFFECTSCROLLS)));

    public static final RegistryObject<Item> COLORED_NODE_MAKER = ITEMS.register("item_nodemaker",
            () -> new ColoredCrystalBase(new Item.Properties().stacksTo(64).tab(TAB_EFFECTSCROLLS)));

    public static final RegistryObject<Item> EFFECT_SCROLL = ITEMS.register("scroll_effect",
            () -> new ScrollBase(new Item.Properties().tab(TAB_EFFECTSCROLLS)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
