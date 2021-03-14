package me.letssee.staffmode.listeners;

import com.google.common.collect.Maps;
import me.letssee.staffmode.StaffModePlugin;
import me.letssee.staffmode.inventories.ReportInventory;
import me.letssee.staffmode.struct.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ReportInventoryListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPageClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if(!inventory.getTitle().equals(ChatColor.translate("&7Reports"))) {
            return;
        }
        event.setCancelled(true);
        if(inventory != event.getClickedInventory()){
            return;
        }
        handlePageClick(event);
        handleReportClick(event);
    }

    private void handleReportClick(InventoryClickEvent event) {
        if(event.getClick() != ClickType.SHIFT_RIGHT) {
            return;
        }
        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) {
            return;
        }
        NBTWrapper wrapper = new NBTWrapper(item);
        if(!wrapper.hasKey("report_nbt")) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        String s_Report = wrapper.getTag("report_nbt").getString("report_string");
        Report report = Report.parse(s_Report);
        Map<String, String> replace = Maps.newHashMap();
        replace.put("%player%", report.getReporter().getName());
        replace.put("%message%", report.getMessage());
        ConfigSettings.sendMessage(player, "delete-report-message", replace);
        report.delete();
        player.closeInventory();
        Bukkit.getScheduler().runTaskLater(StaffModePlugin.getInstance(), () -> player.performCommand("reports"), 1);
    }

    private void handlePageClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        NBTWrapper wrapper = new NBTWrapper(item);
        if(wrapper.hasKey("page")) {
            NBTTagWrapper tag = wrapper.getTag("page");
            int page = tag.getInt("page");
            player.closeInventory();
            Bukkit.getScheduler().runTaskLater(StaffModePlugin.getInstance(), () -> player.openInventory(ReportInventory.getInventory(page)), 1);
            return;
        }
    }

}
