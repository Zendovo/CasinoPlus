package me.z3ndovo.CasinoPlus;

import me.z3ndovo.CasinoPlus.Commands.CreateSlots;
import me.z3ndovo.CasinoPlus.Commands.ResetSlots;
import me.z3ndovo.CasinoPlus.Commands.Test;
import me.z3ndovo.CasinoPlus.Files.ConfigManager;
import me.z3ndovo.CasinoPlus.Listeners.EntityClick;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    public ConfigManager cfgM;
    public static Economy econ;

    public void onEnable() {

        //Check economy
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Check plugin folder
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
        this.getCommand("resetslots").setExecutor(new ResetSlots());

    }

    public void onDisable() {
        //Plugin disable message
        getLogger().info("-------------------------------------------");
        getLogger().info("CasinoPlus has been disabled.");
        getLogger().info("-------------------------------------------");
    }

    //Setup Economy i.e. Vault
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
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
