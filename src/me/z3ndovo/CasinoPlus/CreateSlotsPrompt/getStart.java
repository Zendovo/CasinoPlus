package me.z3ndovo.CasinoPlus.CreateSlotsPrompt;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Utils.GetSkull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.UUID;

public class getStart extends StringPrompt {
    Core plugin = Core.getPlugin(Core.class);
    private GetSkull getSkull;
    private FileConfiguration slotsData;


    @Override
    public String getPromptText(ConversationContext con) {
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        return "Type the co-ordinates of the start button (<x> <y> <z>):";
    }

    @Override
    public Prompt acceptInput(ConversationContext con, String value) {
        this.slotsData = plugin.cfgM.getSlotsData();
        this.getSkull = new GetSkull(plugin);

        String name = con.getSessionData("name").toString();
        String[] args = value.split(" ");
        Player player = (Player) con.getForWhom();
        String world = con.getSessionData("world").toString();
        String url = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ1YzlhY2VhOGRhNzFiNGYyNTJjZDRkZWI1OTQzZjQ5ZTdkYmMwNzY0Mjc0YjI1YTZhNmY1ODc1YmFlYTMifX19";

        try {
            Double.parseDouble(args[0]);
            Double.parseDouble(args[1]);
            Double.parseDouble(args[2]);

        } catch (NumberFormatException err) {
            con.getForWhom().sendRawMessage("Type a valid number!");
            return new getStart();
        }

        Location startLoc = new Location(Bukkit.getWorld(world), (Double.parseDouble(args[0])) + (0.5), (Double.parseDouble(args[1])) - (1.15), (Double.parseDouble(args[2])) + (0.5));
        ArmorStand startAs = startLoc.getWorld().spawn(startLoc, ArmorStand.class);
        slotsData.set("slots." + name + ".start.uuid", startAs.getUniqueId().toString());

        startAs.setVisible(false);
        startAs.setInvulnerable(false);
        startAs.setGravity(false);
        startAs.setCustomName(ChatColor.GREEN + "Start");
        startAs.setCustomNameVisible(true);
        startAs.setHelmet(getSkull.getSkullStack(url, "Start", 1, UUID.randomUUID().toString()));

        slotsData.set("slots." + name + ".start.x", args[0]);
        slotsData.set("slots." + name + ".start.y", args[1]);
        slotsData.set("slots." + name + ".start.z", args[2]);
        plugin.cfgM.saveSlotsData();
        return new getRow0();
    }
}
