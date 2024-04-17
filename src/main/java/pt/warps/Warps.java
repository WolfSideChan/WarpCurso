package pt.warps;

import org.bukkit.plugin.java.JavaPlugin;
import pt.warps.commands.WarpCommand;
import pt.warps.managers.WarpManager;

public final class Warps extends JavaPlugin {
    
    private static Warps instance;
    private WarpManager warpManager;

    @Override
    public void onEnable() {
        instance = this;
        loadConfiguration();
        warpManager = new WarpManager();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }

    public static Warps getInstance() {
        return instance;
    }

    private void registerCommands() {
        getCommand("warp").setExecutor(new WarpCommand());
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }
}
