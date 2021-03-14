package me.letssee.staffmode.struct;

import me.letssee.staffmode.inventories.ReportInventory;
import me.letssee.staffmode.storage.ReportStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Report {

    private UUID reporter;
    private UUID reported;
    private String msg;
    public Report(UUID reporter, UUID reported, String msg) {
        this.reporter = reporter;
        this.reported = reported;
        this.msg = ChatColor.strip(ChatColor.translate(msg));
    }

    public Report(Player reporter, Player reported, String msg) {
        this.reporter = reporter.getUniqueId();
        this.reported = reported.getUniqueId();
        this.msg = ChatColor.strip(ChatColor.translate(msg));
    }

    public String getMessage() {
        return msg;
    }

    public OfflinePlayer getReported() {
        return Bukkit.getOfflinePlayer(reported);
    }

    public OfflinePlayer getReporter() {
        return Bukkit.getOfflinePlayer(reporter);
    }

    public String stringVal() {
        return this.reporter.toString() + ":" + this.reported.toString() + ":" + this.msg;
    }

    public void delete() {
        ReportStorage.removeReport(this);
    }

    public static Report parse(String toParse) {
        String[] split = toParse.split(":");
        return new Report(UUID.fromString(split[0]), UUID.fromString(split[1]), split[2]);
    }
}
