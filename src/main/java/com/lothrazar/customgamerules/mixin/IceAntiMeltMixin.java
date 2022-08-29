package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(IceBlock.class)
public class IceAntiMeltMixin {

  @Inject(at = @At("HEAD"), method = "melt(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V", cancellable = true)
  public void melt(BlockState bs, Level worldIn, BlockPos pos, CallbackInfo info) {
    //      IceBlock me = (IceBlock) (Object) this;
    if (RuleRegistry.isEnabled(worldIn, RuleRegistry.disableLightMeltIce)) {
      info.cancel();
      GameRuleMod.info("IceAntiMeltMixin rule disableLightMeltIce=true");
    }
  }
}
