package com.lothrazar.customgamerules.event;

import java.util.Iterator;
import com.lothrazar.customgamerules.ModGameRule;
import com.lothrazar.customgamerules.net.PacketHungerRuleSync;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import com.lothrazar.library.events.EventFlib;
import com.lothrazar.library.util.LevelWorldUtil;
import com.lothrazar.library.util.MobUtil;
import com.lothrazar.library.util.PacketUtil;
import com.lothrazar.library.util.PlayerUtil;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent.CropGrowEvent;
import net.minecraftforge.event.level.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.event.level.BlockEvent.FluidPlaceBlockEvent;
import net.minecraftforge.event.level.BlockEvent.PortalSpawnEvent;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CustomRuleEvents extends EventFlib {

  /**
   * disablePortalCreationEnd
   */
  @SubscribeEvent
  public void onRightClickBlock(RightClickBlock event) {
    Level world = event.getEntity().level;
    if (RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.disablePortalCreationEnd)
        && world.getBlockState(event.getPos()).getBlock() == Blocks.END_PORTAL_FRAME
        && event.getEntity().getItemInHand(event.getHand()).getItem() == Items.ENDER_EYE) {
      ModGameRule.LOGGER.info("portal creation disabled by gamerule");
      event.setCanceled(true);
    }
  }

  /**
   * disablePortalCreationNether
   */
  @SubscribeEvent
  public void onPortalSpawnEvent(PortalSpawnEvent event) {
    if (RuleRegistry.isEnabled(event.getLevel(), RuleRegistry.disablePortalCreationNether)) {
      ModGameRule.LOGGER.info("portal creation disabled by gamerule");
      event.setCanceled(true);
    }
  }

  /**
   * disableLightningTransform
   */
  @SubscribeEvent
  public void onEntityStruckByLightningEvent(EntityStruckByLightningEvent event) {
    Entity target = event.getEntity();
    if (RuleRegistry.isEnabled(target.level, RuleRegistry.disableLightningTransform)) {
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
    if (event.getNewTarget() instanceof Player
        && RuleRegistry.isEnabled(attacker.level, RuleRegistry.disableTargetingPlayers)) {
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
    //    if (event.getOriginalState().getBlock() == Blocks.REDSTONE_WIRE) {
    //      //      FluidMotionEvent abc;
    //      GameRuleMod.LOGGER.info("water redstone?");
    //    }
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
  public void onPlayerXpEvent(PlayerXpEvent event) {
    Player player = event.getEntity();
    if (RuleRegistry.isEnabled(player.level, RuleRegistry.doInstantExp)) {
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
  public void onPlayerTickEvent(PlayerTickEvent event) {
    Player player = event.player;
    boolean disableHunger = RuleRegistry.isEnabled(player.level, RuleRegistry.disableHunger);
    if (System.currentTimeMillis() % 40 == 0
        && player.level.isClientSide == false) {
      //hack to push gamerule to client to hide hunger bar
      PacketUtil.sendToAllClients(RuleRegistry.INSTANCE, player.level, new PacketHungerRuleSync(disableHunger));
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
    if (event.getItem().isEdible() && RuleRegistry.isEnabled(entity.level, RuleRegistry.doInstantEating)
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
      event.setResult(Result.DENY);
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
  public void onLivingUpdateEvent(LivingTickEvent event) {
    Entity entity = event.getEntity();
    if (RuleRegistry.isEnabled(entity.level, RuleRegistry.doFriendlyIronGolems)
        && event.getEntity() instanceof IronGolem
        && event.getEntity().getKillCredit() instanceof Player) {
      //STAAAP 
      MobUtil.removeAttackTargets(event.getEntity());
    }
    if (entity.yOld > 128 // yas gbaked into rule for now
        && LevelWorldUtil.dimensionToString(entity.level).equalsIgnoreCase("minecraft:the_nether")
        && RuleRegistry.isEnabled(entity.level, RuleRegistry.doNetherVoidAbove)) {
      if (entity.isAlive()) {
        entity.hurt(entity.damageSources().outOfWorld(), 0.5F);
      }
    }
  }

  /***
   * doEyesAlwaysBreak
   * 
   */
  @SubscribeEvent
  public void onNonLivingEntityTick(EntityEvent event) {
    Entity entity = event.getEntity();
    if (entity == null || entity.level == null) {
      return;
    }
    if (entity instanceof EyeOfEnder eye) {
      if (eye.surviveAfterDeath &&
          RuleRegistry.isEnabled(eye.level, RuleRegistry.doEyesAlwaysBreak)) {
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
      //      event.setCanceled(true);//not allowed
      event.setResult(Result.DENY);
    }
  }

  /**
   * disableSaplingGrowth
   */
  @SubscribeEvent
  public void onSaplingGrowTreeEvent(SaplingGrowTreeEvent event) {
    if (event.getLevel() instanceof Level &&
        RuleRegistry.isEnabled((Level) event.getLevel(), RuleRegistry.disableSaplingGrowth)) {
      //      event.setCanceled(true);//not allowed 
      event.setResult(Result.DENY);
    }
  }

  /**
   * disableCriticalHits
   */
  @SubscribeEvent
  public void onCriticalHitEvent(CriticalHitEvent event) {
    Level world = event.getEntity().level;
    if (event.isVanillaCritical() &&
        RuleRegistry.isEnabled(world, RuleRegistry.disableCriticalHits)) {
      event.setResult(Result.DENY);
      // event.setCanceled(true); 
    }
  }

  /***
   * pearlDamage disableEndermanTeleport disableShulkerTeleport
   * 
   */
  @SubscribeEvent
  public void onEnderTeleportEvent(EntityTeleportEvent.EnderEntity event) {
    Level world = event.getEntity().level;
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
    Level world = event.getEntity().level;
    if (!RuleRegistry.isEnabled(world, RuleRegistry.pearlDamage)) {
      event.setAttackDamage(0);
    }
  }

  /**
   * disablePetFriendlyFire
   */
  @SubscribeEvent
  public void onLivingAttackEvent(LivingAttackEvent event) {
    Level world = event.getEntity().level;
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
   * berryDamage cactusDamage doLilypadsBreak suffocationDamage
   */
  @SubscribeEvent
  public void onLivingDamageEvent(LivingDamageEvent event) {
    Level world = event.getEntity().level;
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
    //    if (event.getSource().isExplosion() &&
    //        !RuleRegistry.isEnabled(world, RuleRegistry.tntDamage)) {
    //      //immediate is tnt entity, true is the player that lit the thing if any
    //      //both will be PLAYER if its set by flint and steel
    //      //both null if TNT set by automated method
    //    //      //BUT ALSO this triggers for Creeper entities, etc
    //    GameRuleMod.LOGGER.info("explosion immd source " + event.getSource().getImmediateSource());
    //      GameRuleMod.LOGGER.info("explosion true source " + event.getSource().getTrueSource());
    //      event.setCanceled(true);
    //    }
  }

  /***
   * mobGriefing_____
   */
  @SubscribeEvent
  public void onEntityMobGriefingEvent(EntityMobGriefingEvent event) {
    if (event == null || event.getEntity() == null || event.getEntity().level == null) {
      return;
    }
    Entity ent = event.getEntity();
    Level world = ent.level;
    if (!RuleRegistry.isEnabled(world, GameRules.RULE_MOBGRIEFING)) {
      //mob griefing not allowed, do nothing
      return;
    }
    // mobGriefing == true, meaning a DEFAULT result will fall back to that and allow the grief
    //check if we want to deny specific mobs
    //if that mobs rule is FALSE then deny it
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingSnowgolem) && ent instanceof SnowGolem) {
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingCreeper) && ent instanceof Creeper) {
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingZombie) && ent instanceof Zombie) {
      //turtle eggs, doors
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingEnderman) && ent instanceof EnderMan) {
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingWither) &&
        (ent instanceof WitherBoss || ent instanceof WitherSkull)) {
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingRavager) && ent instanceof Ravager) {
      //break on collide
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingSilverfish) && ent instanceof Silverfish) {
      //entering the stone
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingGhast) && (ent instanceof LargeFireball || ent instanceof Ghast)) {//
      // GHAST Fireball 
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingBlaze) && ent instanceof SmallFireball) {
      // blaze  Fireball
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingVillager) && ent instanceof Villager) {
      // farming
      event.setResult(Result.DENY);
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
    Level world = player.level;
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
    Level world = event.getEntity().level;
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
