package me.letssee.staffmode;

import com.google.common.collect.Maps;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ConfigurationWrapper;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Map;

public class DiscordAPI {
    private static String TOKEN;
    private static ConfigurationWrapper config;
    private static boolean enabled;
    private static JDA client;
    private static Map<String, Object> cachedValues;

    public static void onEnable() {
        config = new ConfigurationWrapper("discord-token.yml", StaffModePlugin.getInstance());
        cachedValues = Maps.newHashMap();
        TOKEN = config.getString("token");
        if(TOKEN.isEmpty()) {
            return;
        }
        try {
            client = JDABuilder.createDefault(TOKEN).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE).build();
            client.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
            if(!config.getString("activity").isEmpty()) {
                client.getPresence().setActivity(Activity.playing(config.getString("activity")));
            }
        } catch (LoginException e) {
            e.printStackTrace();
        }
        if(client == null) {
            Bukkit.getLogger().info("[MCStaff] Discord Client is NULL");
            return;
        }
        Bukkit.getLogger().info("[MCStaff] Discord API Successfully enabled");
        enabled = true;
    }

    public static Object getCachedValue(String path) {
        if (cachedValues.containsKey(path)) {
            return cachedValues.get(path);
        }
        cachedValues.put(path, config.getYamlConf().get(path));
        return getCachedValue(path);
    }

    public static void reload() {
        config.reload();
        onDisable();
        onEnable();
    }

    public static void onDisable() {
        client = null;
        config = null;
        enabled = false;
    }

    public static TextChannel getDefaultTextChannel() {
        return client.getTextChannelById((String)getCachedValue("channel"));
    }

    public static String getFormattedMessage(String message, Map<String, String> replace) {
        for(Map.Entry<String, String> entry : replace.entrySet()) {
            message = message.replaceAll(entry.getKey(), entry.getValue());
        }
        return message;
    }

    public static void createMessage(String message, Map<String, String> replace) {
        TextChannel channel = client.getTextChannelById((String)getCachedValue("channel"));
        if(channel == null) {
            Bukkit.getLogger().info("[MCStaff] Discord Channel is NULL");
            return;
        }
        message = getFormattedMessage(message, replace);
        channel.sendMessage(message).queue();
    }

    public static JDA getClient() {
        return client;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
