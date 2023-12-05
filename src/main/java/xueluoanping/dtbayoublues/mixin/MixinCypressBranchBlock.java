package xueluoanping.dtbayoublues.mixin;

import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.teamaurora.bayou_blues.common.block.CypressBranchBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xueluoanping.dtbayoublues.DTBayouBlues;
import xueluoanping.dtbayoublues.util.RegisterFinderUtil;


@Mixin({CypressBranchBlock.class})
public class MixinCypressBranchBlock {

    @Inject(at = @At("HEAD"), method = "canSurvive", cancellable = true)
    private void zz$canSurvive(BlockState state, IWorldReader reader, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState state1 = reader.getBlockState(pos.relative(state.getValue(CypressBranchBlock.FACING).getOpposite()));
        if (state1.is(RegisterFinderUtil.getBlock(DTBayouBlues.MOD_ID + ":cypress_branch"))) {

            if (((BranchBlock) state1.getBlock()).getRadius(state1) == 8)
                cir.setReturnValue(true);
        }
        // DTBayouBlues.logger("testCypressBranchBlock"+reader.getBlockState(pos.relative(state.getValue(CypressBranchBlock.FACING).getOpposite())));
    }
}
