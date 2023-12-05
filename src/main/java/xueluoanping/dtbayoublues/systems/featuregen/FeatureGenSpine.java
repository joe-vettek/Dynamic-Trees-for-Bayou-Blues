package xueluoanping.dtbayoublues.systems.featuregen;

import com.ferreusveritas.dynamictrees.api.configurations.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.ferreusveritas.dynamictrees.blocks.branches.TrunkShellBlock;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeatures.GenFeatureConfiguration;
import com.ferreusveritas.dynamictrees.systems.genfeatures.context.PostGenerationContext;
import com.ferreusveritas.dynamictrees.systems.genfeatures.context.PostGrowContext;
import com.minecraftabnormals.abnormals_core.lib.sonar.sonar.common.block.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.util.Constants;
import xueluoanping.dtbayoublues.util.RegisterFinderUtil;

import java.util.Random;

public class FeatureGenSpine extends GenFeature {

    public static final ConfigurationProperty<String> SPINE = ConfigurationProperty.property("spine", String.class);

    public BlockState getSpineBlock(GenFeatureConfiguration configuration) {
        return RegisterFinderUtil.getBlock(configuration.get(SPINE)).defaultBlockState();
    }

    public FeatureGenSpine(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
        this.register(SPINE);
    }

    @Override
    protected GenFeatureConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(SPINE, "air");
    }

    @Override
    protected boolean postGenerate(GenFeatureConfiguration configuration, PostGenerationContext context) {
        final IWorld world = context.world();


        final Random random = context.random();
        BlockPos offPos = context.pos();
        for (int i = 0; i < 32; i++) {
            offPos = offPos.above();

            if (!world.isEmptyBlock(offPos)) {
                Block block = world.getBlockState(offPos).getBlock();

                if (!(block instanceof BranchBlock)) {
                    break;
                } else if (((BranchBlock) block).getRadius(world.getBlockState(offPos)) == 8) {// Convert grass or dirt to podzol

                    testAir(world, offPos, Direction.NORTH.getOpposite(), configuration);
                    testAir(world, offPos.east(1), Direction.NORTH.getOpposite(), configuration);
                    testAir(world, offPos.west(1), Direction.NORTH.getOpposite(), configuration);
                    testAir(world, offPos.south(1), Direction.NORTH.getOpposite(), configuration);
                    testAir(world, offPos.north(1), Direction.NORTH.getOpposite(), configuration);
                } else
                    break;

            } else
                break;
        }
        // final BlockPos pos = endPoints.get(random.nextInt(endPoints.size()));


        return true;
    }

    @Override
    protected boolean postGrow(GenFeatureConfiguration configuration, PostGrowContext context) {

        final IWorld world = context.world();
        final Random random = context.random();
        BlockPos offPos = context.pos();
        for (int i = 0; i < 32; i++) {
            offPos = offPos.above();

            if (!world.isEmptyBlock(offPos)) {
                Block block = world.getBlockState(offPos).getBlock();

                if (block instanceof TrunkShellBlock) {
                    continue;
                } else if (!(block instanceof BranchBlock)) {
                    break;
                } else if (((BranchBlock) block).getRadius(world.getBlockState(offPos)) == 8) {// Convert grass or dirt to podzol

                    testAir(world, offPos, Direction.NORTH.getOpposite(), configuration);
                    if (world.getRandom().nextInt(4) == 0)
                        testAir(world, offPos.east(), Direction.EAST, configuration);
                    if (world.getRandom().nextInt(4) == 0)
                        testAir(world, offPos.west(), Direction.WEST, configuration);
                    if (world.getRandom().nextInt(4) == 0)
                        testAir(world, offPos.south(), Direction.SOUTH, configuration);
                    if (world.getRandom().nextInt(4) == 0)
                        testAir(world, offPos.north(), Direction.NORTH, configuration);
                } else
                    break;

            } else
                break;
        }
        // final BlockPos pos = endPoints.get(random.nextInt(endPoints.size()));


        return true;
    }

    private void testAir(IWorld world, BlockPos pos, Direction opposite, GenFeatureConfiguration configuration) {
        if (world.isEmptyBlock(pos)) {
            // random generate , maybe not
            if (world.getRandom().nextInt(20) ==0)
                world.setBlock(pos, getSpineBlock(configuration).setValue(BlockStateProperties.HORIZONTAL_FACING, opposite), Constants.BlockFlags.BLOCK_UPDATE | Constants.BlockFlags.UPDATE_NEIGHBORS);
        }

    }


}
