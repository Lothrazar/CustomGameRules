package com.lothrazar.customgamerules;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RuleEvents {

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
    //      GameRuleMod.LOGGER.info("explosion immd source " + event.getSource().getImmediateSource());
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
  //  @SubscribeEvent
  //  public void onEntityMobGriefingEvent(EntityMobGriefingEvent event) {
  //    //if rule is false, this event does not trigger, so its always true
  //    boolean ruleIsTrue = event.getEntity().world.getGameRules().get(GameRules.MOB_GRIEFING).get();
  //    //true is default, allows destruction
  //    //so if true do7 nothing
  //    if (ruleIsTrue) {
  //      return;//let default take over and rely on rule
  //    }
  //    //    
  //    //    
  //    //    Rabit Entity: raidFarmGoal eats carrots
  //    //    
  //    //    
  //    if (ConfigManager.ZOMBIEGRF.get() && event.getEntity() instanceof ZombieEntity) {
  //      //turtle eggs, doors
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.RABBITGRF.get() && event.getEntity() instanceof RabbitEntity) {
  //      event.setResult(Result.ALLOW);
  //    }
  //    //    
  //    //    
  //    //if rule set FALSE
  //    // then we have list of entities we ALLOW to grief so we pick only what is allowed
  //    if (ConfigManager.ENDERGRF.get() && event.getEntity() instanceof EndermanEntity) {
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.GRIEFCREEPER.get() && event.getEntity() instanceof CreeperEntity) {
  //      event.setResult(Result.ALLOW);
  //    }
  //    if (ConfigManager.WITHERGRF.get() && (event.getEntity() instanceof WitherEntity || event.getEntity() instanceof WitherSkullEntity)) {
  //      ////
  //      event.setResult(Result.ALLOW);
  //    }
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
  //  @SubscribeEvent
  //  public void onPlayerDeath(PlayerEvent.Clone event) {
  //    BlockPos deathPos = event.getOriginal().getPosition();
  //    PlayerEntity player = event.getPlayer();
  //    World world = player.world;
  //    if (!world.isRemote && event.isWasDeath()
  //        && !ConfigManager.KEEP_EXP.get()) {
  //      boolean keepInv = world.getGameRules().get(GameRules.KEEP_INVENTORY).get();
  //      //exp zero
  //      if (keepInv) {
  //        player.experience = 0;
  //        player.experienceLevel = 0;
  //        player.experienceTotal = 0;
  //      }
  //    }
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
  //    }
  //  }
  public void drop(World world, BlockPos p, ItemStack i) {
    ItemEntity e = new ItemEntity(world, p.getX(), p.getY(), p.getZ(), i.copy());
    if (!world.isRemote && !i.isEmpty()) {
      world.addEntity(e);
    }
    i.setCount(0);
  }
}
