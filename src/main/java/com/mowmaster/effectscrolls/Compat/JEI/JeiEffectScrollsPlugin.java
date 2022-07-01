package com.mowmaster.effectscrolls.Compat.JEI;

import com.mowmaster.effectscrolls.Compat.JEI.recipes.CrystalClusterFuelCategory;
import com.mowmaster.effectscrolls.Compat.JEI.recipes.CrystalClusterModifierCategory;
import com.mowmaster.effectscrolls.Compat.JEI.recipes.CrystalNodeRecipeCategory;
import com.mowmaster.effectscrolls.EffectScrollsUtils.References;
import com.mowmaster.effectscrolls.Recipes.CrystalClusterFuelRecipe;
import com.mowmaster.effectscrolls.Recipes.CrystalClusterModifiers;
import com.mowmaster.effectscrolls.Recipes.CrystalNodeRecipe;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterBlocks;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterItems;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterTileBlocks;
import com.mowmaster.mowlib.Compat.JEI.JEISettings;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;


@mezz.jei.api.JeiPlugin
public class JeiEffectScrollsPlugin implements IModPlugin
{
    protected static IJeiRuntime runtime;

    public static IJeiRuntime getJeiRuntime() {
        return runtime;
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(References.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CrystalNodeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrystalClusterModifierCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrystalClusterFuelCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        RecipeManager recipeManager = level.getRecipeManager();

        List<CrystalNodeRecipe> crystalNodeRecipes = recipeManager.getAllRecipesFor(CrystalNodeRecipe.Type.INSTANCE);
        registration.addRecipes(JEIEffectScrollsRecipeTypes.CRYSTAL_NODE_CRAFING, crystalNodeRecipes);

        List<CrystalClusterModifiers> crystalClusterModifier = recipeManager.getAllRecipesFor(CrystalClusterModifiers.Type.INSTANCE);
        registration.addRecipes(JEIEffectScrollsRecipeTypes.CRYSTAL_CLUSTER_MODIFIERS, crystalClusterModifier);

        List<CrystalClusterFuelRecipe> crystalClusterFuel = recipeManager.getAllRecipesFor(CrystalClusterFuelRecipe.Type.INSTANCE);
        registration.addRecipes(JEIEffectScrollsRecipeTypes.CRYSTAL_CLUSTER_FUEL, crystalClusterFuel);


        //this.registerIngredientInfo(registration, DeferredRegisterItems.COLOR_APPLICATOR.get());
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        IStackHelper stackHelper = registration.getJeiHelpers().getStackHelper();
        IRecipeTransferHandlerHelper handlerHelper = registration.getTransferHelper();
        /*
        registration.addUniversalRecipeTransferHandler(new StorageControllerRecipeTransferHandler<>(
                StorageControllerContainer.class, handlerHelper));
        */
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(DeferredRegisterBlocks.CRYSTAL_NODE.get()),
                JEIEffectScrollsRecipeTypes.CRYSTAL_NODE_CRAFING);

        registration.addRecipeCatalyst(new ItemStack(DeferredRegisterTileBlocks.BLOCK_CRAFTER_SCROLL_T15.get()),
                JEIEffectScrollsRecipeTypes.CRYSTAL_CLUSTER_MODIFIERS);

        registration.addRecipeCatalyst(new ItemStack(DeferredRegisterTileBlocks.BLOCK_CRYSTAL_CLUSTER.get()),
                JEIEffectScrollsRecipeTypes.CRYSTAL_CLUSTER_FUEL);

    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JeiEffectScrollsPlugin.runtime = jeiRuntime;
        JEISettings.setJeiLoaded(true);
    }

    public void registerIngredientInfo(IRecipeRegistration registration, ItemLike ingredient) {
        registration.addIngredientInfo(new ItemStack(ingredient.asItem()), VanillaTypes.ITEM_STACK,
                Component.translatable("jei." + References.MODID + ".item." + ForgeRegistries.ITEMS.getKey(ingredient.asItem()).getPath() + ".description"));
    }

}
