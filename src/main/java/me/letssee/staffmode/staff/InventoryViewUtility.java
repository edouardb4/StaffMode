package me.letssee.staffmode.staff;

import me.letssee.staffmode.citems.CustomItem;
import me.letssee.staffmode.citems.CustomItemManager;
import me.letssee.staffmode.inventories.PlayerInventory;
import me.letssee.staffmode.staff.items.ViewInventoryItem;
import me.letssee.staffmode.storage.FrozenPlayerStorage;
import me.letssee.staffmode.storage.StaffModeStorage;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.staff.items.FreezeItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryViewUtility extends StaffUtility implements Listener {


    public InventoryViewUtility(String name) {
        super(name);
    }

    @Override
    public void register() {
        CustomItemManager.registerCustomItem(new ViewInventoryItem("view_inventory_item"));
        registerListener(new InventoryViewUtility(null));
        super.register();
    }

    @Override
    public void unregister() {
        CustomItemManager.unregisterCustomItem("view_inventory_item");
        super.unregister();
    }

    @Override
    public CustomItem getItem() {
        return (CustomItemManager.findCustomItem("view_inventory_item"));
    }

    @Override
    public ItemStack getItemStack() {
        return ((ViewInventoryItem) getItem()).getItem();
    }

    @Override
    public String getPermission() {
        return "staff.permission.viewinventory";
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEntityEvent event) {
        if(!(event.getRightClicked() instanceof Player)) {
            return;
        }
        CustomItem cItem = getItem();
        Player player = event.getPlayer();
        if(!cItem.isItem(player.getItemInHand())) {
            return;
        }
        if(!player.hasPermission(getPermission()) || !StaffModeStorage.isInStaffMode(player)) {
            player.setItemInHand(null);
            player.sendMessage(ChatColor.translate("&c&l(!) &CYou do not have valid permission to use that item! It was removed!"));
            return;
        }
        Player entity = (Player) event.getRightClicked();
        player.openInventory(PlayerInventory.get(entity));
        event.setCancelled(true);
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 20F, 50F);
    }
}
