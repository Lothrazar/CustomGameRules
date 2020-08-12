package com.lothrazar.customgamerules;

import java.util.Iterator;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RuleEvents {

  @SubscribeEvent
  public void onPlayerXpEvent(PlayerXpEvent event) {
    if (RuleRegistry.isEnabled(event.getEntity().world, RuleRegistry.doInstantExp)) {
      //reset XP on pickup
      if (event.getPlayer().xpCooldown > 0)
        event.getPlayer().xpCooldown = 0;
    }
  }

  @SubscribeEvent
  public void onPlayerContainerEvent(LivingEntityUseItemEvent.Tick event) {
    if (event.getItem().isFood() && RuleRegistry.isEnabled(event.getEntity().world, RuleRegistry.doInstantEating)
        && event.getDuration() > 0) {
      event.setDuration(1);//dont set to zero, then it goes -1 and breks
    }
  }

  @SubscribeEvent
  public void onEntityInteract(EntityInteract event) {
    if (!RuleRegistry.isEnabled(event.getWorld(), RuleRegistry.doVillagerTrading)
        && event.getEntity() instanceof PlayerEntity
        && event.getTarget() instanceof VillagerEntity) {
      //  
      event.setCanceled(true);
      event.setResult(Result.DENY);
    }
  }

  /***
   * doMobItemPickup
   * 
   */
  @SubscribeEvent
  public void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
    if (!RuleRegistry.isEnabled(event.getWorld(), RuleRegistry.doMobItemPickup)
        && event.getEntity() instanceof MobEntity) {
      MobEntity mob = (MobEntity) event.getEntity();
      if (mob.canPickUpLoot()) {
        mob.setCanPickUpLoot(false);
        //   GameRuleMod.LOGGER.info("After edit mob with pickup " + mob.canPickUpLoot());
      }
    }
  }

  /***
   * doEyesAlwaysBreak
   * 
   */
  @SubscribeEvent
  public void onLivingUpdateEvent(EntityEvent event) {
    if (event.getEntity() instanceof EyeOfEnderEntity) {
      EyeOfEnderEntity eye = (EyeOfEnderEntity) event.getEntity();
      if (eye.shatterOrDrop &&
          RuleRegistry.isEnabled(eye.world, RuleRegistry.doEyesAlwaysBreak)) {
        eye.shatterOrDrop = false;
      }
    }
  }

  /***
   * pearlDamage
   * 
   */
  @SubscribeEvent
  public void onEnderTeleportEvent(EnderTeleportEvent event) {
    if ((event.getEntityLiving() instanceof PlayerEntity) == false) {
      return;
    }
    PlayerEntity player = (PlayerEntity) event.getEntityLiving();
    World world = player.world;
    if (!RuleRegistry.isEnabled(world, RuleRegistry.pearlDamage)) {
      event.setAttackDamage(0);
    }
  }

  /***
   * <pre>
    * berryDamage
   * cactusDamage
   * doLilypadsBreak
   * suffocationDamage
   * </pre>
   * 
   * 
   */
  @SubscribeEvent
  public void onLivingDamageEvent(LivingDamageEvent event) {
    if ((event.getEntityLiving() instanceof PlayerEntity) == false) {
      return;
    }
    PlayerEntity player = (PlayerEntity) event.getEntityLiving();
    World world = player.world;
    if (event.getSource() == DamageSource.FALL //&&
    ) {
      System.out.println("lillypad " + RuleRegistry.isEnabled(world, RuleRegistry.doLilypadsBreak));
      if (world.getBlockState(player.getPosition()).getBlock() == Blocks.LILY_PAD) {
        world.destroyBlock(player.getPosition(), true, player);
        event.setAmount(0);
      }
    }
    if (event.getSource() == DamageSource.IN_WALL &&
        !RuleRegistry.isEnabled(world, RuleRegistry.suffocationDamage)) {
      event.setCanceled(true);
    }
    if (event.getSource() == DamageSource.CACTUS &&
        !RuleRegistry.isEnabled(world, RuleRegistry.cactusDamage)) {
      //      GameRuleMod.LOGGER.info("cactus");
      event.setCanceled(true);
      //      event.setAmount(0); 
    }
    if (event.getSource() == DamageSource.SWEET_BERRY_BUSH &&
        !RuleRegistry.isEnabled(world, RuleRegistry.berryDamage)) {
      event.setCanceled(true);
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
    Entity ent = event.getEntity();
    World world = ent.world;
    if (!RuleRegistry.isEnabled(world, GameRules.MOB_GRIEFING)) {
      //mob griefing not allowed, do nothing
      return;
    }
    // mobGriefing == true, meaning a DEFAULT result will fall back to that and allow the grief
    //check if we want to deny specific mobs
    //if that mobs rule is FALSE then deny it
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
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingSilverfish) && ent instanceof SilverfishEntity) {
      //entering the stone
      event.setResult(Result.DENY);
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingGhast) && ent instanceof FireballEntity) {
      // GHAST Fireball
      event.setResult(Result.DENY);
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingBlaze) && ent instanceof SmallFireballEntity) {
      // GHAST Fireball
      event.setResult(Result.DENY);
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingVillager) && ent instanceof VillagerEntity) {
      // farming
      event.setResult(Result.DENY);
    }
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
          //          GameRuleMod.LOGGER.info("set count zero" + is);
          is.setCount(0);
        }
      }
    }
  }
  //  public void drop(World world, BlockPos p, ItemStack i) {
  //    ItemEntity e = new ItemEntity(world, p.getX(), p.getY(), p.getZ(), i.copy());
  //    if (!world.isRemote && !i.isEmpty()) {
  //      world.addEntity(e);
  //    }
  //    i.setCount(0);
  //  }
}
