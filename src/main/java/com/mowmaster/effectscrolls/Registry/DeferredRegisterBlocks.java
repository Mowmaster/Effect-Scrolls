package com.mowmaster.effectscrolls.Registry;

import com.mojang.serialization.Codec;
import com.mowmaster.effectscrolls.Blocks.BaseColoredCrystalBlock;
import com.mowmaster.effectscrolls.Blocks.BaseColoredCrystalBlockItem;
import com.mowmaster.effectscrolls.Blocks.BlockEntities.DustJar.DustJarBlockItem;
import com.mowmaster.effectscrolls.Blocks.GeneratedBlocks.BaseCrystalClusterBlock;
import com.mowmaster.effectscrolls.Blocks.GeneratedBlocks.BaseCrystalNodeBlock;
import com.mowmaster.mowlib.Blocks.BaseBlocks.BaseWorkStationBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;
import static com.mowmaster.effectscrolls.Tabs.EffectScrollsTab.TAB_EFFECTSCROLLS;

public class DeferredRegisterBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    //public static final PlacementModifierType<GeodeDecorator> RNG_DECORATOR = register("dust_rng_initializer", GeodeDecorator.CODEC);
    //public static final DeferredRegister<FeatureDecorator<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.DECORATORS, MOD_ID);
    //public static final RegistryObject<FeatureDecorator<NoneDecoratorConfiguration>> RNG_DECORATOR = DECORATORS.register("rng_initializer", GeodeDecorator::new);

    public static final RegistryObject<Block> CRYSTAL_NODE = registerBlockCrystal("block_inertnode",
            () -> new BaseCrystalNodeBlock(BlockBehaviour.Properties.of(Material.AMETHYST).randomTicks().strength(50.0F, 1200.0F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().lightLevel((p_152629_) -> { return 10; })),".crystal_node");

    public static final RegistryObject<Block> CRYSTAL_CLUSTER_FULL = registerBlockCrystal("block_inertcluster_full",
            () -> new BaseCrystalClusterBlock(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST).strength(1.0F).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().lightLevel((p_152629_) -> { return 10; })),".crystal_cluster");
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_LARGE = registerBlockCrystal("block_inertcluster_large",
            () -> new BaseCrystalClusterBlock(5, 3, BlockBehaviour.Properties.of(Material.AMETHYST).strength(0.75F).sound(SoundType.LARGE_AMETHYST_BUD).requiresCorrectToolForDrops().lightLevel((p_152629_) -> { return 7; })),".crystal_cluster");
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_MEDIUM = registerBlockCrystal("block_inertcluster_medium",
            () -> new BaseCrystalClusterBlock(4, 3, BlockBehaviour.Properties.of(Material.AMETHYST).strength(0.5F).sound(SoundType.MEDIUM_AMETHYST_BUD).requiresCorrectToolForDrops().lightLevel((p_152629_) -> { return 5; })),".crystal_cluster");
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_SMALL = registerBlockCrystal("block_inertcluster_small",
            () -> new BaseCrystalClusterBlock(3, 4, BlockBehaviour.Properties.of(Material.AMETHYST).strength(0.25F).sound(SoundType.SMALL_AMETHYST_BUD).requiresCorrectToolForDrops().lightLevel((p_152629_) -> { return 2; })),".crystal_cluster");

    public static final RegistryObject<Block> CRYSTAL_BLOCK = registerBlockCrystal("block_crystal",
            () -> new BaseColoredCrystalBlock(BlockBehaviour.Properties.of(Material.AMETHYST).strength(1.0F).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel((p_152629_) -> { return 10; })),".crystal_block");
    public static final RegistryObject<Block> CRYSTAL_BLOCK_COMPACT = registerBlockCrystal("block_crystal_compact",
            () -> new BaseColoredCrystalBlock(BlockBehaviour.Properties.of(Material.AMETHYST).strength(1.0F).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel((p_152629_) -> { return 15; })),".crystal_block_compact");

    public static final RegistryObject<Block> BASE_WORKSTATION_BLOCK = registerBlock("block_workstation_base",
            () -> new BaseWorkStationBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        DeferredRegisterItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(TAB_EFFECTSCROLLS)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    //For Geodes
    private static <P extends PlacementModifier> PlacementModifierType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, name, () -> {
            return codec;
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlockCrystal(String name, Supplier<T> block, String text) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockCrystalItem(name, toReturn, text);
        return toReturn;
    }

    private static <T extends Block> void registerBlockCrystalItem(String name, RegistryObject<T> block, String text) {
        DeferredRegisterItems.ITEMS.register(name, () -> new BaseColoredCrystalBlockItem(block.get(),
                new Item.Properties().tab(TAB_EFFECTSCROLLS), text));
    }
}
