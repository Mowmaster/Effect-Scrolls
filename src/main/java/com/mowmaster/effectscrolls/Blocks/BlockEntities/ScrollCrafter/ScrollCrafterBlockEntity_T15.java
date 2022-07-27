package com.mowmaster.effectscrolls.Blocks.BlockEntities.ScrollCrafter;

import com.mowmaster.effectscrolls.EffectScrollConfig.EffectScrollsConfig;
import com.mowmaster.effectscrolls.Items.ScrollBase;
import com.mowmaster.effectscrolls.Recipes.CrystalClusterModifiers;
import com.mowmaster.effectscrolls.Registry.DeferredBlockEntityTypes;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterItems;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterTileBlocks;
import com.mowmaster.mowlib.BlockEntities.BaseBuiltMachineBlockEntity;
import com.mowmaster.mowlib.Capabilities.Dust.CapabilityDust;
import com.mowmaster.mowlib.Capabilities.Dust.DustMagic;
import com.mowmaster.mowlib.Capabilities.Dust.IDustHandler;
import com.mowmaster.mowlib.Items.EffectItemBase;
import com.mowmaster.mowlib.MowLibUtils.MowLibColorReference;
import com.mowmaster.mowlib.MowLibUtils.MowLibContainerUtils;
import com.mowmaster.mowlib.MowLibUtils.MowLibEffectUtils;
import com.mowmaster.mowlib.MowLibUtils.MowLibItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.mowmaster.mowlib.MowLibUtils.MowLibReferences.MODID;

import com.mowmaster.mowlib.Capabilities.Dust.IDustHandler.DustAction;

public class ScrollCrafterBlockEntity_T15 extends BaseBuiltMachineBlockEntity {

    private LazyOptional<IItemHandler> tableItemHandler = LazyOptional.of(this::createTableItemHandler);
    private LazyOptional<IDustHandler> dustHandler = LazyOptional.of(this::createhandlerDust);
    private List<ItemStack> stacksListTable = new ArrayList<>();
    private int dustCapacity = 1000;
    private ItemStack getScrollCrafted = ItemStack.EMPTY;

    public ScrollCrafterBlockEntity_T15(BlockPos p_155229_, BlockState p_155230_) {
        super(DeferredBlockEntityTypes.CRAFTER_SCROLL_T15.get(), p_155229_, p_155230_);
    }

    /*============================================================================
    ==============================================================================
    =====================   BASE CLASS OVERRIDES - START   =======================
    ==============================================================================
    ============================================================================*/

    @Override
    public void update()
    {
        getScrollCraftingOutput();
        BlockState state = level.getBlockState(getPos());
        this.level.sendBlockUpdated(getPos(), state, state, 3);
        this.setChanged();
    }

    @Override
    public int getRepairSlotsForRepairs()
    {
        return EffectScrollsConfig.COMMON.repairitemsToCraft_ScrollCrafter_T15.get();
    }

    @Override
    public Block getBlockForThisBlockEntity()
    {
        return DeferredRegisterTileBlocks.BLOCK_CRAFTER_SCROLL_T15.get();
    }

    /*============================================================================
    ==============================================================================
    =====================    BASE CLASS OVERRIDES - END    =======================
    ==============================================================================
    ============================================================================*/



    /*============================================================================
    ==============================================================================
    ===========================    DUST   START      =============================
    ==============================================================================
    ============================================================================*/

