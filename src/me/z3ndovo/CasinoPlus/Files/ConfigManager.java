package me.z3ndovo.CasinoPlus.Files;

import me.z3ndovo.CasinoPlus.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("SpellCheckingInspection")
public class ConfigManager {

    private Core plugin = Core.getPlugin(Core.class);
    private Messages messages = new Messages();

    // Files & File Configs Here
    public FileConfiguration slotsdatacfg;
    public File slotsfile;

    public FileConfiguration msgcfg;
    public File msgfile;
    // --------------------------

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        //Create file object
        slotsfile = new File(plugin.getDataFolder(), "slotsdata.yml");
        msgfile = new File(plugin.getDataFolder(), "messages.yml");

        //Check if exists
        if (!slotsfile.exists()) {
            //Create file
            try {
                slotsfile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The slotsdata.yml file has been created");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender()
                        .sendMessage(ChatColor.RED + "Could not create the slotsdata.yml file");
            }
        }

        if (!msgfile.exists()) {
            //Create file
            try {
                msgfile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The messages.yml file has been created");
                messages.setupMessages();

            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender()
                        .sendMessage(ChatColor.RED + "Could not create the messages.yml file");
            }
        }

        //Load file
        slotsdatacfg = YamlConfiguration.loadConfiguration(slotsfile);
        msgcfg = YamlConfiguration.loadConfiguration(msgfile);

        if(msgcfg.getInt("version") != 1) {
            messages.setupMessages();

        }
    }

    //Get the config
    public FileConfiguration getSlotsData() {
        return slotsdatacfg;
    }
    public FileConfiguration getMsg() {
        return msgcfg;
    }

    //Save the config
    public void saveSlotsData() {
        try {
            slotsdatacfg.save(slotsfile);
            //Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The slotsdata.yml file has been saved");

        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the slotsdata.yml file");
        }
    }

    public void saveMsg() {
        try {
            msgcfg.save(msgfile);
            //Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The messages.yml file has been saved");

        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the messages.yml file");
        }
    }

    //Reload the config
    public void reloadSlotsData() {
        slotsdatacfg = YamlConfiguration.loadConfiguration(slotsfile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The slotsdata.yml file has been reloaded");

    }

    public void reloadMsgs() {
        msgcfg = YamlConfiguration.loadConfiguration(msgfile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The messages.yml file has been reloaded");

    }
}
