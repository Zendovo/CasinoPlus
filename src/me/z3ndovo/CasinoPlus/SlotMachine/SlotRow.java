package me.z3ndovo.CasinoPlus.SlotMachine;

import me.z3ndovo.CasinoPlus.Core;
import me.z3ndovo.CasinoPlus.Files.ConfigManager;
import me.z3ndovo.CasinoPlus.Files.SlotsData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.Location;

public class SlotRow extends ConfigManager {

    public Core plugin = Core.getPlugin(Core.class);

    private String key;
    private int row;
    private int a;
    private int b;
    private int c;

    public SlotRow(Core instance, String key, int row, int a, int b, int c) {
        super(instance, "slotsdata.yml");
        this.key = key;
        this.row = row;

        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Location getLoc(int abc) {
        World world = Bukkit.getServer().getWorld(config.getString("slots." + key + ".world"));
        Double x = config.getDouble("slots." + key + ".world.rows." + row + "." + abc + ".x");
        Double y = config.getDouble("slots." + key + ".world.rows." + row + "." + abc + ".y");
        Double z = config.getDouble("slots." + key + ".world.rows." + row + "." + abc + ".z");
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
