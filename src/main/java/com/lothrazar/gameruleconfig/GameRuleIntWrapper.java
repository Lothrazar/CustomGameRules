package com.lothrazar.gameruleconfig;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.IntegerValue;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraftforge.common.ForgeConfigSpec;

public class GameRuleIntWrapper {

  private GameRules.RuleKey<GameRules.IntegerValue> reference;
  ForgeConfigSpec.IntValue config;
  private int defaultValue;

  public GameRuleIntWrapper(RuleKey<IntegerValue> reference, int defaultValue) {
    super();
    this.setReference(reference);
    this.setDefaultValue(defaultValue);
  }

  public String getName() {
    return getReference().getName();
  }

  public int getConfigValue() {
    return config.get();
  }

  public void setDefaultValue(int defaultValue) {
    this.defaultValue = defaultValue;
  }

  public GameRules.RuleKey<GameRules.IntegerValue> getReference() {
    return reference;
  }

  public void setReference(GameRules.RuleKey<GameRules.IntegerValue> reference) {
    this.reference = reference;
  }

  public int getDefaultValue() {
    return this.defaultValue;
  }
}
