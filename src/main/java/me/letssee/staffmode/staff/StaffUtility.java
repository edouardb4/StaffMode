package me.letssee.staffmode.staff;

import me.letssee.staffmode.StaffModePlugin;
import me.letssee.staffmode.citems.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class StaffUtility {
    private String name;
    public StaffUtility(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getPermission() { return "staff.permission.default"; }

    public CustomItem getItem()  { return null; }
    public ItemStack getItemStack() { return null; }

    public void register() {
        UtilityManager.registerUtility(this);
    }

    public void unregister() {
        UtilityManager.unregisterUtility(this.getName());
    }

    public final void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, StaffModePlugin.getInstance());
    }
}
