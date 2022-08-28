package com.lothrazar.customgamerules.mixin;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(CactusBlock.class)
public class CactusOverwriteMixinFlib {

  @Overwrite
  public void randomTick(BlockState p_51166_, ServerLevel p_51167_, BlockPos p_51168_, Random p_51169_) {
    int MAXHEIGHT = 3;//regular code has this as a bad magic number not a valid block property as it should be
    //IF GAMERULE, H
    if (RuleRegistry.isEnabled(p_51167_, RuleRegistry.doCactusGrowthUnlimited)) {
      MAXHEIGHT = 256;
      GameRuleMod.info("CactusOverwriteMixin mixin success and doCactusGrowthUnlimited=true");
    }
    CactusBlock me = (CactusBlock) (Object) this;
    //now the copy paste of the method
    BlockPos blockpos = p_51168_.above();
    if (p_51167_.isEmptyBlock(blockpos)) {
      int i;
      for (i = 1; p_51167_.getBlockState(p_51168_.below(i)).is(me); ++i) {}
      if (i < MAXHEIGHT) {
        int j = p_51166_.getValue(CactusBlock.AGE);
        if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_51167_, blockpos, p_51166_, true)) {
          if (j == 15) {
            p_51167_.setBlockAndUpdate(blockpos, me.defaultBlockState());
            BlockState blockstate = p_51166_.setValue(CactusBlock.AGE, Integer.valueOf(0));
            p_51167_.setBlock(p_51168_, blockstate, 4);
            blockstate.neighborChanged(p_51167_, blockpos, me, p_51168_, false);
          }
          else {
            p_51167_.setBlock(p_51168_, p_51166_.setValue(CactusBlock.AGE, Integer.valueOf(j + 1)), 4);
          }
          net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_51167_, p_51168_, p_51166_);
        }
      }
    }
  }
}