    public IDustHandler createhandlerDust() {
        return new IDustHandler() {

            protected void onContentsChanged()
            {
                syncDustInTableWithContainer();
                update();
            }

            @Override
            public int getTanks() {
                return 1;
            }

            @NotNull
            @Override
            public DustMagic getDustMagicInTank(int tank) {
                return dustMagic;
            }

            @Override
            public int getTankCapacity(int tank) {
                return dustCapacity;
            }

            @Override
            public boolean isDustValid(int tank, @NotNull DustMagic dustIn) {
                return dustMagic.isDustEqualOrEmpty(dustIn);
            }

            @Override
            public int fill(DustMagic dust, DustAction action) {
                if (dust.isEmpty() || !isDustValid(0,dust))
                {
                    return 0;
                }
                if (action.simulate())
                {
                    if (dustMagic.isEmpty())
                    {
                        return Math.min(getTankCapacity(0), dust.getDustAmount());
                    }
                    if (!dustMagic.isDustEqual(dust))
                    {
                        return 0;
                    }
                    return Math.min(getTankCapacity(0) - dustMagic.getDustAmount(), dust.getDustAmount());
                }
                if (dustMagic.isEmpty())
                {
                    dustMagic = new DustMagic(dust.getDustColor(), Math.min(getTankCapacity(0), dust.getDustAmount()));
                    onContentsChanged();
                    return dustMagic.getDustAmount();
                }
                if (!dustMagic.isDustEqual(dust))
                {
                    return 0;
                }
                int filled = getTankCapacity(0) - dustMagic.getDustAmount();

                if (dust.getDustAmount() < filled)
                {
                    dustMagic.grow(dust.getDustAmount());
                    filled = dust.getDustAmount();
                }
                else
                {
                    dustMagic.setDustAmount(getTankCapacity(0));
                }
                if (filled > 0)
                    onContentsChanged();
                return filled;
            }

            @NotNull
            @Override
            public DustMagic drain(DustMagic dust, DustAction action) {
                if (dust.isEmpty() || !dust.isDustEqual(dustMagic))
                {
                    return new DustMagic(-1, 0);
                }
                return drain(dust.getDustAmount(), action);
            }

            @NotNull
            @Override
            public DustMagic drain(int maxDrain, DustAction action) {
                int drained = maxDrain;
                if (dustMagic.getDustAmount() < drained)drained = dustMagic.getDustAmount();

                DustMagic magic = new DustMagic(dustMagic.getDustColor(), drained);
                if (action.execute() && drained > 0)
                {
                    if(dustMagic.getDustAmount() <= magic.getDustAmount()) dustMagic = new DustMagic(-1, 0);
                    else dustMagic.shrink(drained);
                    onContentsChanged();
                }
                return magic;
            }
        };
    }

    public boolean hasDust()
    {
        IDustHandler h = dustHandler.orElse(null);
        if(!h.getDustMagicInTank(0).isEmpty())return true;
        return false;
    }

    public DustMagic getStoredDust()
    {
        IDustHandler h = dustHandler.orElse(null);
        if(!h.getDustMagicInTank(0).isEmpty())return h.getDustMagicInTank(0);
        return new DustMagic(-1, 0);
    }

    public void setDustCapacity(int capacity)
    {
        this.dustCapacity = capacity;
    }

    public int getDustCapacity()
    {
        IDustHandler h = dustHandler.orElse(null);
        return h.getTankCapacity(0);
    }

    public int spaceForDust()
    {
        return getDustCapacity()-getStoredDust().getDustAmount();
    }

    public boolean canAcceptDust(DustMagic dustMagicIn)
    {
        IDustHandler h = dustHandler.orElse(null);
        return h.isDustValid(0,dustMagicIn);
    }

    public DustMagic removeDust(DustMagic dustMagicToRemove, IDustHandler.DustAction action)
    {
        IDustHandler h = dustHandler.orElse(null);
        return h.drain(dustMagicToRemove,action);
    }

    public DustMagic removeDust(int dustAmountToRemove, IDustHandler.DustAction action)
    {
        IDustHandler h = dustHandler.orElse(null);
        return h.drain(new DustMagic(getStoredDust().getDustColor(),dustAmountToRemove),action);
    }

