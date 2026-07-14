package com.lothrazar.customgamerules.event;

import java.util.Iterator;
import com.lothrazar.customgamerules.ModGameRule;
import com.lothrazar.customgamerules.net.PacketHungerRuleSync;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import com.lothrazar.library.events.EventFlib;
import com.lothrazar.library.util.LevelWorldUtil;
import com.lothrazar.library.util.MobUtil;
import com.lothrazar.library.util.PlayerUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
//import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
//import net.neoforged.neoforge.event.level.BlockEvent.CropGrowEvent;
import net.neoforged.neoforge.event.level.BlockEvent.FarmlandTrampleEvent;
import net.neoforged.neoforge.event.level.BlockEvent.FluidPlaceBlockEvent;
import net.neoforged.neoforge.event.level.BlockEvent.PortalSpawnEvent;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.block.CropGrowEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class CustomRuleEvents extends EventFlib {

  /**
   * disablePortalCreationEnd
   */
  @SubscribeEvent
  public void onRightClickBlock(RightClickBlock event) {
    Level world = event.getEntity().level();
    if (RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.disablePortalCreationEnd)
        && world.getBlockState(event.getPos()).getBlock() == Blocks.END_PORTAL_FRAME
        && event.getEntity().getItemInHand(event.getHand()).getItem() == Items.ENDER_EYE) {
      ModGameRule.LOGGER.debug("portal creation disabled by gamerule");
      event.setCanceled(true);
    }
  }

  /**
   * disablePortalCreationNether
   */
  @SubscribeEvent
  public void onPortalSpawnEvent(PortalSpawnEvent event) {
    if (RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.disablePortalCreationNether)) {
      ModGameRule.LOGGER.debug("portal creation disabled by gamerule");
      event.setCanceled(true);
    }
  }

  /**
   * disableLightningTransform
   */
  @SubscribeEvent
  public void onEntityStruckByLightningEvent(EntityStruckByLightningEvent event) {
    Entity target = event.getEntity();
    if (RuleRegistry.isEnabled(target.level(), RuleRegistry.disableLightningTransform)) {
      event.setCanceled(true);
    }
  }

  /**
   * disableTargetingPlayers
   */
  @SubscribeEvent
  public void onLivingSetAttackTargetEvent(LivingChangeTargetEvent event) {
    //previosly was using net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent
    LivingEntity attacker = event.getEntity();
    if (event.getNewAboutToBeSetTarget() instanceof Player
        && RuleRegistry.isEnabled(attacker.level(), RuleRegistry.disableTargetingPlayers)) {
      MobUtil.removeAttackTargets(attacker);
    }
  }

  /**
   * disableFarmlandTrampling
   */
  @SubscribeEvent
  public void onFarmlandTrampleEvent(FarmlandTrampleEvent event) {
    if (RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.disableFarmlandTrampling)) {
      event.setCanceled(true);
    }
  }

  /**
   * disableGenerateObsidian * disableGenerateStone
   */
  @SubscribeEvent
  public void onFluidPlaceBlockEvent(FluidPlaceBlockEvent event) {
    if (!(event.getLevel() instanceof Level)) {
      return;
    }
    Level world = (Level) event.getLevel();

    Block newBlock = event.getNewState().getBlock();
    if (newBlock == Blocks.OBSIDIAN &&
        RuleRegistry.isEnabled(world, RuleRegistry.disableGenerateObsidian)) {
      event.setCanceled(true);
      event.setNewState(event.getOriginalState());
    }
    if ((newBlock == Blocks.COBBLESTONE || newBlock == Blocks.STONE) &&
        RuleRegistry.isEnabled(world, RuleRegistry.disableGenerateStone)) {
      //cancel should work but busted i guess IDK why
      event.setCanceled(true);
      event.setNewState(event.getOriginalState());
    }
  }

  /**
   * doInstantExp
   */
  @SubscribeEvent
  public void onPlayerXpEvent(PlayerXpEvent.PickupXp event) {
    Player player = event.getEntity();
    if (RuleRegistry.isEnabled(player.level(), RuleRegistry.doInstantExp)) {
      //reset XP on pickup
      if (player.takeXpDelay > 0) {
        player.takeXpDelay = 0;
      }
    }
  }

  /**
   * disableHunger
   */
  @SubscribeEvent
  public void onPlayerTickEvent(PlayerTickEvent.Pre event) {
    Player player = event.getEntity();
    boolean disableHunger = RuleRegistry.isEnabled(player.level(), RuleRegistry.disableHunger);
    if (System.currentTimeMillis() % 40 == 0
        && !player.level().isClientSide
        && player instanceof ServerPlayer serverPlayer) {
      //hack to push gamerule to client to hide hunger bar
      PacketDistributor.sendToPlayer(serverPlayer, new PacketHungerRuleSync(disableHunger));
    }
    if (disableHunger && player.getFoodData().needsFood()) {
      player.getFoodData().eat(1, 1);
    }
  }

  /**
   * doInstantEating
   */
  @SubscribeEvent
  public void onLivingEntityUseItemEvent(LivingEntityUseItemEvent.Tick event) {
    Entity entity = event.getEntity();
    if (event.getItem().has(DataComponents.FOOD)
        && RuleRegistry.isEnabled(entity.level(), RuleRegistry.doInstantEating)
        && event.getDuration() > 0) {
      event.setDuration(1);//dont set to zero, then it goes -1 and breks
    }
  }

  /**
   * disableVillagerTrading
   */
  @SubscribeEvent
  public void onEntityInteract(EntityInteract event) {
    if (RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.disableVillagerTrading)
        && event.getEntity() instanceof Player
        && event.getTarget() instanceof Villager) {
      event.setCanceled(true);
      event.setCancellationResult(InteractionResult.FAIL);
    }
  }

  /**
   * disableMobItemPickup
   *
   */
  @SubscribeEvent
  public void onEntityJoinWorldEvent(EntityJoinLevelEvent event) {
    if (RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.disableMobItemPickup)
        && event.getEntity() instanceof Mob mob) {
      MobUtil.disablePickupLoot(mob);
    }
  }

  /**
   * doArmorStandWeapons
   */
  @SubscribeEvent
  public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
    //
    if (event.getLevel().isClientSide) {
      return;
    } //server side only
    if (!RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.doArmorStandWeapons)) {
      return;
    }
    if (event.getTarget() == null || event.getTarget() instanceof ArmorStand == false) {
      return;
    }
    ArmorStand stand = (ArmorStand) event.getTarget();
    Player player = event.getEntity();
    if (player.isShiftKeyDown() == false) {
      return;
    }
    //gamerule: CAN EQUIP ARMOR STANDS
    //bc when not sneaking, we do the normal single item version
    //we just need to swap what we are holding
    event.setCanceled(true);
    PlayerUtil.swapArmorStand(stand, player, InteractionHand.MAIN_HAND);
    PlayerUtil.swapArmorStand(stand, player, InteractionHand.OFF_HAND);
    boolean showArms = !stand.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()
        || !stand.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty();
    stand.setShowArms(showArms);
  }

  /***
   * doNetherVoidAbove
   *
   */
  @SubscribeEvent
  public void onLivingUpdateEvent(EntityTickEvent.Pre event) {
    Entity entity = event.getEntity();
    if (RuleRegistry.isEnabled(entity.level(), RuleRegistry.doFriendlyIronGolems)
        && event.getEntity() instanceof IronGolem golem
        && golem.getLastHurtByMob() instanceof Player) { // .getKillCredit()
      //STAAAP
      MobUtil.removeAttackTargets(golem);
    }
    if (entity.yOld > 128 // yas gbaked into rule for now
        && LevelWorldUtil.dimensionToString(entity.level()).equalsIgnoreCase("minecraft:the_nether")
        && RuleRegistry.isEnabled(entity.level(), RuleRegistry.doNetherVoidAbove)) {
      if (entity.isAlive()) {
        entity.hurt(entity.damageSources().fellOutOfWorld(), 0.5F);
      }
    }
  }

  /***
   * doEyesAlwaysBreak
   *
   */
  @SubscribeEvent
  public void onNonLivingEntityTick(EntityTickEvent.Pre event) {
    Entity entity = event.getEntity();
    if (entity == null || entity.level() == null) {
      return;
    }
    if (entity instanceof EyeOfEnder eye) {
      if (eye.surviveAfterDeath &&
          RuleRegistry.isEnabled(eye.level(), RuleRegistry.doEyesAlwaysBreak)) {
        eye.surviveAfterDeath = false;
      }
    }
  }

  /**
   * disableCropGrowth
   */
  @SubscribeEvent
  public void onCropGrowEvent(CropGrowEvent.Pre event) {
    if (RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.disableCropGrowth)) {
      event.setResult(CropGrowEvent.Pre.Result.DO_NOT_GROW);
    }
  }

  /**
   * disableSaplingGrowth
   *
   * SaplingGrowTreeEvent class is gone, merged into block grow
   */
  @SubscribeEvent
  public void onSaplingGrowTreeEvent(BlockGrowFeatureEvent event) {
    BlockState state = event.getLevel().getBlockState(event.getPos());
    if (state.getBlock() instanceof SaplingBlock &&
        RuleRegistry.isEnabled((Level) event.getLevel(), RuleRegistry.disableSaplingGrowth)) {
      event.setCanceled(true);
    }
  }

  /**
   * disableCriticalHits
   */
  @SubscribeEvent
  public void onCriticalHitEvent(CriticalHitEvent event) {
    Level world = event.getEntity().level();
    if (event.isVanillaCritical() &&
        RuleRegistry.isEnabled(world, RuleRegistry.disableCriticalHits)) {
      event.setCriticalHit(false);
      // event.setCanceled(true);
    }
  }

  /***
   * pearlDamage disableEndermanTeleport disableShulkerTeleport
   *
   */
  @SubscribeEvent
  public void onEnderTeleportEvent(EntityTeleportEvent.EnderEntity event) {
    Level world = event.getEntity().level();
    if (event.getEntity() instanceof EnderMan
        && RuleRegistry.isEnabled(world, RuleRegistry.disableEndermanTeleport)) {
      event.setCanceled(true);
    }
    if (event.getEntity() instanceof Shulker
        && RuleRegistry.isEnabled(world, RuleRegistry.disableShulkerTeleport)) {
      event.setCanceled(true);
    }
  }

  @SubscribeEvent
  public void onEnderTeleportEvent(EntityTeleportEvent.EnderPearl event) {
    Level world = event.getEntity().level();
    if (!RuleRegistry.isEnabled(world, RuleRegistry.pearlDamage)) {
      event.setAttackDamage(0);
    }
  }

  /**
   * disablePetFriendlyFire
   */
  @SubscribeEvent
  public void onLivingAttackEvent(LivingIncomingDamageEvent event) {
    Level world = event.getEntity().level();
    if (RuleRegistry.isEnabled(world, RuleRegistry.disablePetFriendlyFire)
        && event.getSource().getEntity() instanceof Player dmgOwner) {
      //pets!
      if (event.getEntity() instanceof AbstractHorse horse) {
        //can be tamed
        if (PlayerUtil.isTamedByPlayer(horse, dmgOwner)) {
          // do the cancel
          event.setCanceled(true);
        }
      }
      if (event.getEntity() instanceof TamableAnimal pet) {
        //can be tamed
        //        ParrotEntity y;//yep parrot, cat, wolf all extend tameable
        if (PlayerUtil.isTamedByPlayer(pet, dmgOwner)) {
          event.setCanceled(true);
        }
      }
    }
  }

  /**
   * tntExplodes respawnBlocksExplode
   */
  @SubscribeEvent
  public void onExplosionStart(ExplosionEvent.Start event) {
    Entity source = event.getExplosion().getDirectSourceEntity();
    if (source instanceof PrimedTnt
        && !RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.tntExplodes)) {
      ModGameRule.LOGGER.debug("tntExplodes == false; cancelling explosion");
      event.setCanceled(true);
      return;
    }
    if (source == null && !RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.respawnBlocksExplode)) {
      // getter does not existgetDamageCalculator(); see AT.cfg
      ExplosionDamageCalculator calculator = event.getExplosion().damageCalculator;

//      if (event.getExplosion().getDamageSource().is(DamageTypes.BAD_RESPAWN_POINT)) {
      if (!(calculator instanceof SimpleExplosionDamageCalculator)) {

          ModGameRule.LOGGER.debug("respawnBlocksExplode=false, cancelling explosion");
        event.setCanceled(true);
      }
    }
  }

  /**
   * berryDamage cactusDamage doLilypadsBreak suffocationDamage
   */
  @SubscribeEvent
  public void onLivingDamageEvent(LivingIncomingDamageEvent event) {
    Level world = event.getEntity().level();
    if (event.getSource().is(DamageTypes.IN_WALL) &&
        !RuleRegistry.isEnabled(world, RuleRegistry.suffocationDamage)) {
      event.setCanceled(true);
    }
    if (event.getSource().is(DamageTypes.CACTUS) &&
        !RuleRegistry.isEnabled(world, RuleRegistry.cactusDamage)) {
      //
      event.setCanceled(true);
      //      event.setAmount(0);
    }
    if (event.getSource().is(DamageTypes.SWEET_BERRY_BUSH) &&
        !RuleRegistry.isEnabled(world, RuleRegistry.berryDamage)) {
      event.setCanceled(true);
    }
    //
    if ((event.getEntity() instanceof Player) == false) {
      return;
    }
    Player player = (Player) event.getEntity();
    if (event.getSource().is(DamageTypes.FALL)
        && RuleRegistry.isEnabled(world, RuleRegistry.doLilypadsBreak)) {
      if (world.getBlockState(player.blockPosition()).getBlock() == Blocks.LILY_PAD) {
        world.destroyBlock(player.blockPosition(), true, player);
        event.setAmount(0);
      }
    }
  }

  /***
   * mobGriefing_____
   */
  @SubscribeEvent
  public void onEntityMobGriefingEvent(EntityMobGriefingEvent event) {
    if (event == null || event.getEntity() == null || event.getEntity().level() == null) {
      return;
    }
    Entity ent = event.getEntity();
    Level world = ent.level();
    if (!RuleRegistry.isEnabled(world, GameRules.RULE_MOBGRIEFING)) {
      //mob griefing not allowed, do nothing
      return;
    }
    // mobGriefing == true, meaning a DEFAULT result will fall back to that and allow the grief
    //check if we want to deny specific mobs
    //if that mobs rule is FALSE then deny it
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingSnowgolem) && ent instanceof SnowGolem) {
      event.setCanGrief(false);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingCreeper) && ent instanceof Creeper) {
      event.setCanGrief(false);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingZombie) && ent instanceof Zombie) {
      //turtle eggs, doors
      event.setCanGrief(false);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingEnderman) && ent instanceof EnderMan) {
      event.setCanGrief(false);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingWither) &&
        (ent instanceof WitherBoss || ent instanceof WitherSkull)) {
      event.setCanGrief(false);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingRavager) && ent instanceof Ravager) {
      //break on collide
      event.setCanGrief(false);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingSilverfish) && ent instanceof Silverfish) {
      //entering the stone
      event.setCanGrief(false);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingGhast) && (ent instanceof LargeFireball || ent instanceof Ghast)) {//
      // GHAST Fireball
      event.setCanGrief(false);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingBlaze) && ent instanceof SmallFireball) {
      // blaze  Fireball
      event.setCanGrief(false);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingVillager) && ent instanceof Villager) {
      // farming
      event.setCanGrief(false);
      return;
    }
    //    GameRuleMod.info(" deny trigger ALLOW  " + ent);
    //snow golem, silverfish, sheep not done
  }

  /***
   * keepInventoryExperience
   */
  @SubscribeEvent
  public void onPlayerDeath(PlayerEvent.Clone event) {
    if (!event.isWasDeath()) {
      return;
    }
    //BlockPos deathPos = event.getOriginal().getPosition();
    Player player = event.getEntity();
    Level world = player.level();
    //    if (RuleRegistry.isEnabled(world, RuleRegistry.doReduceHeartsOnDeath)
    //        && !player.isCreative()
    //        && player.getMaxHealth() > 2) {
    //      EntityHelpers.incrementMaxHealth(player);
    //    }
    if (RuleRegistry.isEnabled(world, GameRules.RULE_KEEPINVENTORY)) {
      //sub- rules of keep inventory
      if (RuleRegistry.isEnabled(world, RuleRegistry.keepInventoryExperience)) {
        PlayerUtil.clearAllExp(player);
      }
    }
  }

  /***
   * keepInventoryArmor
   */
  @SubscribeEvent
  public void onPlayerDrops(LivingDropsEvent event) {
    Level world = event.getEntity().level();
    if (RuleRegistry.isEnabled(world, GameRules.RULE_KEEPINVENTORY)
        && event.getEntity() instanceof Player player) {
      //sub- rules of keep inventory
      if (RuleRegistry.isEnabled(world, RuleRegistry.keepInventoryArmor)) {
        Iterator<ItemStack> i = player.getArmorSlots().iterator();
        while (i.hasNext()) {
          ItemStack is = i.next();
          //player.dropItem will drop AFTER DEATH. so
          //          this.drop(player.world, deathPos, is);
          event.getDrops().add(new ItemEntity(world,
              player.getX(), player.getY(), player.getZ(),
              is.copy()));
          is.setCount(0);
        }
      }
    }
  }
}
