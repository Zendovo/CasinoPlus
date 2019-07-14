package me.z3ndovo.CasinoPlus.Commands;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.CreateSlotsPrompt.getName;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

public class CreateSlots {

    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;
    private FileConfiguration messages;

    /*
        /createslots
    */

    public boolean create(CommandSender sender, Command cmd, String s, String[] args) {
        this.slotsData = plugin.cfgM.getSlotsData();
        this.messages = plugin.cfgM.getMsg();

        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only!");
            return true;
        }

        if (!sender.hasPermission("casinoplus.admin")) {

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));
            return true;
        }

        Player player = (Player) sender;
        String world = player.getWorld().getName();

        ConversationFactory cf = new ConversationFactory(plugin);
        Conversation conv = cf.withFirstPrompt(new getName(world)).withEscapeSequence("cancel").withLocalEcho(false).buildConversation(player);
        conv.begin();

        return true;
    }

}
