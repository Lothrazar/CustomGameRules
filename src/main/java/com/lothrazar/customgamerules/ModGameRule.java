package com.lothrazar.customgamerules;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.customgamerules.event.CustomRuleEvents;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModGameRule.MODID)
public class ModGameRule {

  public static final String MODID = "customgamerules";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModGameRule() {
     IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    new CustomRuleEvents();
    bus.addListener(this::setup);
  }

  private void setup(final FMLCommonSetupEvent event) {
    RuleRegistry.setup();
  }

}
