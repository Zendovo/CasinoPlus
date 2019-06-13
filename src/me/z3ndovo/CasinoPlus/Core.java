package me.z3ndovo.CasinoPlus;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.z3ndovo.CasinoPlus.Files.SlotsData;
import me.z3ndovo.CasinoPlus.Listeners.EntityClick;

public class Core extends JavaPlugin {

    private SlotsData slotsData;

    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        @SuppressWarnings("unused")
        FileConfiguration config = getConfig();
        getLogger().info("-------------------------------------------");
        getLogger().info("Casino plugin has been enabled.");
        getLogger().info("-------------------------------------------");

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new EntityClick(this), this);

        if(!(new File(getDataFolder(), "config.yml")).exists()) {
            saveDefaultConfig();
        }

        this.slotsData = new SlotsData(this);
    }

    public void onDisable() {
        getLogger().info("-------------------------------------------");
        getLogger().info("Casino plugin has been disabled.");
        getLogger().info("-------------------------------------------");

        slotsData.save();
    }

}
