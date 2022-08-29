package com.lothrazar.customgamerules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GameRuleMod.MODID)
public class GameRuleMod {

  public static final String MODID = "customgamerules";
  public static final Logger LOGGER = LogManager.getLogger();

  public GameRuleMod() {
    MinecraftForge.EVENT_BUS.register(new com.lothrazar.customgamerules.event.RuleEvents());
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
  }

  private void setup(final FMLCommonSetupEvent event) {
    RuleRegistry.setup();
  }

  public static void info(String s) {
    // LOGGER.info(s);
  }
}
