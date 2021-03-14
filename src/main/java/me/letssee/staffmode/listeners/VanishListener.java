package me.letssee.staffmode.listeners;

import me.letssee.staffmode.staff.UtilityManager;
import me.letssee.staffmode.storage.VanishModeStorage;
import me.letssee.staffmode.struct.ConfigSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishListener implements Listener {
    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(VanishModeStorage.isVanished(player)) {
            VanishModeStorage.setVanished(player, false);
        }
        VanishModeStorage.removeAllInstances(player);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPermission(UtilityManager.findUtility("vanish_utility").getPermission())) {
            return;
        }
        if((boolean) ConfigSettings.getCachedValue("enable-vanish-join")) {
            VanishModeStorage.setVanished(player, true);
        }
        VanishModeStorage.enableVanishedPlayers(player);
    }
}
