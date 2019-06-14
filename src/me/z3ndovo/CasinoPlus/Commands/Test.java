package me.z3ndovo.CasinoPlus.Commands;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.SlotsData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Test implements CommandExecutor {

    Core plugin = Core.getPlugin(Core.class);
    private SlotsData slotsData;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        this.slotsData = new SlotsData(plugin);
        sender.sendMessage("works");
        slotsData.set("slots.test.world", "world");
        slotsData.save();
        return true;
    }
}
