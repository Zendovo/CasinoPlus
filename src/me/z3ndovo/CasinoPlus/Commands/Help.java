package me.z3ndovo.CasinoPlus.Commands;

import me.z3ndovo.CasinoPlus.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class Help {

    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;
    private FileConfiguration messages;

    public boolean msg(CommandSender sender, Command cmd, String label, String[] args) {
        this.slotsData = plugin.cfgM.getSlotsData();
        this.messages = plugin.cfgM.getMsg();

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m--------------------&c&lCasinoPlus&7&m--------------------"));

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lSlots:"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/casinoplus &eslots create"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Create a new slot machine"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/casinoplus &eslots reset <name>"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Reset a damaged slot machine with the new values from slotsdata.yml"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lMisc"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/casinoplus &ehelp"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Shows this help menu"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------------------------------------"));

        return true;
    }
}
