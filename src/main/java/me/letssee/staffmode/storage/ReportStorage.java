package me.letssee.staffmode.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.letssee.staffmode.StaffModePlugin;
import me.letssee.staffmode.struct.ConfigurationWrapper;
import me.letssee.staffmode.struct.Report;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReportStorage {
    public static Map<UUID, List<Report>> reports;
    private static ConfigurationWrapper config;

    public static void onEnable() {
        reports = Maps.newHashMap();
        config = new ConfigurationWrapper("reports.yml", StaffModePlugin.getInstance());
        for(String s_Report : config.getStringList("reports")) {
            Report report = Report.parse(s_Report);
            UUID u_Reporter = report.getReporter().getUniqueId();
            List<Report> l_Reports = reports.containsKey(u_Reporter) ? reports.get(u_Reporter) : Lists.newArrayList();
            reports.put(u_Reporter, l_Reports);
        }
    }

    public static void onDisable() {
        List<String> list = Lists.newArrayList();
        for(List<Report> l_Reports : reports.values()) {
            l_Reports.stream().forEach(report -> list.add(report.stringVal()));
        }
        config.setStringList("reports", list);
    }

    /*
    * @param count is the reports per array index.
    */
    public static Map<Integer, List<Report>> split(int count) {
        List<Report> reports = Lists.newArrayList();
        for(List<Report> loReports : ReportStorage.reports.values()) {
            loReports.stream().forEach(report -> reports.add(report));
        }
        int size = (int) Math.ceil((double) reports.size() / count);
        if(size > count) {
            size += count - size % count;
        }
        Map<Integer, List<Report>> map = Maps.newHashMap();
        for(int i = 0; i < size; i++) {
            map.put(i, Lists.newArrayList());
        }
        for(int i = 0; i < reports.size(); i++) {
            int index = i / 10;
            map.get(index).add(reports.get(i));
        }
        return map;
    }

    public static boolean isDuplicatedReport(Report report) {
        UUID uuid = report.getReporter().getUniqueId();
        List<Report> loReports = reports.containsKey(uuid) ? reports.get(uuid) : Lists.newArrayList();
        String msg = report.getMessage();
        for(int i = 0; i < loReports.size(); i++) {
            if(loReports.get(i).getMessage().equals(msg)) {
                return true;
            }
        }
        return false;
    }

    public static void addReport(Report report) {
        UUID uuid = report.getReporter().getUniqueId();
        List<Report> loReports = reports.containsKey(uuid) ? reports.get(uuid) : Lists.newArrayList();
        loReports.add(report);
        reports.put(report.getReporter().getUniqueId(), loReports);
    }

    public static void removeReport(Report report) {
        UUID uuid = report.getReporter().getUniqueId();
        List<Report> loReports = reports.containsKey(uuid) ? reports.get(uuid) : Lists.newArrayList();
        String msg = report.getMessage();
        for(int i = 0; i < loReports.size(); i++) {
            if(loReports.get(i).getMessage().equals(msg)) {
                loReports.remove(i);
                return;
            }
        }
    }

    public static List<Report> findReportsFromUser(UUID uuid) {
        return reports.containsKey(uuid) ? reports.get(uuid) : Lists.newArrayList();
    }

    public static List<Report> findReportsFromUser(OfflinePlayer player) {
        return findReportsFromUser(player.getUniqueId());
    }
}
