package me.z3ndovo.CasinoPlus;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class GetSkull {

    Core plugin;
    public GetSkull(Core plugin) {
        this.plugin = plugin;
    }

    public ItemStack getSkullStack(String url, String name, int amount, String uuid) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, amount);
        if (url.isEmpty()) return skull;

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.fromString(uuid), null);
        profile.getProperties().put("textures", new Property("textures", url	));
        Field profileField = null;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        skull.setItemMeta(skullMeta);

        return skull;
    }
}
