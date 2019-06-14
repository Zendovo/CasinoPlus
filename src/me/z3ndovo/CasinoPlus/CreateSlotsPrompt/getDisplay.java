package me.z3ndovo.CasinoPlus.CreateSlotsPrompt;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.SlotsData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Zombie;

import java.util.ArrayList;
import java.util.List;

public class getDisplay extends StringPrompt {
    Core plugin = Core.getPlugin(Core.class);
    SlotsData slotsData;

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
        this.slotsData = new SlotsData(plugin);
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

        con.getForWhom().sendRawMessage(args[0] + args[1] + args[2]);
        Location subLoc = new Location(Bukkit.getWorld(world), (Double.parseDouble(args[0])) + (0.5), (Double.parseDouble(args[1])) - (2.15), (Double.parseDouble(args[2])) + (0.5));
        Location displayLoc = new Location(Bukkit.getWorld(world), (Double.parseDouble(args[0])) + (0.5), (Double.parseDouble(args[1])) - (1.15), (Double.parseDouble(args[2])) + (0.5));
        Location addLoc = new Location(Bukkit.getWorld(world), (Double.parseDouble(args[0])) + (0.5), (Double.parseDouble(args[1])) - (0.15), (Double.parseDouble(args[2])) + (0.5));

        ArmorStand as0 = subLoc.getWorld().spawn(subLoc, ArmorStand.class);
        as0.setVisible(false);
        as0.setInvulnerable(false);
        as0.setGravity(false);
        as0.setCustomName(ChatColor.RED + "-");
        as0.setCustomNameVisible(true);

        slotsData.set("slots." + name + ".display.uuid." + Double.toString(0), as0.getUniqueId().toString());

        ArmorStand as1 = displayLoc.getWorld().spawn(displayLoc, ArmorStand.class);
        as1.setVisible(false);
        as1.setInvulnerable(false);
        as1.setGravity(false);
        as1.setCustomName(ChatColor.GREEN + slotsData.getString("slots." + name + ".wager.current"));
        as1.setCustomNameVisible(true);

        slotsData.set("slots." + name + ".display.uuid." + Double.toString(1), as1.getUniqueId().toString());

        ArmorStand as2 = displayLoc.getWorld().spawn(addLoc, ArmorStand.class);
        as2.setVisible(false);
        as2.setInvulnerable(false);
        as2.setGravity(false);
        as2.setCustomName(ChatColor.GREEN + "+");
        as2.setCustomNameVisible(true);

        slotsData.set("slots." + name + ".display.uuid." + Double.toString(2), as2.getUniqueId().toString());

        slotsData.set("slots." + name + ".display.x", args[0]);
        slotsData.set("slots." + name + ".display.y", args[1]);
        slotsData.set("slots." + name + ".display.z", args[2]);
        return new getStart();
    }

    public String getEmblem(int i, String name, SlotsData slotsData) {
        switch (i) {
            case 0:
                return ChatColor.GREEN+ "+";
            case 1:
                return slotsData.getString("slots." + name + ".wager.current");
            case 2:

        }
        return null;
    }
}
