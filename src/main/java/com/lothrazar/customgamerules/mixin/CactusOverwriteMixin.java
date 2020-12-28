package com.lothrazar.customgamerules.mixin;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(CactusBlock.class)
public class CactusOverwriteMixin {
  //(at = @At(value = "HEAD"), method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", cancellable = true)

  @Overwrite
  public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
    //      info.setReturnValue(false);
    CactusBlock me = (CactusBlock) (Object) this;
    int MAXHEIGHT = 3;//regular code has this as a bad magic number not a valid block property as it should be
    //IF GAMERULE, H
    if (RuleRegistry.isEnabled(worldIn, RuleRegistry.doCactusGrowthUnlimited)) {
      MAXHEIGHT = 256;
      GameRuleMod.info("CactusOverwriteMixin mixin success and doCactusGrowthUnlimited=true");
    }
    //now the copy paste of the method
    BlockPos blockpos = pos.up();
    if (worldIn.isAirBlock(blockpos)) {
      int i;
      for (i = 1; worldIn.getBlockState(pos.down(i)).isIn(me); ++i) {}
      if (i < MAXHEIGHT) {
        int j = state.get(me.AGE);
        if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, true)) {
          if (j == 15) {//should be AGE.getMax or something
            worldIn.setBlockState(blockpos, me.getDefaultState());
            BlockState blockstate = state.with(me.AGE, Integer.valueOf(0));
            worldIn.setBlockState(pos, blockstate, 4);
            blockstate.neighborChanged(worldIn, blockpos, me, pos, false);
          }
          else {
            worldIn.setBlockState(pos, state.with(me.AGE, Integer.valueOf(j + 1)), 4);
          }
          net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
      }
    }
  }
}
