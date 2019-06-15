package me.z3ndovo.CasinoPlus.Commands;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.CreateSlotsPrompt.getName;
import me.z3ndovo.CasinoPlus.Files.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

public class CreateSlots implements CommandExecutor {

    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;

    /*
        /casinoplus slots create <name>
    */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        this.slotsData = plugin.cfgM.getSlotsData();

        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only!");
        }

        Player player = (Player) sender;
        String world = player.getWorld().getName();
        player.sendMessage("works");

        ConversationFactory cf = new ConversationFactory(plugin);
        Conversation conv = cf.withFirstPrompt(new getName(world)).withEscapeSequence("cancel").withLocalEcho(false).buildConversation(player);
        conv.begin();

        return true;
    }

}
