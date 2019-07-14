package me.z3ndovo.CasinoPlus.SlotMachine;

import me.z3ndovo.CasinoPlus.Core;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SlotMachine {

    private Random randomGen = new Random();
    private final List<String> abc = Arrays.asList("a", "b", "c");

    public Core plugin;
    private FileConfiguration slotsData;
    private FileConfiguration messages;
    private Rewards rewards;

    //Constructor for ConfigManager
    public SlotMachine(Core plugin) {
        this.plugin = plugin;
    }

    public void start(String key, Player player) {
        this.rewards = new Rewards();
        this.slotsData = plugin.cfgM.getSlotsData();
        this.messages = plugin.cfgM.getMsg();

        HashMap<ItemStack, Integer> map = getMap(key);
        ArrayList<ItemStack> itemArray = getArray(map);
        int n = itemArray.size();

        if (slotsData.getBoolean("slots." + key + ".in-use")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("in-use")));

        } else {

            //Get wager
            int wager = Integer.parseInt(slotsData.getString("slots." + key + ".wager.current"));
            EconomyResponse econRes = plugin.econ.withdrawPlayer(player, wager);

            //Check Transaction
            if (!econRes.transactionSuccess()) {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("insufficient-bal")));

            } else {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("take-bal")).replace("{wager}", plugin.econ.format(wager)));

                slotsData.set("slots." + key + ".in-use", true);
                plugin.cfgM.saveSlotsData();

                //Creating Row objects
                SlotRow row0 = new SlotRow(plugin, key, 0, randomGen.nextInt(n), randomGen.nextInt(n), randomGen.nextInt(n));
                SlotRow row1 = new SlotRow(plugin, key, 1, randomGen.nextInt(n), randomGen.nextInt(n), randomGen.nextInt(n));
                SlotRow row2 = new SlotRow(plugin, key, 2, randomGen.nextInt(n), randomGen.nextInt(n), randomGen.nextInt(n));

                for(int i = 0; i < 10; i++) {

                    final int Fi = i;

                    new BukkitRunnable() {

                        @Override
                        public void run() {

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

                            //Switching Blocks
                            for(String x : abc) {
                                String s0 = slotsData.getString("slots." + key + ".rows.0." + x + ".uuid");
                                UUID u0 = UUID.fromString(s0);
                                ArmorStand as0 = getAsByUniqueId(u0);
                                as0.setHelmet(getItemStack(row0.getValue(x), itemArray));

                                String s1 = slotsData.getString("slots." + key + ".rows.1." + x + ".uuid");
                                UUID u1 = UUID.fromString(s1);
                                ArmorStand as1 = getAsByUniqueId(u1);
                                as1.setHelmet(getItemStack(row1.getValue(x), itemArray));

                                String s2 = slotsData.getString("slots." + key + ".rows.2." + x + ".uuid");
                                UUID u2 = UUID.fromString(s2);
                                ArmorStand as2 = getAsByUniqueId(u2);
                                as2.setHelmet(getItemStack(row2.getValue(x), itemArray));
                            }

                            //Play Sound and Give Reward
                            if(Fi == 9) {
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                int reward = rewards.giveReward(wager, row1.getValue("a"), row1.getValue("b"), row1.getValue("c"), player);

                                if(reward != 0) {
                                    plugin.econ.depositPlayer(player, reward);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes
                                            ('&', messages.getString("win-msg")).replace("{reward}", plugin.econ.format(reward)));
                                } else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("lost-msg")));
                                }

                                slotsData.set("slots." + key + ".in-use", false);
                                plugin.cfgM.saveSlotsData();

                            } else {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            }

                        }

                    }.runTaskLater(plugin, 20 + (int) (Math.pow(i, 2))); //Timer

                }

            }

        }

    }

    //Get the ArmorStand by UUID
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

    //Convert int to ItemStack
    public ItemStack getItemStack(int value, ArrayList<ItemStack> arrayList) {
        ItemStack itemStack = arrayList.get(value);

        return itemStack;

    }

    public HashMap<ItemStack, Integer> getMap(String key) {
        this.slotsData = plugin.cfgM.getSlotsData();

        List<String> list = slotsData.getStringList("slots." + key + ".item-weights");
        HashMap<ItemStack, Integer> ItemWeightMap = new HashMap<>();

        for (String items : list) {
            String[] s = items.split(" ");

            int weight = Integer.parseInt(s[1]);
            ItemStack itemStack = new ItemStack(Material.getMaterial(s[0]), 1);

            ItemWeightMap.put(itemStack, weight);
        }

        return ItemWeightMap;
    }

    public ArrayList<ItemStack> getArray(HashMap<ItemStack, Integer> map) {

        ArrayList<ItemStack> array = new ArrayList<>();

        for (ItemStack item : map.keySet()) {

            int value = map.get(item);

            for (int i = 0; i < value; i++) {
                array.add(item);
            }

        }

        return array;
    }
}
