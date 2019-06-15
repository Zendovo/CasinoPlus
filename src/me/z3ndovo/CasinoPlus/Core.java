package me.z3ndovo.CasinoPlus;

import me.z3ndovo.CasinoPlus.Commands.CreateSlots;
import me.z3ndovo.CasinoPlus.Commands.Test;
import me.z3ndovo.CasinoPlus.Files.ConfigManager;
import me.z3ndovo.CasinoPlus.Listeners.EntityClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    public ConfigManager cfgM;

    public void onEnable() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        //Load config files
        loadConfigManager();
        loadConfig();

        //Plugin enable message
        getLogger().info("-------------------------------------------");
        getLogger().info("CasinoPlus has been enabled.");
        getLogger().info("-------------------------------------------");

        //Register Events
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new EntityClick(), this);

        //Register Commands
        this.getCommand("createslots").setExecutor(new CreateSlots());
        this.getCommand("test").setExecutor(new Test());

    }

    public void onDisable() {
        //Plugin disable message
        getLogger().info("-------------------------------------------");
        getLogger().info("CasinoPlus has been disabled.");
        getLogger().info("-------------------------------------------");
    }

    //Load the custom configs
    public void loadConfigManager(){
        cfgM = new ConfigManager();
        cfgM.setup();
        cfgM.saveSlotsData();
        cfgM.reloadSlotsData();

    }

    //Load config.yml
    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}
