package com.mowmaster.effectscrolls.Blocks.BlockEntities.DustJar;


import com.mowmaster.effectscrolls.EffectScrollsUtils.References;
import com.mowmaster.mowlib.Capabilities.Dust.DustMagic;
import com.mowmaster.mowlib.Items.BaseDustStorageBlockItem;
import com.mowmaster.mowlib.MowLibUtils.ColorReference;
import com.mowmaster.mowlib.MowLibUtils.MessageUtils;
import com.mowmaster.mowlib.MowLibUtils.MowLibReferences;
import com.mowmaster.mowlib.MowLibUtils.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static com.mowmaster.mowlib.MowLibUtils.MowLibReferences.MODID;

import net.minecraft.world.item.Item.Properties;

public class DustJarBlockItem extends BaseDustStorageBlockItem {
    public DustJarBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    public static int getFillLevel(ItemStack stack) {

        // 0 - Empty
        // 1 - Has Dust
        // 2 - 10%
        // 3 - 20%
        // 4 - 30%
        // 5 - 40%
        // 6 - 50%
        // 7 - 60%
        // 8 - 70%
        // 9 - 80%
        // 10 - 90%
        // 11 - Full

        DustMagic getJarMagic = DustMagic.getDustMagicInItemStack(stack);
        double amount = (double)getJarMagic.getDustAmount();
        double capacity = (stack.hasTag())?((stack.getTag().contains(MODID + "_dustCapacity"))?(stack.getTag().getInt(MODID + "_dustCapacity")):(1000)):(1000);
        double heightRenderMultiplier = (double)(10.0 * (amount/capacity));

        if(heightRenderMultiplier<=0)return 0;
        else if(heightRenderMultiplier<=0.99)return 1;
        else if(heightRenderMultiplier<=1.99)return 2;
        else if(heightRenderMultiplier<=2.99)return 3;
        else if(heightRenderMultiplier<=3.99)return 4;
        else if(heightRenderMultiplier<=4.99)return 5;
        else if(heightRenderMultiplier<=5.99)return 6;
        else if(heightRenderMultiplier<=6.99)return 7;
        else if(heightRenderMultiplier<=7.99)return 8;
        else if(heightRenderMultiplier<=8.99)return 9;
        else if(heightRenderMultiplier<=9.99)return 10;
        else if(heightRenderMultiplier>=10.0)return 11;
        else return 0;
    }

    public static int getFillColor(ItemStack stack) {
        DustMagic getJarMagic = DustMagic.getDustMagicInItemStack(stack);
        return getJarMagic.getDustColor();
    }

    @Override
    public void appendHoverText(ItemStack p_40572_, @Nullable Level p_40573_, List<Component> p_40574_, TooltipFlag p_40575_) {
        super.appendHoverText(p_40572_, p_40573_, p_40574_, p_40575_);

        DustMagic magic = DustMagic.getDustMagicInItemStack(p_40572_);
        if(magic.getDustColor() != -1)
        {
            MutableComponent base = Component.translatable(MODID + ".dust_in_jar");
            MutableComponent base2 = Component.translatable(MODID + "." + ColorReference.getColorName(magic.getDustColor()));
            base.append(base2);
            base.append(": ");
            base.append(""+magic.getDustAmount()+"");
            base.withStyle(ChatFormatting.WHITE);
            p_40574_.add(base);
        }
    }
}
