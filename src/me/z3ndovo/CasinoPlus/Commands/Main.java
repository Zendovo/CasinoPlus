package me.z3ndovo.CasinoPlus.Commands;

import me.z3ndovo.CasinoPlus.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Main implements CommandExecutor {

    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;
    private FileConfiguration messages;
    private Help help = new Help();

    /*
        Base Command:
            /casinoplus
        Args:
            /casinoplus help
            /casinoplus slots create
            /casinoplus slots reset
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        this.slotsData = plugin.cfgM.getSlotsData();
        this.messages = plugin.cfgM.getMsg();

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCommand is only for players!"));
            return true;
        }

        Player player = (Player) sender;

        if (!sender.hasPermission("casinoplus.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));
            return true;
        }

        if (args.length < 1) {
            help.msg(sender, cmd, label, args);
            return true;

        } else if (args[0].equals("help")) {
            help.msg(sender, cmd, label, args);
            return true;

        } else if (args[0].equals("slots")) {

            if (args.length < 2) {
                help.msg(sender, cmd, label, args);
                return true;

            } else if (args[1].equals("create")) {
                CreateSlots slots = new CreateSlots();
                slots.create(sender, cmd, label, args);
                return true;

            } else  if (args[1].equals("reset")) {
                ResetSlots slots = new ResetSlots();
                slots.reset(sender, cmd, label, args);
                return true;

            }
        }


        return true;
    }
}
