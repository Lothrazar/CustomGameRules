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
    //access transformers cfg SHULD make this create public
    //    RuleType<BooleanValue> test = GameRules.RuleType.func_223571_a(defaultVal);
    try {
      //      test = new GameRules.BooleanValue.c 
      Method m = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "func_223571_a", boolean.class);
      m.setAccessible(true);
      RuleType<BooleanValue> ruleTypeBoolean = (RuleType<BooleanValue>) m.invoke(null, defaultVal);
      //func_234903_a_ == register
      RuleKey<BooleanValue> rule = GameRules.register(id, cat, ruleTypeBoolean);
      count++;
      return rule;
    }
    catch (Exception e) {
      GameRuleMod.LOGGER.error("Create rule fail", e);
    }
    return null;
  }
}
