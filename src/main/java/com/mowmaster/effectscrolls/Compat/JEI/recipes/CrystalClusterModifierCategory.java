package com.mowmaster.effectscrolls.Compat.JEI.recipes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mowmaster.effectscrolls.Compat.JEI.JEIEffectScrollsRecipeTypes;
import com.mowmaster.effectscrolls.EffectScrollsUtils.References;
import com.mowmaster.effectscrolls.Recipes.CrystalClusterModifiers;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterBlocks;
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
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CrystalClusterModifierCategory implements IRecipeCategory<CrystalClusterModifiers>
{
    private final IDrawable background;
    private final Component localizedName;
    //private final IDrawable overlay;
    private final IDrawable icon;
    private final ItemStack renderStack = new ItemStack(DeferredRegisterTileBlocks.BLOCK_CRAFTER_SCROLL_T15.get());

    public CrystalClusterModifierCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(
                new ResourceLocation(References.MODID, "textures/gui/jei/crystal_cluster_modifier.png"), 0, 0, 196, 96);
        this.localizedName = Component.translatable(References.MODID + ".jei.crystal_cluster_modifier");
        //this.overlay =
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, this.renderStack);
        //this.renderStack.getOrCreateTag().putBoolean("RenderFull", true);
    }

    @Override
    public RecipeType<CrystalClusterModifiers> getRecipeType() {
        return JEIEffectScrollsRecipeTypes.CRYSTAL_CLUSTER_MODIFIERS;
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

        //Block Below
        builder.addSlot(RecipeIngredientRole.INPUT, 88, 17)
                .addIngredients(recipe.getIngredients().get(0));
    }

    @Override
    public void draw(CrystalClusterModifiers recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        RenderSystem.enableBlend();

        Font fontRenderer = Minecraft.getInstance().font;

        MutableComponent duration = Component.translatable(References.MODID + ".crystal_cluster_modifier.duration");
        if(recipe.getResultDurationModifier() < 0)duration.append(Component.literal("x / " + Math.abs(recipe.getResultDurationModifier()) + ""));
        else duration.append(Component.literal("x * " + recipe.getResultDurationModifier() + ""));
        duration.withStyle(ChatFormatting.BLACK);
        fontRenderer.draw(stack,duration,10,48,0xffffffff);

        MutableComponent potency = Component.translatable(References.MODID + ".crystal_cluster_modifier.modifier");
        potency.append(Component.literal("x * " + recipe.getResultPotencyModifier() + ""));
        potency.withStyle(ChatFormatting.BLACK);
        fontRenderer.draw(stack,potency,10,64,0xffffffff);

        MutableComponent corruption = Component.translatable(References.MODID + ".crystal_cluster_modifier.corruption");
        corruption.append(Component.literal("" + recipe.getResultCorruption() + ""));
        corruption.withStyle(ChatFormatting.BLACK);
        fontRenderer.draw(stack,corruption,10,80,0xffffffff);
    }
}
