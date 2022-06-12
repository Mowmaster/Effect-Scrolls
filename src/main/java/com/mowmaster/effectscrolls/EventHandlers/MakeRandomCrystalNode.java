package com.mowmaster.effectscrolls.EventHandlers;

import com.mowmaster.effectscrolls.Registry.DeferredRegisterBlocks;
import com.mowmaster.effectscrolls.Registry.DeferredRegisterItems;
import com.mowmaster.mowlib.Items.ColorApplicator;
import com.mowmaster.mowlib.MowLibUtils.ColorReference;
import com.mowmaster.mowlib.MowLibUtils.MessageUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;

@Mod.EventBusSubscriber
public class MakeRandomCrystalNode {

    @SubscribeEvent()
    public static void makeRandomCrystalNode(PlayerInteractEvent.RightClickBlock event)
    {
        if(!(event.getPlayer() instanceof FakePlayer))
        {
            Level level = event.getWorld();
            Player player = event.getPlayer();
            InteractionHand hand = event.getHand();
            BlockPos pos = event.getPos();

            if(!level.isClientSide() && hand.equals(InteractionHand.MAIN_HAND))
            {
                if(player.getItemInHand(hand).getItem().equals(DeferredRegisterItems.COLORED_NODE_MAKER.get()))
                {
                    int colorListSize = ColorReference.ALL_COLORS.size();
                    int randomColor = new Random().nextInt(colorListSize-1);
                    int getColor = ColorReference.ALL_COLORS.get(randomColor);
                    BlockState oldBlock = level.getBlockState(pos);
                    if(oldBlock.getBlock() instanceof BuddingAmethystBlock)
                    {
                        level.setBlock(pos, ColorReference.addColorToBlockState(DeferredRegisterBlocks.CRYSTAL_NODE.get().defaultBlockState(),getColor) ,3);
                        player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                        MessageUtils.messagePopup(player, ChatFormatting.GREEN, MODID + ".crystal_node.set_success");
                    }
                    else MessageUtils.messagePopup(player, ChatFormatting.RED, MODID + ".crystal_node.set_fail");
                }
            }
        }
    }
}
