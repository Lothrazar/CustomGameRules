package com.lothrazar.customgamerules.mixin;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(LeavesBlock.class)
public class LeavesAntiDecay {

  @Inject(at = @At(value = "HEAD"), method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", cancellable = true)
  public void tickMixin(BlockState bs, ServerWorld worldIn, BlockPos pos, Random rand, CallbackInfo info) {
    LeavesBlock me = (LeavesBlock) (Object) this;
    if (RuleRegistry.isEnabled(worldIn, RuleRegistry.disableDecayLeaves)) {
      info.cancel();
    }
  }
}
