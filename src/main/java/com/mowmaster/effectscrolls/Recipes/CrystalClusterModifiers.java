package com.mowmaster.effectscrolls.Recipes;

import com.google.gson.JsonObject;
import com.mowmaster.mowlib.MowLibUtils.MowLibReferences;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;

public class CrystalClusterModifiers implements Recipe<Container>
{
    @ObjectHolder(registryName = "forge:recipe_serializer", value = MODID + ":cluster_modifier")
    
    private final String group;
    private final ResourceLocation id;
    @Nullable
    private final Ingredient input;
    private final double durationModifier;
    private final double potencyModifier;
    private final boolean corruption;

    public CrystalClusterModifiers(ResourceLocation id, String group, @Nullable Ingredient input, double durationModifier, double potencyModifier, boolean corruption)
    {
        this.group = group;
        this.id = id;
        this.input = input;
        this.durationModifier = durationModifier;
        this.potencyModifier = potencyModifier;
        this.corruption = corruption;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static Collection<CrystalClusterModifiers> getAllRecipes(Level world)
    {
        return world.getRecipeManager().getAllRecipesFor(CrystalClusterModifiers.Type.INSTANCE);
    }

    @Override
    public String getGroup()
    {
        return group;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return new ItemStack(Items.BARRIER);
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> allIngredients = NonNullList.create();
        allIngredients.add(input != null ? input : Ingredient.EMPTY);
        return allIngredients;
    }

    @Override
    public boolean matches(Container inv, Level worldIn)
    {
        ItemStack inputStack = inv.getItem(0);
        return input.test(inputStack);
    }

    @Override
    public ItemStack assemble(Container inv)
    {
        return getResultItem().copy();
    }

    public double getResultDurationModifier()
    {
        return getDurationModifier();
    }


    public double getResultPotencyModifier()
    {
        return getPotencyModifier();
    }

    public boolean getResultCorruption()
    {
        return getCorruption();
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CrystalClusterModifiers.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return CrystalClusterModifiers.Type.INSTANCE;
    }

    public static class Type implements RecipeType<CrystalClusterModifiers> {
        private Type() { }
        public static final CrystalClusterModifiers.Type INSTANCE = new CrystalClusterModifiers.Type();
        public static final String ID = "cluster_modifier";
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(Items.NETHER_STAR.asItem());
    }

    //Can Be Negative
    public double getDurationModifier()
    {
        return durationModifier;
    }

    //Can Be Negative
    public double getPotencyModifier()
    {
        return potencyModifier;
    }

    public boolean getCorruption()
    {
        return corruption;
    }

    public Ingredient getPattern()
    {
        return input != null ? input : Ingredient.EMPTY;
    }

    public static class Serializer implements RecipeSerializer<CrystalClusterModifiers>
    {
        public static final CrystalClusterModifiers.Serializer INSTANCE = new CrystalClusterModifiers.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(MODID,"cluster_modifier");
        
        protected CrystalClusterModifiers createRecipe(ResourceLocation recipeId, String group, Ingredient input, double durationModifier, double potencyCap, boolean corruption)
        {
            return new CrystalClusterModifiers(recipeId, group, input, durationModifier, potencyCap, corruption);
        }

        @Override
        public CrystalClusterModifiers fromJson(ResourceLocation recipeId, JsonObject json)
        {
            String group = GsonHelper.getAsString(json, "group", "");
            Ingredient input = json.has("input") ? CraftingHelper.getIngredient(json.get("input")) : null;
            double durationModifier = json.has("durationModifier") ? GsonHelper.getAsDouble(json,"durationModifier") : 1;
            double potencyCap = json.has("potencyModifier") ? GsonHelper.getAsDouble(json,"potencyModifier") : 1;
            boolean corruption = json.has("corruption") ? GsonHelper.getAsBoolean(json,"corruption") : false;
            return createRecipe(recipeId, group, input, durationModifier, potencyCap, corruption);
        }

        @Override
        public CrystalClusterModifiers fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            String group = buffer.readUtf(32767);
            boolean hasInput = buffer.readBoolean();
            Ingredient input = hasInput ? Ingredient.fromNetwork(buffer) : null;
            double durationModifier = buffer.readDouble();
            double potencyCap = buffer.readDouble();
            boolean corruption = buffer.readBoolean();
            return createRecipe(recipeId, group,  input, durationModifier, potencyCap, corruption);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrystalClusterModifiers recipe)
        {
            buffer.writeUtf(recipe.group);
            boolean hasInput = recipe.input != null;
            buffer.writeBoolean(hasInput);
            if (hasInput) recipe.input.toNetwork(buffer);
            buffer.writeDouble(recipe.durationModifier);
            buffer.writeDouble(recipe.potencyModifier);
            buffer.writeBoolean(recipe.corruption);
        }

        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        public ResourceLocation getRegistryName() {
            return ID;
        }

        public Class<RecipeSerializer<?>> getRegistryType() {
            return CrystalClusterModifiers.Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
