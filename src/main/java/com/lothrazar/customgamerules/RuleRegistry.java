package com.lothrazar.customgamerules;

import com.lothrazar.library.reflect.RuleFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class RuleRegistry {

  private static final String PROTOCOL_VERSION = Integer.toString(1);
  public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
      .named(new ResourceLocation(GameRuleMod.MODID, "main_channel"))
      .clientAcceptedVersions(PROTOCOL_VERSION::equals)
      .serverAcceptedVersions(PROTOCOL_VERSION::equals)
      .networkProtocolVersion(() -> PROTOCOL_VERSION)
      .simpleChannel();
  public static GameRules.Key<BooleanValue> disableBiomeFreezeIce; // BiomeAntiFreezeMixin.java
  public static GameRules.Key<BooleanValue> disableBlockGravity; //   FallingBlockGravityMixin.java
  public static GameRules.Key<BooleanValue> disableDecayCoral; // CoralAntiDecayMixin.java CoralFanAntiDecayMixin.java
  public static GameRules.Key<BooleanValue> disableDecayLeaves; //  LeavesAntiDecayMixin.java
  public static GameRules.Key<BooleanValue> disableFarmlandTrampling;
  public static GameRules.Key<BooleanValue> disableGenerateObsidian;
  public static GameRules.Key<BooleanValue> disableGenerateStone;
  public static GameRules.Key<BooleanValue> disableLightMeltIce; //   IceAntiMeltMixin.java
  public static GameRules.Key<BooleanValue> disableMobItemPickup;
  public static GameRules.Key<BooleanValue> disablePetFriendlyFire;
  public static GameRules.Key<BooleanValue> disableVillagerTrading;
  // that disable features
  public static GameRules.Key<BooleanValue> doArmorStandWeapons;
  public static GameRules.Key<BooleanValue> doLilypadsBreak;
  public static GameRules.Key<BooleanValue> doEyesAlwaysBreak;
  public static GameRules.Key<BooleanValue> doCactusGrowthUnlimited; // CactusOverwriteMixin.java
  public static GameRules.Key<BooleanValue> doInstantEating;
  public static GameRules.Key<BooleanValue> doInstantExp;
  public static GameRules.Key<BooleanValue> doNetherVoidAbove;
  public static GameRules.Key<BooleanValue> doMapsAlwaysUpdate; // FilledMapItemRefreshMixin.java
  public static GameRules.Key<BooleanValue> doSugarGrowthUnlimited; //SugarOverwriteMixin.java
  // Game rules related to player damage
  public static GameRules.Key<BooleanValue> suffocationDamage;
  public static GameRules.Key<BooleanValue> pearlDamage;
  public static GameRules.Key<BooleanValue> cactusDamage;
  public static GameRules.Key<BooleanValue> berryDamage;
  // game rules that depend on other existing rules for fine-tuned control
  public static GameRules.Key<BooleanValue> keepInventoryExperience;
  public static GameRules.Key<BooleanValue> keepInventoryArmor;
  public static GameRules.Key<BooleanValue> mobGriefingCreeper;
  public static GameRules.Key<BooleanValue> mobGriefingEnderman;
  public static GameRules.Key<BooleanValue> mobGriefingVillager;
  public static GameRules.Key<BooleanValue> mobGriefingZombie;
  public static GameRules.Key<BooleanValue> mobGriefingWither;
  public static GameRules.Key<BooleanValue> mobGriefingRavager;
  public static GameRules.Key<BooleanValue> mobGriefingGhast;
  public static GameRules.Key<BooleanValue> mobGriefingSilverfish;
  public static GameRules.Key<BooleanValue> mobGriefingBlaze;
  public static GameRules.Key<BooleanValue> disableEndermanTeleport;
  public static GameRules.Key<BooleanValue> disableShulkerTeleport;
  public static GameRules.Key<BooleanValue> disableCropGrowth;
  public static GameRules.Key<BooleanValue> disableSaplingGrowth;
  public static GameRules.Key<BooleanValue> disableCriticalHits;
  public static GameRules.Key<BooleanValue> disableHunger;
  public static GameRules.Key<BooleanValue> disableTargetingPlayers;
  public static GameRules.Key<BooleanValue> disableLightningTransform;
  public static GameRules.Key<BooleanValue> disablePortalCreationNether;
  public static GameRules.Key<BooleanValue> disablePortalCreationEnd;
  public static GameRules.Key<BooleanValue> mobGriefingSnowgolem;
  public static GameRules.Key<BooleanValue> doFriendlyIronGolems;

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
    doFriendlyIronGolems = RuleFactory.createBoolean("doFriendlyIronGolems", true, GameRules.Category.MOBS);
    doMapsAlwaysUpdate = RuleFactory.createBoolean("doMapsAlwaysUpdate", true, GameRules.Category.PLAYER);
    doLilypadsBreak = RuleFactory.createBoolean("doLilypadsBreak", true, GameRules.Category.PLAYER);
    doInstantEating = RuleFactory.createBoolean("doInstantEating", false, GameRules.Category.PLAYER);
    doInstantExp = RuleFactory.createBoolean("doInstantExp", false, GameRules.Category.PLAYER);
    doArmorStandWeapons = RuleFactory.createBoolean("doArmorStandWeapons", true, GameRules.Category.PLAYER);
    doEyesAlwaysBreak = RuleFactory.createBoolean("doEyesAlwaysBreak", false, GameRules.Category.DROPS);
    doNetherVoidAbove = RuleFactory.createBoolean("doNetherVoidAbove", false, GameRules.Category.MISC);
    doCactusGrowthUnlimited = RuleFactory.createBoolean("doCactusGrowthUnlimited", false, GameRules.Category.MISC);
    doSugarGrowthUnlimited = RuleFactory.createBoolean("doSugarGrowthUnlimited", false, GameRules.Category.MISC);
    //= RuleFactory.createBoolean("doInstantEating", true, GameRules.Category.PLAYER);
    //
    //disable_____   
    disablePortalCreationEnd = RuleFactory.createBoolean("disablePortalCreationEnd", false, GameRules.Category.PLAYER);
    disablePortalCreationNether = RuleFactory.createBoolean("disablePortalCreationNether", false, GameRules.Category.PLAYER);
    disableLightningTransform = RuleFactory.createBoolean("disableLightningTransform", false, GameRules.Category.MOBS);
    disableTargetingPlayers = RuleFactory.createBoolean("disableTargetingPlayers", false, GameRules.Category.MOBS);
    disableVillagerTrading = RuleFactory.createBoolean("disableVillagerTrading", false, GameRules.Category.MOBS);
    disableBlockGravity = RuleFactory.createBoolean("disableBlockGravity", false, GameRules.Category.UPDATES);
    disableBiomeFreezeIce = RuleFactory.createBoolean("disableBiomeFreezeIce", false, GameRules.Category.UPDATES);
    disableLightMeltIce = RuleFactory.createBoolean("disableLightMeltIce", false, GameRules.Category.UPDATES);
    disableDecayLeaves = RuleFactory.createBoolean("disableDecayLeaves", false, GameRules.Category.UPDATES);
    disableDecayCoral = RuleFactory.createBoolean("disableDecayCoral", false, GameRules.Category.UPDATES);
    disableGenerateStone = RuleFactory.createBoolean("disableGenerateStone", false, GameRules.Category.UPDATES);
    disableGenerateObsidian = RuleFactory.createBoolean("disableGenerateObsidian", false, GameRules.Category.UPDATES);
    disablePetFriendlyFire = RuleFactory.createBoolean("disablePetFriendlyFire", true, GameRules.Category.UPDATES);
    disableFarmlandTrampling = RuleFactory.createBoolean("disableFarmlandTrampling", false, GameRules.Category.UPDATES);
    disableMobItemPickup = RuleFactory.createBoolean("disableMobItemPickup", false, GameRules.Category.MOBS);
    disableEndermanTeleport = RuleFactory.createBoolean("disableEndermanTeleport", false, GameRules.Category.MOBS);
    disableShulkerTeleport = RuleFactory.createBoolean("disableShulkerTeleport", false, GameRules.Category.MOBS);
    disableCropGrowth = RuleFactory.createBoolean("disableCropGrowth", false, GameRules.Category.UPDATES);
    disableSaplingGrowth = RuleFactory.createBoolean("disableSaplingGrowth", false, GameRules.Category.UPDATES);
    disableCriticalHits = RuleFactory.createBoolean("disableCriticalHits", false, GameRules.Category.UPDATES);
    disableHunger = RuleFactory.createBoolean("disableHunger", false, GameRules.Category.PLAYER);
    //
    //mobGriefing_______
    //
    mobGriefingCreeper = RuleFactory.createBoolean("mobGriefingCreeper", true, GameRules.Category.MOBS);
    mobGriefingEnderman = RuleFactory.createBoolean("mobGriefingEnderman", true, GameRules.Category.MOBS);
    mobGriefingVillager = RuleFactory.createBoolean("mobGriefingVillager", true, GameRules.Category.MOBS);
    mobGriefingZombie = RuleFactory.createBoolean("mobGriefingZombie", true, GameRules.Category.MOBS);
    mobGriefingWither = RuleFactory.createBoolean("mobGriefingWither", true, GameRules.Category.MOBS);
    mobGriefingRavager = RuleFactory.createBoolean("mobGriefingRavager", true, GameRules.Category.MOBS);
    mobGriefingSilverfish = RuleFactory.createBoolean("mobGriefingSilverfish", true, GameRules.Category.MOBS);
    mobGriefingGhast = RuleFactory.createBoolean("mobGriefingGhast", true, GameRules.Category.MOBS);
    mobGriefingBlaze = RuleFactory.createBoolean("mobGriefingBlaze", true, GameRules.Category.MOBS);
    mobGriefingSnowgolem = RuleFactory.createBoolean("mobGriefingSnowgolem", true, GameRules.Category.MOBS);
    ////    disableHunger// ONLY if we can HIDE the hunger bar
    //    RenderGameOverlayEvent yz;//CLIENT ONLY
    //
    //tntExplodes
    //    tntDamage = RuleFactory.createBoolean("tntDamage", true, GameRules.Category.PLAYER);
  }

  public static boolean isEnabled(Level world, GameRules.Key<BooleanValue> key) {
    return world.getGameRules().getBoolean(key);
  }

  public static boolean isEnabled(LevelAccessor world, GameRules.Key<BooleanValue> key) {
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
