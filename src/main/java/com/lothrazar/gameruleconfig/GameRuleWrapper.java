package com.lothrazar.gameruleconfig;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraftforge.common.ForgeConfigSpec;

public class GameRuleWrapper {

  private GameRules.RuleKey<GameRules.BooleanValue> reference;
  ForgeConfigSpec.BooleanValue config;
  private boolean defaultValue;

  public GameRuleWrapper(RuleKey<BooleanValue> reference, boolean defaultValue) {
    super();
    this.setReference(reference);
    this.setDefaultValue(defaultValue);
  }

  public String getName() {
    return getReference().getName();
  }

  public boolean getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(boolean defaultValue) {
    this.defaultValue = defaultValue;
  }

  public GameRules.RuleKey<GameRules.BooleanValue> getReference() {
    return reference;
  }

  public void setReference(GameRules.RuleKey<GameRules.BooleanValue> reference) {
    this.reference = reference;
  }
}
