package me.z3ndovo.CasinoPlus.Listeners;

import java.util.UUID;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.ConfigManager;
import me.z3ndovo.CasinoPlus.Files.SlotsData;
import me.z3ndovo.CasinoPlus.SlotMachine.SlotMachine;

public class EntityClick extends ConfigManager implements Listener {
    private SlotsData slotsData;
    private SlotMachine slotMachine;

    Core plugin;
    public EntityClick(Core instance) {
        super(instance, "slotsdata.yml");
    }

    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent e) {
        Entity entity = e.getRightClicked();
        Player p = e.getPlayer();

        if (entity instanceof ArmorStand) {
            UUID uuid = entity.getUniqueId();

            boolean slots = slotsData.compareUUID(uuid);

            if(slots) {
                String id = slotsData.getKey(uuid);
				slotMachine.start(id, p);
            }
        }

    }

}
