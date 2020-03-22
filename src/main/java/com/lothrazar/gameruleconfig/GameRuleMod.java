package com.lothrazar.gameruleconfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.gameruleconfig.setup.ClientProxy;
import com.lothrazar.gameruleconfig.setup.IProxy;
import com.lothrazar.gameruleconfig.setup.ServerProxy;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

// TODO: The value here should match an entry in the META-INF/mods.toml file
// TODO: Also search and replace it in build.gradle
@Mod(GameRuleMod.MODID)
public class GameRuleMod {

  public static final String MODID = "gameruleconfig";
  public static final String certificateFingerprint = "@FINGERPRINT@";
  public static final IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
  public static final Logger LOGGER = LogManager.getLogger();
  public static ConfigManager config;

  public GameRuleMod() {
    // Register the setup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    //only for server starting
    MinecraftForge.EVENT_BUS.register(this);
    config = new ConfigManager(FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));
  }

  private void setup(final FMLCommonSetupEvent event) {}

  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent event) {
    //you probably will not need this 
    World overworld = event.getServer().getWorld(DimensionType.OVERWORLD);
    if (overworld != null) {
      BooleanValue rule = overworld.getGameRules().get(GameRules.KEEP_INVENTORY);//.set(true, event.getServer());
      rule.set(true, overworld.getServer());
      for (GameRuleWrapper entry : ConfigManager.list) {
        //ok
        rule = overworld.getGameRules().get(entry.getReference());
        //    
        rule.set(entry.getDefaultValue(), overworld.getServer());
        LOGGER.info(MODID + ": gamerule updated successfully " + entry.getName() + " -> " + entry.getDefaultValue());
      }
      //
      //      // 
      //      Method m = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "create", boolean.class);
      //      m.setAccessible(true);
      //      try {
      //        GameRules.RuleType<GameRules.BooleanValue> newRule = (RuleType<BooleanValue>) m.invoke(null, false);
      //        RuleKey<BooleanValue> created = GameRules.register("keepExperience", newRule);
      //        LOGGER.error("good RULE ", created);
      //      }
      //      catch (Exception e) {
      //        //
      //        LOGGER.error("BAD RULE ", e);
      //      }
    }
    else {
      //
      LOGGER.info(MODID + ": null world, gamerule set failed");
    }
    //
  }

  @SubscribeEvent
  public static void onFingerprintViolation(FMLFingerprintViolationEvent event) {
    // https://tutorials.darkhax.net/tutorials/jar_signing/
    String source = (event.getSource() == null) ? "" : event.getSource().getName() + " ";
    String msg = MODID + "Invalid fingerprint detected! The file " + source + "may have been tampered with. This version will NOT be supported by the author!";
    System.out.println(msg);
  }
}
