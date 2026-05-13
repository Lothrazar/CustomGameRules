package com.lothrazar.customgamerules.mixin;

import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.common.CommonHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.ModGameRule;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(SugarCaneBlock.class)
public class SugarOverwriteMixin {

  @Inject(at = @At("HEAD"), method = "randomTick", cancellable = true, remap = false)
  public void randomTickUnlimited(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
    if (!RuleRegistry.isEnabled(level, RuleRegistry.doSugarGrowthUnlimited)) {
      return;
    }
    ModGameRule.LOGGER.debug("SugarOverwriteMixin rule doSugarGrowthUnlimited=true");
    SugarCaneBlock me = (SugarCaneBlock) (Object) this;

    if (level.isEmptyBlock(pos.above())) {
      int i;
      for (i = 1; level.getBlockState(pos.below(i)).is(me); ++i) {}
      if (i < 256) { // THIS is where we override the hardcoded 3 !
        int j = state.getValue(SugarCaneBlock.AGE);
        if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos, state, true)) {
          if (j == 15) {
            level.setBlockAndUpdate(pos.above(), me.defaultBlockState());
            CommonHooks.fireCropGrowPost(level, pos.above(), me.defaultBlockState());
            level.setBlock(pos, state.setValue(SugarCaneBlock.AGE, 0), 4);
          } else {
            level.setBlock(pos, state.setValue(SugarCaneBlock.AGE, j + 1), 4);
          }
          //removed to match current vanilla/neoforge status exactly
//          net.neoforged.neoforge.common.CommonHooks.onCropsGrowPost(level, pos, state);
        }
      }
    }
    info.cancel();
  }
}
