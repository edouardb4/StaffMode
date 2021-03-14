package me.letssee.staffmode.inventories;

import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.InventoryWrapper;
import me.letssee.staffmode.struct.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PlayerInventory {
    public static Inventory get(Player player) {
        InventoryWrapper inventory = new InventoryWrapper(Bukkit.createInventory(null, 45,
                String.valueOf(ChatColor.translate("&8")) + player.getName() + "'s Inventory"));
        ItemStack pane = new ItemBuilder(Material.STAINED_GLASS_PANE).durability((short)7).displayname("&7").build();
        Integer[] list = { 36, 37, 40, 43, 44 };
        Arrays.asList(list).stream().forEach(i -> inventory.setContent(i, pane));
        ItemStack[] contents = player.getInventory().getContents();
        for (int j = 8; j >= 0; --j) {
            ItemStack item = contents[j];
            if (item != null && item.getType() != Material.AIR) {
                inventory.setContent(36 - (9 - j), item);
            }
        }
        for (int j = 9; j < contents.length; ++j) {
            final ItemStack item = contents[j];
            if (item != null) {
                inventory.setContent(j - 9, item);
            }
        }
        list = new Integer[] { 38, 39, 41, 42 };
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        for (int i = 0; i < armorContents.length; ++i) {
            ItemStack item = armorContents[i];
            if (item != null) {
                inventory.setContent(list[i], item);
            }
        }
        return inventory.get();
    }
}
