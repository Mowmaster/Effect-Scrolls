package com.mowmaster.effectscrolls.Blocks.GeneratedBlocks;

import com.mowmaster.effectscrolls.Blocks.BaseColoredCrystalBlock;
import com.mowmaster.effectscrolls.EffectScrollConfig.EffectScrollsConfig;
import com.mowmaster.effectscrolls.Recipes.CrystalNodeRecipe;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterBlocks;
import com.mowmaster.mowlib.Blocks.BaseBlocks.BaseColoredBlock;
import com.mowmaster.mowlib.MowLibUtils.MowLibColorReference;
import com.mowmaster.mowlib.MowLibUtils.MowLibContainerUtils;
import com.mowmaster.mowlib.MowLibUtils.MowLibReferences;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.mowmaster.mowlib.MowLibUtils.MowLibReferences.MODID;

public class BaseCrystalNodeBlock extends BaseColoredCrystalBlock
{
    private static final Direction[] DIRECTIONS = Direction.values();

    public BaseCrystalNodeBlock(BlockBehaviour.Properties p_152726_) {
        super(p_152726_);
    }

    public PushReaction getPistonPushReaction(BlockState p_152733_) {
        return PushReaction.DESTROY;
    }

    @Nullable
    protected CrystalNodeRecipe getRecipe(Level level, ItemStack stackIn) {
        Container cont = MowLibContainerUtils.getContainer(1);
        cont.setItem(-1,stackIn);
        List<CrystalNodeRecipe> recipes = level.getRecipeManager().getRecipesFor(CrystalNodeRecipe.Type.INSTANCE,cont,level);
        return recipes.size() > 0 ? level.getRecipeManager().getRecipesFor(CrystalNodeRecipe.Type.INSTANCE,cont,level).get(0) : null;
    }

    protected Collection<ItemStack> getProcessResults(CrystalNodeRecipe recipe) {
        return (recipe == null)?(Arrays.asList(ItemStack.EMPTY)):(Collections.singleton(recipe.getResultItem()));
    }

    public void randomTick(BlockState p_222954_, ServerLevel p_222955_, BlockPos p_222956_, RandomSource p_222957_) {
        if (p_222957_.nextInt(EffectScrollsConfig.COMMON.nodeGrowthChance.get()) == 0) {
            Direction direction = DIRECTIONS[p_222957_.nextInt(DIRECTIONS.length)];
            BlockState blockstateOrigin = p_222955_.getBlockState(p_222956_);
            BlockPos blockpos = p_222956_.relative(direction);
            BlockState blockstate = p_222955_.getBlockState(blockpos);
            int getColor = MowLibColorReference.DEFAULTCOLOR;
            Block block = null;
            Collection<ItemStack> jsonResults = getProcessResults(getRecipe(p_222955_,new ItemStack(blockstate.getBlock())));
            ItemStack returnedRecipe = ItemStack.EMPTY;

            if (canClusterGrowAtState(blockstate)) {
                blockstate = p_222955_.getBlockState(p_222956_);
                getColor = MowLibColorReference.getColorFromStateInt(blockstate);
                block = DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get();
            } else if (blockstate.is(DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get()) && blockstate.getValue(BaseCrystalClusterBlock.FACING) == direction) {
                getColor = MowLibColorReference.getColorFromStateInt(blockstate);
                block = DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get();
            } else if (blockstate.is(DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get()) && blockstate.getValue(BaseCrystalClusterBlock.FACING) == direction) {
                getColor = MowLibColorReference.getColorFromStateInt(blockstate);
                block = DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get();
            } else if (blockstate.is(DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get()) && blockstate.getValue(BaseCrystalClusterBlock.FACING) == direction) {
                getColor = MowLibColorReference.getColorFromStateInt(blockstate);
                block = DeferredRegisterBlocks.CRYSTAL_CLUSTER_FULL.get();
            } else if(!jsonResults.iterator().next().isEmpty())
            {
                //Make Colored Stone Here Now
                returnedRecipe = (jsonResults.iterator().next().isEmpty())?(ItemStack.EMPTY):(jsonResults.iterator().next());
                blockstate = p_222955_.getBlockState(p_222956_);
                getColor = MowLibColorReference.getColorFromStateInt(blockstate);
                if(returnedRecipe.getItem() instanceof BlockItem)
                {
                    block = Block.byItem(returnedRecipe.getItem());
                }
            }



            if (block != null) {
                if(block instanceof BaseCrystalClusterBlock)
                {
                    BlockState blockstate1 = MowLibColorReference.addColorToBlockState(block.defaultBlockState(),getColor).setValue(BaseCrystalClusterBlock.FACING, direction).setValue(BaseCrystalClusterBlock.WATERLOGGED, Boolean.valueOf(blockstate.getFluidState().getType() == Fluids.WATER));
                    p_222955_.setBlockAndUpdate(blockpos, blockstate1);
                }
                else if(block instanceof BaseColoredBlock)
                {
                    if(p_222955_.getBlockState(blockpos).getBlock() instanceof BaseColoredBlock)
                    {
                        int colorOrigin = MowLibColorReference.getColorFromStateInt(blockstateOrigin);
                        int colorToMix = MowLibColorReference.getColorFromStateInt(p_222955_.getBlockState(blockpos));
                        if(colorOrigin != colorToMix)
                        {
                            int colorMix = MowLibColorReference.mixColorsInt(colorOrigin,colorToMix);
                            BlockState blockstate1 = MowLibColorReference.addColorToBlockState(block.defaultBlockState(),colorMix);
                            p_222955_.setBlockAndUpdate(blockpos, blockstate1);
                        }
                    }
                    else if(!jsonResults.iterator().next().isEmpty())
                    {
                        BlockState blockstate1 = MowLibColorReference.addColorToBlockState(block.defaultBlockState(),getColor);
                        p_222955_.setBlockAndUpdate(blockpos, blockstate1);
                    }
                }
            }
            else if (!returnedRecipe.isEmpty())
            {
                if(ForgeRegistries.ITEMS.getKey(returnedRecipe.getItem()).getNamespace().equals(MODID))
                {
                    MowLibColorReference.addColorToItemStack(returnedRecipe,getColor);
                }
                ItemEntity itemEn = new ItemEntity(p_222955_,blockpos.getX()+0.5,blockpos.getY()+0.5,blockpos.getZ()+0.5,returnedRecipe);
                itemEn.setInvulnerable(true);
                itemEn.setUnlimitedLifetime();
                p_222955_.addFreshEntity(itemEn);
            }

        }
    }

    public static boolean canClusterGrowAtState(BlockState p_152735_) {
        return p_152735_.isAir() || p_152735_.is(Blocks.WATER) && p_152735_.getFluidState().getAmount() == 8;
    }
}
