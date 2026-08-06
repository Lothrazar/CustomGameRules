package com.lothrazar.customgamerules.rules;

import com.lothrazar.customgamerules.ModGameRule;
import com.lothrazar.customgamerules.net.PacketHungerRuleSync;
import com.lothrazar.library.registry.GameRuleFactory;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class RuleRegistry {

  //
  public static GameRule<Boolean> disableBiomeFreezeIce; // BiomeAntiFreezeMixin.java
  public static GameRule<Boolean> disableBlockGravity; //   FallingBlockGravityMixin.java
  public static GameRule<Boolean> disableDecayCoral; // CoralAntiDecayMixin.java CoralFanAntiDecayMixin.java
  public static GameRule<Boolean> disableDecayLeaves; //  LeavesAntiDecayMixin.java
  public static GameRule<Boolean> disableFarmlandTrampling;
  public static GameRule<Boolean> disableGenerateObsidian;
  public static GameRule<Boolean> disableGenerateStone;
  public static GameRule<Boolean> disableLightMeltIce; //   IceAntiMeltMixin.java
  public static GameRule<Boolean> disableMobItemPickup;
  public static GameRule<Boolean> disablePetFriendlyFire;
  public static GameRule<Boolean> disableVillagerTrading;
  // that disable features
  public static GameRule<Boolean> doArmorStandWeapons;
  public static GameRule<Boolean> doLilypadsBreak;
  public static GameRule<Boolean> doEyesAlwaysBreak;
  public static GameRule<Boolean> doCactusGrowthUnlimited; // CactusOverwriteMixin.java
  public static GameRule<Boolean> doInstantEating;
  public static GameRule<Boolean> doInstantExp;
  public static GameRule<Boolean> doNetherVoidAbove;
  public static GameRule<Boolean> doMapsAlwaysUpdate; // FilledMapItemRefreshMixin.java
  public static GameRule<Boolean> doSugarGrowthUnlimited; //SugarOverwriteMixin.java
  // Game rules related to player damage
  public static GameRule<Boolean> suffocationDamage;
  public static GameRule<Boolean> pearlDamage;
  public static GameRule<Boolean> cactusDamage;
  public static GameRule<Boolean> berryDamage;
  // game rules that depend on other existing rules for fine-tuned control
  public static GameRule<Boolean> keepInventoryExperience;
  public static GameRule<Boolean> keepInventoryArmor;
  public static GameRule<Boolean> mobGriefingCreeper;
  public static GameRule<Boolean> mobGriefingEnderman;
  public static GameRule<Boolean> mobGriefingVillager;
  public static GameRule<Boolean> mobGriefingZombie;
  public static GameRule<Boolean> mobGriefingWither;
  public static GameRule<Boolean> mobGriefingRavager;
  public static GameRule<Boolean> mobGriefingGhast;
  public static GameRule<Boolean> mobGriefingSilverfish;
  public static GameRule<Boolean> mobGriefingBlaze;
  public static GameRule<Boolean> disableEndermanTeleport;
  public static GameRule<Boolean> disableShulkerTeleport;
  public static GameRule<Boolean> disableCropGrowth;
  public static GameRule<Boolean> disableSaplingGrowth;
  public static GameRule<Boolean> disableCriticalHits;
  public static GameRule<Boolean> disableHunger;
  public static GameRule<Boolean> disableTargetingPlayers;
  public static GameRule<Boolean> disableLightningTransform;
  public static GameRule<Boolean> disablePortalCreationNether;
  public static GameRule<Boolean> disablePortalCreationEnd;
  public static GameRule<Boolean> mobGriefingSnowgolem;
  public static GameRule<Boolean> doFriendlyIronGolems;

  public static GameRule<Boolean> tntExplodes;
  public static GameRule<Boolean> respawnBlocksExplode;

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
  private static GameRule<Boolean> createBoolean(String id, boolean defaultVal, GameRuleCategory cat) {
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
    suffocationDamage = createBoolean("suffocationDamage", true, GameRuleCategory.PLAYER);
    pearlDamage = createBoolean("pearlDamage", true, GameRuleCategory.PLAYER);
    cactusDamage = createBoolean("cactusDamage", true, GameRuleCategory.PLAYER);
    berryDamage = createBoolean("berryDamage", true, GameRuleCategory.PLAYER);
    //
    //   keepInventory _______
    //
    keepInventoryExperience = createBoolean("keepInventoryExperience", false, GameRuleCategory.PLAYER);
    keepInventoryArmor = createBoolean("keepInventoryArmor", false, GameRuleCategory.PLAYER);
    //
    // do______
    //
    doFriendlyIronGolems = createBoolean("doFriendlyIronGolems", true, GameRuleCategory.MOBS);
    doMapsAlwaysUpdate = createBoolean("doMapsAlwaysUpdate", true, GameRuleCategory.PLAYER);
    doLilypadsBreak = createBoolean("doLilypadsBreak", true, GameRuleCategory.PLAYER);
    doInstantEating = createBoolean("doInstantEating", false, GameRuleCategory.PLAYER);
    doInstantExp = createBoolean("doInstantExp", false, GameRuleCategory.PLAYER);
    doArmorStandWeapons = createBoolean("doArmorStandWeapons", true, GameRuleCategory.PLAYER);
    doEyesAlwaysBreak = createBoolean("doEyesAlwaysBreak", false, GameRuleCategory.DROPS);
    doNetherVoidAbove = createBoolean("doNetherVoidAbove", false, GameRuleCategory.MISC);
    doCactusGrowthUnlimited = createBoolean("doCactusGrowthUnlimited", false, GameRuleCategory.MISC);
    doSugarGrowthUnlimited = createBoolean("doSugarGrowthUnlimited", false, GameRuleCategory.MISC);
    //= RuleFactory.createBoolean("doInstantEating", true, GameRuleCategory.PLAYER);
    //
    //disable_____
    disablePortalCreationEnd = createBoolean("disablePortalCreationEnd", false, GameRuleCategory.PLAYER);
    disablePortalCreationNether = createBoolean("disablePortalCreationNether", false, GameRuleCategory.PLAYER);
    disableLightningTransform = createBoolean("disableLightningTransform", false, GameRuleCategory.MOBS);
    disableTargetingPlayers = createBoolean("disableTargetingPlayers", false, GameRuleCategory.MOBS);
    disableVillagerTrading = createBoolean("disableVillagerTrading", false, GameRuleCategory.MOBS);
    disableBlockGravity = createBoolean("disableBlockGravity", false, GameRuleCategory.UPDATES);
    disableBiomeFreezeIce = createBoolean("disableBiomeFreezeIce", false, GameRuleCategory.UPDATES);
    disableLightMeltIce = createBoolean("disableLightMeltIce", false, GameRuleCategory.UPDATES);
    disableDecayLeaves = createBoolean("disableDecayLeaves", false, GameRuleCategory.UPDATES);
    disableDecayCoral = createBoolean("disableDecayCoral", false, GameRuleCategory.UPDATES);
    disableGenerateStone = createBoolean("disableGenerateStone", false, GameRuleCategory.UPDATES);
    disableGenerateObsidian = createBoolean("disableGenerateObsidian", false, GameRuleCategory.UPDATES);
    disablePetFriendlyFire = createBoolean("disablePetFriendlyFire", true, GameRuleCategory.UPDATES);
    disableFarmlandTrampling = createBoolean("disableFarmlandTrampling", false, GameRuleCategory.UPDATES);
    disableMobItemPickup = createBoolean("disableMobItemPickup", false, GameRuleCategory.MOBS);
    disableEndermanTeleport = createBoolean("disableEndermanTeleport", false, GameRuleCategory.MOBS);
    disableShulkerTeleport = createBoolean("disableShulkerTeleport", false, GameRuleCategory.MOBS);
    disableCropGrowth = createBoolean("disableCropGrowth", false, GameRuleCategory.UPDATES);
    disableSaplingGrowth = createBoolean("disableSaplingGrowth", false, GameRuleCategory.UPDATES);
    disableCriticalHits = createBoolean("disableCriticalHits", false, GameRuleCategory.UPDATES);
    disableHunger = createBoolean("disableHunger", false, GameRuleCategory.PLAYER);
    //
    //mobGriefing_______
    //
    mobGriefingCreeper = createBoolean("mobGriefingCreeper", true, GameRuleCategory.MOBS);
    mobGriefingEnderman = createBoolean("mobGriefingEnderman", true, GameRuleCategory.MOBS);
    mobGriefingVillager = createBoolean("mobGriefingVillager", true, GameRuleCategory.MOBS);
    mobGriefingZombie = createBoolean("mobGriefingZombie", true, GameRuleCategory.MOBS);
    mobGriefingWither = createBoolean("mobGriefingWither", true, GameRuleCategory.MOBS);
    mobGriefingRavager = createBoolean("mobGriefingRavager", true, GameRuleCategory.MOBS);
    mobGriefingSilverfish = createBoolean("mobGriefingSilverfish", true, GameRuleCategory.MOBS);
    mobGriefingGhast = createBoolean("mobGriefingGhast", true, GameRuleCategory.MOBS);
    mobGriefingBlaze = createBoolean("mobGriefingBlaze", true, GameRuleCategory.MOBS);
    mobGriefingSnowgolem = createBoolean("mobGriefingSnowgolem", true, GameRuleCategory.MOBS);
    ////    disableHunger// ONLY if we can HIDE the hunger bar
    //    RenderGameOverlayEvent yz;//CLIENT ONLY
    //
    //
    // bedrock feature parity
    //
    tntExplodes = createBoolean("tntExplodes", true, GameRuleCategory.PLAYER);
    respawnBlocksExplode = createBoolean("respawnBlocksExplode", true, GameRuleCategory.PLAYER);
    // TODO:  showCoordinates, showDaysPlayed, pvp, recipesUnlock, showTags

  }

  public static boolean isEnabled(Level world, GameRule<Boolean> key) {
    if (!(world instanceof net.minecraft.server.level.ServerLevel serverLevel)) {
      return false;
    }
    return serverLevel.getGameRules().get(key);
  }

  public static boolean isEnabled(LevelAccessor world, GameRule<Boolean> key) {
    if (!(world instanceof net.minecraft.server.level.ServerLevel serverLevel)) {
      return false;
    }
    return serverLevel.getGameRules().get(key);
  }
}
