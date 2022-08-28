package com.lothrazar.customgamerules.mixin;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(LeavesBlock.class)
public class LeavesAntiDecayMixin {

  @Inject(at = @At("HEAD"), method = "randomTick(Lnet/minecraft/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V", cancellable = true)
  public void randomTick(BlockState bs, ServerLevel worldIn, BlockPos pos, Random rand, CallbackInfo info) {
    //    LeavesBlock me = (LeavesBlock) (Object) this;
    if (RuleRegistry.isEnabled(worldIn, RuleRegistry.disableDecayLeaves)) {
      info.cancel();
      GameRuleMod.info("LeavesAntiDecayMixin mixin success and disableDecayLeaves=true");
    }
  }
}
