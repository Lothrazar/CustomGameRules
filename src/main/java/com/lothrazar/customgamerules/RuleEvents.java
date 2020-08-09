package com.lothrazar.customgamerules;

import java.util.Iterator;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RuleEvents {

  @SubscribeEvent
  public void onLivingUpdateEvent(net.minecraftforge.event.entity.EntityEvent event) {
    if (event.getEntity() instanceof EyeOfEnderEntity) {
      EyeOfEnderEntity eye = (EyeOfEnderEntity) event.getEntity();
      if (RuleRegistry.isEnabled(eye.world, RuleRegistry.doEyesAlwaysBreak)) {
        eye.shatterOrDrop = false;
      }
    }
  }

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
    //      //BUT ALSO this triggers for Creeper entities, etc
    GameRuleMod.LOGGER.info("explosion immd source " + event.getSource().getImmediateSource());
    //      GameRuleMod.LOGGER.info("explosion true source " + event.getSource().getTrueSource());
    //      event.setCanceled(true);
    //    }
  }

  /**
   * 
   * <li>{@link Result#ALLOW} means this instance of mob griefing is allowed.</li>
   * <li>{@link Result#DEFAULT} means the {@code mobGriefing} game rule is used to determine the behaviour.</li>
   * <li>{@link Result#DENY} means this instance of mob griefing is not allowed.</li><br>
   * 
   * @param event
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
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingZombie) && event.getEntity() instanceof ZombieEntity) {
      //turtle eggs, doors
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingEnderman) && event.getEntity() instanceof EndermanEntity) {
      event.setResult(Result.DENY);
      return;
    }
    if (!RuleRegistry.isEnabled(world, RuleRegistry.mobGriefingWither) &&
        (event.getEntity() instanceof WitherEntity || event.getEntity() instanceof WitherSkullEntity)) {
      event.setResult(Result.DENY);
      return;
    }
  }

  //    if (ConfigManager.SNOWGOLEMGRF.get() && event.getEntity() instanceof SnowGolemEntity) {
  //      //
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.SILVERFISHGRF.get() && event.getEntity() instanceof SilverfishEntity) {
  //      //enter stone blocks  
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.RAVAGERGRF.get() && event.getEntity() instanceof RavagerEntity) {
  //      //break on collide
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.FOXGRF.get() && event.getEntity() instanceof FoxEntity) {
  //      //eat berries 
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.GHASTGRF.get() && event.getEntity() instanceof FireballEntity) {
  //      // ghast fireball
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.VILLAGERGRF.get() && event.getEntity() instanceof VillagerEntity) {
  //      // villager farming
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.SHEEPGRF.get() && event.getEntity() instanceof SheepEntity) {
  //      // sheep eat grass
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.BLAZEFBALLGRF.get() && event.getEntity() instanceof SmallFireballEntity) {
  //      // blaze fireball
  //      event.setResult(Result.ALLOW);
  //    }
  //  }
  // 
  //
  @SubscribeEvent
  public void onPlayerDeath(PlayerEvent.Clone event) {
    if (!event.isWasDeath()) {
      return;
    }
    BlockPos deathPos = event.getOriginal().getPosition();
    PlayerEntity player = event.getPlayer();
    World world = player.world;
    if (RuleRegistry.isEnabled(world, GameRules.KEEP_INVENTORY)) {
      //sub- rules of keep inventory
      if (RuleRegistry.isEnabled(world, RuleRegistry.keepInventoryExperience)) {
        player.experience = 0;
        player.experienceLevel = 0;
        player.experienceTotal = 0;
      }
    }
  }

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
          GameRuleMod.LOGGER.info("set count zero" + is);
          is.setCount(0);
        }
      }
    }
  }
  //    if (!world.isRemote && event.isWasDeath()
  //        && !ConfigManager.KEEP_ARMOR.get()) {
  //      boolean keepInv = world.getGameRules().get(GameRules.KEEP_INVENTORY).get();
  //      //exp zero
  //      if (keepInv) {
  //        // do not keep armor
  //        Iterator<ItemStack> i = player.getArmorInventoryList().iterator();
  //        while (i.hasNext()) {
  //          ItemStack is = i.next();
  //          //player.dropItem will drop AFTER DEATH. so
  //          this.drop(player.world, deathPos, is);
  //        }
  //      }
  //    }
  //    if (!world.isRemote && event.isWasDeath()
  //        && !ConfigManager.KEEP_WEAPONS.get()) {
  //      boolean keepInv = world.getGameRules().get(GameRules.KEEP_INVENTORY).get();
  //      //exp zero
  //      if (keepInv) {
  //        // do not keep armor
  //        Iterator<ItemStack> i = player.getHeldEquipment().iterator();
  //        while (i.hasNext()) {
  //          ItemStack is = i.next();
  //          this.drop(player.world, deathPos, is);
  //        }
  //      }

  public void drop(World world, BlockPos p, ItemStack i) {
    ItemEntity e = new ItemEntity(world, p.getX(), p.getY(), p.getZ(), i.copy());
    if (!world.isRemote && !i.isEmpty()) {
      world.addEntity(e);
    }
    i.setCount(0);
  }
}
