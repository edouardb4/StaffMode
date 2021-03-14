package me.letssee.staffmode.citems;

import java.util.List;
import java.util.Scanner;

import me.letssee.staffmode.StaffModePlugin;
import me.letssee.staffmode.listeners.CustomItemListener;
import me.letssee.staffmode.struct.ConfigurationWrapper;
import org.bukkit.Bukkit;

import com.google.common.collect.Lists;

public class CustomItemManager {

    private static ConfigurationWrapper config;
    private static List<CustomItem> registeredItems;

    public static void onEnable() {
        registeredItems = Lists.newArrayList();
        config = new ConfigurationWrapper("custom_items.yml", StaffModePlugin.getInstance());
        Bukkit.getPluginManager().registerEvents(new CustomItemListener(), StaffModePlugin.getInstance());
    }

    public static void onDisable() {
        for(CustomItem item : getCustomItems()) {
            item.onDisable();
        }
    }

    public static ConfigurationWrapper getConfig() {
        return config;
    }

    public static List<CustomItem> getCustomItems() {
        return registeredItems;
    }

    public static void unregisterCustomItem(CustomItem item) {
        unregisterCustomItem(item.getInternalName());
    }

    public static void unregisterCustomItem(String name) {
        for(int i = 0; i < registeredItems.size(); i++) {
            if(registeredItems.get(i).getInternalName().equals(name)) {
                registeredItems.remove(i);
                return;
            }
        }
    }

    public static void registerCustomItem(CustomItem item) {
        registeredItems.add(item);
        item.onEnable();
        item.gen();
    }

    public static void reloadConfig() {
        config.reload();
    }

    public static CustomItem findCustomItem(String internalName) {
        for(CustomItem item : registeredItems) {
            if(item.getInternalName().equals(internalName)) {
                return item;
            }
        }
        return null;
    }

}