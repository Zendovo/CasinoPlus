package me.z3ndovo.CasinoPlus.CreateSlotsPrompt;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class getWager extends StringPrompt {
    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;

    @Override
    public String getPromptText(ConversationContext con) {
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        return "Type the wager in the format (<max> <step> <min>):";
    }

    @Override
    public Prompt acceptInput(ConversationContext con, String value) {
        this.slotsData = plugin.cfgM.getSlotsData();
        String name = con.getSessionData("name").toString();
        String[] args = value.split(" ");

        try {
            Double.parseDouble(args[0]);
            Double.parseDouble(args[1]);
            Double.parseDouble(args[2]);

        } catch (NumberFormatException err) {
            con.getForWhom().sendRawMessage("Type a valid number!");
            return new getDisplay();
        }

        slotsData.set("slots." + name + ".wager.max", Integer.parseInt(args[0]));
        slotsData.set("slots." + name + ".wager.step", Integer.parseInt(args[1]));
        slotsData.set("slots." + name + ".wager.min", Integer.parseInt(args[2]));
        slotsData.set("slots." + name + ".wager.current", (Integer.parseInt(args[0]) / 2));
        plugin.cfgM.saveSlotsData();
        return new getDisplay();
    }
}
