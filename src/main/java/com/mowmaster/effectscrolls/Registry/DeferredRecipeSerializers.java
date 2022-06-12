package com.mowmaster.effectscrolls.Registry;

import com.mowmaster.effectscrolls.Recipes.CrystalClusterFuelRecipe;
import com.mowmaster.effectscrolls.Recipes.CrystalClusterModifiers;
import com.mowmaster.effectscrolls.Recipes.CrystalNodeRecipe;
import com.mowmaster.mowlib.Recipes.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;


public final class DeferredRecipeSerializers
{

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    public static final RegistryObject<RecipeSerializer<CrystalNodeRecipe>> CRYSTAL_NODE_RECIPE_SERIALIZER =
            SERIALIZERS.register("crystalnode", () -> CrystalNodeRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CrystalClusterFuelRecipe>> CLUSTER_FUEL_RECIPE_SERIALIZER =
            SERIALIZERS.register("cluster_fuel", () -> CrystalClusterFuelRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CrystalClusterModifiers>> CRYSTAL_MODIFIERS_RECIPE_SERIALIZER =
            SERIALIZERS.register("cluster_modifier", () -> CrystalClusterModifiers.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
