package me.letssee.staffmode.commands;

import com.google.common.collect.Maps;
import me.letssee.staffmode.storage.ReportStorage;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigSettings;
import me.letssee.staffmode.struct.Report;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandReport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if(args.length > 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null || !target.isOnline()) {
                ConfigSettings.sendMessage(player, "report-player-offline", Maps.newHashMap());
                return false;
            }
            int maxReports = (int) ConfigSettings.getCachedValue("max-reports-per-player");
            if(maxReports > 0 && maxReports <= ReportStorage.findReportsFromUser(player).size()) {
                ConfigSettings.sendMessage(player, "max-reports-reached-message", Maps.newHashMap());
                return false;
            }
            StringBuilder sb = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                sb.append(args[i] + " ");
            }
            String msg = sb.toString().trim();
            Report report = new Report(player, target, msg);
            if(ReportStorage.isDuplicatedReport(report)) {
                ConfigSettings.sendMessage(player, "duplicated-report-message", Maps.newHashMap());
                return false;
            }
            ReportStorage.addReport(report);
            Map<String, String> replace = Maps.newHashMap();
            replace.put("%player%", target.getName());
            replace.put("%message%", msg);
            ConfigSettings.sendMessage(player, "successful-report-message", replace);
            return false;
        }
        player.sendMessage(ChatColor.translate("&f") + "/report [PLAYER] [ARGS]");
        return false;
    }
}
