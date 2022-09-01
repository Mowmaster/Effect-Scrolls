package com.mowmaster.effectscrolls.Blocks;

import com.mowmaster.effectscrolls.Items.ColoredCrystalBase;
import com.mowmaster.mowlib.MowLibUtils.MowLibColorReference;
import com.mowmaster.mowlib.MowLibUtils.MowLibReferences;
import com.mowmaster.mowlib.api.IColorable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;

public class BaseColoredCrystalBlockItem extends BlockItem implements IColorable {

    private String blockComponentTranslatableText;

    public BaseColoredCrystalBlockItem(Block p_40565_, Properties p_40566_, String componentTranslatableText) {
        super(p_40565_, p_40566_);
        this.blockComponentTranslatableText = componentTranslatableText;

    }

    @Override
    public Component getName(ItemStack p_41458_) {

        int color = MowLibColorReference.getColorFromItemStackInt(p_41458_);
        if(color >= 0)
        {
            return Component.translatable(MODID + blockComponentTranslatableText);
        }

        return super.getName(p_41458_);
    }

    @Override
    public void appendHoverText(ItemStack p_40572_, @Nullable Level p_40573_, List<Component> p_40574_, TooltipFlag p_40575_) {
        super.appendHoverText(p_40572_, p_40573_, p_40574_, p_40575_);
        p_40574_.add(Component.translatable(MowLibReferences.MODID + ".colortext").withStyle(ChatFormatting.GOLD).append(Component.translatable(MowLibReferences.MODID + "." + MowLibColorReference.getColorName(MowLibColorReference.getColorFromItemStackInt(p_40572_))).withStyle(ChatFormatting.WHITE)));
    }
}
