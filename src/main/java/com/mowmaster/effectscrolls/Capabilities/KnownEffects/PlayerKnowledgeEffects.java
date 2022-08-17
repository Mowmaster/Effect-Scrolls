package com.mowmaster.effectscrolls.Capabilities.KnownEffects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mowmaster.effectscrolls.EffectScrollsUtils.References.MODID;

/*
    Actually saves the data and does a few other things

     */
public class PlayerKnowledgeEffects
{
    private Map<MobEffect, Integer> knownEffectsAndPotency;

    public boolean knowsEffect(MobEffectInstance incomingMobEffect)
    {
        return knownEffectsAndPotency.containsValue(incomingMobEffect.getEffect())
                && incomingMobEffect.getAmplifier() <= knownEffectsAndPotency.get(incomingMobEffect.getEffect());
    }

    public void addToKnownList(MobEffectInstance incomingMobEffect)
    {
        if(knownEffectsAndPotency.containsValue(incomingMobEffect.getEffect()))
        {
            //replace amplifier value
            knownEffectsAndPotency.replace(incomingMobEffect.getEffect(),incomingMobEffect.getAmplifier());
        }
        else
        {
            //Add new Effect and potency
            this.knownEffectsAndPotency.put(incomingMobEffect.getEffect(),incomingMobEffect.getAmplifier());
        }

    }

    public void copyFrom(PlayerKnowledgeEffects source)
    {
        this.knownEffectsAndPotency = source.knownEffectsAndPotency;
    }

    /*
    public static Map<Enchantment, Integer> deserializeEnchantments(ListTag p_44883_) {
      Map<Enchantment, Integer> map = Maps.newLinkedHashMap();

      for(int i = 0; i < p_44883_.size(); ++i) {
         CompoundTag compoundtag = p_44883_.getCompound(i);
         Registry.ENCHANTMENT.getOptional(getEnchantmentId(compoundtag)).ifPresent((p_44871_) -> {
            map.put(p_44871_, getEnchantmentLevel(compoundtag));
         });
      }

      return map;
   }
     */
    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putInt(MODID + "_known_effects_size",knownEffectsAndPotency.size());
        List<Integer> potencyList = new ArrayList<>();
        int counter = 1;
        for (Map.Entry<MobEffect, Integer> entry : knownEffectsAndPotency.entrySet()) {
            ResourceLocation effectLoc = ForgeRegistries.MOB_EFFECTS.getKey(entry.getKey());
            int potency = entry.getValue();
            nbt.putString(MODID + "_known_effect_" + counter + "", effectLoc.toString());
            potencyList.add(potency);
            counter++;
        }
        nbt.putIntArray(MODID + "_known_effect_potencies",potencyList);
    }

    public void loadNBTData(CompoundTag nbt)
    {
        nbt.putInt(MODID + "_known_effects_size",knownEffectsAndPotency.size());
        List<Integer> potencyList = new ArrayList<>();
        int counter = 1;
        for (Map.Entry<MobEffect, Integer> entry : knownEffectsAndPotency.entrySet()) {
            ResourceLocation effectLoc = ForgeRegistries.MOB_EFFECTS.getKey(entry.getKey());
            int potency = entry.getValue();
            nbt.putString(MODID + "_known_effect_" + counter + "", effectLoc.toString());
            potencyList.add(potency);
            counter++;
        }
        nbt.putIntArray(MODID + "_known_effect_potencies",potencyList);
    }



}
