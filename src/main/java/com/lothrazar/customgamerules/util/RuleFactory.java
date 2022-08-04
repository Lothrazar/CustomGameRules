package com.lothrazar.customgamerules.util;

import java.lang.reflect.Method;
import com.lothrazar.customgamerules.GameRuleMod;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;
//import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class RuleFactory {

  public static int count = 0;

  @SuppressWarnings("unchecked")
  public static Key<BooleanValue> createBoolean(String id, boolean defaultVal, Category cat) {
    //access transformers cfg SHULD make this create public
//      Type<BooleanValue> ruleTypeBoolean2 = GameRules.BooleanValue.create(true); // this works if AT works
    try {
      Method m = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "m_46250_", boolean.class);
//      m.setAccessible(true);
      Type<BooleanValue> ruleTypeBoolean = (Type<BooleanValue>) m.invoke(null, defaultVal);
      Key<BooleanValue> rule = GameRules.register(id, cat, ruleTypeBoolean);
      count++;
      return rule;
    }
    catch (Exception e) {
      GameRuleMod.LOGGER.error("Create gamerule error", e);
    }
    return null;
  }
}
