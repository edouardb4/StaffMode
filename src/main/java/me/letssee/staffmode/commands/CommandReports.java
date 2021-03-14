package me.letssee.staffmode.commands;

import com.google.common.collect.Maps;
import me.letssee.staffmode.inventories.ReportInventory;
import me.letssee.staffmode.struct.ConfigSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandReports implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("staff.permission.reports")) {
            ConfigSettings.sendMessage(player, "no-permission-reports-command", Maps.newHashMap());
            return false;
        }
        Inventory inventory = ReportInventory.getInventory(0);
        player.openInventory(inventory);
        return false;
    }
}
