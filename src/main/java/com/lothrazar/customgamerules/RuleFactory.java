package com.lothrazar.customgamerules;

import java.lang.reflect.Method;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.GameRules.Category;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraft.world.GameRules.RuleType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class RuleFactory {

  @SuppressWarnings("unchecked")
  public static RuleKey<BooleanValue> createBoolean(String id, boolean defaultVal, Category cat) {
    try {
      //access transformers cfg SHULD make this create public
      //      BufferBuilder.
      Method m = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "func_223567_b", boolean.class);
      m.setAccessible(true);
      RuleType<BooleanValue> b = (RuleType<BooleanValue>) m.invoke(null, defaultVal);
      RuleKey<BooleanValue> rule = GameRules.func_234903_a_(id, cat, b);
      return rule;
    }
    catch (Exception e) {
      GameRuleMod.LOGGER.error("Create rule fail", e);
    }
    return null;
  }
}
