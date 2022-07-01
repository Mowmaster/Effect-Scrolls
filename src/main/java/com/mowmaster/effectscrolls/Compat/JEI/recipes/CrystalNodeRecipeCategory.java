package com.mowmaster.effectscrolls.Compat.JEI.recipes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mowmaster.effectscrolls.Compat.JEI.JEIEffectScrollsRecipeTypes;
import com.mowmaster.effectscrolls.EffectScrollsUtils.References;
import com.mowmaster.effectscrolls.Recipes.CrystalNodeRecipe;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CrystalNodeRecipeCategory implements IRecipeCategory<CrystalNodeRecipe>
{
    private final IDrawable background;
    private final Component localizedName;
    //private final IDrawable overlay;
    private final IDrawable icon;
    private final ItemStack renderStack = new ItemStack(DeferredRegisterBlocks.CRYSTAL_NODE.get());

    public CrystalNodeRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(
                new ResourceLocation(References.MODID, "textures/gui/jei/node_crafting.png"), 0, 0, 128, 64);
        this.localizedName = Component.translatable(References.MODID + ".jei.node_crafting");
        //this.overlay =
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, this.renderStack);
        //this.renderStack.getOrCreateTag().putBoolean("RenderFull", true);
    }

    @Override
    public RecipeType<CrystalNodeRecipe> getRecipeType() {
        return JEIEffectScrollsRecipeTypes.CRYSTAL_NODE_CRAFING;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrystalNodeRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 56,24)
                .addItemStack(new ItemStack(DeferredRegisterBlocks.CRYSTAL_NODE.get()));

        //Block Below
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 24)
                .addIngredients(recipe.getIngredients().get(0));

        //Result
        builder.addSlot(RecipeIngredientRole.OUTPUT, 87, 24)
                .addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(CrystalNodeRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
    }
}
