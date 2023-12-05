package xueluoanping.dtbayoublues.mixin;

import com.teamaurora.bayou_blues.common.block.BeardMossBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xueluoanping.dtbayoublues.DTBayouBlues;
import xueluoanping.dtbayoublues.util.RegisterFinderUtil;


@Mixin({BeardMossBlock.class})
public class MixinBeardMossBlock {

    @Inject(at = @At("HEAD"), method = "canSurvive", cancellable = true)
    private  void zz$canSurvive(BlockState state, IWorldReader reader, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (reader.getBlockState(pos.above()).is(RegisterFinderUtil.getBlock(DTBayouBlues.MOD_ID+":beard_moss_leaves")))
        {
                cir.setReturnValue(true);
        }
    }
}
