package me.letssee.staffmode.listeners;

import com.google.common.collect.Maps;
import me.letssee.staffmode.DiscordAPI;
import me.letssee.staffmode.StaffModePlugin;
import me.letssee.staffmode.storage.StaffModeStorage;
import me.letssee.staffmode.struct.ConfigSettings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class StaffModeListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(!StaffModeStorage.isInStaffMode(player)) {
            return;
        }
        StaffModeStorage.setStaffMode(player, false);
        event.getDrops().clear();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if(!StaffModeStorage.isInStaffMode(player)) {
            return;
        }
        if((boolean)ConfigSettings.getCachedValue("remove-staff-mode-on-death")) {
            StaffModeStorage.setStaffMode(player, false);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamaged(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if(!StaffModeStorage.isInStaffMode(player) || player.isOp()) {
            return;
        }
        if((boolean)ConfigSettings.getCachedValue("invincible-staff-mode")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(!StaffModeStorage.isInStaffMode(player) || player.isOp()) {
            return;
        }
        if(!(boolean)ConfigSettings.getCachedValue("place-blocks-staff-mode")) {
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(!StaffModeStorage.isInStaffMode(player) || player.isOp()) {
            return;
        }
        if(!(boolean)ConfigSettings.getCachedValue("place-blocks-staff-mode")) {
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getDamager();
        if(!StaffModeStorage.isInStaffMode(player) || player.isOp()) {
            return;
        }
        if(event.getEntity() instanceof Player && (boolean)ConfigSettings.getCachedValue("harm-players-staff-mode")) {
            event.setCancelled(true);
            return;
        }
        if(!(event.getEntity() instanceof Player) && (boolean)ConfigSettings.getCachedValue("harm-entities-staff-mode")) {
            event.setCancelled(true);
            return;
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!StaffModeStorage.isInStaffMode(player) || player.isOp()) {
            return;
        }
        if(!(boolean) ConfigSettings.getCachedValue("move-inventory-staff-mode")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(!StaffModeStorage.isInStaffMode(player) || player.isOp()) {
            return;
        }
        if((boolean)ConfigSettings.getCachedValue("drop-items-staff-mode")) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityFocus(EntityTargetEvent event) {
        if(event.getTarget() instanceof Player && StaffModeStorage.isInStaffMode((Player)event.getTarget())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        if(!DiscordAPI.isEnabled()){
            return;
        }
        Player player = event.getPlayer();
        if(!player.hasPermission("staff.permission.log")) {
            return;
        }
        Map<String, String> replace = Maps.newHashMap();
        replace.put("%player%", player.getName());
        replace.put("%server%", StaffModePlugin.getInstance().getServer().getServerName());
        for(Player pl : Bukkit.getOnlinePlayers()) {
            if(!pl.hasPermission("staff.permission.log")) {
                continue;
            }
            ConfigSettings.sendMessage(pl, "staff-login-message", replace);
        }
        TextChannel channel = DiscordAPI.getDefaultTextChannel();
        EmbedBuilder builder = new EmbedBuilder();
        String text = DiscordAPI.getFormattedMessage((String)DiscordAPI.getCachedValue("staff-login-message"), replace);
        builder.setDescription(text);
        builder.setTitle((String)DiscordAPI.getCachedValue("staff-login-header"));
        builder.setFooter((String)DiscordAPI.getCachedValue("staff-login-footer"));
        channel.sendMessage(builder.build()).queue();
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        if(!DiscordAPI.isEnabled()){
            return;
        }
        Player player = event.getPlayer();
        if(!player.hasPermission("staff.permission.log")) {
            return;
        }
        Map<String, String> replace = Maps.newHashMap();
        replace.put("%player%", player.getName());
        replace.put("%server%", StaffModePlugin.getInstance().getServer().getServerName());
        for(Player pl : Bukkit.getOnlinePlayers()) {
            if(!pl.hasPermission("staff.permission.log")) {
                continue;
            }
            ConfigSettings.sendMessage(pl, "staff-logout-message", replace);
        }
        TextChannel channel = DiscordAPI.getDefaultTextChannel();
        EmbedBuilder builder = new EmbedBuilder();
        String text = DiscordAPI.getFormattedMessage((String)DiscordAPI.getCachedValue("staff-logout-message"), replace);
        builder.setDescription(text);
        builder.setTitle((String)DiscordAPI.getCachedValue("staff-logout-header"));
        builder.setFooter((String)DiscordAPI.getCachedValue("staff-logout-footer"));
        channel.sendMessage(builder.build()).queue();
    }
}
