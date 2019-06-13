package me.z3ndovo.CasinoPlus.SlotMachine;

import org.bukkit.entity.Player;

public class Rewards {

    public void giveReward(int wager, int a, int b, int c,Player p) {

        if (a == b && b == c) {
            Double d = multiplier(a);
            p.sendMessage("You won " + (wager * d));
        } else if (a == b && b != c) {
            Double d = multiplier(a);
            p.sendMessage("You won " + wager * (1 + (d/(5 + a))));
        } else if (a != b && b == c) {
            Double d = multiplier(c);
            p.sendMessage("You won " + wager * (1 + (d/(5 + c))));
        } else if (a == c && b != c) {
            Double d = multiplier(a);
            p.sendMessage("You won " + wager * (1 + (d/(5 + a))));
        } else {
            p.sendMessage("You lost.");
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
