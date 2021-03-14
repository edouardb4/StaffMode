package me.letssee.staffmode.struct;

import com.google.common.collect.Maps;
import me.letssee.staffmode.StaffModePlugin;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ConfigSettings {
    private static ConfigurationWrapper config;
    private static Map<String, Object> cachedValues;

    public static void onEnable() {
        cachedValues = Maps.newHashMap();
        config = new ConfigurationWrapper("settings.yml", StaffModePlugin.getInstance());
    }

    public static void onDisable() {
        cachedValues.clear();
    }

    public static void reload() {
        onDisable();
        onEnable();
    }

    public static Object getCachedValue(String path) {
        if(cachedValues.containsKey(path)) {
            return cachedValues.get(path);
        }
        cachedValues.put(path, config.getYamlConf().get(path));
        return getCachedValue(path);
    }

    public static void sendMessage(Player player, String path, Map<String, String> replace) {
        List<String> message = (List<String>) getCachedValue(path);
        if(message.size() == 1 && message.get(0).length() == 0) {
            return;
        }
        for(String msg : message){
            for(Map.Entry<String, String> entry : replace.entrySet()) {
                msg = msg.replaceAll(entry.getKey(), entry.getValue());
            }
            player.sendMessage(ChatColor.translate(msg));
        }
    }

    private static void cache(String path) {
        cachedValues.put(path, config.getYamlConf().get(path));
    }
}
