package me.z3ndovo.CasinoPlus.SlotMachine;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class SlotRow extends ConfigManager {

    public Core plugin = Core.getPlugin(Core.class);
    private FileConfiguration slotsData;

    private String key;
    private int row;
    private int a;
    private int b;
    private int c;

    //Constructor
    public SlotRow(Core instance, String key, int row, int a, int b, int c) {
        this.key = key;
        this.row = row;

        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Location getLoc(int abc) {
        this.slotsData = plugin.cfgM.getSlotsData();

        //World
        World world = Bukkit.getServer().getWorld(slotsData.getString("slots." + key + ".world"));
        //Co-ordinates
        Double x = slotsData.getDouble("slots." + key + ".world.rows." + row + "." + abc + ".x");
        Double y = slotsData.getDouble("slots." + key + ".world.rows." + row + "." + abc + ".y");
        Double z = slotsData.getDouble("slots." + key + ".world.rows." + row + "." + abc + ".z");

        //Return the location
        Location loc = new Location(world, x, y, z);

        return loc;
    }

    public void setValue(String abc,int value) {
        switch (abc) {
            case "a":
                a = value;
            case "b":
                b = value;
            case "c":
                c = value;
        }
    }

    public int getValue(String abc) {
        switch (abc) {
            case "a":
                return a;
            case "b":
                return b;
            case "c":
                return c;
        }
        return 1;
    }


}
