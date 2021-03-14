package me.letssee.staffmode.staff;

import me.letssee.staffmode.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class StaffPlayer {
    private String location;
    private UUID uuid;
    private ItemStack[] inventoryContents;
    private ItemStack[] armorContents;
    public StaffPlayer(Player player) {
        this.location = LocationUtils.toLocationString(player.getLocation());
        this.uuid = player.getUniqueId();
        this.inventoryContents = player.getInventory().getContents();
        this.armorContents = player.getInventory().getArmorContents();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public Location getLocation() {
        return LocationUtils.toLocationObject(this.location);
    }

    public void restoreInventory() {
        Player player = Bukkit.getPlayer(this.uuid);
        for (int i = 0; i < inventoryContents.length; i++) {
            player.getInventory().setItem(i, inventoryContents[i]);
        }
        player.getInventory().setHelmet(armorContents[3]);
        player.getInventory().setChestplate(armorContents[2]);
        player.getInventory().setLeggings(armorContents[1]);
        player.getInventory().setBoots(armorContents[0]);
        player.updateInventory();
    }

    public void setupContents() {
        Player player = Bukkit.getPlayer(this.uuid);
        for(StaffUtility utility : UtilityManager.getRegisteredUtilities()) {
            if(utility.getItem().getInventorySlot() <= -1 || !player.hasPermission(utility.getPermission())) {
                continue;
            }
            player.getInventory().setItem(utility.getItem().getInventorySlot(), utility.getItemStack());
        }
    }

    public void clearAllContents() {
        Player player = getPlayer();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        for(int i = 0; i < player.getInventory().getSize(); i++) {
            player.getInventory().setItem(i, null);
        }
        player.setItemOnCursor(null);
    }
}
