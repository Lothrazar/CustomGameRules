package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.block.AbstractCoralPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

@Mixin(AbstractCoralPlantBlock.class)
public class CoralFanAntiDecayMixin {

  //@Inject(at = @At("HEAD"), method = "updateIfDry(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;)V", cancellable = true)
  //inject with callback info DOES NOT WORK AFTER I COMPILE DPELOY AND RUN IN PRODUCTION
  //, CallbackInfo info
  //overwrite somehow magically does work
  @Inject(at = @At("HEAD"), method = "updateIfDry(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;)V", cancellable = true)
  public void tickMixin(BlockState state, IWorld worldIn, BlockPos pos, CallbackInfo info) {
    //    AbstractCoralPlantBlock b = (AbstractCoralPlantBlock) (Object) this;
    if (worldIn instanceof World &&
        RuleRegistry.isEnabled((World) worldIn, RuleRegistry.disableDecayCoral)) {
      info.cancel();
    }
  }
}
