package com.lothrazar.customgamerules.mixin;

import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.ModGameRule;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(LeavesBlock.class)
public class LeavesAntiDecayMixin {

  @Inject(at = @At("HEAD"), method = "randomTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", cancellable = true, remap = false)
  public void randomTick(BlockState bs, ServerLevel worldIn, BlockPos pos, RandomSource rand, CallbackInfo info) {
    //    LeavesBlock me = (LeavesBlock) (Object) this;
    if (RuleRegistry.isEnabled(worldIn, RuleRegistry.disableDecayLeaves)) {
      info.cancel();
      ModGameRule.LOGGER.debug("LeavesAntiDecayMixin rule disableDecayLeaves=true");
    }
  }
}
