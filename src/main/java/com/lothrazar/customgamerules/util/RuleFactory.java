package com.lothrazar.customgamerules.util;

import java.lang.reflect.Method;

import com.lothrazar.customgamerules.GameRuleMod;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;
//import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.commons.codec.language.bm.RuleType;

public class RuleFactory {

  public static int count = 0;

  @SuppressWarnings("unchecked")
  public static Key<BooleanValue> createBoolean(String id, boolean defaultVal, Category cat) {
    //access transformers cfg SHULD make this create public
//    RuleType<BooleanValue> test = GameRules.RuleType.access$000(defaultVal);
    try {
      //      test = new GameRules.BooleanValue.c 
//      Method m = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "access$000", boolean.class);
//      m.setAccessible(true);
//      Type<BooleanValue> ruleTypeBoolean = (Type<BooleanValue>) m.invoke(null, defaultVal);
      //register == register
      Key<BooleanValue> rule = GameRules.register(id, cat, GameRules.BooleanValue.create(true));
      count++;
      return rule;
    }
    catch (Exception e) {
      GameRuleMod.LOGGER.error("Create gamerule error", e);
    }
    return null;
  }
}
