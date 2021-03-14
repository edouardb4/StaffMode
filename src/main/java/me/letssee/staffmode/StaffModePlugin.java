package me.letssee.staffmode;

import com.google.common.collect.Lists;
import me.letssee.staffmode.citems.CustomItemManager;
import me.letssee.staffmode.commands.*;
import me.letssee.staffmode.listeners.*;
import me.letssee.staffmode.staff.*;
import me.letssee.staffmode.storage.FrozenPlayerStorage;
import me.letssee.staffmode.storage.ReportStorage;
import me.letssee.staffmode.storage.StaffModeStorage;
import me.letssee.staffmode.storage.VanishModeStorage;
import me.letssee.staffmode.struct.ConfigSettings;
import me.letssee.staffmode.struct.Report;
import me.letssee.staffmode.tasks.FreezePlayersTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class StaffModePlugin extends JavaPlugin {

    private static StaffModePlugin StaffModePlugin;
    private static List<BukkitRunnable> tasks;

    @Override
    public void onEnable() {
        StaffModePlugin = this;
        ConfigSettings.onEnable();
        CustomItemManager.onEnable();
        ReportStorage.onEnable();
        DiscordAPI.onEnable();
        registerCommands();
        registerListeners();
        registerTasks();
        registerUtilities();
    }

    @Override
    public void onDisable() {
        unregisterTasks();
        UtilityManager.unregisterAllUtilities();
        CustomItemManager.onDisable();
        ConfigSettings.onDisable();
        FrozenPlayerStorage.onDisable();
        StaffModeStorage.onDisable();
        VanishModeStorage.onDisable();
        ReportStorage.onDisable();
        DiscordAPI.onDisable();
        StaffModePlugin = null;
    }

    private void registerTasks() {
        tasks = Lists.newArrayList();
        tasks.add(new FreezePlayersTask());
        tasks.get(0).runTaskTimer(this, 5, 5);
    }

    private void unregisterTasks() {
        for(BukkitRunnable task : tasks) {
            task.cancel();
        }
        tasks.clear();
    }

    private void registerCommands() {
        this.getCommand("freeze").setExecutor(new CommandFreeze());
        this.getCommand("reloadstaffconfig").setExecutor(new CommandReloadStaffConfig());
        this.getCommand("staffmode").setExecutor(new CommandStaffMode());
        this.getCommand("vanish").setExecutor(new CommandVanish());
        this.getCommand("report").setExecutor(new CommandReport());
        this.getCommand("reports").setExecutor(new CommandReports());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new FrozenPlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ViewInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new StaffChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new VanishListener(), this);
        Bukkit.getPluginManager().registerEvents(new StaffModeListener(), this);
        Bukkit.getPluginManager().registerEvents(new ReportInventoryListener(), this);
    }

    private void registerUtilities() {
        new FreezeUtility("freeze_utility").register();
        new SpeedBoostUtility("speed_boost_utility").register();
        new InventoryViewUtility("inventory_view_utility").register();
        new VanishUtility("vanish_utility").register();
        new FlyUtility("fly_utility").register();
    }

    public static StaffModePlugin getInstance() {
        return StaffModePlugin;
    }

}