    public int addDust(DustMagic dustMagicIn, IDustHandler.DustAction action)
    {
        IDustHandler h = dustHandler.orElse(null);
        return h.fill(dustMagicIn,action);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if ((cap == CapabilityDust.DUST_HANDLER)) {
            return dustHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    /*============================================================================
    ==============================================================================
    ===========================     DUST   END       =============================
    ==============================================================================
    ============================================================================*/



    /*============================================================================
    ==============================================================================
    ===========================     ITEM START       =============================
    ==============================================================================
    ============================================================================*/

    private IItemHandler createTableItemHandler() {
        /*
         * CONFIG IS USED TO SET THIS, USERS MUST DEFINE RECIPES AND CHANGE CONFIG TO MAKE CHANGES TO MACHINES NOW
         */

        return new ItemStackHandler(3) {

            @Override
            protected void onLoad() {

                if(getSlots()<3)
                {
                    for(int i = 0; i < getSlots(); ++i) {
                        stacksListTable.add(i,getStackInSlot(i));
                    }
                    setSize(getRepairListStacks().size());
                    for(int j = 0;j<stacksListTable.size();j++) {
                        setStackInSlot(j, stacksListTable.get(j));
                    }
                }

                super.onLoad();
            }

            @Override
            protected void onContentsChanged(int slot) {
                if(!(stacksListTable.size()>0))
                {
                    update();
                }
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0 && stack.getItem().equals(Items.PAPER)) return true;
                if (slot == 1 && stack.getItem().equals(Items.GOLD_NUGGET)) return true;
                if (slot == 2 && addModifier(stack, true).getCount()>0) return true;

                return false;
            }
        };
    }

    public int getSlotForItemStackInTable(ItemStack stackIncoming)
    {
        IItemHandler h = tableItemHandler.orElse(null);
        if(h!=null)
        {
            for(int i=0;i<h.getSlots();i++)
            {
                if(h.isItemValid(i,stackIncoming))return i;
            }
        }

        return -1;
    }

    public boolean isItemAllowedInTable(ItemStack stackIncoming)
    {
        IItemHandler h = tableItemHandler.orElse(null);
        if(h!=null)
        {
            for(int i=0;i<h.getSlots();i++)
            {
                if(h.isItemValid(i,stackIncoming))return true;
            }
        }

        return false;
    }

    public boolean hasItemInTable(int slot)
    {
        IItemHandler h = tableItemHandler.orElse(null);
        if(h!=null)
        {
            if(!(h.getStackInSlot(slot).isEmpty()))return true;

        }
        return false;
    }

    public ItemStack getItemInTable(int slot)
    {
        IItemHandler h = tableItemHandler.orElse(null);
        if(h!=null)
        {
            if(hasItemInTable(slot))return h.getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    public ItemStack removeItemInTable(int slot, int numToRemove) {
        IItemHandler h = tableItemHandler.orElse(null);
        ItemStack stack = ItemStack.EMPTY;
        if(h!=null)
        {
            stack = h.extractItem(slot,numToRemove,false);
        }

        return stack;
    }

    public ItemStack removeItemInTable(int slot) {
        IItemHandler h = tableItemHandler.orElse(null);
        ItemStack stack = ItemStack.EMPTY;
        if(h!=null)
        {
            stack = h.extractItem(0,getItemInTable(slot).getMaxStackSize(),false);
        }

        return stack;
    }

    public ItemStack addItemInTable(ItemStack stackIncoming ,boolean simulate)
    {
        ItemStack returner = stackIncoming.copy();
        if(isItemAllowedInTable(stackIncoming))
        {
            IItemHandler h = tableItemHandler.orElse(null);
            int slot = getSlotForItemStackInTable(stackIncoming);
            if(h.isItemValid(slot,stackIncoming))
            {
                if(hasItemInTable(slot))
                {
                    if(ItemHandlerHelper.canItemStacksStack(getItemInTable(slot),stackIncoming))
                    {
                        returner = h.insertItem(slot, stackIncoming.copy(), simulate);
                        if(!simulate) {update();}
                        return returner;
                    }
                }
                else
                {
                    returner = h.insertItem(slot, stackIncoming.copy(), simulate);
                    if(!simulate) {update();}
                    return returner;
                }
            }
        }

        return returner;
    }

    public int getSlotSizeLimitInTable(int slot)
    {
        IItemHandler h = tableItemHandler.orElse(null);
        return (h != null)?(h.getSlotLimit(slot)):(0);
    }

    /*============================================================================
    ==============================================================================
    ===========================      ITEM END        =============================
    ==============================================================================
    ============================================================================*/



    /*============================================================================
    ==============================================================================
    ===========================    MODIFIER START    =============================
    ==============================================================================
    ============================================================================*/

    @Nullable
    protected CrystalClusterModifiers getRecipeModifier(Level level, ItemStack stackIn) {
        Container container = MowLibContainerUtils.getContainer(1);
        container.setItem(-1,stackIn);
        List<CrystalClusterModifiers> recipes = level.getRecipeManager().getRecipesFor(CrystalClusterModifiers.Type.INSTANCE,container,level);
        return recipes.size() > 0 ? recipes.stream().findFirst().get() : null;
    }

    protected double getProcessResultModifierDuration(CrystalClusterModifiers recipe) {
        return (recipe == null)?(0.0):(recipe.getResultDurationModifier());
    }

    protected double getProcessResultModifierPotency(CrystalClusterModifiers recipe) {
        return (recipe == null)?(0.0):(recipe.getResultPotencyModifier());
    }

    protected boolean getProcessResultCorruption(CrystalClusterModifiers recipe) {
        return (recipe == null)?(false):(recipe.getResultCorruption());
    }

    public boolean hasModifierItems()
    {
        IItemHandler h = tableItemHandler.orElse(null);
        if(h.getStackInSlot(2).isEmpty())return false;

        return true;
    }

    //Also has check for if fuel supports modifier
    public boolean isAcceptedModifierItem(ItemStack stack)
    {
        double durationMod = getProcessResultModifierDuration(getRecipeModifier(getLevel(),stack));
        double potencyMod = getProcessResultModifierPotency(getRecipeModifier(getLevel(),stack));
        //add 1 since potencyMod needs over 1 to be more effective by default, but default effect potency is 0
        int fuelAllowedPotency = EffectScrollsConfig.COMMON.effectMaxPotency.get();
        if(potencyMod > fuelAllowedPotency)return false;
        //Since we only check for INT values, and +/- ones count, but 0 would result null values in our maths, so those are bad.
        if(durationMod > 0.0 || durationMod < 0.0)return true;
        else if(potencyMod > 0.0 || potencyMod < 0.0)return true;
        else if(hasCorruption())return true;

        return false;
    }

    public boolean isModifierItem(ItemStack stack)
    {
        double durationMod = getProcessResultModifierDuration(getRecipeModifier(getLevel(),stack));
        double potencyMod = getProcessResultModifierPotency(getRecipeModifier(getLevel(),stack));
        //Since we only check for INT values, and +/- ones count, but 0 would result null values in our maths, so those are bad.
        if(durationMod > 0.0 || durationMod < 0.0)return true;
        if(potencyMod > 0.0 || potencyMod < 0.0)return true;
        else if(hasCorruption())return true;

        return false;
    }

    public ItemStack getModifierStack()
    {
        if(hasModifierItems())
        {
            IItemHandler h = tableItemHandler.orElse(null);
            return h.getStackInSlot(2);
        }

        return ItemStack.EMPTY;
    }

    public boolean doModifiersMatch(ItemStack stackIncoming)
    {
        if(hasModifierItems())
        {
            ItemStack currentModifier = getModifierStack();
            return ItemHandlerHelper.canItemStacksStack(currentModifier,stackIncoming);
        }

        return false;
    }

    // Returns the stack that can/will be added.
    public ItemStack addModifier(ItemStack stack, boolean simulate)
    {
        if(isModifierItem(stack))
        {
            if(hasModifierItems())
            {
                if(doModifiersMatch(stack))
                {
                    ItemStack currentModifier = getModifierStack();
                    int spaceAvailable = currentModifier.getMaxStackSize() - currentModifier.getCount();
                    int countToAdd = (stack.getCount()>spaceAvailable)?(spaceAvailable):(stack.getCount());
                    if(countToAdd > 0)
                    {
                        if(!simulate)
                        {
                            ItemStack returner = stack.copy();
                            returner.setCount(countToAdd);
                            IItemHandler h = tableItemHandler.orElse(null);
                            h.insertItem(2,returner,simulate);

                            return returner;
                        }
                        else
                        {
                            ItemStack returner = stack.copy();
                            returner.setCount(countToAdd);
                            return returner;
                        }
                    }
                }
            }
            else
            {
                //For When Adding new Modifiers

                int spaceAvailable = stack.getMaxStackSize();
                int countToAdd = (stack.getCount()>spaceAvailable)?(spaceAvailable):(stack.getCount());
                if(countToAdd > 0)
                {
                    if(!simulate)
                    {
                        ItemStack returner = stack.copy();
                        returner.setCount(countToAdd);
                        IItemHandler h = tableItemHandler.orElse(null);
                        h.insertItem(2,returner,simulate);

                        return returner;
                    }
                    else
                    {
                        ItemStack returner = stack.copy();
                        returner.setCount(countToAdd);
                        return returner;
                    }
                }
            }
        }

        return ItemStack.EMPTY;
    }

    public ItemStack removeModifier(int removalAmount)
    {
        if(hasModifierItems())
        {
            ItemStack returnStack = getModifierStack().copy();
            //-1 as an option to remove all
            int countToRemove = (removalAmount == -1)?(returnStack.getCount()):(removalAmount);
            returnStack.setCount(countToRemove);
            IItemHandler h = tableItemHandler.orElse(null);
            h.extractItem(2,countToRemove,false);

            return returnStack;
        }
        return ItemStack.EMPTY;
    }

    public boolean hasCorruption()
    {
        if(hasModifierItems())
        {
            return getProcessResultCorruption(getRecipeModifier(getLevel(),getModifierStack()));
        }
        return false;
    }

    public int calculateModifiedPotency()
    {
        //0 + 1 But will have to subtract 1 later
        double defaultPotency = 1.0;
        double calculatedPotency = 0.0;
        double potencyMod = getProcessResultModifierPotency(getRecipeModifier(getLevel(),getModifierStack()));
        //Verify fuel can support potency modifier
        if(isAcceptedModifierItem(getModifierStack()))calculatedPotency = defaultPotency * potencyMod;

        //Subtracting 1 'Later'
        return (int)(calculatedPotency-1.0);
    }

    /*============================================================================
    ==============================================================================
    ===========================     MODIFIER END     =============================
    ==============================================================================
    ============================================================================*/



    /*============================================================================
    ==============================================================================
    ===========================   CRAFTING START     =============================
    ==============================================================================
    ============================================================================*/

    public boolean hasEnoughToCraftScroll()
    {
        if(dustMagic.getDustAmount()>0)
        {
            if(hasItemInTable(0) && hasItemInTable(1)) return true;
        }

        return false;
    }

    public int calculateFuelModifiedDuration()
    {
        int fuelDuration = getStoredDust().getDustAmount();
        double durationMod = 0;
        if(isAcceptedModifierItem(getModifierStack())) durationMod = getProcessResultModifierDuration(getRecipeModifier(getLevel(),getModifierStack()));
        if(durationMod > 0)
        {
            return (int)((double)fuelDuration * durationMod);
        }
        else if(durationMod < 0)
        {
            double modifierAbs = Math.abs(durationMod);
            return (int)((double)fuelDuration / modifierAbs);
        }
        else if(fuelDuration > 0)
        {
            return fuelDuration;
        }

        return 0;
    }

    public ItemStack setupCraftedScroll()
    {
        return setupCraftedScroll(1);
    }

    public ItemStack setupCraftedScroll(int count)
    {
        ItemStack returnedStack = ItemStack.EMPTY;
        if(hasEnoughToCraftScroll())
        {
            int duration = calculateFuelModifiedDuration()/count;
            if(duration>=1)
            {
                returnedStack = new ItemStack(DeferredRegisterItems.EFFECT_SCROLL.get(),count);
                ItemStack crystalStack = new ItemStack(DeferredRegisterItems.COLORED_CRYSTAL.get(),count);
                //MobEffect getEffect = calculateMobEffect();
                MobEffect getEffect = MowLibEffectUtils.getEffectForColor(getLevel(), crystalStack, hasCorruption(), getStoredDust().getDustColor());
                int ticksPerDust = EffectScrollsConfig.COMMON.normalEffectTicksDurationPerDust.get();
                int durationModified = duration*ticksPerDust;
                if(getEffect.isInstantenous())
                {
                    int instantDurationTicks = MowLibEffectUtils.getInstantDuration(getLevel(), crystalStack, hasCorruption(),getStoredDust().getDustColor());
                    int dustPerBurst = EffectScrollsConfig.COMMON.instaEffectDustPerEffectBurst.get();
                    int instantMod = duration/dustPerBurst;
                    durationModified = instantDurationTicks * ((instantMod<=0)?(1):(instantMod));
                }
                MobEffectInstance newInstance = new MobEffectInstance(getEffect,durationModified,calculateModifiedPotency(),false,false,true);

                if(returnedStack.getItem() instanceof EffectItemBase)
                {
                    EffectItemBase scroll = (EffectItemBase)returnedStack.getItem();
                    scroll.setEffectToItem(returnedStack, newInstance);
                    MowLibColorReference.addColorToItemStack(returnedStack,getStoredDust().getDustColor());
                }
            }
        }

        return returnedStack;
    }

    public ItemStack craftScrolls(int count)
    {
        int getPaper = getItemInTable(0).getCount();
        int getNuggs = getItemInTable(1).getCount();
        ItemStack getModifier = getItemInTable(2);

        int allowedCount = (count > getPaper)?(getPaper):(count);
        allowedCount = (allowedCount > getNuggs)?(getNuggs):(allowedCount);
        if(!getModifier.isEmpty())allowedCount = (allowedCount > getModifier.getCount())?(getModifier.getCount()):(allowedCount);

        if( this.getScrollCrafted.getItem() instanceof ScrollBase scroll)
        {
            MobEffectInstance effect = scroll.getEffectFromItem(this.getScrollCrafted);
            if(effect.getEffect().isInstantenous())
            {
                int scrollSize = scroll.getMaxStackSize(this.getScrollCrafted);
                allowedCount = (allowedCount > scrollSize)?(scrollSize):(allowedCount);
            }
        }

        ItemStack returnerStack = setupCraftedScroll(allowedCount);
        if(!returnerStack.isEmpty())
        {
            removeItemInTable(0, allowedCount);
            removeItemInTable(1, allowedCount);
            removeItemInTable(2, allowedCount);
            removeDust(getStoredDust().getDustAmount(), IDustHandler.DustAction.EXECUTE);
            update();
            return returnerStack;
        }
        return ItemStack.EMPTY;
    }



    private ItemStack getScrollCraftingOutput()
    {
        if(isFullyRepaired())
        {
            if(hasEnoughToCraftScroll())
            {
                this.getScrollCrafted = setupCraftedScroll();
            }
            else
            {
                this.getScrollCrafted = ItemStack.EMPTY;
            }
        }
        else
        {
            this.getScrollCrafted = ItemStack.EMPTY;
        }

        return this.getScrollCrafted;
    }

    public ItemStack getScrollCrafted()
    {
        return this.getScrollCrafted;
    }

    /*============================================================================
    ==============================================================================
    ===========================    CRAFTING END      =============================
    ==============================================================================
    ============================================================================*/



    public void dropInventoryItemsPrivate(Level worldIn, BlockPos pos) {
        IItemHandler ph = tableItemHandler.orElse(null);
        for(int i = 0; i < ph.getSlots(); ++i) {
            MowLibItemUtils.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ph.getStackInSlot(i));
        }
    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);

        CompoundTag invTableTag = p_155245_.getCompound("inv_table");
        tableItemHandler.ifPresent(h -> ((INBTSerializable<CompoundTag>) h).deserializeNBT(invTableTag));

        if(p_155245_.contains("stackCrafted"))
        {
            CompoundTag invTag = p_155245_.getCompound("stackCrafted");
            ItemStackHandler handler = new ItemStackHandler();
            ((INBTSerializable<CompoundTag>) handler).deserializeNBT(invTag);
            this.getScrollCrafted = handler.getStackInSlot(0);
        }

        this.dustCapacity = p_155245_.getInt(MODID + "_dustCapacity");
    }

    @Override
    public CompoundTag save(CompoundTag p_58888_) {
        super.save(p_58888_);

        tableItemHandler.ifPresent(h -> {
            CompoundTag compound = ((INBTSerializable<CompoundTag>) h).serializeNBT();
            p_58888_.put("inv_table", compound);
        });

        CompoundTag compoundStorage = new CompoundTag();
        ItemStackHandler handler = new ItemStackHandler();
        handler.setSize(1);
        handler.setStackInSlot(0,this.getScrollCrafted);
        compoundStorage = handler.serializeNBT();
        p_58888_.put("stackCrafted",compoundStorage);

        p_58888_.putInt(MODID + "_dustCapacity", this.getDustCapacity());

        return p_58888_;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if(this.dustHandler != null) {
            this.dustHandler.invalidate();
        }
    }
}
