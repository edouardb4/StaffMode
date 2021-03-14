package me.letssee.staffmode.staff;

import me.letssee.staffmode.citems.CustomItem;
import me.letssee.staffmode.citems.CustomItemManager;
import me.letssee.staffmode.inventories.PlayerInventory;
import me.letssee.staffmode.staff.items.SpeedBoostItem;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class SpeedBoostUtility extends StaffUtility implements Listener {


    public SpeedBoostUtility(String name) {
        super(name);
    }

    @Override
    public void register() {
        CustomItemManager.registerCustomItem(new SpeedBoostItem("speed_boost_item"));
        registerListener(new SpeedBoostUtility(null));
        super.register();
    }

    @Override
    public void unregister() {
        CustomItemManager.unregisterCustomItem("speed_boost_item");
        super.unregister();
    }

    @Override
    public CustomItem getItem() {
        return (CustomItemManager.findCustomItem("speed_boost_item"));
    }

    @Override
    public ItemStack getItemStack() {
        return ((SpeedBoostItem) getItem()).getItem();
    }

    @Override
    public String getPermission() {
        return "staff.permission.speedboost";
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
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
        player.setVelocity(player.getLocation().getDirection().multiply(1.5f));
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 20F, 50F);
    }
}
