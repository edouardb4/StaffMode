package me.letssee.staffmode.struct;

public class ChatColor {
    public static String translate(String value) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', value);
    }

    public static String strip(String value) {
        return org.bukkit.ChatColor.stripColor(value);
    }
}
