package com.lothrazar.customgamerules.rules;

import com.lothrazar.customgamerules.ModGameRule;
import com.lothrazar.customgamerules.net.PacketHungerRuleSync;
import com.lothrazar.library.registry.GameRuleFactory;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class RuleRegistry {

  //
  public static Key<BooleanValue> disableBiomeFreezeIce; // BiomeAntiFreezeMixin.java
  public static Key<BooleanValue> disableBlockGravity; //   FallingBlockGravityMixin.java
  public static Key<BooleanValue> disableDecayCoral; // CoralAntiDecayMixin.java CoralFanAntiDecayMixin.java
  public static Key<BooleanValue> disableDecayLeaves; //  LeavesAntiDecayMixin.java
  public static Key<BooleanValue> disableFarmlandTrampling;
  public static Key<BooleanValue> disableGenerateObsidian;
  public static Key<BooleanValue> disableGenerateStone;
  public static Key<BooleanValue> disableLightMeltIce; //   IceAntiMeltMixin.java
  public static Key<BooleanValue> disableMobItemPickup;
  public static Key<BooleanValue> disablePetFriendlyFire;
  public static Key<BooleanValue> disableVillagerTrading;
  // that disable features
  public static Key<BooleanValue> doArmorStandWeapons;
  public static Key<BooleanValue> doLilypadsBreak;
  public static Key<BooleanValue> doEyesAlwaysBreak;
  public static Key<BooleanValue> doCactusGrowthUnlimited; // CactusOverwriteMixin.java
  public static Key<BooleanValue> doInstantEating;
  public static Key<BooleanValue> doInstantExp;
  public static Key<BooleanValue> doNetherVoidAbove;
  public static Key<BooleanValue> doMapsAlwaysUpdate; // FilledMapItemRefreshMixin.java
  public static Key<BooleanValue> doSugarGrowthUnlimited; //SugarOverwriteMixin.java
  // Game rules related to player damage
  public static Key<BooleanValue> suffocationDamage;
  public static Key<BooleanValue> pearlDamage;
  public static Key<BooleanValue> cactusDamage;
  public static Key<BooleanValue> berryDamage;
  // game rules that depend on other existing rules for fine-tuned control
  public static Key<BooleanValue> keepInventoryExperience;
  public static Key<BooleanValue> keepInventoryArmor;
  public static Key<BooleanValue> mobGriefingCreeper;
  public static Key<BooleanValue> mobGriefingEnderman;
  public static Key<BooleanValue> mobGriefingVillager;
  public static Key<BooleanValue> mobGriefingZombie;
  public static Key<BooleanValue> mobGriefingWither;
  public static Key<BooleanValue> mobGriefingRavager;
  public static Key<BooleanValue> mobGriefingGhast;
  public static Key<BooleanValue> mobGriefingSilverfish;
  public static Key<BooleanValue> mobGriefingBlaze;
  public static Key<BooleanValue> disableEndermanTeleport;
  public static Key<BooleanValue> disableShulkerTeleport;
  public static Key<BooleanValue> disableCropGrowth;
  public static Key<BooleanValue> disableSaplingGrowth;
  public static Key<BooleanValue> disableCriticalHits;
  public static Key<BooleanValue> disableHunger;
  public static Key<BooleanValue> disableTargetingPlayers;
  public static Key<BooleanValue> disableLightningTransform;
  public static Key<BooleanValue> disablePortalCreationNether;
  public static Key<BooleanValue> disablePortalCreationEnd;
  public static Key<BooleanValue> mobGriefingSnowgolem;
  public static Key<BooleanValue> doFriendlyIronGolems;

  public static Key<BooleanValue> tntExplodes;
  public static Key<BooleanValue> respawnBlocksExplode;

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
  private static Key<BooleanValue> createBoolean(String id, boolean defaultVal, GameRules.Category cat) {
    return GameRuleFactory.createBoolean(id, defaultVal, cat);
  }

  public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
    var registrar = event.registrar(ModGameRule.MODID);
    registrar.playToClient(
        PacketHungerRuleSync.TYPE,
        PacketHungerRuleSync.CODEC,
        PacketHungerRuleSync::handle
    );
  }

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
    suffocationDamage = createBoolean("suffocationDamage", true, GameRules.Category.PLAYER);
    pearlDamage = createBoolean("pearlDamage", true, GameRules.Category.PLAYER);
    cactusDamage = createBoolean("cactusDamage", true, GameRules.Category.PLAYER);
    berryDamage = createBoolean("berryDamage", true, GameRules.Category.PLAYER);
    //
    //   keepInventory _______
    //
    keepInventoryExperience = createBoolean("keepInventoryExperience", false, GameRules.Category.PLAYER);
    keepInventoryArmor = createBoolean("keepInventoryArmor", false, GameRules.Category.PLAYER);
    //
    // do______
    //
    doFriendlyIronGolems = createBoolean("doFriendlyIronGolems", true, GameRules.Category.MOBS);
    doMapsAlwaysUpdate = createBoolean("doMapsAlwaysUpdate", true, GameRules.Category.PLAYER);
    doLilypadsBreak = createBoolean("doLilypadsBreak", true, GameRules.Category.PLAYER);
    doInstantEating = createBoolean("doInstantEating", false, GameRules.Category.PLAYER);
    doInstantExp = createBoolean("doInstantExp", false, GameRules.Category.PLAYER);
    doArmorStandWeapons = createBoolean("doArmorStandWeapons", true, GameRules.Category.PLAYER);
    doEyesAlwaysBreak = createBoolean("doEyesAlwaysBreak", false, GameRules.Category.DROPS);
    doNetherVoidAbove = createBoolean("doNetherVoidAbove", false, GameRules.Category.MISC);
    doCactusGrowthUnlimited = createBoolean("doCactusGrowthUnlimited", false, GameRules.Category.MISC);
    doSugarGrowthUnlimited = createBoolean("doSugarGrowthUnlimited", false, GameRules.Category.MISC);
    //= RuleFactory.createBoolean("doInstantEating", true, GameRules.Category.PLAYER);
    //
    //disable_____
    disablePortalCreationEnd = createBoolean("disablePortalCreationEnd", false, GameRules.Category.PLAYER);
    disablePortalCreationNether = createBoolean("disablePortalCreationNether", false, GameRules.Category.PLAYER);
    disableLightningTransform = createBoolean("disableLightningTransform", false, GameRules.Category.MOBS);
    disableTargetingPlayers = createBoolean("disableTargetingPlayers", false, GameRules.Category.MOBS);
    disableVillagerTrading = createBoolean("disableVillagerTrading", false, GameRules.Category.MOBS);
    disableBlockGravity = createBoolean("disableBlockGravity", false, GameRules.Category.UPDATES);
    disableBiomeFreezeIce = createBoolean("disableBiomeFreezeIce", false, GameRules.Category.UPDATES);
    disableLightMeltIce = createBoolean("disableLightMeltIce", false, GameRules.Category.UPDATES);
    disableDecayLeaves = createBoolean("disableDecayLeaves", false, GameRules.Category.UPDATES);
    disableDecayCoral = createBoolean("disableDecayCoral", false, GameRules.Category.UPDATES);
    disableGenerateStone = createBoolean("disableGenerateStone", false, GameRules.Category.UPDATES);
    disableGenerateObsidian = createBoolean("disableGenerateObsidian", false, GameRules.Category.UPDATES);
    disablePetFriendlyFire = createBoolean("disablePetFriendlyFire", true, GameRules.Category.UPDATES);
    disableFarmlandTrampling = createBoolean("disableFarmlandTrampling", false, GameRules.Category.UPDATES);
    disableMobItemPickup = createBoolean("disableMobItemPickup", false, GameRules.Category.MOBS);
    disableEndermanTeleport = createBoolean("disableEndermanTeleport", false, GameRules.Category.MOBS);
    disableShulkerTeleport = createBoolean("disableShulkerTeleport", false, GameRules.Category.MOBS);
    disableCropGrowth = createBoolean("disableCropGrowth", false, GameRules.Category.UPDATES);
    disableSaplingGrowth = createBoolean("disableSaplingGrowth", false, GameRules.Category.UPDATES);
    disableCriticalHits = createBoolean("disableCriticalHits", false, GameRules.Category.UPDATES);
    disableHunger = createBoolean("disableHunger", false, GameRules.Category.PLAYER);
    //
    //mobGriefing_______
    //
    mobGriefingCreeper = createBoolean("mobGriefingCreeper", true, GameRules.Category.MOBS);
    mobGriefingEnderman = createBoolean("mobGriefingEnderman", true, GameRules.Category.MOBS);
    mobGriefingVillager = createBoolean("mobGriefingVillager", true, GameRules.Category.MOBS);
    mobGriefingZombie = createBoolean("mobGriefingZombie", true, GameRules.Category.MOBS);
    mobGriefingWither = createBoolean("mobGriefingWither", true, GameRules.Category.MOBS);
    mobGriefingRavager = createBoolean("mobGriefingRavager", true, GameRules.Category.MOBS);
    mobGriefingSilverfish = createBoolean("mobGriefingSilverfish", true, GameRules.Category.MOBS);
    mobGriefingGhast = createBoolean("mobGriefingGhast", true, GameRules.Category.MOBS);
    mobGriefingBlaze = createBoolean("mobGriefingBlaze", true, GameRules.Category.MOBS);
    mobGriefingSnowgolem = createBoolean("mobGriefingSnowgolem", true, GameRules.Category.MOBS);
    ////    disableHunger// ONLY if we can HIDE the hunger bar
    //    RenderGameOverlayEvent yz;//CLIENT ONLY
    //
    //
    // bedrock feature parity
    //
    tntExplodes = createBoolean("tntExplodes", true, GameRules.Category.PLAYER);
    respawnBlocksExplode = createBoolean("respawnBlocksExplode", true, GameRules.Category.PLAYER);
    // TODO:  showCoordinates, showDaysPlayed, pvp, recipesUnlock, showTags

  }

  public static boolean isEnabled(Level world, Key<BooleanValue> key) {
    return world.getGameRules().getBoolean(key);
  }

  public static boolean isEnabled(LevelAccessor world, Key<BooleanValue> key) {
    if (!(world instanceof Level)) {
      return false;
    }
    return ((Level) world).getGameRules().getBoolean(key);
  }
}
