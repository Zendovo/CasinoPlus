package me.z3ndovo.CasinoPlus.CreateSlotsPrompt;

import me.z3ndovo.CasinoPlus.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public class getDisplay extends StringPrompt {
    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;

    @Override
    public String getPromptText(ConversationContext con) {
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        return "Type the co-ordinates of the wager display (<x> <y> <z>):";
    }

    @Override
    public Prompt acceptInput(ConversationContext con, String value) {
        this.slotsData = plugin.cfgM.getSlotsData();

        String name = con.getSessionData("name").toString();
        String[] args = value.split(" ");
        String world = con.getSessionData("world").toString();
        //Player player = (Player) con.getForWhom();

        try {
            Double.parseDouble(args[0]);
            Double.parseDouble(args[1]);
            Double.parseDouble(args[2]);

        } catch (NumberFormatException err) {
            con.getForWhom().sendRawMessage("Type a valid number!");
            return new getDisplay();
        }

        Location loc = new Location(Bukkit.getWorld(world), (Double.parseDouble(args[0])) + (0.5), (Double.parseDouble(args[1])) - (1.15), (Double.parseDouble(args[2])) + (0.5));
        List<ArmorStand> asArray = new ArrayList<ArmorStand>();
        for (int i = -1; i < 2; i++) {
            if (i < 0) {
                ArmorStand as = loc.getWorld().spawn(loc.subtract(0, 1,0), ArmorStand.class);
                as.setVisible(true);
                as.setInvulnerable(false);
                as.setGravity(false);
                as.setCustomName(getEmblem(i, name, slotsData));
                as.setCustomNameVisible(true);

                String f = Integer.toString(i + 1);
                slotsData.set("slots." + name + ".display.uuid." + f, as.getUniqueId().toString());

                asArray.add(as);
            } else {
                ArmorStand as = loc.getWorld().spawn(loc.add(0, i,0), ArmorStand.class);
                as.setVisible(true);
                as.setInvulnerable(false);
                as.setGravity(false);
                as.setCustomName(getEmblem(i, name, slotsData));
                as.setCustomNameVisible(true);

                String f = Integer.toString(i + 1);
                slotsData.set("slots." + name + ".display.uuid." + f, as.getUniqueId().toString());

                asArray.add(as);
            }
        }

        slotsData.set("slots." + name + ".display.x", args[0]);
        slotsData.set("slots." + name + ".display.y", args[1]);
        slotsData.set("slots." + name + ".display.z", args[2]);
        plugin.cfgM.saveSlotsData();
        return new getStart();
    }

    public String getEmblem(int i, String name, FileConfiguration slotsData) {
        switch (i) {
            case -1:
                return ChatColor.GREEN + "+";
            case 0:
                return slotsData.getString("slots." + name + ".wager.current");
            case 1:
                return ChatColor.RED + "-";
        }
        return null;
    }
}
