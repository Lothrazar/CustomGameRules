package com.lothrazar.customgamerules.util;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;

public class UtilWorld {

  public static String dimensionToString(Level w) {
    //example: returns "minecraft:overworld" resource location
    //    getRegistryName became location in 1.19
    return w.dimension().location().toString();
  }

  public static double distanceBetweenHorizontal(BlockPos start, BlockPos end) {
    int xDistance = Math.abs(start.getX() - end.getX());
    int zDistance = Math.abs(start.getZ() - end.getZ());
    // ye olde pythagoras
    return Math.sqrt(xDistance * xDistance + zDistance * zDistance);
  }

  public static BlockPos nextReplaceableInDirection(Level world, BlockPos posIn, Direction facing, int max, @Nullable Block blockMatch) {
    BlockPos posToPlaceAt = new BlockPos(posIn);
    BlockPos posLoop = new BlockPos(posIn);
    //    world.getBlockState(posLoop).getBlock().isReplaceable(state, useContext)
    for (int i = 0; i < max; i++) {
      BlockState state = world.getBlockState(posLoop);
      if (state.getBlock() != null
          && world.getBlockState(posLoop).getBlock() == Blocks.AIR//.isAir(state)//.isReplaceable(world, posLoop)
      ) {
        posToPlaceAt = posLoop;
        break;
      }
      else {
        posLoop = posLoop.relative(facing);
      }
    }
    return posToPlaceAt;
  }

  public static ItemEntity dropItemStackInWorld(Level world, BlockPos pos, ItemStack stack) {
    if (pos == null || world == null || stack.isEmpty()) {
      return null;
    }
    ItemEntity entityItem = new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
    if (world.isClientSide == false) {
      world.addFreshEntity(entityItem);
    }
    return entityItem;
  }

  public static BlockPos getRandomPos(RandomSource rand, BlockPos here, int hRadius) {
    int x = here.getX();
    int z = here.getZ();
    // search in a square
    int xMin = x - hRadius;
    int xMax = x + hRadius;
    int zMin = z - hRadius;
    int zMax = z + hRadius;
    int posX = Mth.nextInt(rand, xMin, xMax);
    int posZ = Mth.nextInt(rand, zMin, zMax);
    return new BlockPos(posX, here.getY(), posZ);
  }

  public static ArrayList<BlockPos> findBlocks(Level world, BlockPos start, Block blockHunt, int RADIUS) {
    ArrayList<BlockPos> found = new ArrayList<BlockPos>();
    int xMin = start.getX() - RADIUS;
    int xMax = start.getX() + RADIUS;
    int yMin = start.getY() - RADIUS;
    int yMax = start.getY() + RADIUS;
    int zMin = start.getZ() - RADIUS;
    int zMax = start.getZ() + RADIUS;
    BlockPos posCurrent = null;
    for (int xLoop = xMin; xLoop <= xMax; xLoop++) {
      for (int yLoop = yMin; yLoop <= yMax; yLoop++) {
        for (int zLoop = zMin; zLoop <= zMax; zLoop++) {
          posCurrent = new BlockPos(xLoop, yLoop, zLoop);
          if (world.getBlockState(posCurrent).getBlock().equals(blockHunt)) {
            found.add(posCurrent);
          }
        }
      }
    }
    return found;
  }

  public static void toggleLeverPowerState(Level worldIn, BlockPos blockPos, BlockState blockState) {
    boolean hasPowerHere = blockState.getValue(LeverBlock.POWERED).booleanValue();//this.block.getStrongPower(blockState, worldIn, pointer, EnumFacing.UP) > 0;
    BlockState stateNew = blockState.setValue(LeverBlock.POWERED, !hasPowerHere);
    boolean success = worldIn.setBlockAndUpdate(blockPos, stateNew);
    if (success) {
      flagUpdate(worldIn, blockPos, blockState, stateNew);
      flagUpdate(worldIn, blockPos.below(), blockState, stateNew);
      flagUpdate(worldIn, blockPos.above(), blockState, stateNew);
      flagUpdate(worldIn, blockPos.west(), blockState, stateNew);
      flagUpdate(worldIn, blockPos.east(), blockState, stateNew);
      flagUpdate(worldIn, blockPos.north(), blockState, stateNew);
      flagUpdate(worldIn, blockPos.south(), blockState, stateNew);
    }
  }

  public static void flagUpdate(Level worldIn, BlockPos blockPos, BlockState blockState, BlockState stateNew) {
    worldIn.sendBlockUpdated(blockPos, blockState, stateNew, 3);
    worldIn.updateNeighborsAt(blockPos, stateNew.getBlock());
    worldIn.updateNeighborsAt(blockPos, blockState.getBlock());//THIS one works only with true
    //        worldIn.scheduleBlockUpdate(blockPos, stateNew.getBlock(), 3, 3);
    //        worldIn.scheduleUpdate(blockPos, stateNew.getBlock(), 3);
  }

  @SuppressWarnings("deprecation")
  public static BlockPos findClosestBlock(Player player, Block blockHunt, int RADIUS) {
    BlockPos found = null;
    int xMin = (int) player.getX() - RADIUS;
    int xMax = (int) player.getX() + RADIUS;
    int yMin = (int) player.getY() - RADIUS;
    int yMax = (int) player.getY() + RADIUS;
    int zMin = (int) player.getZ() - RADIUS;
    int zMax = (int) player.getZ() + RADIUS;
    int distance = 0, distanceClosest = RADIUS * RADIUS;
    BlockPos posCurrent = null;
    Level world = player.getCommandSenderWorld();
    for (int xLoop = xMin; xLoop <= xMax; xLoop++) {
      for (int yLoop = yMin; yLoop <= yMax; yLoop++) {
        for (int zLoop = zMin; zLoop <= zMax; zLoop++) {
          posCurrent = new BlockPos(xLoop, yLoop, zLoop);
          if (world.isAreaLoaded(posCurrent, 1) == false) {
            continue;
          }
          if (world.getBlockState(posCurrent).getBlock().equals(blockHunt)) {
            // find closest?
            if (found == null) {
              found = posCurrent;
            }
            else {
              distance = (int) distanceBetweenHorizontal(player.blockPosition(), posCurrent);
              if (distance < distanceClosest) {
                found = posCurrent;
                distanceClosest = distance;
              }
            }
          }
        }
      }
    }
    return found;
  }

  public static List<BlockPos> getPositionsInRange(BlockPos pos, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
    List<BlockPos> found = new ArrayList<BlockPos>();
    for (int x = xMin; x <= xMax; x++)
      for (int y = yMin; y <= yMax; y++)
        for (int z = zMin; z <= zMax; z++) {
          found.add(new BlockPos(x, y, z));
        }
    return found;
  }

  public static boolean doBlockStatesMatch(BlockState replacedBlockState, BlockState newToPlace) {
    //    replacedBlockState.eq?
    return replacedBlockState.equals(newToPlace);
  }
}
