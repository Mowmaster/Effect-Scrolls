package com.mowmaster.effectscrolls.Compat.JEI.recipes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mowmaster.effectscrolls.Compat.JEI.JEIEffectScrollsRecipeTypes;
import com.mowmaster.effectscrolls.EffectScrollsUtils.References;
import com.mowmaster.effectscrolls.Recipes.CrystalClusterModifiers;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterBlocks;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterItems;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterTileBlocks;
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
import net.minecraft.world.item.Items;

public class ScrollCraftingRecipeCategory implements IRecipeCategory<CrystalClusterModifiers>
{
    private final IDrawable background;
    private final Component localizedName;
    //private final IDrawable overlay;
    private final IDrawable icon;
    private final ItemStack renderStack = new ItemStack(DeferredRegisterItems.EFFECT_SCROLL.get());

    public ScrollCraftingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(
                new ResourceLocation(References.MODID, "textures/gui/jei/scroll_crafting_recipe.png"), 0, 0, 180, 128);
        this.localizedName = Component.translatable(References.MODID + ".jei.scroll_crafting");
        //this.overlay =
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, this.renderStack);
        //this.renderStack.getOrCreateTag().putBoolean("RenderFull", true);
    }

    @Override
    public RecipeType<CrystalClusterModifiers> getRecipeType() {
        return JEIEffectScrollsRecipeTypes.SCROLL_CRAFTING_RECIPES;
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
    public void setRecipe(IRecipeLayoutBuilder builder, CrystalClusterModifiers recipe, IFocusGroup focuses) {

        //Gold Nuggies
        builder.addSlot(RecipeIngredientRole.INPUT, 17, 13)
                .addItemStack(new ItemStack(Items.GOLD_NUGGET));
        //Paper
        builder.addSlot(RecipeIngredientRole.INPUT, 17, 33)
                .addItemStack(new ItemStack(Items.PAPER));
        //Modifiers
        builder.addSlot(RecipeIngredientRole.INPUT, 128, 13)
                .addIngredients(recipe.getIngredients().get(0));
        //Dust
        builder.addSlot(RecipeIngredientRole.INPUT, 128, 33)
                .addItemStack(new ItemStack(DeferredRegisterItems.COLORED_CRYSTAL_DUST.get()));
        //Crafter
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 74,63)
                .addItemStack(new ItemStack(DeferredRegisterTileBlocks.BLOCK_CRAFTER_SCROLL_T15.get()));
        //String
        builder.addSlot(RecipeIngredientRole.INPUT, 107, 63)
                .addItemStack(new ItemStack(Items.STRING));
        //Result (Scroll)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 74, 95)
                .addItemStack(new ItemStack(DeferredRegisterItems.EFFECT_SCROLL.get()));
    }

    @Override
    public void draw(CrystalClusterModifiers recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
    }
}
