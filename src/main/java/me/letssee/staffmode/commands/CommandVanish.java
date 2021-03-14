package me.letssee.staffmode.commands;

import com.google.common.collect.Maps;
import me.letssee.staffmode.staff.UtilityManager;
import me.letssee.staffmode.storage.VanishModeStorage;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandVanish implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission(UtilityManager.findUtility("vanish_utility").getPermission())) {
            ConfigSettings.sendMessage(player, "vanish-no-permission-message", Maps.newHashMap());
            return false;
        }
        VanishModeStorage.setVanished(player, VanishModeStorage.isVanished(player));
        return false;
    }
}
