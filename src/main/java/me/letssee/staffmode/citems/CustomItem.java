package me.letssee.staffmode.citems;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.letssee.staffmode.enums.ItemCType;
import me.letssee.staffmode.struct.ChatColor;
import me.letssee.staffmode.struct.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import com.google.common.collect.Lists;

public class CustomItem {

    private String internalName;

    public CustomItem(String internalName) {
        this.internalName = internalName;
    }

    public String getInternalName() {
        return this.internalName;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void gen() {
        YamlConfiguration yamlConf = CustomItemManager.getConfig().getYamlConf();
        if(!yamlConf.contains(internalName + ".material")) {
            yamlConf.set(internalName + ".material", "DIAMOND");
        }
        if(!yamlConf.contains(internalName + ".display")) {
            yamlConf.set(internalName + ".display", "");
        }
        if(!yamlConf.contains(internalName + ".lore")) {
            yamlConf.set(internalName + ".lore", Lists.newArrayList());
        }
        if(!yamlConf.contains(internalName + ".inventory-slot")) {
            yamlConf.set(internalName + ".inventory-slot", 0);

        }
        CustomItemManager.getConfig().save(yamlConf);
    }

    public Object getConfigValue(String path) {
        return CustomItemManager.getConfig().getYamlConf().get(path);
    }

    public void addDoesntExist(String path, Object value) {
        YamlConfiguration yamlConf = CustomItemManager.getConfig().getYamlConf();
        if(yamlConf.contains(path)) {
            return;
        }
        yamlConf.set(path, value);
        CustomItemManager.getConfig().save(yamlConf);
    }

    public void addToConfig(String path, Object value) {
        YamlConfiguration yamlConf = CustomItemManager.getConfig().getYamlConf();
        if(!yamlConf.contains(path)) {
            yamlConf.set(path, value);
        }
        CustomItemManager.getConfig().save(yamlConf);
    }

    public boolean isItem(ItemStack item) {
        return false;
    }

    public boolean onClick(InventoryClickEvent event, ItemCType type) {
        return false;
    }

    public boolean onInteract(PlayerInteractEvent event) {
        return false;
    }

    public int getInventorySlot() {
        YamlConfiguration yamlConf = CustomItemManager.getConfig().getYamlConf();
        return yamlConf.getInt(internalName + ".inventory-slot");
    }

    public ItemStack getItem(Map<String, Object> replace) {
        YamlConfiguration yamlConf = CustomItemManager.getConfig().getYamlConf();
        List<String> lore = yamlConf.getStringList(internalName + ".lore");
        for(int i = 0; i < lore.size(); i++) {
            lore.set(i, ChatColor.translate(lore.get(i)));
            if(replace != null) {
                for(Entry<String, Object> entry : replace.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    lore.set(i, lore.get(i).replaceAll("%" + key + "%", value.toString()));
                }
            }
        }
        String materialString = yamlConf.getString(internalName + ".material");
        Material material = Material.matchMaterial(materialString.indexOf(":") != -1 ? materialString.split(":") [0] : materialString);
        short id = (short) (materialString.indexOf(":") != -1 ? Integer.valueOf(materialString.split(":") [1]) : 0);
        String displayName = ChatColor.translate(yamlConf.getString(internalName + ".display"));
        if(replace != null) {
            for(Entry<String, Object> entry : replace.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                displayName = displayName.replaceAll("%" + key + "%", value.toString());
            }
        }
        ItemStack item = new ItemBuilder(material).durability(id).displayname(displayName).lore(lore).build();
        return item;
    }

}
