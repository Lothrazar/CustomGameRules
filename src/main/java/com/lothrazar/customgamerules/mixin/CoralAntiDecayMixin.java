package com.lothrazar.customgamerules.mixin;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(CoralBlock.class)
public class CoralAntiDecayMixin {

  @Inject(at = @At("HEAD"), method = "tick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", cancellable = true)
  public void tickMixin(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand, CallbackInfo info) {
    //    CoralBlock block = (CoralBlock) (Object) this; 
    if (RuleRegistry.isEnabled(worldIn, RuleRegistry.disableDecayCoral)) {
      info.cancel();
      GameRuleMod.info("CoralAntiDecayMixin mixin success and disableDecayCoral=true");
    }
  }
}
