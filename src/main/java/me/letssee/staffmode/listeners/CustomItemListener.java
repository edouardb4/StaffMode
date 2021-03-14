package me.letssee.staffmode.listeners;

import me.letssee.staffmode.citems.CustomItem;
import me.letssee.staffmode.citems.CustomItemManager;
import me.letssee.staffmode.enums.ItemCType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomItemListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        for(CustomItem item : CustomItemManager.getCustomItems()) {
            boolean cursor = item.isItem(event.getCursor());
            boolean current = item.isItem(event.getCurrentItem());
            if(!cursor && !current) {
                continue;
            }
            if(cursor && current) {
                item.onClick(event, ItemCType.BOTH);
            }
            else if(cursor) {
                item.onClick(event, ItemCType.CURSOR);
            }
            else {
                item.onClick(event, ItemCType.CURRENT);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        for(CustomItem item : CustomItemManager.getCustomItems()) {
            item.onInteract(event);
        }
    }
}
