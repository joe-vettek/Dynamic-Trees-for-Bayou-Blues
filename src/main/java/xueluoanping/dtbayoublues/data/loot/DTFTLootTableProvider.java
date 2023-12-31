package xueluoanping.dtbayoublues.data.loot;

import com.ferreusveritas.dynamictrees.data.provider.DTLootTableProvider;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraftforge.common.data.ExistingFileHelper;

//  I inherited DTLootTableProvider, but many of its functions are private,
//  so I have to copy them to facilitate modification.

public class DTFTLootTableProvider extends DTLootTableProvider {

    private static final ILootCondition.IBuilder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item()
            .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    private static final ILootCondition.IBuilder HAS_SHEARS =
            MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final ILootCondition.IBuilder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final ILootCondition.IBuilder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    private final DataGenerator generator;
    private final String modId;
    private final ExistingFileHelper existingFileHelper;
    public DTFTLootTableProvider(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
        super(generator, modId, existingFileHelper);
        this.generator = generator;
        this.modId = modId;
        this.existingFileHelper = existingFileHelper;
    }

    // The reason why these functions appear is that
    // the loot table of the leaves block needs to be overwritten.
    // @Override
    // public void run(DirectoryCache cache) {
    //
    //     // First generate the default
    //     super.run(cache);
    //
    //     // Now overwrite and generate the parts that need to be customized.
    //     addTables();
    //     writeTables(cache);
    // }
    //
    //
    // private void addTables() {
    //
    //     LeavesProperties.REGISTRY.dataGenerationStream(modId).forEach(this::addLeavesBlockTable);
    //
    // }
    // private void writeTables(DirectoryCache cache) {
    //     Path outputFolder = this.generator.getOutputFolder();
    //     lootTables.forEach((key, lootTable) -> {
    //         Path path = outputFolder.resolve("data/" + key.getNamespace() + "/" + key.getPath());
    //         try {
    //             IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable.build()), path);
    //         } catch (IOException e) {
    //             DTFruitTrees.LOGGER.error("Couldn't write loot table {}", path, e);
    //         }
    //     });
    // }
    // private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    //
    // private final Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();
    //
    // private ResourceLocation getFullDropsPath(ResourceLocation path) {
    //     return ResourceLocationUtils.surround(path, "loot_tables/", ".json");
    // }
    //
    // // The only changed behavior is here.
    // private void addLeavesBlockTable(LeavesProperties leavesProperties) {
    //     if (leavesProperties.shouldGenerateBlockDrops()) {
    //         final ResourceLocation leavesBlockTablePath = getFullDropsPath(leavesProperties.getBlockLootTableName());
    //         if (!existingFileHelper.exists(leavesBlockTablePath, ResourcePackType.SERVER_DATA)) {
    //             lootTables.put(leavesBlockTablePath, leavesProperties.createBlockDrops().withPool(
    //                     LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(HAS_NO_SHEARS_OR_SILK_TOUCH)
    //                             .add(ItemLootEntry.lootTableItem(leavesProperties.getFamily().getCommonSpecies().getSeed().get().asItem())
    //                                     .apply(SetCount.setCount(
    //                                             RandomValueRange.between(1.0F, 2.0F)
    //                                     ))
    //                                     .apply(ExplosionDecay.explosionDecay())
    //                                     .when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.083333336F, 0.1F)))
    //             ));
    //         }
    //     }
    // }
}
