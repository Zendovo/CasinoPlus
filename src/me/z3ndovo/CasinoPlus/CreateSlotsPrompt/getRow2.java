package me.z3ndovo.CasinoPlus.CreateSlotsPrompt;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.SlotsData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public class getRow2 extends StringPrompt {
    Core plugin = Core.getPlugin(Core.class);
    SlotsData slotsData;

    @Override
    public String getPromptText(ConversationContext con) {
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("Type the co-ordinates of the bottom row slots (<xA> <xB> <xC> <yA> <yB> <yC> <zA> <zB> <zC>):");
        return "A is the left slot, the B middle, the C right.";
    }

    @Override
    public Prompt acceptInput(ConversationContext con, String value) {
        this.slotsData = new SlotsData(plugin);
        String name = con.getSessionData("name").toString();
        String[] args = value.split(" ");
        String world = con.getSessionData("world").toString();
        //Player player = (Player) con.getForWhom();

        try {
            for(int i = 0; i < 8; i++){
                Double.parseDouble(args[i]);
            }

        } catch (NumberFormatException err) {
            con.getForWhom().sendRawMessage("Type a valid number!");
            return new getRow2();
        }

        List<Location> row2Loc = new ArrayList<Location>();
        List<ArmorStand> row2As = new ArrayList<ArmorStand>();
        for(int i = 0; i < 3; i++) {
            Location loc = new Location(Bukkit.getServer().getWorld(world), (Double.parseDouble(args[0 + i])) + (0.5), (Double.parseDouble(args[3 + i])) - (1.15), (Double.parseDouble(args[6 + i])) + (0.5));
            row2Loc.add(loc);

            ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(world).spawn(loc, ArmorStand.class);
            as.setVisible(false);
            as.setGravity(false);
            as.setInvulnerable(false);
            row2As.add(as);
            slotsData.set("slots." + name + ".rows.2." + getabc(i) + ".uuid", as.getUniqueId().toString());
        }

        for(int i = 0; i < 3; i++) {
            slotsData.set("slots." + name + ".rows.2." + getabc(i) + ".x", args[0 + i]);
            slotsData.set("slots." + name + ".rows.2." + getabc(i) + ".y", args[3 + i]);
            slotsData.set("slots." + name + ".rows.2." + getabc(i) + ".z", args[6 + i]);
        }
        slotsData.save();
        return END_OF_CONVERSATION;
    }

    public String getabc(int i) {
        switch (i) {
            case 0:
                return "a";
            case 1:
                return "b";
            case 2:
                return "c";
        }
        return null;
    }
}
