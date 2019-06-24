package me.z3ndovo.CasinoPlus.Utils;

import me.z3ndovo.CasinoPlus.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AdjustWager {
    Core plugin;
    private FileConfiguration slotsData;
    private FileConfiguration messages;

    public AdjustWager(Core plugin) {
        this.plugin = plugin;
    }

    public void increaseWager(String key, Player player) {
        this.slotsData = plugin.cfgM.getSlotsData();
        this.messages = plugin.cfgM.getMsg();

        int max = slotsData.getInt("slots." + key + ".wager.max");
        int min = slotsData.getInt("slots." + key + ".wager.min");
        int step = slotsData.getInt("slots." + key + ".wager.step");
        int current = slotsData.getInt("slots." + key + ".wager.current");

        ArmorStand display = getAsByUniqueId(UUID.fromString(slotsData.getString("slots." + key + ".display.uuid.1")));

        int adjusted = current + step;

        if (min <= adjusted && adjusted <= max) {
            slotsData.set("slots." + key + ".wager.current", adjusted);
            plugin.cfgM.saveSlotsData();
            display.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + plugin.econ.format(adjusted));

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("increase-wager").replace("{new}", plugin.econ.format(adjusted))));

        } else {
            player.sendMessage("You have reached the maximum wager!");
        }

    }

    public void decreaseWager(String key, Player player) {
        this.slotsData = plugin.cfgM.getSlotsData();
        this.messages = plugin.cfgM.getMsg();

        int max = slotsData.getInt("slots." + key + ".wager.max");
        int min = slotsData.getInt("slots." + key + ".wager.min");
        int step = slotsData.getInt("slots." + key + ".wager.step");
        int current = slotsData.getInt("slots." + key + ".wager.current");

        ArmorStand display = getAsByUniqueId(UUID.fromString(slotsData.getString("slots." + key + ".display.uuid.1")));

        int adjusted = current - step;

        if (min <= adjusted && adjusted <= max) {
            slotsData.set("slots." + key + ".wager.current", adjusted);
            plugin.cfgM.saveSlotsData();
            display.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + plugin.econ.format(adjusted));

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("decrease-wager").replace("{new}", plugin.econ.format(adjusted))));

        } else {
            player.sendMessage("You have reached the minimum wager!");
        }

    }

    public ArmorStand getAsByUniqueId(UUID uniqueId) {
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

}
