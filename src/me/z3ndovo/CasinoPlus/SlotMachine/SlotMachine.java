package me.z3ndovo.CasinoPlus.SlotMachine;

import java.util.Random;

public class SlotMachine {
    private int n = 4;
    private Random randomGen = new Random();

    public void start(String key) {

        SlotRow row0 = new SlotRow(key, 0, randomGen.nextInt(n), randomGen.nextInt(n), randomGen.nextInt(n));
        SlotRow row1 = new SlotRow(key, 0, randomGen.nextInt(n), randomGen.nextInt(n), randomGen.nextInt(n));
        SlotRow row2 = new SlotRow(key, 0, randomGen.nextInt(n), randomGen.nextInt(n), randomGen.nextInt(n));

    }

}
