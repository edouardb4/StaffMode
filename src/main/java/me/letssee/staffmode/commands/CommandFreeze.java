package me.letssee.staffmode.commands;

import com.google.common.collect.Maps;
import me.letssee.staffmode.staff.UtilityManager;
import me.letssee.staffmode.storage.FrozenPlayerStorage;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandFreeze implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) {
            return false;
        }
        Player player = (Player) commandSender;
        if(!player.hasPermission(UtilityManager.findUtility("freeze_utility").getPermission())) {
            ConfigSettings.sendMessage(player, "invalid-permission-freeze-command", Maps.newHashMap());
        }
        if(args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null || !target.isOnline()) {
                ConfigSettings.sendMessage(player, "freeze-command-player-offline", Maps.newHashMap());
                return false;
            }
            FrozenPlayerStorage.setFrozen(target, player, FrozenPlayerStorage.isFrozen(target));
            return false;
        }
        player.sendMessage(ChatColor.translate("&eUsage: &f/freeze <player>"));
        return false;
    }
}
