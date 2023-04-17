package com.lothrazar.customgamerules.rules;

import com.lothrazar.customgamerules.ModGameRule;
import com.lothrazar.customgamerules.net.PacketHungerRuleSync;
import com.lothrazar.library.registry.GameRuleFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class RuleRegistry {

  private static final String PROTOCOL_VERSION = Integer.toString(1);
  public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
      .named(new ResourceLocation(ModGameRule.MODID, "main_channel"))
      .clientAcceptedVersions(PROTOCOL_VERSION::equals)
      .serverAcceptedVersions(PROTOCOL_VERSION::equals)
      .networkProtocolVersion(() -> PROTOCOL_VERSION)
      .simpleChannel();
  //
  //  public static Key<BooleanValue> disableBiomeFreezeIce; // BiomeAntiFreezeMixin.java
  //  public static Key<BooleanValue> disableBlockGravity; //   FallingBlockGravityMixin.java
  //  public static Key<BooleanValue> disableDecayCoral; // CoralAntiDecayMixin.java CoralFanAntiDecayMixin.java
  //  public static Key<BooleanValue> disableDecayLeaves; //  LeavesAntiDecayMixin.java
  public static Key<BooleanValue> disableFarmlandTrampling;
  public static Key<BooleanValue> disableGenerateObsidian;
  public static Key<BooleanValue> disableGenerateStone;
  //  public static Key<BooleanValue> disableLightMeltIce; //   IceAntiMeltMixin.java
  public static Key<BooleanValue> disableMobItemPickup;
  public static Key<BooleanValue> disablePetFriendlyFire;
  public static Key<BooleanValue> disableVillagerTrading;
  // that disable features
  public static Key<BooleanValue> doArmorStandWeapons;
  public static Key<BooleanValue> doLilypadsBreak;
  public static Key<BooleanValue> doEyesAlwaysBreak;
  //  public static Key<BooleanValue> doCactusGrowthUnlimited; // CactusOverwriteMixin.java
  public static Key<BooleanValue> doInstantEating;
  public static Key<BooleanValue> doInstantExp;
  public static Key<BooleanValue> doNetherVoidAbove;
  //  public static Key<BooleanValue> doMapsAlwaysUpdate; // FilledMapItemRefreshMixin.java
  //  public static Key<BooleanValue> doSugarGrowthUnlimited; //SugarOverwriteMixin.java
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
    int id = 0;
    INSTANCE.registerMessage(id++, PacketHungerRuleSync.class, PacketHungerRuleSync::encode, PacketHungerRuleSync::decode, PacketHungerRuleSync::handle);
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
    suffocationDamage = GameRuleFactory.createBoolean("suffocationDamage", true, GameRules.Category.PLAYER);
    pearlDamage = GameRuleFactory.createBoolean("pearlDamage", true, GameRules.Category.PLAYER);
    cactusDamage = GameRuleFactory.createBoolean("cactusDamage", true, GameRules.Category.PLAYER);
    berryDamage = GameRuleFactory.createBoolean("berryDamage", true, GameRules.Category.PLAYER);
    //
    //   keepInventory _______
    //
    keepInventoryExperience = GameRuleFactory.createBoolean("keepInventoryExperience", false, GameRules.Category.PLAYER);
    keepInventoryArmor = GameRuleFactory.createBoolean("keepInventoryArmor", false, GameRules.Category.PLAYER);
    //
    // do______ 
    //
    doFriendlyIronGolems = GameRuleFactory.createBoolean("doFriendlyIronGolems", true, GameRules.Category.MOBS);
    //    doMapsAlwaysUpdate = RuleFactory.createBoolean("doMapsAlwaysUpdate", true, GameRules.Category.PLAYER);
    doLilypadsBreak = GameRuleFactory.createBoolean("doLilypadsBreak", true, GameRules.Category.PLAYER);
    doInstantEating = GameRuleFactory.createBoolean("doInstantEating", false, GameRules.Category.PLAYER);
    doInstantExp = GameRuleFactory.createBoolean("doInstantExp", false, GameRules.Category.PLAYER);
    doArmorStandWeapons = GameRuleFactory.createBoolean("doArmorStandWeapons", true, GameRules.Category.PLAYER);
    doEyesAlwaysBreak = GameRuleFactory.createBoolean("doEyesAlwaysBreak", false, GameRules.Category.DROPS);
    doNetherVoidAbove = GameRuleFactory.createBoolean("doNetherVoidAbove", false, GameRules.Category.MISC);
    //    doCactusGrowthUnlimited = RuleFactory.createBoolean("doCactusGrowthUnlimited", false, GameRules.Category.MISC);
    //    doSugarGrowthUnlimited = RuleFactory.createBoolean("doSugarGrowthUnlimited", false, GameRules.Category.MISC);
    //= RuleFactory.createBoolean("doInstantEating", true, GameRules.Category.PLAYER);
    //
    //disable_____   
    disablePortalCreationEnd = GameRuleFactory.createBoolean("disablePortalCreationEnd", false, GameRules.Category.PLAYER);
    disablePortalCreationNether = GameRuleFactory.createBoolean("disablePortalCreationNether", false, GameRules.Category.PLAYER);
    disableLightningTransform = GameRuleFactory.createBoolean("disableLightningTransform", false, GameRules.Category.MOBS);
    disableTargetingPlayers = GameRuleFactory.createBoolean("disableTargetingPlayers", false, GameRules.Category.MOBS);
    disableVillagerTrading = GameRuleFactory.createBoolean("disableVillagerTrading", false, GameRules.Category.MOBS);
    //    disableBlockGravity = RuleFactory.createBoolean("disableBlockGravity", false, GameRules.Category.UPDATES);
    //    disableBiomeFreezeIce = RuleFactory.createBoolean("disableBiomeFreezeIce", false, GameRules.Category.UPDATES);
    //    disableLightMeltIce = RuleFactory.createBoolean("disableLightMeltIce", false, GameRules.Category.UPDATES);
    //    disableDecayLeaves = RuleFactory.createBoolean("disableDecayLeaves", false, GameRules.Category.UPDATES);
    //    disableDecayCoral = RuleFactory.createBoolean("disableDecayCoral", false, GameRules.Category.UPDATES);
    disableGenerateStone = GameRuleFactory.createBoolean("disableGenerateStone", false, GameRules.Category.UPDATES);
    disableGenerateObsidian = GameRuleFactory.createBoolean("disableGenerateObsidian", false, GameRules.Category.UPDATES);
    disablePetFriendlyFire = GameRuleFactory.createBoolean("disablePetFriendlyFire", true, GameRules.Category.UPDATES);
    disableFarmlandTrampling = GameRuleFactory.createBoolean("disableFarmlandTrampling", false, GameRules.Category.UPDATES);
    disableMobItemPickup = GameRuleFactory.createBoolean("disableMobItemPickup", false, GameRules.Category.MOBS);
    disableEndermanTeleport = GameRuleFactory.createBoolean("disableEndermanTeleport", false, GameRules.Category.MOBS);
    disableShulkerTeleport = GameRuleFactory.createBoolean("disableShulkerTeleport", false, GameRules.Category.MOBS);
    disableCropGrowth = GameRuleFactory.createBoolean("disableCropGrowth", false, GameRules.Category.UPDATES);
    disableSaplingGrowth = GameRuleFactory.createBoolean("disableSaplingGrowth", false, GameRules.Category.UPDATES);
    disableCriticalHits = GameRuleFactory.createBoolean("disableCriticalHits", false, GameRules.Category.UPDATES);
    disableHunger = GameRuleFactory.createBoolean("disableHunger", false, GameRules.Category.PLAYER);
    //
    //mobGriefing_______
    //
    mobGriefingCreeper = GameRuleFactory.createBoolean("mobGriefingCreeper", true, GameRules.Category.MOBS);
    mobGriefingEnderman = GameRuleFactory.createBoolean("mobGriefingEnderman", true, GameRules.Category.MOBS);
    mobGriefingVillager = GameRuleFactory.createBoolean("mobGriefingVillager", true, GameRules.Category.MOBS);
    mobGriefingZombie = GameRuleFactory.createBoolean("mobGriefingZombie", true, GameRules.Category.MOBS);
    mobGriefingWither = GameRuleFactory.createBoolean("mobGriefingWither", true, GameRules.Category.MOBS);
    mobGriefingRavager = GameRuleFactory.createBoolean("mobGriefingRavager", true, GameRules.Category.MOBS);
    mobGriefingSilverfish = GameRuleFactory.createBoolean("mobGriefingSilverfish", true, GameRules.Category.MOBS);
    mobGriefingGhast = GameRuleFactory.createBoolean("mobGriefingGhast", true, GameRules.Category.MOBS);
    mobGriefingBlaze = GameRuleFactory.createBoolean("mobGriefingBlaze", true, GameRules.Category.MOBS);
    mobGriefingSnowgolem = GameRuleFactory.createBoolean("mobGriefingSnowgolem", true, GameRules.Category.MOBS);
    ////    disableHunger// ONLY if we can HIDE the hunger bar
    //    RenderGameOverlayEvent yz;//CLIENT ONLY
    //
    //tntExplodes
    //    tntDamage = RuleFactory.createBoolean("tntDamage", true, GameRules.Category.PLAYER); 
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

  public static void sendToAllClients(Level world, PacketHungerRuleSync packet) {
    for (Player player : world.players()) {
      if (player instanceof ServerPlayer) {
        //test 
        ServerPlayer sp = ((ServerPlayer) player);
        INSTANCE.sendTo(packet,
            sp.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
      }
    }
  }
}
