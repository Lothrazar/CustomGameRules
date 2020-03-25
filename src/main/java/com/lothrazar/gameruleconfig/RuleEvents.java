package com.lothrazar.gameruleconfig;

import java.lang.reflect.Method;
import java.util.Iterator;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.GameRules.IntegerValue;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class RuleEvents {

  /**
   * 
   * <li>{@link Result#ALLOW} means this instance of mob griefing is allowed.</li>
   * <li>{@link Result#DEFAULT} means the {@code mobGriefing} game rule is used to determine the behaviour.</li>
   * <li>{@link Result#DENY} means this instance of mob griefing is not allowed.</li><br>
   * 
   * @param event
   */
  @SubscribeEvent
  public void onEntityMobGriefingEvent(EntityMobGriefingEvent event) {
    //if rule is false, this event does not trigger, so its always true
    boolean ruleIsTrue = event.getEntity().world.getGameRules().get(GameRules.MOB_GRIEFING).get();
    //true is default, allows destruction
    //so if true do7 nothing
    if (ruleIsTrue) {
      return;//let default take over and rely on rule
    }
    //if rule set FALSE
    // then we have list of entities we ALLOW to grief so we pick only what is allowed
    if (ConfigManager.ENDERGRF.get() && event.getEntity() instanceof EndermanEntity) {
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.GRIEFCREEPER.get() && event.getEntity() instanceof CreeperEntity) {
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.WITHERGRF.get() && (event.getEntity() instanceof WitherEntity || event.getEntity() instanceof WitherSkullEntity)) {
      //
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.SNOWGOLEMGRF.get() && event.getEntity() instanceof SnowGolemEntity) {
      //
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.SILVERFISHGRF.get() && event.getEntity() instanceof SilverfishEntity) {
      //enter stone blocks  
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.RAVAGERGRF.get() && event.getEntity() instanceof RavagerEntity) {
      //break on collide
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.FOXGRF.get() && event.getEntity() instanceof FoxEntity) {
      //eat berries 
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.GHASTGRF.get() && event.getEntity() instanceof FireballEntity) {
      // ghast fireball
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.VILLAGERGRF.get() && event.getEntity() instanceof VillagerEntity) {
      // villager farming
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.SHEEPGRF.get() && event.getEntity() instanceof SheepEntity) {
      // sheep eat grass
      event.setResult(Result.ALLOW);
    }
    if (ConfigManager.BLAZEFBALLGRF.get() && event.getEntity() instanceof SmallFireballEntity) {
      // blaze fireball
      event.setResult(Result.ALLOW);
    }
  }

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
