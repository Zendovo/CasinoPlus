package me.z3ndovo.CasinoPlus.Listeners;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.Messages;
import me.z3ndovo.CasinoPlus.SlotMachine.SlotMachine;
import me.z3ndovo.CasinoPlus.Utils.AdjustWager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.UUID;

public class EntityClick implements Listener {
    private SlotMachine slotMachine;
    private AdjustWager adjustWager;

    Core plugin = Core.getPlugin(Core.class);

    private FileConfiguration messages;
    private FileConfiguration slotsData = plugin.cfgM.getSlotsData();

    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent e) {
        this.slotMachine = new SlotMachine(plugin);
        this.adjustWager = new AdjustWager(plugin);
        this.messages = plugin.cfgM.getMsg();

        Entity entity = e.getRightClicked();
        Player player = e.getPlayer();

        if (entity instanceof ArmorStand) {
            UUID uuid = entity.getUniqueId();

            boolean slotsStart = compareUUID(uuid, player, "start.uuid");
            boolean slotsInc = compareUUID(uuid, player, "display.uuid.2");
            boolean slotsDec = compareUUID(uuid, player, "display.uuid.0");

            if(slotsStart) {
                e.setCancelled(true);

                String key = getKey(uuid, "start.uuid");
                if (player.hasPermission("casinoplus.slots.use." + key) || player.hasPermission("casinoplus.slots.use.all")) {
                    slotMachine.start(key, player);
                } else {
                    if(messages.getString("slots.no-permission." + key) == null) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));

                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("slots.no-permission." + key)));

                    }
                }

            } else if (slotsInc) {
                e.setCancelled(true);

                String key = getKey(uuid, "display.uuid.2");
                if (player.hasPermission("casinoplus.slots.use." + key) || player.hasPermission("casinoplus.slots.use.all")) {
                    adjustWager.increaseWager(key, player);
                } else {
                    if(messages.getString("slots.no-permission." + key) == null) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));

                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("slots.no-permission." + key)));

                    }
                }

            } else if (slotsDec) {
                e.setCancelled(true);

                String key = getKey(uuid, "display.uuid.0");
                if (player.hasPermission("casinoplus.slots.use." + key) || player.hasPermission("casinoplus.slots.use.all")) {
                    adjustWager.increaseWager(key, player);
                } else {
                    if(messages.getString("slots.no-permission." + key) == null) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));

                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("slots.no-permission." + key)));

                    }
                }

            }
        }

    }

    public boolean compareUUID(UUID uuid, Player player, String string) {
        for (String key : slotsData.getConfigurationSection("slots").getKeys(false)) {
            if (uuid.equals(UUID.fromString(slotsData.getString("slots." + key + "." + string)))) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    public String getKey(UUID uuid, String string) {
        for (String key : slotsData.getConfigurationSection("slots").getKeys(false)) {
            if (uuid.equals(UUID.fromString(slotsData.getString("slots." + key + "." + string)))) {
                return key;
            } else {
                continue;
            }
        }
        return null;
    }

}
