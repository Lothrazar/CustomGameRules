package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

@Mixin(Biome.class)
public class BiomeAntiFreezeMixinFlib {
  //MC 1.18.2: net/minecraft/world/level/biome/Biome.shouldFreeze
  //Name: a => m_47480_ => shouldFreeze
  //Side: BOTH
  //Descriptor: boolean shouldFreeze(net.minecraft.world.level.LevelReader,net.minecraft.core.BlockPos,boolean)
  //AT: public net.minecraft.world.level.biome.Biome m_47480_(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z # shouldFreeze

  @Inject(at = @At("HEAD"), method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z", cancellable = true)
  public void shouldFreeze(LevelReader worldIn, BlockPos pos, boolean mustBeAtEdge, CallbackInfoReturnable<Boolean> info) {
    if (worldIn instanceof Level &&
        RuleRegistry.isEnabled((Level) worldIn, RuleRegistry.disableBiomeFreezeIce)) {
      info.setReturnValue(false);
      GameRuleMod.info("BiomeAntiFreezeMixin mixin success and disableBiomeFreezeIce=true");
    }
  }
}
