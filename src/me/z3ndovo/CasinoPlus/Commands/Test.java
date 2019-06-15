package me.z3ndovo.CasinoPlus.Commands;

import me.z3ndovo.CasinoPlus.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class Test implements CommandExecutor {

    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        this.slotsData = plugin.cfgM.getSlotsData();

        sender.sendMessage("works");
        String s = slotsData.getString("slots.test.world");
        sender.sendMessage(s);

        plugin.cfgM.saveSlotsData();
        return true;
    }
}
