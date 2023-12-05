package xueluoanping.dtbayoublues.systems.featuregen;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.configurations.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.compat.seasons.SeasonHelper;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeatureConfiguration;
import com.ferreusveritas.dynamictrees.systems.genfeatures.context.PostGenerationContext;
import com.ferreusveritas.dynamictrees.systems.genfeatures.context.PostGrowContext;
import com.ferreusveritas.dynamictrees.systems.nodemappers.FindEndsNode;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import com.ferreusveritas.dynamictrees.util.WorldContext;
import com.teamaurora.bayou_blues.common.block.HangingCypressLeavesBlock;
import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import net.minecraft.block.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import xueluoanping.dtbayoublues.DTBayouBlues;

import javax.annotation.Nullable;
import java.util.List;

// have to fix some
public class VinesGenFeature extends com.ferreusveritas.dynamictrees.systems.genfeatures.VinesGenFeature {


    public VinesGenFeature(ResourceLocation registryName) {
        super(registryName);
    }


    @Override
    protected boolean postGenerate(GenFeatureConfiguration configuration, PostGenerationContext context) {
        if (!context.isWorldGen() || context.endPoints().isEmpty()) {
            return false;
        }

        final com.ferreusveritas.dynamictrees.systems.genfeatures.VinesGenFeature.VineType vineType = configuration.get(VINE_TYPE);
        final int quantity = configuration.get(QUANTITY);

        for (int i = 0; i < quantity; i++) {
            final BlockPos endPoint = context.endPoints().get(context.random().nextInt(context.endPoints().size()));

            switch (vineType) {
                case SIDE:
                    this.addSideVines(configuration, context.world(), context.species(), context.pos(), endPoint, context.bounds(), true);
                    break;
                case CEILING:
                    this.addVerticalVines(configuration, context.world(), context.species(), context.pos(), endPoint, context.bounds(), false);
                    break;
                case FLOOR:
                    super.addVerticalVines(configuration, context.world(), context.species(), context.pos(), endPoint, context.bounds(), true);
                    break;
            }
        }

        return true;
    }

    @Override
    protected boolean postGrow(GenFeatureConfiguration configuration, PostGrowContext context) {
        final IWorld world = context.world();
        final BlockPos rootPos = context.pos();
        final Species species = context.species();
        int fruitingRadius = configuration.get(FRUITING_RADIUS);

        if (fruitingRadius < 0 || context.fertility() < 1) {
            return false;
        }
        final BlockState blockState = world.getBlockState(context.treePos());
        final BranchBlock branch = TreeHelper.getBranch(blockState);

        if (branch != null && branch.getRadius(blockState) >= fruitingRadius && context.natural()) {
            if (SeasonHelper.globalSeasonalFruitProductionFactor(WorldContext.create(world), rootPos, false)
                    > world.getRandom().nextFloat()) {
                final FindEndsNode endFinder = new FindEndsNode();
                TreeHelper.startAnalysisFromRoot(world, rootPos, new MapSignal(endFinder));
                final List<BlockPos> endPoints = endFinder.getEnds();
                final int qty = configuration.get(QUANTITY);

                if (!endPoints.isEmpty()) {
                    for (int i = 0; i < qty; i++) {
                        BlockPos endPoint = endPoints.get(world.getRandom().nextInt(endPoints.size()));
                        if (configuration.get(VINE_TYPE) == VineType.SIDE) {
                            this.addSideVines(configuration, world, species, rootPos, endPoint, SafeChunkBounds.ANY, false);
                        } else {
                            if (configuration.get(VINE_TYPE) != VineType.CEILING)
                                super.addVerticalVines(configuration, world, species, rootPos, endPoint, SafeChunkBounds.ANY, false);
                            this.addVerticalVines(configuration, world, species, rootPos, endPoint, SafeChunkBounds.ANY, false);
                        }
                    }
                    return true;
                }
            }
        }

        return true;
    }


    protected void addVerticalVines(GenFeatureConfiguration configuration, IWorld world, Species species, BlockPos rootPos, BlockPos branchPos, SafeChunkBounds safeBounds, boolean worldgen) {
        // Uses fruit ray trace method to grab a position under the tree's leaves.
        BlockPos vinePos = CoordUtils.getRayTraceFruitPos(world, species, rootPos, branchPos, safeBounds);
        if (!safeBounds.inBounds(vinePos, true)) {
            return;
        }

        if (vinePos == BlockPos.ZERO) {
            return;
        }
        this.placeVines(world, vinePos, configuration.get(BLOCK).defaultBlockState(),
                configuration.get(MAX_LENGTH),
                configuration.getAsOptional(TIP_BLOCK)
                        .map(block -> {
                            if (block.defaultBlockState().hasProperty(AbstractTopPlantBlock.AGE))
                                return block.defaultBlockState().setValue(AbstractTopPlantBlock.AGE, worldgen ? 25 : 0);
                            else
                                return block.defaultBlockState();
                        })
                        .orElse(null),
                configuration.get(VINE_TYPE), worldgen);
    }


    protected void placeVines(IWorld world, BlockPos vinePos, BlockState vinesState, int maxLength, @Nullable BlockState tipState, VineType vineType, boolean worldgen) {
        // Generate a random length for the vine.
        final int len = worldgen ? MathHelper.clamp(world.getRandom().nextInt(maxLength) + 3, 3, maxLength) : 2;
        final BlockPos.Mutable mPos = new BlockPos.Mutable(vinePos.getX(), vinePos.getY(), vinePos.getZ());
        BlockState state = world.getBlockState(vinePos.above());
        if (tipState != null)
            for (int i = 0; i < len; i++) {

                if (world.isEmptyBlock(mPos)) {
                    if (vinesState.hasProperty(DynamicLeavesBlock.DISTANCE) && state.hasProperty(DynamicLeavesBlock.DISTANCE))
                        vinesState = vinesState.setValue(DynamicLeavesBlock.DISTANCE, state.getValue(DynamicLeavesBlock.DISTANCE));
                    world.setBlock(mPos, i != 0 ? tipState : vinesState, 3);
                    mPos.setY(mPos.getY() - 1);
                }
                // else if (world.getBlockState(mPos).is(BayouBluesBlocks.HANGING_CYPRESS_LEAVES.get()) && i != 0) {
                //     world.setBlock(mPos, tipState, 3);
                //     mPos.setY(mPos.getY() - 1);
                // }
            }
    }

}

