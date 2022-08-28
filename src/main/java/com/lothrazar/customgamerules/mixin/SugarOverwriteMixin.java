package com.lothrazar.customgamerules.mixin;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(SugarCaneBlock.class)
public class SugarOverwriteMixin {
  //(at = @At(value = "HEAD"), method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", cancellable = true)

  @Overwrite
  public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
    //      info.setReturnValue(false);
    SugarCaneBlock me = (SugarCaneBlock) (Object) this;
    int MAXHEIGHT = 3;//regular code has this as a bad magic number not a valid block property as it should be
    //IF GAMERULE, H
    if (RuleRegistry.isEnabled(worldIn, RuleRegistry.doSugarGrowthUnlimited)) {
      MAXHEIGHT = 256;
      GameRuleMod.info("SugarOverwriteMixin mixin success and doSugarGrowthUnlimited=true");
    }
    //now the copy paste of the method
    BlockPos blockpos = pos.above();
    if (worldIn.isEmptyBlock(blockpos)) {
      int i;
      for (i = 1; worldIn.getBlockState(pos.below(i)).is(me); ++i) {}
      if (i < MAXHEIGHT) {
        int j = state.getValue(SugarCaneBlock.AGE);
        if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, true)) {
          if (j == 15) {//should be AGE.getMax or something
            worldIn.setBlockAndUpdate(blockpos, me.defaultBlockState());
            BlockState blockstate = state.setValue(SugarCaneBlock.AGE, Integer.valueOf(0));
            worldIn.setBlock(pos, blockstate, 4);
            blockstate.neighborChanged(worldIn, blockpos, me, pos, false);
          }
          else {
            worldIn.setBlock(pos, state.setValue(SugarCaneBlock.AGE, Integer.valueOf(j + 1)), 4);
          }
          net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
      }
    }
  }
}
