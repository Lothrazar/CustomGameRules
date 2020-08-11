package com.lothrazar.customgamerules;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraft.world.World;

public class RuleRegistry {

  public static RuleKey<BooleanValue> suffocationDamage;
  public static RuleKey<BooleanValue> pearlDamage;
  //  public static RuleKey<BooleanValue> tntDamage;
  public static RuleKey<BooleanValue> cactusDamage;
  public static RuleKey<BooleanValue> berryDamage;
  public static RuleKey<BooleanValue> doLilypadsBreak;
  public static RuleKey<BooleanValue> doEyesAlwaysBreak;
  public static RuleKey<BooleanValue> keepInventoryExperience;
  public static RuleKey<BooleanValue> keepInventoryArmor;
  public static RuleKey<BooleanValue> mobGriefingCreeper;
  public static RuleKey<BooleanValue> mobGriefingEnderman;
  public static RuleKey<BooleanValue> mobGriefingVillager;
  public static RuleKey<BooleanValue> mobGriefingZombie;
  public static RuleKey<BooleanValue> mobGriefingWither;
  public static RuleKey<BooleanValue> mobGriefingRavager;
  public static RuleKey<BooleanValue> mobGriefingGhast;
  public static RuleKey<BooleanValue> mobGriefingSilverfish;
  public static RuleKey<BooleanValue> mobGriefingBlaze;
  public static RuleKey<BooleanValue> doMobItemPickup;
  //  public static RuleKey<BooleanValue> doReduceHeartsOnDeath;
  public static RuleKey<BooleanValue> doVillagerTrading;

  /**
   * <pre>
   *    * PREFIXES
   * command___
   * disable___
   * do___
   * max___
   * natural___ 
   * random___
   * reduced__
   * send__
   * show__
   * 
   * 
   * SUFFIXES
   * ___Damage
   * 
   * 
   * SINGLE USE ONLY / DONT FIT
   *  forgiveDeadPlayers
   *  announceAdvancements
   *  keepInventory
   *  logAdminCommands
   *  mobGriefing
   *  naturalRegeneration
   *  randomTickSpeed
   *  reducedDebugInfo
   *  sendCommandFeedback
   *  spawnRadius
   *  spectatorsGenerateChunks
   *  universalAnger
   *  showTags
   * </pre>
   */
  public static void setup() {
    // Player  ____Damage
    suffocationDamage = RuleFactory.createBoolean("suffocationDamage", true, GameRules.Category.PLAYER);
    pearlDamage = RuleFactory.createBoolean("pearlDamage", true, GameRules.Category.PLAYER);
    cactusDamage = RuleFactory.createBoolean("cactusDamage", true, GameRules.Category.PLAYER);
    berryDamage = RuleFactory.createBoolean("berryDamage", true, GameRules.Category.PLAYER);
    // Player other
    doLilypadsBreak = RuleFactory.createBoolean("doLilypadsBreak", true, GameRules.Category.PLAYER);
    keepInventoryExperience = RuleFactory.createBoolean("keepInventoryExperience", false, GameRules.Category.PLAYER);
    keepInventoryArmor = RuleFactory.createBoolean("keepInventoryArmor", false, GameRules.Category.PLAYER);
    //drops
    doEyesAlwaysBreak = RuleFactory.createBoolean("doEyesAlwaysBreak", true, GameRules.Category.DROPS);
    //mobs
    doVillagerTrading = RuleFactory.createBoolean("doVillagerTrading", true, GameRules.Category.MOBS);
    //mobs - mobGriefing
    mobGriefingCreeper = RuleFactory.createBoolean("mobGriefingCreeper", true, GameRules.Category.MOBS);
    mobGriefingEnderman = RuleFactory.createBoolean("mobGriefingEnderman", false, GameRules.Category.MOBS);
    mobGriefingVillager = RuleFactory.createBoolean("mobGriefingVillager", true, GameRules.Category.MOBS);
    mobGriefingZombie = RuleFactory.createBoolean("mobGriefingZombie", true, GameRules.Category.MOBS);
    mobGriefingWither = RuleFactory.createBoolean("mobGriefingWither", true, GameRules.Category.MOBS);
    mobGriefingRavager = RuleFactory.createBoolean("mobGriefingRavager", true, GameRules.Category.MOBS);
    mobGriefingSilverfish = RuleFactory.createBoolean("mobGriefingSilverfish", true, GameRules.Category.MOBS);
    mobGriefingGhast = RuleFactory.createBoolean("mobGriefingGhast", true, GameRules.Category.MOBS);
    mobGriefingBlaze = RuleFactory.createBoolean("mobGriefingBlaze", true, GameRules.Category.MOBS);
    doMobItemPickup = RuleFactory.createBoolean("doMobItemPickup", true, GameRules.Category.MOBS);
    //
    //
    //    tntDamage = RuleFactory.createBoolean("tntDamage", true, GameRules.Category.PLAYER);
    // do____
    //    RuleFactory.createBoolean("doBurnBabyZombies", false);
    //    RuleFactory.createBoolean("doInstantEating", false);
    //FROM BEDROCK
    //    RuleFactory.createBoolean("showCoordinates", true);
    //    tntExplodes
    //commandBlocksEnabled
    //    showTags
  }

  public static boolean isEnabled(World world, RuleKey<BooleanValue> key) {
    return world.getGameRules().getBoolean(key);
  }
}
