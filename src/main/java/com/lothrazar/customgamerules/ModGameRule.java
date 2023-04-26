package com.lothrazar.customgamerules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.customgamerules.event.CustomRuleEvents;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModGameRule.MODID)
public class ModGameRule {

  public static final String MODID = "customgamerules";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModGameRule() {
    new CustomRuleEvents();
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
  }

  private void setup(final FMLCommonSetupEvent event) {
    RuleRegistry.setup();
  }

  public static void info(String s) {
    //    LOGGER.info(s);
  }
}
