package com.lothrazar.gameruleconfig;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigManager {

  public static List<GameRuleWrapper> list = new ArrayList<>();
  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
  private static ForgeConfigSpec COMMON_CONFIG;
  static {
    initConfig();
  }

  private static void initConfig() {
    COMMON_BUILDER.comment("General settings").push(GameRuleMod.MODID);
    list.add(new GameRuleWrapper(GameRules.DO_FIRE_TICK, false));
    list.add(new GameRuleWrapper(GameRules.MOB_GRIEFING, false));
    list.add(new GameRuleWrapper(GameRules.KEEP_INVENTORY, false));
    list.add(new GameRuleWrapper(GameRules.DO_MOB_SPAWNING, false));
    list.add(new GameRuleWrapper(GameRules.DO_MOB_LOOT, false));
    list.add(new GameRuleWrapper(GameRules.DO_TILE_DROPS, false));
    list.add(new GameRuleWrapper(GameRules.DO_ENTITY_DROPS, false));
    list.add(new GameRuleWrapper(GameRules.COMMAND_BLOCK_OUTPUT, false));
    list.add(new GameRuleWrapper(GameRules.NATURAL_REGENERATION, false));
    list.add(new GameRuleWrapper(GameRules.DO_DAYLIGHT_CYCLE, false));
    list.add(new GameRuleWrapper(GameRules.LOG_ADMIN_COMMANDS, false));
    list.add(new GameRuleWrapper(GameRules.SHOW_DEATH_MESSAGES, false));
    //  randomTickSpeed = 3
    list.add(new GameRuleWrapper(GameRules.SEND_COMMAND_FEEDBACK, false));
    list.add(new GameRuleWrapper(GameRules.REDUCED_DEBUG_INFO, false));
    // trigger internal
    list.add(new GameRuleWrapper(GameRules.SPECTATORS_GENERATE_CHUNKS, false));
    //  spawnRadius = 10
    list.add(new GameRuleWrapper(GameRules.DISABLE_ELYTRA_MOVEMENT_CHECK, false));
    // maxEntityCramming = 24
    list.add(new GameRuleWrapper(GameRules.DO_WEATHER_CYCLE, false));
    list.add(new GameRuleWrapper(GameRules.DO_LIMITED_CRAFTING, false));
    //maxCommandChainLength = 65536
    list.add(new GameRuleWrapper(GameRules.ANNOUNCE_ADVANCEMENTS, false));
    list.add(new GameRuleWrapper(GameRules.DISABLE_RAIDS, false));
    list.add(new GameRuleWrapper(GameRules.DO_INSOMNIA, false));
    list.add(new GameRuleWrapper(GameRules.DO_IMMEDIATE_RESPAWN, false));
    //
    list.add(new GameRuleWrapper(GameRules.DROWNING_DAMAGE, false));
    list.add(new GameRuleWrapper(GameRules.FALL_DAMAGE, false));
    list.add(new GameRuleWrapper(GameRules.FIRE_DAMAGE, false));
    list.add(new GameRuleWrapper(GameRules.field_230127_D_, false));//patrol
    list.add(new GameRuleWrapper(GameRules.field_230128_E_, false));//trader
    for (GameRuleWrapper rule : list) {
      rule.config = COMMON_BUILDER.comment("Default value for this gamerule")
          .define("default." + rule.getName(),
              rule.getDefaultValue());
    }
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
