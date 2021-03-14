package me.letssee.staffmode.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.letssee.staffmode.staff.StaffPlayer;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import me.letssee.staffmode.struct.FrozenPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class VanishModeStorage {
    private static Map<UUID, Set<UUID>> players = Maps.newHashMap();

    public static Map<UUID, Set<UUID>> getVanishedPlayers() {
        return players;
    }

    public static void onDisable() {
        for(UUID uuid : players.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            removePlayer(player);
        }
        players.clear();
    }

    public static void addPlayer(Player player) {
        List<String> message = (List<String>) ConfigSettings.getCachedValue("vanish-enable-message");
        message.stream().forEach(msg -> player.sendMessage(ChatColor.translate(msg)));
        UUID uuid = player.getUniqueId();
        Set<UUID> vanished = Sets.newHashSet();
        for(Player pl : Bukkit.getOnlinePlayers()) {
            if(pl.hasPermission("staff.permission.vanish") && !(boolean)ConfigSettings.getCachedValue("vanish-to-other-staff")) {
                continue;
            }
            vanished.add(pl.getUniqueId());
            pl.hidePlayer(player);
        }
        players.put(uuid, vanished);
    }

    public static void removePlayer(Player player) {
        List<String> message = (List<String>) ConfigSettings.getCachedValue("vanish-disable-message");
        message.stream().forEach(msg -> player.sendMessage(ChatColor.translate(msg)));
        Set<UUID> uuids = players.remove(player.getUniqueId());
        for(UUID uuid : uuids) {
            Player pl = Bukkit.getPlayer(uuid);
            pl.showPlayer(player);
        }
    }

    public static void removeAllInstances(Player player) {
        UUID uuid = player.getUniqueId();
        for(Map.Entry<UUID, Set<UUID>> entry : players.entrySet()) {
            Set<UUID> uuids = entry.getValue();
            if(!uuids.contains(uuid)) {
                continue;
            }
            uuids.remove(player.getUniqueId());
            player.showPlayer(Bukkit.getPlayer(entry.getKey()));
        }
        return;
    }

    public static void enableVanishedPlayers(Player player) {
        if(player.hasPermission("staff.permission.vanish") && !(boolean)ConfigSettings.getCachedValue("vanish-to-other-staff")) {
            return;
        }
        for(Map.Entry<UUID, Set<UUID>> entry : players.entrySet()) {
            Set<UUID> uuids = entry.getValue();
            uuids.add(player.getUniqueId());
            player.hidePlayer(Bukkit.getPlayer(entry.getKey()));
        }
    }

    public static void setVanished(Player player, boolean active) {
        if(!active) {
            addPlayer(player);
            return;
        }
        removePlayer(player);
    }

    public static boolean isVanished(Player player) {
        return isContained(player.getUniqueId());
    }

    public static boolean isContained(UUID uuid) {
        return players.containsKey(uuid);
    }

    public static void removePlayer(UUID uuid) {
        players.remove(uuid);
    }
}
