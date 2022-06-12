package com.mowmaster.effectscrolls.EffectScrollConfig;

import com.mowmaster.effectscrolls.effectscrolls;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class EffectScrollsConfig
{
    public static class Common {
        public final ForgeConfigSpec.BooleanValue funHaters;

        public final ForgeConfigSpec.IntValue effectMaxPotency;

        public final ForgeConfigSpec.IntValue nodeGrowthChance;

        public final ForgeConfigSpec.IntValue repairitemsToCraft_ScrollCrafter_T15;

        public final ForgeConfigSpec.IntValue normalEffectTicksDurationPerDust;
        public final ForgeConfigSpec.IntValue instaEffectDustPerEffectBurst;


        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Do you Hate Fun???")
                    .push("FunHaters");

            funHaters = builder.comment("Only set to true if you hate fun").define("funhaters", false);

            builder.pop();

            builder.comment("Potency Setting").push("Potency");

            effectMaxPotency = builder
                    .comment("Max Effect Potency")
                    .defineInRange("maxPotency", 5, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.comment("Crystals").push("Crystal");

            nodeGrowthChance = builder
                    .comment("Crystal Node Growth Chance (1/X)")
                    .defineInRange("maxChance", 5, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.comment("Machine Settings").push("Machines");

            repairitemsToCraft_ScrollCrafter_T15 = builder
                    .comment("Repair Items Needed To Craft the Scroll Crafter Tier 1.5")
                    .defineInRange("repairItemsNeeded_scrollcrafter_t15", 3, 1, Integer.MAX_VALUE);
            normalEffectTicksDurationPerDust = builder
                    .comment("Ticks Of Duration for Normal Effects Per Dust Used")
                    .defineInRange("normalEffectTicksDurationPerDust", 20, 1, Integer.MAX_VALUE);
            instaEffectDustPerEffectBurst = builder
                    .comment("Dust Need per Burst of an Instant Effect")
                    .defineInRange("instaEffectDustPerEffectBurst", 16, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        effectscrolls.LOGGER.debug("Loaded EffectScrolls config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
        effectscrolls.LOGGER.debug("EffectScrolls config just got changed on the file system!");
    }
}
