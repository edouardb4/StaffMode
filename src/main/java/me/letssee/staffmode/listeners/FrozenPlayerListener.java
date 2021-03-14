package me.letssee.staffmode.listeners;

import com.google.common.collect.Maps;
import me.letssee.staffmode.storage.FrozenPlayerStorage;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class FrozenPlayerListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(!FrozenPlayerStorage.isFrozen(player)) {
            return;
        }
        if((boolean) ConfigSettings.getCachedValue("allow-chat-frozen")) {
            return;
        }
        ConfigSettings.sendMessage(player, "no-chat-frozen-message", Maps.newHashMap());
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if(!FrozenPlayerStorage.isFrozen(player)) {
            return;
        }
        ConfigSettings.sendMessage(player, "no-damage-player-frozen-message", Maps.newHashMap());
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if(player.isOp()) {
            return;
        }
        if(!FrozenPlayerStorage.isFrozen(player)) {
            return;
        }
        if((boolean) ConfigSettings.getCachedValue("allow-commands-frozen")) {
            return;
        }
        ConfigSettings.sendMessage(player, "no-commands-frozen-message", Maps.newHashMap());
        event.setCancelled(true);
    }

}
