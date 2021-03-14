package me.letssee.staffmode.storage;

import com.google.common.collect.Maps;
import me.letssee.staffmode.staff.StaffPlayer;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import me.letssee.staffmode.struct.FrozenPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StaffModeStorage {
    private static Map<UUID, StaffPlayer> players = Maps.newHashMap();

    public static Map<UUID, StaffPlayer> getFrozenPlayers() {
        return players;
    }

    public static void onDisable() {
        for(UUID uuid : players.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            removePlayer(player);
        }
        players.clear();
    }

    public static StaffPlayer getStaffPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static void addPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        if(players.containsKey(uuid)){
            return;
        }
        ConfigSettings.sendMessage(player , "staff-mode-enable-message", Maps.newHashMap());
        StaffPlayer sp = new StaffPlayer(player);
        sp.clearAllContents();
        sp.setupContents();
        players.put(uuid, sp);
        if((boolean)ConfigSettings.getCachedValue("creative-staff-mode")) {
            player.setGameMode(GameMode.CREATIVE);
        }
    }

    public static void removePlayer(Player player) {
        StaffPlayer sp = players.remove(player.getUniqueId());
        sp.restoreInventory();
        ConfigSettings.sendMessage(player , "staff-mode-disable-message", Maps.newHashMap());
        if((boolean)ConfigSettings.getCachedValue("teleport-original-location")) {
            player.teleport(sp.getLocation());
        }
        if((boolean)ConfigSettings.getCachedValue("creative-staff-mode")) {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    public static void setStaffMode(Player player, boolean active) {
        if(!active) {
            addPlayer(player);
            return;
        }
        removePlayer(player);
    }

    public static boolean isInStaffMode(Player player) {
        return isContained(player.getUniqueId());
    }

    public static boolean isContained(UUID uuid) {
        return players.containsKey(uuid);
    }

    public static void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

}
