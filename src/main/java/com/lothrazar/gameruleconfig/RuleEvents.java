package com.lothrazar.gameruleconfig;

import java.lang.reflect.Method;
import java.util.Iterator;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.GameRules.IntegerValue;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class RuleEvents {

  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent event) {
    //you probably will not need this 
    World overworld = event.getServer().getWorld(DimensionType.OVERWORLD);
    try {
      if (overworld != null) {
        for (GameRuleBoolWrapper entry : ConfigManager.boolList) {
          //ok
          BooleanValue rule = overworld.getGameRules().get(entry.getReference());
          rule.set(entry.getConfigValue(), overworld.getServer());
          GameRuleMod.LOGGER.info(GameRuleMod.MODID + ": gamerule updated successfully " + entry.getName() + " -> " + entry.getConfigValue());
        }
        for (GameRuleIntWrapper entry : ConfigManager.intList) {
          //ok
          IntegerValue rulei = overworld.getGameRules().get(entry.getReference());
          //"func_223553_a"==setStringValue
          Method m = ObfuscationReflectionHelper.findMethod(IntegerValue.class, "func_223553_a", String.class);
          m.setAccessible(true);
          m.invoke(rulei, "" + entry.getConfigValue());
          GameRuleMod.LOGGER.info(GameRuleMod.MODID + ": gamerule updated successfully " + entry.getName() + " -> " + entry.getConfigValue());
        }
      }
      else {
        GameRuleMod.LOGGER.info(GameRuleMod.MODID + ": null world, gamerule set failed");
      }
    }
    catch (Exception e) {
      //
      GameRuleMod.LOGGER.error("Rule error", e);
    }
  }

  @SubscribeEvent
  public void onPlayerDeath(PlayerEvent.Clone event) {
    BlockPos deathPos = event.getOriginal().getPosition();
    PlayerEntity player = event.getPlayer();
    World world = player.world;
    if (!world.isRemote && event.isWasDeath()
        && !ConfigManager.KEEP_EXP.get()) {
      boolean keepInv = world.getGameRules().get(GameRules.KEEP_INVENTORY).get();
      //exp zero
      if (keepInv) {
        //        LOGGER.info("set exp zero on death");
        player.experience = 0;
        player.experienceLevel = 0;
        player.experienceTotal = 0;
      }
    }
    if (!world.isRemote && event.isWasDeath()
        && !ConfigManager.KEEP_ARMOR.get()) {
      boolean keepInv = world.getGameRules().get(GameRules.KEEP_INVENTORY).get();
      //exp zero
      if (keepInv) {
        // do not keep armor
        Iterator<ItemStack> i = player.getArmorInventoryList().iterator();
        while (i.hasNext()) {
          ItemStack is = i.next();
          //player.dropItem will drop AFTER DEATH. so
          this.drop(player.world, deathPos, is);
        }
      }
    }
    if (!world.isRemote && event.isWasDeath()
        && !ConfigManager.KEEP_WEAPONS.get()) {
      boolean keepInv = world.getGameRules().get(GameRules.KEEP_INVENTORY).get();
      //exp zero
      if (keepInv) {
        // do not keep armor
        Iterator<ItemStack> i = player.getHeldEquipment().iterator();
        while (i.hasNext()) {
          ItemStack is = i.next();
          this.drop(player.world, deathPos, is);
        }
      }
    }
  }

  public void drop(World world, BlockPos p, ItemStack i) {
    ItemEntity e = new ItemEntity(world, p.getX(), p.getY(), p.getZ(), i.copy());
    if (!world.isRemote && !i.isEmpty()) {
      world.addEntity(e);
    }
    i.setCount(0);
  }
}
