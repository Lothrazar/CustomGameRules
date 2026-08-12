package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.ModGameRule;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(BaseCoralPlantTypeBlock.class)
public class CoralFanAntiDecayMixin {

  // Signature changed from (BlockState, LevelAccessor, BlockPos) - Mojang reworked tick
  // scheduling to take BlockGetter + ScheduledTickAccess + RandomSource, with BlockPos last.
  // Confirmed against the actual 26.1.2 game jar via Mixin's mixin.dumpTargetOnFailure output.
  @Inject(at = @At("HEAD"), method = "tryScheduleDieTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/level/ScheduledTickAccess;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)V", cancellable = true, remap = false)
  public void tryScheduleDieTick(BlockState state, BlockGetter worldIn, ScheduledTickAccess tickAccess, RandomSource random, BlockPos pos, CallbackInfo info) {
    if (worldIn instanceof Level level &&
        RuleRegistry.isEnabled(level, RuleRegistry.disableDecayCoral)) {
      info.cancel();
      ModGameRule.LOGGER.debug("CoralFanAntiDecayMixin rule disableDecayCoral=true");
    }
  }
}
