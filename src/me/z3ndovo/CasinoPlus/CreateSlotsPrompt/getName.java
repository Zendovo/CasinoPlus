package me.z3ndovo.CasinoPlus.CreateSlotsPrompt;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.SlotsData;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class getName extends StringPrompt {
    Core plugin = Core.getPlugin(Core.class);
    private SlotsData slotsData;
    String world;

    public getName(String world) {
        this.world = world;
    }

    @Override
    public String getPromptText(ConversationContext con) {
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        return "Type the name of the slot machine:";
    }

    @Override
    public Prompt acceptInput(ConversationContext con, String value) {
        this.slotsData = new SlotsData(plugin);
        String[] args = value.split(" ");
        slotsData.set("slots." + args[0] + ".world", world);

        con.setSessionData("name", args[0]);
        con.setSessionData("world", world);
        return new getWager();
    }
}
