package me.z3ndovo.CasinoPlus.SlotMachine;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SlotMachine extends ConfigManager {
    private int n = 4;
    private Random randomGen = new Random();
    private final List<String> abc = Arrays.asList("a", "b", "c");

    public Core plugin = Core.getPlugin(Core.class);
    private Rewards rewards;

    public SlotMachine(Core core) {
        super(core, "slotsdata.yml");
    }

    public void start(String key, Player player) {

        SlotRow row0 = new SlotRow(plugin, key, 0, randomGen.nextInt(n), randomGen.nextInt(n), randomGen.nextInt(n));
        SlotRow row1 = new SlotRow(plugin, key, 1, randomGen.nextInt(n), randomGen.nextInt(n), randomGen.nextInt(n));
        SlotRow row2 = new SlotRow(plugin, key, 2, randomGen.nextInt(n), randomGen.nextInt(n), randomGen.nextInt(n));

        for(int i = 0; i < n; i++) {

            //Switching Values
            row2.setValue("a", row1.getValue("a"));
            row2.setValue("b", row1.getValue("b"));
            row2.setValue("c", row1.getValue("c"));

            row1.setValue("a", row0.getValue("a"));
            row1.setValue("b", row0.getValue("b"));
            row1.setValue("c", row0.getValue("c"));

            row0.setValue("a", randomGen.nextInt(n));
            row0.setValue("b", randomGen.nextInt(n));
            row0.setValue("c", randomGen.nextInt(n));

            final int Fi = i;

            new BukkitRunnable() {

                @Override
                public void run() {

                    //Switching Blocks
                    for(String x : abc) {
                        String s0 = config.getString("slots." + key + ".rows.0." + x + ".uuid");
                        UUID u0 = UUID.fromString(s0);
                        ArmorStand as0 = getAsByUniqueId(u0);
                        as0.setHelmet(getItemStack(row0.getValue(x)));

                        String s1 = config.getString("slots." + key + ".rows.1." + x + ".uuid");
                        UUID u1 = UUID.fromString(s1);
                        ArmorStand as1 = getAsByUniqueId(u1);
                        as1.setHelmet(getItemStack(row1.getValue(x)));

                        String s2 = config.getString("slots." + key + ".rows.2." + x + ".uuid");
                        UUID u2 = UUID.fromString(s2);
                        ArmorStand as2 = getAsByUniqueId(u2);
                        as2.setHelmet(getItemStack(row2.getValue(x)));
                    }

                    int wager = config.getInt("slots." + key + ".wager.current");

                    if(Fi == 9) {
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        rewards.giveReward(wager, row1.getValue("a"), row1.getValue("b"), row1.getValue("c"), player);
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    }

                }

            }.runTaskLater(plugin, 20 + (int) (Math.pow(i, 2)));

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

    public ItemStack getItemStack(int value) {
        if (value == 0) {
            ItemStack is = new ItemStack(Material.DIAMOND_BLOCK, 1);
            return(is);
        } else if (value == 1) {
            ItemStack is = new ItemStack(Material.GOLD_BLOCK, 1);
            return(is);
        } else if (value == 2){
            ItemStack is = new ItemStack(Material.IRON_BLOCK, 1);
            return(is);
        } else{
            ItemStack is = new ItemStack(Material.COAL_BLOCK, 1);
            return(is);
        }
    }

}
