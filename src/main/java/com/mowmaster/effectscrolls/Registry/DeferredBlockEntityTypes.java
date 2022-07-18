package com.mowmaster.effectscrolls.Registry;


import com.mowmaster.effectscrolls.Blocks.BlockEntities.CrystalCluster.EffectCrystalClusterBlockEntity;
import com.mowmaster.effectscrolls.Blocks.BlockEntities.DustJar.DustJarBlockEntity;
import com.mowmaster.effectscrolls.Blocks.BlockEntities.ScrollCrafter.ScrollCrafterBlockEntity_T15;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;


public class DeferredBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);



    public static final RegistryObject<BlockEntityType<EffectCrystalClusterBlockEntity>> CLUSTER = BLOCK_ENTITIES.register(
            "block_entity_cluster",
            () -> BlockEntityType.Builder.of(EffectCrystalClusterBlockEntity::new, DeferredRegisterTileBlocks.BLOCK_CRYSTAL_CLUSTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<DustJarBlockEntity>> DUST_JAR = BLOCK_ENTITIES.register(
            "block_entity_dust_jar",
            () -> BlockEntityType.Builder.of(DustJarBlockEntity::new, DeferredRegisterTileBlocks.BLOCK_DUST_JAR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ScrollCrafterBlockEntity_T15>> CRAFTER_SCROLL_T15 = BLOCK_ENTITIES.register(
            "block_entity_crafter_scroll_t15",
            () -> BlockEntityType.Builder.of(ScrollCrafterBlockEntity_T15::new, DeferredRegisterTileBlocks.BLOCK_CRAFTER_SCROLL_T15.get()).build(null));


    private DeferredBlockEntityTypes() {
    }
}
