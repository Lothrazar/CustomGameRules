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
    suffocationDamage = createBoolean("customgamerules:suffocation_damage", true, GameRuleCategory.PLAYER);
    pearlDamage = createBoolean("customgamerules:pearl_damage", true, GameRuleCategory.PLAYER);
    cactusDamage = createBoolean("customgamerules:cactus_damage", true, GameRuleCategory.PLAYER);
    berryDamage = createBoolean("customgamerules:berry_damage", true, GameRuleCategory.PLAYER);
    //
    //   keepInventory _______
    //
    keepInventoryExperience = createBoolean("customgamerules:keep_inventory_experience", false, GameRuleCategory.PLAYER);
    keepInventoryArmor = createBoolean("customgamerules:keep_inventory_armor", false, GameRuleCategory.PLAYER);
    //
    // do______
    //
    doFriendlyIronGolems = createBoolean("customgamerules:do_friendly_iron_golems", true, GameRuleCategory.MOBS);
    doMapsAlwaysUpdate = createBoolean("customgamerules:do_maps_always_update", true, GameRuleCategory.PLAYER);
    doLilypadsBreak = createBoolean("customgamerules:do_lilypads_break", true, GameRuleCategory.PLAYER);
    doInstantEating = createBoolean("customgamerules:do_instant_eating", false, GameRuleCategory.PLAYER);
    doInstantExp = createBoolean("customgamerules:do_instant_exp", false, GameRuleCategory.PLAYER);
    doArmorStandWeapons = createBoolean("customgamerules:do_armor_stand_weapons", true, GameRuleCategory.PLAYER);
    doEyesAlwaysBreak = createBoolean("customgamerules:do_eyes_always_break", false, GameRuleCategory.DROPS);
    doNetherVoidAbove = createBoolean("customgamerules:do_nether_void_above", false, GameRuleCategory.MISC);
    doCactusGrowthUnlimited = createBoolean("customgamerules:do_cactus_growth_unlimited", false, GameRuleCategory.MISC);
    doSugarGrowthUnlimited = createBoolean("customgamerules:do_sugar_growth_unlimited", false, GameRuleCategory.MISC);
    //= RuleFactory.createBoolean("customgamerules:do_instant_eating", true, GameRuleCategory.PLAYER);
    //
    //disable_____
    disablePortalCreationEnd = createBoolean("customgamerules:disable_portal_creation_end", false, GameRuleCategory.PLAYER);
    disablePortalCreationNether = createBoolean("customgamerules:disable_portal_creation_nether", false, GameRuleCategory.PLAYER);
    disableLightningTransform = createBoolean("customgamerules:disable_lightning_transform", false, GameRuleCategory.MOBS);
    disableTargetingPlayers = createBoolean("customgamerules:disable_targeting_players", false, GameRuleCategory.MOBS);
    disableVillagerTrading = createBoolean("customgamerules:disable_villager_trading", false, GameRuleCategory.MOBS);
    disableBlockGravity = createBoolean("customgamerules:disable_block_gravity", false, GameRuleCategory.UPDATES);
    disableBiomeFreezeIce = createBoolean("customgamerules:disable_biome_freeze_ice", false, GameRuleCategory.UPDATES);
    disableLightMeltIce = createBoolean("customgamerules:disable_light_melt_ice", false, GameRuleCategory.UPDATES);
    disableDecayLeaves = createBoolean("customgamerules:disable_decay_leaves", false, GameRuleCategory.UPDATES);
    disableDecayCoral = createBoolean("customgamerules:disable_decay_coral", false, GameRuleCategory.UPDATES);
    disableGenerateStone = createBoolean("customgamerules:disable_generate_stone", false, GameRuleCategory.UPDATES);
    disableGenerateObsidian = createBoolean("customgamerules:disable_generate_obsidian", false, GameRuleCategory.UPDATES);
    disablePetFriendlyFire = createBoolean("customgamerules:disable_pet_friendly_fire", true, GameRuleCategory.UPDATES);
    disableFarmlandTrampling = createBoolean("customgamerules:disable_farmland_trampling", false, GameRuleCategory.UPDATES);
    disableMobItemPickup = createBoolean("customgamerules:disable_mob_item_pickup", false, GameRuleCategory.MOBS);
    disableEndermanTeleport = createBoolean("customgamerules:disable_enderman_teleport", false, GameRuleCategory.MOBS);
    disableShulkerTeleport = createBoolean("customgamerules:disable_shulker_teleport", false, GameRuleCategory.MOBS);
    disableCropGrowth = createBoolean("customgamerules:disable_crop_growth", false, GameRuleCategory.UPDATES);
    disableSaplingGrowth = createBoolean("customgamerules:disable_sapling_growth", false, GameRuleCategory.UPDATES);
    disableCriticalHits = createBoolean("customgamerules:disable_critical_hits", false, GameRuleCategory.UPDATES);
    disableHunger = createBoolean("customgamerules:disable_hunger", false, GameRuleCategory.PLAYER);
    //
    //mobGriefing_______
    //
    mobGriefingCreeper = createBoolean("customgamerules:mob_griefing_creeper", true, GameRuleCategory.MOBS);
    mobGriefingEnderman = createBoolean("customgamerules:mob_griefing_enderman", true, GameRuleCategory.MOBS);
    mobGriefingVillager = createBoolean("customgamerules:mob_griefing_villager", true, GameRuleCategory.MOBS);
    mobGriefingZombie = createBoolean("customgamerules:mob_griefing_zombie", true, GameRuleCategory.MOBS);
    mobGriefingWither = createBoolean("customgamerules:mob_griefing_wither", true, GameRuleCategory.MOBS);
    mobGriefingRavager = createBoolean("customgamerules:mob_griefing_ravager", true, GameRuleCategory.MOBS);
    mobGriefingSilverfish = createBoolean("customgamerules:mob_griefing_silverfish", true, GameRuleCategory.MOBS);
    mobGriefingGhast = createBoolean("customgamerules:mob_griefing_ghast", true, GameRuleCategory.MOBS);
    mobGriefingBlaze = createBoolean("customgamerules:mob_griefing_blaze", true, GameRuleCategory.MOBS);
    mobGriefingSnowgolem = createBoolean("customgamerules:mob_griefing_snowgolem", true, GameRuleCategory.MOBS);
    ////    disableHunger// ONLY if we can HIDE the hunger bar
    //    RenderGameOverlayEvent yz;//CLIENT ONLY
    //
    //
    // bedrock feature parity
    //
    tntExplodes = createBoolean("customgamerules:tnt_explodes", true, GameRuleCategory.PLAYER);
    respawnBlocksExplode = createBoolean("customgamerules:respawn_blocks_explode", true, GameRuleCategory.PLAYER);
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
