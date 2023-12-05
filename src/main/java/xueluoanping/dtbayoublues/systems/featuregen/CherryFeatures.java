package xueluoanping.dtbayoublues.systems.featuregen;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeature;
import net.minecraft.util.ResourceLocation;

import xueluoanping.dtbayoublues.DTBayouBlues;

public class CherryFeatures {
    public static final GenFeature FALLEN_LEAVES = new FeatureGenFallenLeaves(regName("fallen_leaves"));
    public static final GenFeature VINES = new VinesGenFeature(regName("vines"));
    public static final GenFeature SPINES = new FeatureGenSpine(regName("spines"));
    private static ResourceLocation regName(String name) {
        return new ResourceLocation(DTBayouBlues.MOD_ID, name);
    }

    public static void register(final Registry<GenFeature> registry) {
        registry.registerAll(FALLEN_LEAVES,VINES, SPINES);
    }
}
