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
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(CoralBlock.class)
public class CoralAntiDecayMixin {

  @Inject(at = @At("HEAD"), method = "tick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", cancellable = true, remap = false)
  public void tickMixin(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand, CallbackInfo info) {
    //    CoralBlock block = (CoralBlock) (Object) this;
    if (RuleRegistry.isEnabled(worldIn, RuleRegistry.disableDecayCoral)) {
      info.cancel();
      ModGameRule.LOGGER.debug("CoralAntiDecayMixin rule disableDecayCoral=true");
    }
  }
}
