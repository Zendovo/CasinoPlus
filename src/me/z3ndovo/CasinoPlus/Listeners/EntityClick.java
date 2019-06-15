package me.z3ndovo.CasinoPlus.Listeners;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.SlotMachine.SlotMachine;
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
    Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData = plugin.cfgM.getSlotsData();


    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent e) {
        this.slotMachine = new SlotMachine(plugin);

        Entity entity = e.getRightClicked();
        Player player = e.getPlayer();

        if (entity instanceof ArmorStand) {
            UUID uuid = entity.getUniqueId();
            String uuidS = getKey(uuid);

            boolean slots = compareUUID(uuid, player);
            player.sendMessage("cfg: " + slotsData.getString("slots." + "Babnik" + ".start.uuid"));
            player.sendMessage("clc: " + uuid.toString());
            player.sendMessage(Boolean.toString(slots));

            if(slots) {
				slotMachine.start(uuidS, player);
            }
        }

    }

    public boolean compareUUID(UUID uuid, Player player) {
        for (String key : slotsData.getConfigurationSection("slots").getKeys(false)) {
            if (uuid.equals(UUID.fromString(slotsData.getString("slots." + key + ".start.uuid")))) {
                player.sendMessage("Ok");
                return true;
            } else {
                player.sendMessage("Hmm");
                continue;
            }
        }
        player.sendMessage("No");
        return false;
    }

    public String getKey(UUID uuid) {
        for (String key : slotsData.getConfigurationSection("slots").getKeys(false)) {
            if (uuid.toString() == slotsData.getString("slots." + key + ".start.uuid")) {
                return key;
            } else {
                return key;
            }
        }
        return null;
    }

}
