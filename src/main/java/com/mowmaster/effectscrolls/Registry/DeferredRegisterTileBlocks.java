package com.mowmaster.effectscrolls.Registry;


import com.mowmaster.effectscrolls.Blocks.BlockEntities.CrystalCluster.EffectCrystalClusterBlock;
import com.mowmaster.effectscrolls.Blocks.BlockEntities.DustJar.DustJarBlock;
import com.mowmaster.effectscrolls.Blocks.BlockEntities.DustJar.DustJarBlockItem;
import com.mowmaster.effectscrolls.Blocks.BlockEntities.ScrollCrafter.ScrollCrafterBlock_T15;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;
import static com.mowmaster.effectscrolls.Tabs.EffectScrollsTab.TAB_EFFECTSCROLLS;


public class DeferredRegisterTileBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);



    public static final RegistryObject<Block> BLOCK_CRYSTAL_CLUSTER = registerBlock("block_crystal_cluster",
            () -> new EffectCrystalClusterBlock(BlockBehaviour.Properties.of(Material.AMETHYST).strength(2.0F).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> BLOCK_DUST_JAR = registerBlockJar("block_dust_jar",
            () -> new DustJarBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.25F).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> BLOCK_CRAFTER_SCROLL_T15 = registerBlock("block_crafter_scroll_t15",
            () -> new ScrollCrafterBlock_T15(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F).sound(SoundType.STONE)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        DeferredRegisterItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(TAB_EFFECTSCROLLS)));
    }

    private static <T extends Block> RegistryObject<T> registerBlockJar(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerJarBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerJarBlockItem(String name, RegistryObject<T> block) {
        DeferredRegisterItems.ITEMS.register(name, () -> new DustJarBlockItem(block.get(),
                new Item.Properties().tab(TAB_EFFECTSCROLLS)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
