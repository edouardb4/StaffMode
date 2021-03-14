package me.letssee.staffmode.commands;

import me.letssee.staffmode.DiscordAPI;
import me.letssee.staffmode.citems.CustomItemManager;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReloadStaffConfig implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("staff.permission.reloadconfig")) {
            return false;
        }
        CustomItemManager.reloadConfig();
        ConfigSettings.reload();
        DiscordAPI.reload();
        sender.sendMessage(ChatColor.translate("&fReloaded config..."));
        return false;
    }
}
