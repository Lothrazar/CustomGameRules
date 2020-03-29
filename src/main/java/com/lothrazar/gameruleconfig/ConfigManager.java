package com.lothrazar.gameruleconfig;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ConfigManager {

  public static List<GameRuleBoolWrapper> boolList = new ArrayList<>();
  public static List<GameRuleIntWrapper> intList = new ArrayList<>();
  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
  private static ForgeConfigSpec COMMON_CONFIG;
  public static BooleanValue KEEP_EXP;
  public static BooleanValue KEEP_ARMOR;
  static BooleanValue KEEP_WEAPONS;
  static BooleanValue GRIEFCREEPER;
  static BooleanValue WITHERGRF;
  static BooleanValue SNOWGOLEMGRF;
  static BooleanValue SILVERFISHGRF;
  static BooleanValue RAVAGERGRF;
  static BooleanValue FOXGRF;
  static BooleanValue GHASTGRF;
  static BooleanValue VILLAGERGRF;
  static BooleanValue SHEEPGRF;
  static BooleanValue BLAZEFBALLGRF;
  static BooleanValue ENDERGRF;
  static BooleanValue ZOMBIEGRF;
  static BooleanValue RABBITGRF;
  static {
    initConfig();
  }

  private static void initConfig() {
    COMMON_BUILDER.comment("General settings").push(GameRuleMod.MODID);
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_FIRE_TICK, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.MOB_GRIEFING, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.KEEP_INVENTORY, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_MOB_SPAWNING, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_MOB_LOOT, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_TILE_DROPS, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_ENTITY_DROPS, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.COMMAND_BLOCK_OUTPUT, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.NATURAL_REGENERATION, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_DAYLIGHT_CYCLE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.LOG_ADMIN_COMMANDS, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.SHOW_DEATH_MESSAGES, true));
    intList.add(new GameRuleIntWrapper(GameRules.RANDOM_TICK_SPEED, 3));
    boolList.add(new GameRuleBoolWrapper(GameRules.SEND_COMMAND_FEEDBACK, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.REDUCED_DEBUG_INFO, false));
    // trigger internal
    boolList.add(new GameRuleBoolWrapper(GameRules.SPECTATORS_GENERATE_CHUNKS, false));
    intList.add(new GameRuleIntWrapper(GameRules.SPAWN_RADIUS, 10));
    boolList.add(new GameRuleBoolWrapper(GameRules.DISABLE_ELYTRA_MOVEMENT_CHECK, true));
    intList.add(new GameRuleIntWrapper(GameRules.MAX_ENTITY_CRAMMING, 24));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_WEATHER_CYCLE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_LIMITED_CRAFTING, false));
    intList.add(new GameRuleIntWrapper(GameRules.MAX_COMMAND_CHAIN_LENGTH, 65536));
    boolList.add(new GameRuleBoolWrapper(GameRules.ANNOUNCE_ADVANCEMENTS, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DISABLE_RAIDS, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_INSOMNIA, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_IMMEDIATE_RESPAWN, false));
    //
    boolList.add(new GameRuleBoolWrapper(GameRules.DROWNING_DAMAGE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.FALL_DAMAGE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.FIRE_DAMAGE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.field_230127_D_, true));//patrol
    boolList.add(new GameRuleBoolWrapper(GameRules.field_230128_E_, true));//trader
    for (GameRuleBoolWrapper rule : boolList) {
      rule.config = COMMON_BUILDER.comment("Default value for this gamerule")
          .define("default." + rule.getName(),
              rule.getDefaultValue());
    }
    for (GameRuleIntWrapper rule : intList) {
      rule.config = COMMON_BUILDER.comment("Default value for this gamerule")
          .defineInRange("default." + rule.getName(),
              rule.getDefaultValue(), 0, 999999999);
    }
    KEEP_EXP = COMMON_BUILDER.comment("If the keepInventory gamerule is true"
        + " and keepExperience is false, you no longer keep any experience it is gone on death.")
        .define("change." + GameRules.KEEP_INVENTORY.getName() + ".keepExperience", false);
    KEEP_ARMOR = COMMON_BUILDER.comment("If the keepInventory gamerule is true"
        + " and keepArmor is false, you no longer keep any armor it dropped on death.")
        .define("change." + GameRules.KEEP_INVENTORY.getName() + ".keepArmor", false);
    KEEP_WEAPONS = COMMON_BUILDER.comment("If the keepInventory gamerule is true"
        + " and keepArmor is false, you no longer keep any armor it dropped on death.")
        .define("change." + GameRules.KEEP_INVENTORY.getName() + ".keepWeapons", false);
    ////////////////////
    ENDERGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow enderman block pickup to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".enderman", false);
    GRIEFCREEPER = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow creeper explosions to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".creeper", false);
    WITHERGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow wither and projectiles to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".wither", false);
    SNOWGOLEMGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow snow golems to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".snowgolem", true);
    SILVERFISHGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow silverfish (entering stone to make infected stone) to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".silverfish", true);
    RAVAGERGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow ravagers (in the raids) to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".ravager", false);
    FOXGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow foxes eating berries to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".fox", true);
    GHASTGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow ghasts projectiles to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".ghast", false);
    VILLAGERGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow villagers (farming) to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".villager", true);
    SHEEPGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow sheep (eating grass) to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".sheep", true);
    BLAZEFBALLGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow blaze (projectiles / setting fire) to bypass gamerule and affect the world.")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".blaze", true);
    ZOMBIEGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow zombies to bypass gamerule and affect the world (for example doors, turtle eggs).")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".zombie", true);
    RABBITGRF = COMMON_BUILDER.comment("If the mobGriefing gamerule is false and this is true, "
        + "it will allow rabbits to bypass gamerule and affect the world (for example, raiding carrot farms and eating them).")
        .define("change." + GameRules.MOB_GRIEFING.getName() + ".rabbit", true);
    COMMON_BUILDER.pop();
    COMMON_CONFIG = COMMON_BUILDER.build();
  }

  public ConfigManager(Path path) {
    final CommentedFileConfig configData = CommentedFileConfig.builder(path)
        .sync()
        .autosave()
        .writingMode(WritingMode.REPLACE)
        .build();
    configData.load();
    COMMON_CONFIG.setConfig(configData);
  }
}
