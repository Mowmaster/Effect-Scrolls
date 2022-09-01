package com.mowmaster.effectscrolls.Blocks.GeneratedBlocks;

import com.mowmaster.effectscrolls.Blocks.BaseColoredCrystalBlock;
import com.mowmaster.effectscrolls.EffectScrollConfig.EffectScrollsConfig;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterBlocks;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterItems;
import com.mowmaster.mowlib.MowLibUtils.MowLibColorReference;
import com.mowmaster.mowlib.MowLibUtils.MowLibReferences;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;


public class BaseCrystalClusterBlock extends BaseColoredCrystalBlock implements SimpleWaterloggedBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected final VoxelShape northAabb;
    protected final VoxelShape southAabb;
    protected final VoxelShape eastAabb;
    protected final VoxelShape westAabb;
    protected final VoxelShape upAabb;
    protected final VoxelShape downAabb;

    //BlockLoot
    //createShulkerBoxDrop -->CopyNbtFunction
    //OOOR just copy how the hardcoded shulker box class does it :P

    public BaseCrystalClusterBlock(int p_152015_, int p_152016_, BlockBehaviour.Properties p_152726_) {

        super(p_152726_);
        this.registerDefaultState(MowLibColorReference.addColorToBlockState(this.defaultBlockState(),MowLibColorReference.DEFAULTCOLOR).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.UP));
        this.upAabb = Block.box((double)p_152016_, 0.0D, (double)p_152016_, (double)(16 - p_152016_), (double)p_152015_, (double)(16 - p_152016_));
        this.downAabb = Block.box((double)p_152016_, (double)(16 - p_152015_), (double)p_152016_, (double)(16 - p_152016_), 16.0D, (double)(16 - p_152016_));
        this.northAabb = Block.box((double)p_152016_, (double)p_152016_, (double)(16 - p_152015_), (double)(16 - p_152016_), (double)(16 - p_152016_), 16.0D);
        this.southAabb = Block.box((double)p_152016_, (double)p_152016_, 0.0D, (double)(16 - p_152016_), (double)(16 - p_152016_), (double)p_152015_);
        this.eastAabb = Block.box(0.0D, (double)p_152016_, (double)p_152016_, (double)p_152015_, (double)(16 - p_152016_), (double)(16 - p_152016_));
        this.westAabb = Block.box((double)(16 - p_152015_), (double)p_152016_, (double)p_152016_, 16.0D, (double)(16 - p_152016_), (double)(16 - p_152016_));
    }

    public VoxelShape getShape(BlockState p_152021_, BlockGetter p_152022_, BlockPos p_152023_, CollisionContext p_152024_) {
        Direction direction = p_152021_.getValue(FACING);
        switch(direction) {
            case NORTH:
                return this.northAabb;
            case SOUTH:
                return this.southAabb;
            case EAST:
                return this.eastAabb;
            case WEST:
                return this.westAabb;
            case DOWN:
                return this.downAabb;
            case UP:
            default:
                return this.upAabb;
        }
    }

    public boolean canSurvive(BlockState p_152026_, LevelReader p_152027_, BlockPos p_152028_) {
        Direction direction = p_152026_.getValue(FACING);
        BlockPos blockpos = p_152028_.relative(direction.getOpposite());
        return p_152027_.getBlockState(blockpos).isFaceSturdy(p_152027_, blockpos, direction);
    }

    public BlockState updateShape(BlockState p_152036_, Direction p_152037_, BlockState p_152038_, LevelAccessor p_152039_, BlockPos p_152040_, BlockPos p_152041_) {
        if (p_152036_.getValue(WATERLOGGED)) {
            p_152039_.getFluidTicks();
            //.scheduleTick(p_152040_, Fluids.WATER, Fluids.WATER.getTickDelay(p_152039_))
        }

        return p_152037_ == p_152036_.getValue(FACING).getOpposite() && !p_152036_.canSurvive(p_152039_, p_152040_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_152036_, p_152037_, p_152038_, p_152039_, p_152040_, p_152041_);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_) {
        LevelAccessor levelaccessor = p_152019_.getLevel();
        BlockPos blockpos = p_152019_.getClickedPos();
        Direction direction = p_152019_.getClickedFace();
        BlockState blockstate = p_152019_.getLevel().getBlockState(p_152019_.getClickedPos());
        int getColor = MowLibColorReference.getColorFromStateInt(blockstate);
        return blockstate.is(this) && blockstate.getValue(FACING) == direction
                ?
                MowLibColorReference.addColorToBlockState(this.defaultBlockState(),getColor).setValue(FACING, direction.getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER))
                :
                MowLibColorReference.addColorToBlockState(this.defaultBlockState(),getColor).setValue(FACING, direction).setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER));
    }

    public BlockState rotate(BlockState p_152033_, Rotation p_152034_) {
        return p_152033_.setValue(FACING, p_152034_.rotate(p_152033_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_152030_, Mirror p_152031_) {
        return p_152030_.rotate(p_152031_.getRotation(p_152030_.getValue(FACING)));
    }

    public FluidState getFluidState(BlockState p_152045_) {
        return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_152045_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_) {
        p_152043_.add(WATERLOGGED, FACING, COLOR_RED, COLOR_GREEN, COLOR_BLUE);
    }

    public PushReaction getPistonPushReaction(BlockState p_152733_) {
        return PushReaction.DESTROY;
    }

    //SOme way to chage the color of the crystals given blocks around it???
    public void randomTick(BlockState p_152728_, ServerLevel p_152729_, BlockPos p_152730_, Random p_152731_) {
        if (p_152731_.nextInt(25) == 0) {
            Direction direction = p_152728_.getValue(FACING);
            BlockState blockstate = p_152729_.getBlockState(p_152730_);
            int getColor = MowLibColorReference.getColorFromStateInt(blockstate);
            Block block = null;
        }
    }

    public VoxelShape getOcclusionShape(BlockState p_154272_, BlockGetter p_154273_, BlockPos p_154274_) {
        return Shapes.empty();
    }

    public VoxelShape getCollisionShape(BlockState p_154285_, BlockGetter p_154286_, BlockPos p_154287_, CollisionContext p_154288_) {
        return Shapes.empty();
    }

    public void entityInside(BlockState p_57270_, Level p_57271_, BlockPos p_57272_, Entity p_57273_) {
        if(EffectScrollsConfig.COMMON.funHaters.get())return;

        if (p_57273_ instanceof LivingEntity) {
            boolean isCreative = false;
            if(p_57273_ instanceof ServerPlayer)isCreative = ((ServerPlayer) p_57273_).isCreative();
            if(isCreative)return;
            Direction getFacing = (p_57270_.hasProperty(FACING))?(p_57270_.getValue(FACING)):(null);
            BlockState blockBelow = (getFacing != null)?(p_57271_.getBlockState(p_57272_.offset(getFacing.getOpposite().getNormal()))):(DeferredRegisterBlocks.CRYSTAL_NODE.get().defaultBlockState());

            if(blockBelow.getBlock() instanceof BaseCrystalNodeBlock)
            {
                p_57273_.makeStuckInBlock(p_57270_, new Vec3((double)0.8F, 0.75D, (double)0.8F));
                if (!p_57271_.isClientSide && (p_57273_.xOld != p_57273_.getX() || p_57273_.zOld != p_57273_.getZ())) {
                    double d0 = Math.abs(p_57273_.getX() - p_57273_.xOld);
                    double d1 = Math.abs(p_57273_.getZ() - p_57273_.zOld);

                    DamageSource CrystalCluster = new DamageSource(MODID + getCrystalDamageType(p_57270_)).damageHelmet().bypassArmor().setScalesWithDifficulty().setExplosion();

                    if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                        //WORLD.explode(TARGET_ENTITY,DamageSource,ExplosionDamageCalculator,BlockPosX,BlockPosY,BlockPosZ,ExplosionRadius,SpawnFire, Explosion.BlockInteraction);
                        p_57271_.explode(p_57273_,CrystalCluster,new EntityBasedExplosionDamageCalculator(p_57273_),p_57272_.getX()+0.5,p_57272_.getY()+2.0,p_57272_.getZ()+0.5,getExplosionSize(p_57270_),false, Explosion.BlockInteraction.BREAK);
                        //Instead of blowing up the full area, just destroy the block walked into
                        p_57271_.setBlock(p_57272_,Blocks.AIR.defaultBlockState(),3);
                        float damage = getExplosionSize(p_57270_);
                        p_57273_.hurt(CrystalCluster,Math.scalb(damage,2));
                    }
                }
            }
        }
    }

    //Should Fix Building Gadgets dropps issues
    //https://github.com/Direwolf20-MC/MiningGadgets/blob/1.19/src/main/java/com/direwolf20/mininggadgets/common/tiles/RenderBlockTileEntity.java#L444
    @Override
    public List<ItemStack> getDrops(BlockState p_60537_, LootContext.Builder p_60538_) {
        Random rand = new Random();
        if (p_60537_.getBlock() instanceof BaseCrystalClusterBlock) {

            List<ItemStack> stacks = new ArrayList<>();

            if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH,p_60538_.getParameter(LootContextParams.TOOL))>0)
            {
                ItemStack itemstack = new ItemStack(this);
                int getColor = MowLibColorReference.getColorFromStateInt(p_60537_);
                ItemStack newStack = MowLibColorReference.addColorToItemStack(itemstack,getColor);
                newStack.setCount(1);
                stacks.add(newStack);
                return stacks;
            }
            else
            {
                ItemStack itemstack = new ItemStack(DeferredRegisterItems.COLORED_CRYSTAL.get());
                if(p_60537_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get()))itemstack.setCount(rand.nextInt(0, 1));
                if(p_60537_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get()))itemstack.setCount(rand.nextInt(0, 2));
                if(p_60537_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get()))itemstack.setCount(rand.nextInt(1, 3 + 1));
                if(p_60537_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_FULL.get()))itemstack.setCount(rand.nextInt(2, 5 + 1));

                int getColor = MowLibColorReference.getColorFromStateInt(p_60537_);
                ItemStack newStack = MowLibColorReference.addColorToItemStack(itemstack,getColor);
                stacks.add(newStack);
                return stacks;
            }
        }
        return super.getDrops(p_60537_, p_60538_);
    }

    /*@Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        //&& !(p_60518_.getBlock() instanceof BaseCrystalClusterBlock)
        if(!p_60516_.isClientSide() && p_60519_)
        {
            Random rand = new Random();
            if (p_60515_.getBlock() instanceof BaseCrystalClusterBlock) {
                ItemStack itemstack = new ItemStack(DeferredRegisterItems.COLORED_CRYSTAL.get());
                if(p_60515_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get()))itemstack.setCount(rand.nextInt(0, 1));
                if(p_60515_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get()))itemstack.setCount(rand.nextInt(0, 2));
                if(p_60515_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get()))itemstack.setCount(rand.nextInt(1, 3 + 1));
                if(p_60515_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_FULL.get()))itemstack.setCount(rand.nextInt(2, 5 + 1));

                int getColor = MowLibColorReference.getColorFromStateInt(p_60515_);
                ItemStack newStack = MowLibColorReference.addColorToItemStack(itemstack,getColor);
                ItemEntity itementity = new ItemEntity(p_60516_, (double)p_60517_.getX() + 0.5D, (double)p_60517_.getY() + 0.5D, (double)p_60517_.getZ() + 0.5D, newStack);
                itementity.setDefaultPickUpDelay();
                p_60516_.addFreshEntity(itementity);
            }
        }
        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
    }*/


    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return true;
    }

    /*@Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        if(!level.isClientSide())
        {
            Random rand = new Random();
            if (state.getBlock() instanceof BaseCrystalClusterBlock) {
                ItemStack itemstack = new ItemStack(DeferredRegisterItems.COLORED_CRYSTAL.get());
                if(state.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get()))itemstack.setCount(rand.nextInt(0, 1));
                if(state.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get()))itemstack.setCount(rand.nextInt(0, 2));
                if(state.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get()))itemstack.setCount(rand.nextInt(1, 3 + 1));
                if(state.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_FULL.get()))itemstack.setCount(rand.nextInt(2, 5 + 1));

                int getColor = MowLibColorReference.getColorFromStateInt(state);
                ItemStack newStack = MowLibColorReference.addColorToItemStack(itemstack,getColor);
                ItemEntity itementity = new ItemEntity(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, newStack);
                itementity.setDefaultPickUpDelay();
                level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
                level.addFreshEntity(itementity);
            }
        }
        //super.onBlockExploded(state, level, pos, explosion);
    }

    @Override
    public void playerWillDestroy(Level p_56212_, BlockPos p_56213_, BlockState p_56214_, Player p_56215_) {

        //if(p_56215_ == null)return;
        if(!p_56212_.isClientSide())
        {
            Random rand = new Random();
            if (p_56214_.getBlock() instanceof BaseCrystalClusterBlock) {
                if(EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH,p_56215_)>0)
                {
                    ItemStack itemstack = new ItemStack(this);
                    int getColor = MowLibColorReference.getColorFromStateInt(p_56214_);
                    ItemStack newStack = MowLibColorReference.addColorToItemStack(itemstack,getColor);
                    newStack.setCount(1);
                    ItemEntity itementity = new ItemEntity(p_56212_, (double)p_56213_.getX() + 0.5D, (double)p_56213_.getY() + 0.5D, (double)p_56213_.getZ() + 0.5D, newStack);
                    itementity.setDefaultPickUpDelay();
                    p_56212_.addFreshEntity(itementity);
                }
                else
                {
                    ItemStack itemstack = new ItemStack(DeferredRegisterItems.COLORED_CRYSTAL.get());
                    if(p_56214_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get()))itemstack.setCount(rand.nextInt(0, 1));
                    if(p_56214_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get()))itemstack.setCount(rand.nextInt(0, 2));
                    if(p_56214_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get()))itemstack.setCount(rand.nextInt(1, 3 + 1));
                    if(p_56214_.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_FULL.get()))itemstack.setCount(rand.nextInt(2, 5 + 1));

                    int getColor = MowLibColorReference.getColorFromStateInt(p_56214_);
                    ItemStack newStack = MowLibColorReference.addColorToItemStack(itemstack,getColor);
                    ItemEntity itementity = new ItemEntity(p_56212_, (double)p_56213_.getX() + 0.5D, (double)p_56213_.getY() + 0.5D, (double)p_56213_.getZ() + 0.5D, newStack);
                    itementity.setDefaultPickUpDelay();
                    p_56212_.addFreshEntity(itementity);
                }

            }
            else super.playerWillDestroy(p_56212_, p_56213_, p_56214_, p_56215_);
        }
    }*/

    private float getExplosionSize(BlockState state)
    {
        if(state.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_SMALL.get())) return 1.0f;
        else if(state.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_MEDIUM.get())) return 2.0f;
        else if(state.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_LARGE.get())) return 3.0f;
        else if(state.getBlock().equals(DeferredRegisterBlocks.CRYSTAL_CLUSTER_FULL.get())) return 4.0f;
        return 0.0f;
    }

    private String getCrystalDamageType(BlockState state)
    {
        if(state.getBlock() instanceof BaseColoredCrystalBlock)
        {

            switch(MowLibColorReference.getColorFromStateInt(state))
            {
                case 2763306: return ".death000";
                        case 85: return ".death001";
                        case 170: return ".death002";
                        case 255: return ".death003";
                        case 21760: return ".death010";
                        case 21845: return ".death011";
                        case 21930: return ".death012";
                        case 22015: return ".death013";
                        case 43520: return ".death020";
                        case 43605: return ".death021";
                        case 43690: return ".death022";
                        case 43775: return ".death023";
                        case 65280: return ".death030";
                        case 65365: return ".death031";
                        case 65450: return ".death032";
                        case 65535: return ".death033";
                        case 5570560: return ".death100";
                        case 5570645: return ".death101";
                        case 5570730: return ".death102";
                        case 5570815: return ".death103";
                        case 5592320: return ".death110";
                        case 5592405: return ".death111";
                        case 5592490: return ".death112";
                        case 5592575: return ".death113";
                        case 5614080: return ".death120";
                        case 5614165: return ".death121";
                        case 5614250: return ".death122";
                        case 5614335: return ".death123";
                        case 5635840: return ".death130";
                        case 5635925: return ".death131";
                        case 5636010: return ".death132";
                        case 5636095: return ".death133";
                        case 11141120: return ".death200";
                        case 11141205: return ".death201";
                        case 11141290: return ".death202";
                        case 11141375: return ".death203";
                        case 11162880: return ".death210";
                        case 11162965: return ".death211";
                        case 11163050: return ".death212";
                        case 11163135: return ".death213";
                        case 11184640: return ".death220";
                        case 11184725: return ".death221";
                        case 11184810: return ".death222";
                        case 11184895: return ".death223";
                        case 11206400: return ".death230";
                        case 11206485: return ".death231";
                        case 11206570: return ".death232";
                        case 11206655: return ".death233";
                        case 16711680: return ".death300";
                        case 16711765: return ".death301";
                        case 16711850: return ".death302";
                        case 16711935: return ".death303";
                        case 16733440: return ".death310";
                        case 16733525: return ".death311";
                        case 16733610: return ".death312";
                        case 16733695: return ".death313";
                        case 16755200: return ".death320";
                        case 16755285: return ".death321";
                        case 16755370: return ".death322";
                        case 16755455: return ".death323";
                        case 16776960: return ".death330";
                        case 16777045: return ".death331";
                        case 16777130: return ".death332";
                        case 16777215: return ".death333";
                default: return ".deathDefault";
            }
        }

        return ".deathReturner";
    }
}
