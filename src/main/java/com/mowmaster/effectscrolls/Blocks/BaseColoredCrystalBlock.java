package com.mowmaster.effectscrolls.Blocks;


import com.mowmaster.mowlib.Blocks.BaseBlocks.BaseColoredBlock;
import com.mowmaster.mowlib.Items.ColorApplicator;
import com.mowmaster.mowlib.MowLibUtils.ColorReference;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CryingObsidianBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.List;

public class BaseColoredCrystalBlock extends BaseColoredBlock
{
    public BaseColoredCrystalBlock(Properties p_152915_)
    {
        super(p_152915_);
        this.registerDefaultState(ColorReference.addColorToBlockState(this.defaultBlockState(),ColorReference.DEFAULTCOLOR));
    }

    //This only exists because i cant figure out how Mining Gadgets breaks blocks (currently my blocks dont drop when mined with their mod...)
    @Override
    public float defaultDestroyTime() {
        return -0.01F;
    }

    @Override
    public void setPlacedBy(Level p_49847_, BlockPos p_49848_, BlockState p_49849_, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
        if(p_49850_ instanceof Player)
        {
            Player player = ((Player)p_49850_);
            if((player.getOffhandItem().getItem() instanceof ColorApplicator) && player.isCreative())
            {
                int getColor = ColorReference.getColorFromItemStackInt(player.getOffhandItem());
                BlockState newState = ColorReference.addColorToBlockState(p_49849_,getColor);
                p_49847_.setBlock(p_49848_,newState,3);
            }
            else
            {
                int getColor = ColorReference.getColorFromItemStackInt(p_49851_);
                BlockState newState = ColorReference.addColorToBlockState(p_49849_,getColor);
                p_49847_.setBlock(p_49848_,newState,3);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {

        if((p_60506_.getItemInHand(p_60507_).getItem() instanceof ColorApplicator) && p_60506_.isCreative())
        {
            int getColor = ColorReference.getColorFromItemStackInt(p_60506_.getItemInHand(p_60507_));
            BlockState newState = ColorReference.addColorToBlockState(p_60503_,getColor);
            p_60504_.setBlock(p_60505_,newState,3);
            //p_60504_.markAndNotifyBlock(p_60505_,null,p_60503_,newState,3,1);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;

    }

    public void onProjectileHit(Level p_152001_, BlockState p_152002_, BlockHitResult p_152003_, Projectile p_152004_) {
        if (!p_152001_.isClientSide) {
            BlockPos blockpos = p_152003_.getBlockPos();
            p_152001_.playSound((Player)null, blockpos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 0.5F + p_152001_.random.nextFloat() * 1.2F);
            p_152001_.playSound((Player)null, blockpos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 0.5F + p_152001_.random.nextFloat() * 1.2F);
        }
    }

    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> p_49818_, TooltipFlag p_49819_) {
        super.appendHoverText(p_49816_, p_49817_, p_49818_, p_49819_);

    }
}
