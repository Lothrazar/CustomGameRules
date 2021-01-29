package com.lothrazar.customgamerules.event;

import com.lothrazar.customgamerules.PacketHungerRuleSync;
import com.lothrazar.customgamerules.RuleRegistry;
import com.lothrazar.customgamerules.util.UtilWorld;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.event.world.BlockEvent.FluidPlaceBlockEvent;
import net.minecraftforge.event.world.BlockEvent.PortalSpawnEvent;
import net.minecraftforge.event.world.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RuleEvents {

  /**
   * disablePortalCreationEnd
   */
  @SubscribeEvent
  public void onRightClickBlock(RightClickBlock event) {
    World world = event.getPlayer().world;
    if (RuleRegistry.isEnabled(event.getWorld(), RuleRegistry.disablePortalCreationEnd)
        && world.getBlockState(event.getPos()).getBlock() == Blocks.END_PORTAL_FRAME
        && event.getPlayer().getHeldItem(event.getHand()).getItem() == Items.ENDER_EYE) {
      event.setCanceled(true);
    }
  }

  /**
   * disablePortalCreationNether
   */
  @SubscribeEvent
  public void onPortalSpawnEvent(PortalSpawnEvent event) {
    if (RuleRegistry.isEnabled(event.getWorld(), RuleRegistry.disablePortalCreationNether)) {
      event.setCanceled(true);
    }
  }

  /**
   * disableLightningTransform
   */
  @SubscribeEvent
  public void onEntityStruckByLightningEvent(EntityStruckByLightningEvent event) {
    Entity target = event.getEntity();
    if (RuleRegistry.isEnabled(target.world, RuleRegistry.disableLightningTransform)) {
      event.setCanceled(true);
    }
  }

  /**
   * disableTargetingPlayers
   */
  @SubscribeEvent
  public void onLivingSetAttackTargetEvent(LivingSetAttackTargetEvent event) {
    // 
    LivingEntity attacker = event.getEntityLiving();
    if (event.getTarget() instanceof PlayerEntity
        && RuleRegistry.isEnabled(attacker.world, RuleRegistry.disableTargetingPlayers)) {
      //      event.setCanceled(true);
      //      event.setResult(Result.DENY);
      attacker.setRevengeTarget(null);
      attacker.setLastAttackedEntity(null);
      if (attacker instanceof MobEntity) {
        MobEntity mob = (MobEntity) attacker;
        mob.setAttackTarget(null);
      }
    }
  }

  /**
   * disableFarmlandTrampling
   */
  @SubscribeEvent
  public void onFarmlandTrampleEvent(FarmlandTrampleEvent event) {
    if (RuleRegistry.isEnabled(event.getWorld(), RuleRegistry.disableFarmlandTrampling)) {
      event.setCanceled(true);
    }
  }

  /**
   * disableGenerateObsidian * disableGenerateStone
   */
  @SubscribeEvent
  public void onFluidPlaceBlockEvent(FluidPlaceBlockEvent event) {
    if (!(event.getWorld() instanceof World)) {
      return;
    }
    World world = (World) event.getWorld();
    //    if (event.getOriginalState().getBlock() == Blocks.REDSTONE_WIRE) {
    //      //      FluidMotionEvent abc;
    //      GameRuleMod.LOGGER.info("water redstone?");
    //    }
    Block newBlock = event.getNewState().getBlock();
    if (newBlock == Blocks.OBSIDIAN &&
        RuleRegistry.isEnabled(world, RuleRegistry.disableGenerateObsidian)) {
      //event.setCanceled(true);
      event.setNewState(event.getOriginalState());
    }
    if ((newBlock == Blocks.COBBLESTONE || newBlock == Blocks.STONE) &&
        RuleRegistry.isEnabled(world, RuleRegistry.disableGenerateStone)) {
      //cancel should work but busted i guess IDK why
      //      event.setCanceled(true);
      event.setNewState(event.getOriginalState());
    }
  }

  /**
   * doInstantExp
   */
  @SubscribeEvent
  public void onPlayerXpEvent(PlayerXpEvent event) {
    PlayerEntity player = event.getPlayer();
    if (RuleRegistry.isEnabled(player.world, RuleRegistry.doInstantExp)) {
      //reset XP on pickup
      if (player.xpCooldown > 0)
        player.xpCooldown = 0;
    }
  }

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onRenderGameOverlayEvent(RenderGameOverlayEvent event) {
    //hack because gamerule is false on client even if server is true
    //    System.out.println("hide "
    //        + Minecraft.getInstance().player.getPersistentData().getBoolean("disableHungerHACK"));
    if (event.getType() == ElementType.FOOD) {
      boolean hide = RuleRegistry.isEnabled(Minecraft.getInstance().player.world, RuleRegistry.disableHunger)
          || Minecraft.getInstance().player.getPersistentData().getBoolean("disableHungerHACK");
      if (hide) {
        //
        event.setCanceled(true);
      }
    }
  }

  /**
   * disableHunger
   */
  @SubscribeEvent
  public void onPlayerTickEvent(PlayerTickEvent event) {
    PlayerEntity player = event.player;
    boolean disableHunger = RuleRegistry.isEnabled(player.world, RuleRegistry.disableHunger);
    if (System.currentTimeMillis() % 40 == 0
        && player.world.isRemote == false) {
      //hack to push gamerule to client to hide hunger bar
      RuleRegistry.sendToAllClients(player.world, new PacketHungerRuleSync(disableHunger));
    }
    if (disableHunger && player.getFoodStats().needFood()) {
      player.getFoodStats().addStats(1, 1);
    }
  }

  /**
   * doInstantEating
   */
  @SubscribeEvent
  public void onLivingEntityUseItemEvent(LivingEntityUseItemEvent.Tick event) {
    Entity entity = event.getEntity();
    if (event.getItem().isFood() && RuleRegistry.isEnabled(entity.world, RuleRegistry.doInstantEating)
        && event.getDuration() > 0) {
      event.setDuration(1);//dont set to zero, then it goes -1 and breks
    }
  }

  /**
   * disableVillagerTrading
   */
  @SubscribeEvent
  public void onEntityInteract(EntityInteract event) {
    if (RuleRegistry.isEnabled(event.getWorld(), RuleRegistry.disableVillagerTrading)
        && event.getEntity() instanceof PlayerEntity
        && event.getTarget() instanceof VillagerEntity) {
      //  
      event.setCanceled(true);
      event.setResult(Result.DENY);
    }
  }

  /**
   * disableMobItemPickup
   * 
   */
  @SubscribeEvent
  public void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
    if (RuleRegistry.isEnabled(event.getWorld(), RuleRegistry.disableMobItemPickup)
        && event.getEntity() instanceof MobEntity) {
      MobEntity mob = (MobEntity) event.getEntity();
      if (mob.canPickUpLoot()) {
        mob.setCanPickUpLoot(false);
        //   GameRuleMod.LOGGER.info("After edit mob with pickup " + mob.canPickUpLoot());
      }
    }
  }

  /**
   * doArmorStandWeapons
   */
  @SubscribeEvent
  public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
    //
    if (event.getWorld().isRemote) {
      return;
    } //server side only
    if (!RuleRegistry.isEnabled(event.getWorld(), RuleRegistry.doArmorStandWeapons)) {
      return;
    }
    if (event.getTarget() == null || event.getTarget() instanceof ArmorStandEntity == false) {
      return;
    }
    ArmorStandEntity stand = (ArmorStandEntity) event.getTarget();
    PlayerEntity player = event.getPlayer();
    if (player.isSneaking() == false) {
      return;
    }
    //gamerule: CAN EQUIP ARMOR STANDS
    //bc when not sneaking, we do the normal single item version
    //we just need to swap what we are holding
    event.setCanceled(true);
    swapArmorStand(stand, player, Hand.MAIN_HAND);
    swapArmorStand(stand, player, Hand.OFF_HAND);
    boolean showArms = !stand.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()
        ||
        !stand.getItemStackFromSlot(EquipmentSlotType.OFFHAND).isEmpty();
    //oh at least one arm is holding a thing? ok
    stand.setShowArms(showArms);
  }

  private void swapArmorStand(ArmorStandEntity stand, PlayerEntity player, Hand hand) {
    ItemStack heldPlayer = player.getHeldItem(hand).copy();
    ItemStack heldStand = stand.getHeldItem(hand).copy();
    EquipmentSlotType slot = (hand == Hand.MAIN_HAND) ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND;
    stand.setItemStackToSlot(slot, heldPlayer);
    player.setItemStackToSlot(slot, heldStand);
    //    stand.getShowArms()
  }

  /***
   * doNetherVoidAbove
   * 
   */
  @SubscribeEvent
  public void onLivingUpdateEvent(LivingUpdateEvent event) {
    Entity entity = event.getEntity();
    if (RuleRegistry.isEnabled(entity.world, RuleRegistry.doFriendlyIronGolems)
        && event.getEntityLiving() instanceof IronGolemEntity
        && event.getEntityLiving().getAttackingEntity() instanceof PlayerEntity) {
      //STAAAP 
      IronGolemEntity golem = (IronGolemEntity) event.getEntityLiving();
      golem.setAttackTarget(null);
      golem.setRevengeTarget(null);
      golem.setLastAttackedEntity(null);
    }
    if (entity.lastTickPosY > 128
        && UtilWorld.dimensionToString(entity.world).equalsIgnoreCase("minecraft:the_nether") //
        && RuleRegistry.isEnabled(entity.world, RuleRegistry.doNetherVoidAbove)) {
      //WTF is null about this
      if (entity.isAlive())
        entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 0.5F);
    }
  }
  //    //
  //    if (event.getEntityLiving() instanceof PlayerEntity) {
  //      PlayerEntity player = (PlayerEntity) event.getEntityLiving();
  //      FoodStats food = player.getFoodStats();
  //    }

  /***
   * doEyesAlwaysBreak
   * 
   */
  @SubscribeEvent
  public void onNonLivingEntityTick(EntityEvent event) {
    Entity entity = event.getEntity();
    if (entity == null || entity.world == null) {
      return;
    }
    if (entity instanceof EyeOfEnderEntity) {
      EyeOfEnderEntity eye = (EyeOfEnderEntity) event.getEntity();
      if (eye.shatterOrDrop &&
          RuleRegistry.isEnabled(eye.world, RuleRegistry.doEyesAlwaysBreak)) {
        eye.shatterOrDrop = false;
      }
    }
  }

  /**
   * disableCropGrowth
   */
  @SubscribeEvent
  public void onCropGrowEvent(CropGrowEvent.Pre event) {
    if (RuleRegistry.isEnabled(event.getWorld(), RuleRegistry.disableCropGrowth)) {
      //      event.setCanceled(true);//not allowed
      event.setResult(Result.DENY);
    }
  }

  /**
   * disableSaplingGrowth
   */
  @SubscribeEvent
  public void onSaplingGrowTreeEvent(SaplingGrowTreeEvent event) {
    if (event.getWorld() instanceof World &&
        RuleRegistry.isEnabled((World) event.getWorld(), RuleRegistry.disableSaplingGrowth)) {
      //      event.setCanceled(true);//not allowed 
      event.setResult(Result.DENY);
    }
  }

  /**
   * disableCriticalHits
   */
  @SubscribeEvent
  public void onCriticalHitEvent(CriticalHitEvent event) {
    World world = event.getEntity().world;
    if (event.isVanillaCritical() &&
        RuleRegistry.isEnabled(world, RuleRegistry.disableCriticalHits)) {
      event.setResult(Result.DENY);// event.setCanceled(true); 
    }
  }

  /***
   * pearlDamage disableEndermanTeleport disableShulkerTeleport
   * 
   */
  @SubscribeEvent
  public void onEnderTeleportEvent(EnderTeleportEvent event) {
    World world = event.getEntity().world;
    if (event.getEntityLiving() instanceof EndermanEntity
        && RuleRegistry.isEnabled(world, RuleRegistry.disableEndermanTeleport)) {
      event.setCanceled(true);
    }
    if (event.getEntityLiving() instanceof ShulkerEntity
        && RuleRegistry.isEnabled(world, RuleRegistry.disableShulkerTeleport)) {
      event.setCanceled(true);
    }
    if (event.getEntityLiving() instanceof PlayerEntity) {
      //      PlayerEntity player = (PlayerEntity) event.getEntityLiving();
      if (!RuleRegistry.isEnabled(world, RuleRegistry.pearlDamage)) {
        event.setAttackDamage(0);
      }
    }
  }

  /**
   * disablePetFriendlyFire
   */
  @SubscribeEvent
  public void onLivingAttackEvent(LivingAttackEvent event) {
    World world = event.getEntityLiving().world;
    if (RuleRegistry.isEnabled(world, RuleRegistry.disablePetFriendlyFire)
        && event.getSource().getTrueSource() instanceof PlayerEntity) {
      PlayerEntity dmgOwner = (PlayerEntity) event.getSource().getTrueSource();
      //pets!
      if (event.getEntityLiving() instanceof AbstractHorseEntity) {
        //can be tamed
        AbstractHorseEntity horse = (AbstractHorseEntity) event.getEntityLiving();
        if (horse.isTame() && horse.getOwnerUniqueId() != null &&
            horse.getOwnerUniqueId().equals(dmgOwner.getUniqueID())) {
          // do the cancel
          event.setCanceled(true);
        }
      }
      if (event.getEntityLiving() instanceof TameableEntity) {
        //can be tamed
        //        ParrotEntity y;//yep parrot, cat, wolf all extend tameable
        TameableEntity pet = (TameableEntity) event.getEntityLiving();
        if (pet.isTamed() && pet.getOwnerId() != null &&
            pet.getOwnerId().equals(dmgOwner.getUniqueID())) {
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
    World world = event.getEntityLiving().world;
    if (event.getSource() == DamageSource.IN_WALL &&
        !RuleRegistry.isEnabled(world, RuleRegistry.suffocationDamage)) {
      event.setCanceled(true);
    }
    if (event.getSource() == DamageSource.CACTUS &&
        !RuleRegistry.isEnabled(world, RuleRegistry.cactusDamage)) {
      //     
      event.setCanceled(true);
      //      event.setAmount(0); 
    }
    if (event.getSource() == DamageSource.SWEET_BERRY_BUSH &&
        !RuleRegistry.isEnabled(world, RuleRegistry.berryDamage)) {
      event.setCanceled(true);
    }
    // 
    if ((event.getEntityLiving() instanceof PlayerEntity) == false) {
      return;
    }
    PlayerEntity player = (PlayerEntity) event.getEntityLiving();
    if (event.getSource() == DamageSource.FALL
        && RuleRegistry.isEnabled(world, RuleRegistry.doLilypadsBreak)) {
      if (world.getBlockState(player.getPosition()).getBlock() == Blocks.LILY_PAD) {
        world.destroyBlock(player.getPosition(), true, player);
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
    if (event == null || event.getEntity() == null || event.getEntity().world == null) {
      return;
    }
    Entity ent = event.getEntity();
    World world = ent.world;
    if (!RuleRegistry.isEnabled(world, GameRules.MOB_GRIEFING)) {
      //mob griefing not allowed, do nothing
      return;
    }
    // mobGriefing == true, meaning a DEFAULT result will fall back to that and allow the grief
    //check if we want to deny specific mobs
    //if that mobs rule is FALSE then deny it
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingSnowgolem) && ent instanceof SnowGolemEntity) {
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingCreeper) && ent instanceof CreeperEntity) {
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingZombie) && ent instanceof ZombieEntity) {
      //turtle eggs, doors
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingEnderman) && ent instanceof EndermanEntity) {
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingWither) &&
        (ent instanceof WitherEntity || ent instanceof WitherSkullEntity)) {
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingRavager) && ent instanceof RavagerEntity) {
      //break on collide
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingSilverfish) && ent instanceof SilverfishEntity) {
      //entering the stone
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingGhast) && (ent instanceof FireballEntity || ent instanceof GhastEntity)) {//
      // GHAST Fireball 
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingBlaze) && ent instanceof SmallFireballEntity) {
      // blaze  Fireball
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingVillager) && ent instanceof VillagerEntity) {
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
    PlayerEntity player = event.getPlayer();
    World world = player.world;
    //    if (RuleRegistry.isEnabled(world, RuleRegistry.doReduceHeartsOnDeath)
    //        && !player.isCreative()
    //        && player.getMaxHealth() > 2) {
    //      EntityHelpers.incrementMaxHealth(player);
    //    }
    if (RuleRegistry.isEnabled(world, GameRules.KEEP_INVENTORY)) {
      //sub- rules of keep inventory
      if (RuleRegistry.isEnabled(world, RuleRegistry.keepInventoryExperience)) {
        player.experience = 0;
        player.experienceLevel = 0;
        player.experienceTotal = 0;
      }
    }
  }

  /***
   * keepInventoryArmor
   */
  @SubscribeEvent
  public void onPlayerDrops(LivingDropsEvent event) {
    if (event.getEntityLiving() instanceof PlayerEntity == false) {
      return;
    }
    PlayerEntity player = (PlayerEntity) event.getEntityLiving();
    World world = player.world;
    if (RuleRegistry.isEnabled(world, GameRules.KEEP_INVENTORY)) {
      //sub- rules of keep inventory
      if (RuleRegistry.isEnabled(world, RuleRegistry.keepInventoryArmor)) {
        Iterator<ItemStack> i = player.getArmorInventoryList().iterator();
        while (i.hasNext()) {
          ItemStack is = i.next();
          //player.dropItem will drop AFTER DEATH. so
          //          this.drop(player.world, deathPos, is);
          event.getDrops().add(new ItemEntity(world,
              player.getPosX(), player.getPosY(), player.getPosZ(),
              is.copy()));
          is.setCount(0);
        }
      }
    }
  }
}
