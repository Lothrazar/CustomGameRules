package com.lothrazar.gameruleconfig;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class RuleEvents {

  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent event) {
    //you probably will not need this 
    World overworld = event.getServer().getWorld(DimensionType.OVERWORLD);
    if (overworld != null) {
      BooleanValue rule = overworld.getGameRules().get(GameRules.KEEP_INVENTORY);//.set(true, event.getServer());
      rule.set(true, overworld.getServer());
      for (GameRuleWrapper entry : ConfigManager.list) {
        //ok
        rule = overworld.getGameRules().get(entry.getReference());
        //    
        rule.set(entry.getDefaultValue(), overworld.getServer());
        //        LOGGER.info(MODID + ": gamerule updated successfully " + entry.getName() + " -> " + entry.getDefaultValue());
      }
    }
    else {
      //
      //      LOGGER.info(MODID + ": null world, gamerule set failed");
    }
    //
  }

  @SubscribeEvent
  public void onPlayerDeath(PlayerEvent.Clone event) {
    //exp? cost?
    World world = event.getPlayer().world;
    if (!world.isRemote && event.isWasDeath()
        && !ConfigManager.KEEP_EXP.get()) {
      boolean keepInv = world.getGameRules().get(GameRules.KEEP_INVENTORY).get();
      //exp zero
      if (keepInv) {
        //        LOGGER.info("set exp zero on death");
        event.getPlayer().experience = 0;
        event.getPlayer().experienceLevel = 0;
        event.getPlayer().experienceTotal = 0;
      }
    }
  }
}
