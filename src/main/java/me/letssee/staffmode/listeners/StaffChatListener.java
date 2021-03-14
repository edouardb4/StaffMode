package me.letssee.staffmode.listeners;

import com.google.common.collect.Maps;
import me.letssee.staffmode.storage.StaffModeStorage;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.Map;

public class StaffChatListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        if(!msg.startsWith("@")) {
            return;
        }
        Player player = event.getPlayer();
        if(!player.hasPermission("staff.permission.chat")) {
            return;
        }
        msg = msg.substring(1, msg.length());
        if(msg.length() <= 0) {
            return;
        }
        String prefix = (String) ConfigSettings.getCachedValue("staff-mode-prefix");
        prefix = prefix.replaceAll("%player%", player.getName());
        msg = prefix.replaceAll("%message%", msg);
        for(Player pl : Bukkit.getOnlinePlayers()) {
            if(!pl.hasPermission("staff.permission.chat")) {
                continue;
            }
            pl.sendMessage(ChatColor.translate(msg));
        }
        event.setCancelled(true);
    }

}
