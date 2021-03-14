package me.letssee.staffmode.tasks;

import com.google.common.collect.Maps;
import me.letssee.staffmode.storage.FrozenPlayerStorage;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import me.letssee.staffmode.struct.FrozenPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FreezePlayersTask extends BukkitRunnable {

    @Override
    public void run() {
        if((boolean)ConfigSettings.getCachedValue("allow-move-frozen")) {
            return;
        }
        for(Map.Entry<UUID, FrozenPlayer> entry : FrozenPlayerStorage.getFrozenPlayers().entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            FrozenPlayer fp = entry.getValue();
            if(fp.isSimilarLocation(player.getLocation())) {
                continue;
            }
            Location location = fp.getLocation();
            location.setYaw(player.getLocation().getYaw());
            location.setPitch(player.getLocation().getPitch());
            player.teleport(location);
            ConfigSettings.sendMessage(player, "no-teleport-frozen-message", Maps.newHashMap());
        }
    }
}
