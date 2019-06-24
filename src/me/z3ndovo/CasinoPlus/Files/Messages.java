package me.z3ndovo.CasinoPlus.Files;

import me.z3ndovo.CasinoPlus.Core;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {

    private Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration msgs;

    public void setupMessages() {
        this.msgs = plugin.cfgM.getMsg();

        msgs.set("version", 1);

        msgs.set("insufficient-bal", "&cYou do not have the sufficient balance in your account!");
        msgs.set("take-bal", "&e{wager} &fwas taken from your balance!");
        msgs.set("in-use","&cThe machine is currently in use.");
        msgs.set("win-msg", "&bYou won &e&l{reward}&b.");
        msgs.set("lost-msg", "&cYou lost.");
        msgs.set("no-permission", "&cYou have no permission to do this!");
        msgs.set("increase-wager", "&dIncreased wager to &e&l{new}");
        msgs.set("decrease-wager", "&dDecreased wager to &e&l{new}");

        plugin.cfgM.saveMsg();

    }
}
