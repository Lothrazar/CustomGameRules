package com.lothrazar.customgamerules;

import com.lothrazar.customgamerules.event.CustomRuleEvents;
import com.lothrazar.customgamerules.rules.RuleRegistry;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModGameRule.MODID)
public class ModGameRule {

  public static final String MODID = "customgamerules";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModGameRule(IEventBus modBus) {
    new CustomRuleEvents();
    modBus.addListener(this::onRegister);
    modBus.addListener(RuleRegistry::onRegisterPayloads);
  }

  // Game rules are now a real, freezable registry (minecraft:game_rule) instead of the old
  // ad-hoc GameRules.Key system, so registration has to happen during RegisterEvent for that
  // registry, not FMLCommonSetupEvent - by then the registry is already frozen.
  private void onRegister(final RegisterEvent event) {
    if (event.getRegistryKey().equals(Registries.GAME_RULE)) {
      RuleRegistry.setup();
    }
  }
}
