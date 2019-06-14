package me.z3ndovo.CasinoPlus;

import me.z3ndovo.CasinoPlus.Commands.CreateSlots;
import me.z3ndovo.CasinoPlus.Commands.Test;
import me.z3ndovo.CasinoPlus.Files.SlotsData;
import me.z3ndovo.CasinoPlus.Listeners.EntityClick;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Core extends JavaPlugin {

    private static Core instance;
    private SlotsData slotsData;

    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        this.getCommand("createslots").setExecutor(new CreateSlots());
        this.getCommand("test").setExecutor(new Test());

        FileConfiguration config = getConfig();
        getLogger().info("-------------------------------------------");
        getLogger().info("CasinoPlus has been enabled.");
        getLogger().info("-------------------------------------------");

        setInstance(this);

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new EntityClick(this), this);

        if(!(new File(getDataFolder(), "config.yml")).exists()) {
            saveDefaultConfig();
        }

        this.slotsData = new SlotsData(this);
    }

    public void onDisable() {
        getLogger().info("-------------------------------------------");
        getLogger().info("CasinoPlus has been disabled.");
        getLogger().info("-------------------------------------------");

        slotsData.save();
    }

    public static Core getInstance() {
        return instance;
    }

    private static void setInstance(Core instance) {
        Core.instance = instance;
    }

}
