package com.lothrazar.customgamerules;

import com.lothrazar.customgamerules.event.CustomRuleEvents;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModGameRule.MODID)
public class ModGameRule {

  public static final String MODID = "customgamerules";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModGameRule(IEventBus modBus) {
    new CustomRuleEvents();
    modBus.addListener(this::setup);
    modBus.addListener(RuleRegistry::onRegisterPayloads);
  }

  private void setup(final FMLCommonSetupEvent event) {
    RuleRegistry.setup();
  }
}
