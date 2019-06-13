package me.z3ndovo.CasinoPlus.SlotMachine;

import org.bukkit.Location;

import me.z3ndovo.CasinoPlus.Core;

public class SlotRow {

    public Core plugin = Core.getPlugin(Core.class);

    private String key;
    private int row;
    private int a;
    private int b;
    private int c;

    public SlotRow(String key, int row, int a, int b, int c) {
        this.key = key;
        this.row = row;

        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Location getLoc(int slotH) {
//        Location loc = new Location()
        return null;
    }

}
