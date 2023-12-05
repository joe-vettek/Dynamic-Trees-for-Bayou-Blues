package xueluoanping.dtbayoublues;

import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.dtbayoublues.systems.featuregen.CherryFeatures;
import xueluoanping.dtbayoublues.systems.leaves.RankineLeavesProperties;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTBayouBluesRegistries {

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(new ResourceLocation(DTBayouBlues.MOD_ID, "rankine_trees"), RankineLeavesProperties.TYPE);
    }


    public static final FeatureCanceller FRUIT_TREES_CANCELLER = new FeatureCanceller(new ResourceLocation(DTBayouBlues.MOD_ID, "fruittrees")) {
        @Override
        public boolean shouldCancel(ConfiguredFeature<?, ?> configuredFeature, BiomePropertySelectors.FeatureCancellations featureCancellations) {
            // Note it not in ForgeRegistries.FEATURES
            final ResourceLocation featureName = WorldGenRegistries.CONFIGURED_FEATURE.getKey(configuredFeature);
            if (featureName == null) {
                return false;
            }

           return featureCancellations.shouldCancelNamespace(featureName.getNamespace())
                   &&(WorldGenRegistries.CONFIGURED_FEATURE.getKey(configuredFeature)+"").startsWith("fruittrees");
        }

    };

    @SubscribeEvent
    public static void onFeatureCancellerRegistry(final RegistryEvent<FeatureCanceller> event) {
        event.getRegistry().registerAll(FRUIT_TREES_CANCELLER);
    }

    @SubscribeEvent
    public static void onGenFeatureRegistry(final RegistryEvent<GenFeature> event) {
        CherryFeatures.register(event.getRegistry());
    }

    // @SubscribeEvent
    // public static void onGrowthLogicKitRegistry(final RegistryEvent<GrowthLogicKit> event) {
    //     DTRankineGrowthLogicKits.register(event.getRegistry());
    // }
}
