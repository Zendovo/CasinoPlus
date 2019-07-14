package me.z3ndovo.CasinoPlus.Commands;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Utils.GetSkull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class ResetSlots {

    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;
    private FileConfiguration messages;
    private GetSkull getSkull = new GetSkull(plugin);

    public boolean reset(CommandSender sender, Command cmd, String label, String[] args) {
        this.slotsData = plugin.cfgM.getSlotsData();
        this.messages = plugin.cfgM.getMsg();

        if(!sender.hasPermission("casinoplus.admin")) {

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));
            return true;
        }

        if(args.length < 3) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEnter the name of the slot machine you want to reset!"));
            return true;
        }

        String key = args[2];

        try {
            respawnStart(slotsData, key);
            respawnDisplay(slotsData, key);
            respawnRow0(slotsData, key);
            respawnRow1(slotsData, key);
            respawnRow2(slotsData, key);

        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSlot Machine not found!"));
            plugin.getLogger().warning("Error occurred: IAE - Wrong Name?");
            return true;
        }

        plugin.cfgM.saveSlotsData();
        plugin.cfgM.reloadSlotsData();

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lWARNING! &cMake sure to restart or reload the server to ensure proper functioning!"));

        return true;
    }

    public ArmorStand getAsByUniqueId(String uniqueIdS) {
        UUID uniqueId = UUID.fromString(uniqueIdS);

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(uniqueId)) {
                    if (entity instanceof ArmorStand) {
                        return (ArmorStand) entity;
                    }
                }
            }
        }

        return null;
    }

    public void setAttributes(ArmorStand as, boolean nameVisibilty) {

        as.setVisible(false);
        as.setInvulnerable(true);
        as.setGravity(false);
        as.setCustomNameVisible(nameVisibilty);

    }

    public void respawnStart(FileConfiguration slotsData, String key) {
        World world = Bukkit.getServer().getWorld(slotsData.getString("slots." + key + ".world"));
        int x = slotsData.getInt("slots." + key + ".start.x");
        int y = slotsData.getInt("slots." + key + ".start.y");
        int z = slotsData.getInt("slots." + key + ".start.z");
        String url = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjExNmI2OGQ2MmEzNWMwY2UwYzhjNTU3YWM2MzRmNzY3NzA0OGM2ZmVkMjk2YTBkZDFlZDFhOWM0NzZiMjZlNiJ9fX0=";

        Location loc = new Location(world, x + (0.5), y - (1.15), z + (0.5));

        try {
            ArmorStand old = getAsByUniqueId(slotsData.getString("slots." + key + ".start.uuid"));
            old.remove();

        } catch (NullPointerException e) {
            plugin.getLogger().info("ArmorStand not found, skipping");
        }

        ArmorStand startAs = loc.getWorld().spawn(loc, ArmorStand.class);

        setAttributes(startAs, true);
        startAs.setCustomName(ChatColor.GREEN + "Start");
        startAs.setHelmet(getSkull.getSkullStack(url, "Start", 1, UUID.randomUUID().toString()));

        slotsData.set("slots." + key + ".start.uuid", startAs.getUniqueId().toString());
        plugin.cfgM.saveSlotsData();

    }

    public void respawnDisplay(FileConfiguration slotsData, String key) {
        World world = Bukkit.getServer().getWorld(slotsData.getString("slots." + key + ".world"));
        int x = slotsData.getInt("slots." + key + ".display.x");
        int y = slotsData.getInt("slots." + key + ".display.y");
        int z = slotsData.getInt("slots." + key + ".display.z");
        String url = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjExNmI2OGQ2MmEzNWMwY2UwYzhjNTU3YWM2MzRmNzY3NzA0OGM2ZmVkMjk2YTBkZDFlZDFhOWM0NzZiMjZlNiJ9fX0=";

        Location locSub = new Location(world, x + (0.5), y - (0.40), z + (0.5));
        Location locWager = new Location(world, x + (0.5), y - (1.15), z + (0.5));
        Location locAdd = new Location(world, x + (0.5), y - (0.65), z + (0.5));

        try {
            ArmorStand oldSub = getAsByUniqueId(slotsData.getString("slots." + key + ".display.uuid.0"));
            ArmorStand oldWager = getAsByUniqueId(slotsData.getString("slots." + key + ".display.uuid.1"));
            ArmorStand oldAdd = getAsByUniqueId(slotsData.getString("slots." + key + ".display.uuid.2"));
            oldSub.remove();
            oldWager.remove();
            oldAdd.remove();

        } catch (NullPointerException e) {
            plugin.getLogger().info("ArmorStand not found, skipping");
        }

        String urlSub = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjFlMWU3MzBjNzcyNzljOGUyZTE1ZDhiMjcxYTExN2U1ZTJjYTkzZDI1YzhiZTNhMDBjYzkyYTAwY2MwYmI4NSJ9fX0=";
        ArmorStand asSub = locSub.getWorld().spawn(locSub.subtract(0, 0.75,0), ArmorStand.class);
        setAttributes(asSub, false);
        slotsData.set("slots." + key + ".display.uuid.0", asSub.getUniqueId().toString());
        asSub.setHelmet(getSkull.getSkullStack(urlSub, "Sub", 1, UUID.randomUUID().toString()));

        ArmorStand asWager = locWager.getWorld().spawn(locWager, ArmorStand.class);
        setAttributes(asWager, true);
        asWager.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + slotsData.getInt("slots." + key + ".wager.current"));
        slotsData.set("slots." + key + ".display.uuid.1", asWager.getUniqueId().toString());

        String urlAdd = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWNkYjhmNDM2NTZjMDZjNGU4NjgzZTJlNjM0MWI0NDc5ZjE1N2Y0ODA4MmZlYTRhZmYwOWIzN2NhM2M2OTk1YiJ9fX0=";
        ArmorStand asAdd = locAdd.getWorld().spawn(locAdd.add(0, 0.75,0), ArmorStand.class);
        setAttributes(asAdd, false);
        slotsData.set("slots." + key + ".display.uuid.2", asAdd.getUniqueId().toString());
        asAdd.setHelmet(getSkull.getSkullStack(urlAdd, "Add", 1, UUID.randomUUID().toString()));

        plugin.cfgM.saveSlotsData();

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

    public void respawnRow0(FileConfiguration slotsData, String key) {
        String world = (slotsData.getString("slots." + key + ".world"));

        String[] args = new String[]{slotsData.getString("slots." + key + ".rows.0.a.x"),
                slotsData.getString("slots." + key + ".rows.0.b.x"),
                slotsData.getString("slots." + key + ".rows.0.c.x"),

                slotsData.getString("slots." + key + ".rows.0.a.y"),
                slotsData.getString("slots." + key + ".rows.0.b.y"),
                slotsData.getString("slots." + key + ".rows.0.c.y"),

                slotsData.getString("slots." + key + ".rows.0.a.z"),
                slotsData.getString("slots." + key + ".rows.0.b.z"),
                slotsData.getString("slots." + key + ".rows.0.c.z")};

        for(int i = 0; i < 3; i++) {
            try {
                ArmorStand as = getAsByUniqueId(slotsData.getString("slots." + key + ".rows.0." + getabc(i) + ".uuid"));
                as.remove();
            } catch (NullPointerException e) {
                plugin.getLogger().info("ArmorStand not found, skipping");
            }
        }

        for(int i = 0; i < 3; i++) {
            Location loc = new Location(Bukkit.getServer().getWorld(world), (Double.parseDouble(args[0 + i])) + (0.5), (Double.parseDouble(args[3 + i])) - (1.15), (Double.parseDouble(args[6 + i])) + (0.5));

            ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(world).spawn(loc, ArmorStand.class);
            as.setVisible(false);
            as.setGravity(false);
            as.setInvulnerable(true);
            slotsData.set("slots." + key + ".rows.0." + getabc(i) + ".uuid", as.getUniqueId().toString());
        }

        plugin.cfgM.saveSlotsData();

    }

    public void respawnRow1(FileConfiguration slotsData, String key) {
        String world = (slotsData.getString("slots." + key + ".world"));

        String[] args = new String[]{slotsData.getString("slots." + key + ".rows.1.a.x"),
                slotsData.getString("slots." + key + ".rows.1.b.x"),
                slotsData.getString("slots." + key + ".rows.1.c.x"),

                slotsData.getString("slots." + key + ".rows.1.a.y"),
                slotsData.getString("slots." + key + ".rows.1.b.y"),
                slotsData.getString("slots." + key + ".rows.1.c.y"),

                slotsData.getString("slots." + key + ".rows.1.a.z"),
                slotsData.getString("slots." + key + ".rows.1.b.z"),
                slotsData.getString("slots." + key + ".rows.1.c.z")};

        for(int i = 0; i < 3; i++) {
            try {
                ArmorStand as = getAsByUniqueId(slotsData.getString("slots." + key + ".rows.1." + getabc(i) + ".uuid"));
                as.remove();
            } catch (NullPointerException e) {
                plugin.getLogger().info("ArmorStand not found, skipping");
            }
        }

        for(int i = 0; i < 3; i++) {
            Location loc = new Location(Bukkit.getServer().getWorld(world), (Double.parseDouble(args[0 + i])) + (0.5), (Double.parseDouble(args[3 + i])) - (1.15), (Double.parseDouble(args[6 + i])) + (0.5));

            ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(world).spawn(loc, ArmorStand.class);
            as.setVisible(false);
            as.setGravity(false);
            as.setInvulnerable(true);
            slotsData.set("slots." + key + ".rows.1." + getabc(i) + ".uuid", as.getUniqueId().toString());
        }

        plugin.cfgM.saveSlotsData();

    }

    public void respawnRow2(FileConfiguration slotsData, String key) {
        String world = (slotsData.getString("slots." + key + ".world"));

        String[] args = new String[]{slotsData.getString("slots." + key + ".rows.2.a.x"),
                slotsData.getString("slots." + key + ".rows.2.b.x"),
                slotsData.getString("slots." + key + ".rows.2.c.x"),

                slotsData.getString("slots." + key + ".rows.2.a.y"),
                slotsData.getString("slots." + key + ".rows.2.b.y"),
                slotsData.getString("slots." + key + ".rows.2.c.y"),

                slotsData.getString("slots." + key + ".rows.2.a.z"),
                slotsData.getString("slots." + key + ".rows.2.b.z"),
                slotsData.getString("slots." + key + ".rows.2.c.z")};

        for(int i = 0; i < 3; i++) {
            try {
                ArmorStand as = getAsByUniqueId(slotsData.getString("slots." + key + ".rows.2." + getabc(i) + ".uuid"));
                as.remove();
            } catch (NullPointerException e) {
                plugin.getLogger().info("ArmorStand not found, skipping");
            }

        }

        for(int i = 0; i < 3; i++) {
            Location loc = new Location(Bukkit.getServer().getWorld(world), (Double.parseDouble(args[0 + i])) + (0.5), (Double.parseDouble(args[3 + i])) - (1.15), (Double.parseDouble(args[6 + i])) + (0.5));

            ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(world).spawn(loc, ArmorStand.class);
            as.setVisible(false);
            as.setGravity(false);
            as.setInvulnerable(true);
            slotsData.set("slots." + key + ".rows.2." + getabc(i) + ".uuid", as.getUniqueId().toString());
        }

        plugin.cfgM.saveSlotsData();

    }

}
