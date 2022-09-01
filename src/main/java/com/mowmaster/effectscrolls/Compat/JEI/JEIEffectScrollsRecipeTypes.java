package com.mowmaster.effectscrolls.Compat.JEI;

import com.mowmaster.effectscrolls.Recipes.CrystalClusterFuelRecipe;
import com.mowmaster.effectscrolls.Recipes.CrystalClusterModifiers;
import com.mowmaster.effectscrolls.Recipes.CrystalNodeRecipe;
import com.mowmaster.mowlib.Recipes.*;
import mezz.jei.api.recipe.RecipeType;

import static com.mowmaster.mowlib.MowLibUtils.MowLibReferences.MODID;

public class JEIEffectScrollsRecipeTypes
{
    public static final RecipeType<CrystalNodeRecipe> CRYSTAL_NODE_CRAFING =
            RecipeType.create(MODID, "crystal_node_crafting", CrystalNodeRecipe.class);

    public static final RecipeType<CrystalClusterFuelRecipe> CRYSTAL_CLUSTER_FUEL =
            RecipeType.create(MODID, "crystal_cluster_fuels", CrystalClusterFuelRecipe.class);

    public static final RecipeType<CrystalClusterModifiers> CRYSTAL_CLUSTER_MODIFIERS =
            RecipeType.create(MODID, "crystal_cluster_modifiers", CrystalClusterModifiers.class);

    public static final RecipeType<CrystalClusterModifiers> SCROLL_CRAFTING_RECIPES =
            RecipeType.create(MODID, "scroll_crafting_recipes", CrystalClusterModifiers.class);

}
