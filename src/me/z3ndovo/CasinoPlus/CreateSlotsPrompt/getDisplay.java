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

public class getDisplay extends StringPrompt {
    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;
    GetSkull getSkull;

    @Override
    public String getPromptText(ConversationContext con) {
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage("");
        con.getForWhom().sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&eType the co-ordinates of the wager display &7(&b<x> &e<y> &c<z>&7):"));
        return "-----------------------------------------------";
    }

    @Override
    public Prompt acceptInput(ConversationContext con, String value) {
        this.slotsData = plugin.cfgM.getSlotsData();
        this.getSkull = new GetSkull(plugin);

        String name = con.getSessionData("name").toString();
        String[] args = value.split(" ");
        String world = con.getSessionData("world").toString();
        Player player = (Player) con.getForWhom();

        try {
            Double.parseDouble(args[0]);
            Double.parseDouble(args[1]);
            Double.parseDouble(args[2]);

        } catch (NumberFormatException err) {
            con.getForWhom().sendRawMessage("Type a valid number!");
            return new getDisplay();
        }

        Location locSub = new Location(Bukkit.getWorld(world), (Double.parseDouble(args[0])) + (0.5), (Double.parseDouble(args[1])) - (0.40), (Double.parseDouble(args[2])) + (0.5));
        Location locWager = new Location(Bukkit.getWorld(world), (Double.parseDouble(args[0])) + (0.5), (Double.parseDouble(args[1])) - (1.15), (Double.parseDouble(args[2])) + (0.5));
        Location locAdd = new Location(Bukkit.getWorld(world), (Double.parseDouble(args[0])) + (0.5), (Double.parseDouble(args[1])) - (0.65), (Double.parseDouble(args[2])) + (0.5));

        String urlSub = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjFlMWU3MzBjNzcyNzljOGUyZTE1ZDhiMjcxYTExN2U1ZTJjYTkzZDI1YzhiZTNhMDBjYzkyYTAwY2MwYmI4NSJ9fX0=";
        ArmorStand asSub = locSub.getWorld().spawn(locSub.subtract(0, 0.75,0), ArmorStand.class);
        setAttributes(asSub, false);
        slotsData.set("slots." + name + ".display.uuid.0", asSub.getUniqueId().toString());
        asSub.setHelmet(getSkull.getSkullStack(urlSub, "Sub", 1, UUID.randomUUID().toString()));

        ArmorStand asWager = locWager.getWorld().spawn(locWager, ArmorStand.class);
        setAttributes(asWager, true);
        asWager.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + slotsData.getInt("slots." + name + ".wager.current"));
        slotsData.set("slots." + name + ".display.uuid.1", asWager.getUniqueId().toString());

        String urlAdd = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWNkYjhmNDM2NTZjMDZjNGU4NjgzZTJlNjM0MWI0NDc5ZjE1N2Y0ODA4MmZlYTRhZmYwOWIzN2NhM2M2OTk1YiJ9fX0=";
        ArmorStand asAdd = locAdd.getWorld().spawn(locAdd.add(0, 0.75,0), ArmorStand.class);
        setAttributes(asAdd, false);
        slotsData.set("slots." + name + ".display.uuid.2", asAdd.getUniqueId().toString());
        asAdd.setHelmet(getSkull.getSkullStack(urlAdd, "Add", 1, UUID.randomUUID().toString()));

        slotsData.set("slots." + name + ".display.x", Integer.parseInt(args[0]));
        slotsData.set("slots." + name + ".display.y", Integer.parseInt(args[1]));
        slotsData.set("slots." + name + ".display.z", Integer.parseInt(args[2]));
        plugin.cfgM.saveSlotsData();
        return new getStart();
    }

    public void setAttributes(ArmorStand as, boolean nameVisibilty) {

        as.setVisible(false);
        as.setInvulnerable(true);
        as.setGravity(false);
        as.setCustomNameVisible(nameVisibilty);

    }
}
