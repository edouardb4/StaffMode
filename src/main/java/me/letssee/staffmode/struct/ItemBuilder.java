package me.letssee.staffmode.struct;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

public class ItemBuilder {
    private ItemStack item;
    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
    }

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder material(Material material) {
        item.setType(material);
        return this;
    }

    public ItemBuilder durability(short durability) {
        item.setDurability((short)durability);
        return this;
    }

    public ItemBuilder displayname(String displayName) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translate(displayName));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String... loreToAdd) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
        for(String line : loreToAdd) {
            lore.add(ChatColor.translate(line));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        for(int i = 0; i < lore.size(); i++ ) {
            lore.set(i, ChatColor.translate(lore.get(i)));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }
}
