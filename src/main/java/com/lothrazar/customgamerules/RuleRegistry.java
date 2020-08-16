package com.lothrazar.customgamerules;

import com.lothrazar.customgamerules.util.RuleFactory;
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
  public static RuleKey<BooleanValue> disableVillagerTrading;
  public static RuleKey<BooleanValue> doInstantEating;
  public static RuleKey<BooleanValue> doInstantExp;
  public static RuleKey<BooleanValue> doNetherVoidAbove;
  public static RuleKey<BooleanValue> doArmorStandWeapons;
  public static RuleKey<BooleanValue> doAlwaysUpdateMap;
  public static RuleKey<BooleanValue> disableBlockGravity;
  public static RuleKey<BooleanValue> disableBiomeIceFreeze;

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
    /**
     * NEW:
     * 
     * 
     * DO: this is ADDING something new to the game (usually def-true unless crazy)
     * 
     * 
     * DISABLE: This is removing a feature in the game
     */
    //
    //____Damage
    //
    suffocationDamage = RuleFactory.createBoolean("suffocationDamage", true, GameRules.Category.PLAYER);
    pearlDamage = RuleFactory.createBoolean("pearlDamage", true, GameRules.Category.PLAYER);
    cactusDamage = RuleFactory.createBoolean("cactusDamage", true, GameRules.Category.PLAYER);
    berryDamage = RuleFactory.createBoolean("berryDamage", true, GameRules.Category.PLAYER);
    //
    //   keepInventory _______
    //
    keepInventoryExperience = RuleFactory.createBoolean("keepInventoryExperience", false, GameRules.Category.PLAYER);
    keepInventoryArmor = RuleFactory.createBoolean("keepInventoryArmor", false, GameRules.Category.PLAYER);
    //
    // do______ 
    //
    doAlwaysUpdateMap = RuleFactory.createBoolean("doAlwaysUpdateMaps", true, GameRules.Category.PLAYER);
    doLilypadsBreak = RuleFactory.createBoolean("doLilypadsBreak", true, GameRules.Category.PLAYER);
    doInstantEating = RuleFactory.createBoolean("doInstantEating", false, GameRules.Category.PLAYER);
    doInstantExp = RuleFactory.createBoolean("doInstantExp", true, GameRules.Category.PLAYER);
    doArmorStandWeapons = RuleFactory.createBoolean("doArmorStandWeapons", true, GameRules.Category.PLAYER);
    doEyesAlwaysBreak = RuleFactory.createBoolean("doEyesAlwaysBreak", true, GameRules.Category.DROPS);
    doNetherVoidAbove = RuleFactory.createBoolean("doNetherVoidAbove", false, GameRules.Category.MISC);
    //= RuleFactory.createBoolean("doInstantEating", true, GameRules.Category.PLAYER);
    //
    //disable_____   
    //
    disableVillagerTrading = RuleFactory.createBoolean("disableVillagerTrading", false, GameRules.Category.MOBS);
    disableBlockGravity = RuleFactory.createBoolean("disableBlockGravity", false, GameRules.Category.UPDATES);
    disableBiomeIceFreeze = RuleFactory.createBoolean("disableBiomeIceFreeze", false, GameRules.Category.UPDATES);
    //
    //mobGriefing_______
    //
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
    //misc
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
