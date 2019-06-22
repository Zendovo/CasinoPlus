package me.z3ndovo.CasinoPlus.Listeners;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.SlotMachine.SlotMachine;
import me.z3ndovo.CasinoPlus.Utils.AdjustWager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.UUID;

public class EntityClick implements Listener {
    SlotMachine slotMachine;
    AdjustWager adjustWager;
    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData = plugin.cfgM.getSlotsData();


    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent e) {
        this.slotMachine = new SlotMachine(plugin);
        this.adjustWager = new AdjustWager(plugin);

        Entity entity = e.getRightClicked();
        Player player = e.getPlayer();

        if (entity instanceof ArmorStand) {
            UUID uuid = entity.getUniqueId();

            boolean slotsStart = compareUUID(uuid, player, "start.uuid");
            boolean slotsInc = compareUUID(uuid, player, "display.uuid.2");
            boolean slotsDec = compareUUID(uuid, player, "display.uuid.0");

            if(slotsStart) {
				slotMachine.start(getKey(uuid, "start.uuid"), player);
				e.setCancelled(true);

            } else if (slotsInc) {
                adjustWager.increaseWager(getKey(uuid, "display.uuid.2"), player);
                e.setCancelled(true);

            } else if (slotsDec) {
                adjustWager.decreaseWager(getKey(uuid, "display.uuid.0"), player);
                e.setCancelled(true);

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
