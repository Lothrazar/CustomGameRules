package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(BaseCoralPlantTypeBlock.class)
public class CoralFanAntiDecayMixin {

  @Inject(at = @At("HEAD"), method = "tryScheduleDieTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)V", cancellable = true)
  public void tryScheduleDieTick(BlockState state, LevelAccessor worldIn, BlockPos pos, CallbackInfo info) {
    //    BaseCoralPlantTypeBlock b = (BaseCoralPlantTypeBlock) (Object) this;
    if (worldIn instanceof Level &&
        RuleRegistry.isEnabled(worldIn, RuleRegistry.disableDecayCoral)) {
      info.cancel();
      GameRuleMod.info("CoralFanAntiDecayMixin rule disableDecayCoral=true");
    }
  }
}
