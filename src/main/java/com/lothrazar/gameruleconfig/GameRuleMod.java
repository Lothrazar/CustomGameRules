package com.lothrazar.gameruleconfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.gameruleconfig.setup.ClientProxy;
import com.lothrazar.gameruleconfig.setup.IProxy;
import com.lothrazar.gameruleconfig.setup.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

// TODO: The value here should match an entry in the META-INF/mods.toml file
// TODO: Also search and replace it in build.gradle
@Mod(GameRuleMod.MODID)
public class GameRuleMod {

  public static final String MODID = "gameruleconfig";
  public static final IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
  public static final Logger LOGGER = LogManager.getLogger();
  public static ConfigManager config;

  public GameRuleMod() {
    MinecraftForge.EVENT_BUS.register(new RuleEvents());
    config = new ConfigManager(FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));
  }
}
