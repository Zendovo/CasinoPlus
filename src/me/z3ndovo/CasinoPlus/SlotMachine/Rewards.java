package me.z3ndovo.CasinoPlus.SlotMachine;

import org.bukkit.entity.Player;

public class Rewards {

    public Rewards() {

    }

    public int giveReward(int wager, int a, int b, int c,Player p) {

        if (a == b && b == c) {
            Double d = multiplier(a);
            return (int) ((wager * d));
        } else if (a == b && b != c) {
            Double d = multiplier(a);
            return (int) (wager * (1 + (d/(5 + a))));
        } else if (a != b && b == c) {
            Double d = multiplier(c);
            return (int) (wager * (1 + (d/(5 + c))));
        } else if (a == c && b != c) {
            Double d = multiplier(a);
            return (int) (wager * (1 + (d/(5 + a))));
        } else {
            return 0;
        }

    }

    public double multiplier(double i) {
        double m;

        if (i == 0) {
            m = 2;
        } else if(i == 1) {
            m = 1.6;
        } else if(i == 2) {
            m = 1.20;
        } else {
            m = 1;
        }
        return m;
    }

}
