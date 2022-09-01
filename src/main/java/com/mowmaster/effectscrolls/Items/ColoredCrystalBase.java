package com.mowmaster.effectscrolls.Items;

import com.mowmaster.effectscrolls.Registry.DeferredRegisterItems;
import com.mowmaster.mowlib.Items.BaseDustStorageItem;
import com.mowmaster.mowlib.MowLibUtils.MowLibColorReference;
import com.mowmaster.mowlib.MowLibUtils.MowLibReferences;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;

public class ColoredCrystalBase extends BaseDustStorageItem {
    boolean changeName;
    public ColoredCrystalBase(Item.Properties p_41383_, boolean colorableNameChange) {
        super(p_41383_);
        this.changeName = colorableNameChange;
    }

    @Override
    public Component getName(ItemStack p_41458_) {

        int color = MowLibColorReference.getColorFromItemStackInt(p_41458_);
        if(changeName)
        {
            if(color>=0)
            {
                MutableComponent comp = Component.translatable(MowLibReferences.MODID + "." + MowLibColorReference.getColorName(color));
                comp.append( Component.translatable(MODID + ".crystal"));
                return comp;
            }
        }

        return super.getName(p_41458_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);

    }
}
