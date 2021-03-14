package me.letssee.staffmode.staff;

import me.letssee.staffmode.citems.CustomItem;
import me.letssee.staffmode.citems.CustomItemManager;
import me.letssee.staffmode.staff.items.FlyItem;
import me.letssee.staffmode.staff.items.SpeedBoostItem;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FlyUtility extends StaffUtility implements Listener {


    public FlyUtility(String name) {
        super(name);
    }

    @Override
    public void register() {
        CustomItemManager.registerCustomItem(new FlyItem("fly_item"));
        registerListener(new FlyUtility(null));
        super.register();
    }

    @Override
    public void unregister() {
        CustomItemManager.unregisterCustomItem("fly_item");
        super.unregister();
    }

    @Override
    public CustomItem getItem() {
        return (CustomItemManager.findCustomItem("fly_item"));
    }

    @Override
    public ItemStack getItemStack() {
        return ((FlyItem) getItem()).getItem();
    }

    @Override
    public String getPermission() {
        return "staff.permission.fly";
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
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
        player.setAllowFlight(!player.getAllowFlight());
        event.setCancelled(true);
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 20F, 50F);
    }
}
