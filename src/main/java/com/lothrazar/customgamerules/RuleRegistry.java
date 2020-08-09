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

  public static void setup() {
    // ____Damage
    suffocationDamage = RuleFactory.createBoolean("suffocationDamage", true, GameRules.Category.PLAYER);
    pearlDamage = RuleFactory.createBoolean("pearlDamage", true, GameRules.Category.PLAYER);
    cactusDamage = RuleFactory.createBoolean("cactusDamage", true, GameRules.Category.PLAYER);
    berryDamage = RuleFactory.createBoolean("berryDamage", true, GameRules.Category.PLAYER);
    doLilypadsBreak = RuleFactory.createBoolean("doLilypadsBreak", true, GameRules.Category.PLAYER);
    doEyesAlwaysBreak = RuleFactory.createBoolean("doEyesAlwaysBreak", true, GameRules.Category.DROPS);
    keepInventoryExperience = RuleFactory.createBoolean("keepInventoryExperience", false, GameRules.Category.PLAYER);
    keepInventoryArmor = RuleFactory.createBoolean("keepInventoryArmor", false, GameRules.Category.PLAYER);
    mobGriefingCreeper = RuleFactory.createBoolean("mobGriefingCreeper", false, GameRules.Category.MOBS);
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
