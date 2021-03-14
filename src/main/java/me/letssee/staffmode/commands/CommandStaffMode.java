package me.letssee.staffmode.commands;

import me.letssee.staffmode.storage.StaffModeStorage;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandStaffMode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if(!sender.hasPermission("staff.permission.staffmode")) {
            List<String> message = (List<String>) ConfigSettings.getCachedValue("invalid-permission-staff-mode");
            message.stream().forEach(msg -> player.sendMessage(ChatColor.translate(msg)));
            return false;
        }
        StaffModeStorage.setStaffMode(player, StaffModeStorage.isInStaffMode(player));
        //.playSound(player.getLocation(), Sound.BAT_LOOP, 1F, 1.5F);
        return false;
    }
}
