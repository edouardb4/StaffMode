package me.letssee.staffmode.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import me.letssee.staffmode.struct.FrozenPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class FrozenPlayerStorage {
    private static Map<UUID, FrozenPlayer> frozenPlayers = Maps.newHashMap();

    public static Map<UUID, FrozenPlayer> getFrozenPlayers() {
        return frozenPlayers;
    }

    public static void onDisable() {
        for(UUID uuid : frozenPlayers.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            removePlayer(player, null);
        }
        frozenPlayers.clear();
    }

    public static void addPlayer(Player player, Player freezer) {
        UUID uuid = player.getUniqueId();
        if(frozenPlayers.containsKey(uuid)){
            return;
        }
        if(freezer != null) {
            Map<String, String> replace = Maps.newHashMap();
            replace.put("%player%", freezer.getName());
            ConfigSettings.sendMessage(player, "freeze-player-message", replace);
            replace.clear();
            replace.put("%player%", player.getName());
            ConfigSettings.sendMessage(freezer, "freeze-player-staff-message", replace);
        }
        frozenPlayers.put(uuid, new FrozenPlayer(player.getLocation()));
    }

    public static void removePlayer(Player player, Player unfreezer) {
        removePlayer(player.getUniqueId());
        if(unfreezer != null) {
            Map<String, String> replace = Maps.newHashMap();
            replace.put("%player%", unfreezer.getName());
            ConfigSettings.sendMessage(player, "unfreeze-player-message", replace);
            replace.clear();
            replace.put("%player%", player.getName());
            ConfigSettings.sendMessage(unfreezer, "unfreeze-player-staff-message", replace);
        }
    }

    public static void setFrozen(Player player, Player other, boolean frozen) {
        if(!frozen) {
            addPlayer(player, other);
            return;
        }
        removePlayer(player, other);
    }

    public static boolean isFrozen(Player player) {
        return isContained(player.getUniqueId());
    }

    public static boolean isContained(UUID uuid) {
        return frozenPlayers.containsKey(uuid);
    }

    public static void removePlayer(UUID uuid) {
        frozenPlayers.remove(uuid);
    }
}
