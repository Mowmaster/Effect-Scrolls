package com.mowmaster.effectscrolls.Capabilities.KnownEffects;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.PrivateKey;


/*
    Actual Capability Provider
     */
public class PlayerKnowledgeEffectsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
    //https://youtu.be/My70x9LzeUM
    public static Capability<PlayerKnowledgeEffects> PLAYER_EFFECT_KNOWLEDGE = CapabilityManager.get(new CapabilityToken<PlayerKnowledgeEffects>() { });

    private PlayerKnowledgeEffects effectKnowledge = null;
    private final LazyOptional<PlayerKnowledgeEffects> optional = LazyOptional.of(this::createPlayerEffectKnowledge);

    private PlayerKnowledgeEffects createPlayerEffectKnowledge() {
        if(this.effectKnowledge == null)
        {
            this.effectKnowledge = new PlayerKnowledgeEffects();
        }

        return this.effectKnowledge;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_EFFECT_KNOWLEDGE)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerEffectKnowledge().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerEffectKnowledge().loadNBTData(nbt);
    }
}
