package me.z3ndovo.CasinoPlus.Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.z3ndovo.CasinoPlus.Core;

public class ConfigManager {

    protected Core plugin = Core.getPlugin(Core.class);
    private File file;
    protected FileConfiguration config;

    public ConfigManager(Core plugin, String fileName) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

}
