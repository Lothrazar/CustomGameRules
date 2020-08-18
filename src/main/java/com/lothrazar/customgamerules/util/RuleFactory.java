package com.lothrazar.customgamerules.util;

import java.lang.reflect.Method;
import com.lothrazar.customgamerules.GameRuleMod;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.GameRules.Category;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraft.world.GameRules.RuleType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class RuleFactory {

  public static int count = 0;

  @SuppressWarnings("unchecked")
  public static RuleKey<BooleanValue> createBoolean(String id, boolean defaultVal, Category cat) {
    try {
      //      GameRules.BooleanValue.create(true);
      //access transformers cfg SHULD make this create public
      Method m = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "func_223567_b", boolean.class);
      m.setAccessible(true);
      RuleType<BooleanValue> b = (RuleType<BooleanValue>) m.invoke(null, defaultVal);
      RuleKey<BooleanValue> rule = GameRules.func_234903_a_(id, cat, b);
      count++;
      return rule;
    }
    catch (Exception e) {
      GameRuleMod.LOGGER.error("Create rule fail", e);
    }
    return null;
  }
}
