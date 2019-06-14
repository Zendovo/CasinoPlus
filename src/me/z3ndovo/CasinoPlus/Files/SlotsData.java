package me.z3ndovo.CasinoPlus.Files;

import java.util.UUID;

import me.z3ndovo.CasinoPlus.Core;

public class SlotsData extends ConfigManager{

    public SlotsData(Core plugin) {
        super(plugin, "slotsdata.yml");
    }

    public boolean compareUUID(UUID uuid) {
        for (String key : config.getConfigurationSection("slots").getKeys(false)) {
            if (uuid.toString() == config.getString("slots." + key + ".start.uuid")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String getKey(UUID uuid) {
        for (String key : config.getConfigurationSection("slots").getKeys(false)) {
            if (uuid.toString() == config.getString("slots." + key + ".start.uuid")) {
                return key;
            } else {
                return key;
            }
        }
        return null;
    }

    public String getString(String value) {
        String res = config.getString(value);
        return res;
    }

    public void set(String section, String value) {
        config.set(section, value);
    }

}
