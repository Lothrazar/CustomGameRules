package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(IceBlock.class)
public class IceAntiMeltMixin {

  @Inject(at = @At(value = "HEAD"), method = "turnIntoWater(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", cancellable = true)
  public void tickMixin(BlockState bs, World worldIn, BlockPos pos, CallbackInfo info) {
    //if disable == true, then      
    System.out.println("ICE ???????????");
    IceBlock me = (IceBlock) (Object) this;
    if (RuleRegistry.isEnabled(worldIn, RuleRegistry.disableLightMeltIce)
        && bs.getBlock() == Blocks.ICE) {
      info.cancel();
      System.out.println("ICE CANCEL");
    }
  }
}
