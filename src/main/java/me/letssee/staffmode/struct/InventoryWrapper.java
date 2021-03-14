package me.letssee.staffmode.struct;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class InventoryWrapper {

    private int size;
    private Map<Integer, ItemStack> contents;
    private String title;

    public InventoryWrapper(Inventory inventory) {
        this.size = inventory.getSize();
        this.title = inventory.getTitle();
        copyContents(inventory);
    }

    public InventoryWrapper(String title, int size) {
        this.title = ChatColor.translate(title);
        this.size = size;
        this.contents = Maps.newHashMap();
    }

    public InventoryWrapper(String title, int columns, int rows) {
        this.title = title;
        this.size = columns * rows;
        this.contents = Maps.newHashMap();
    }

    public int getSize() {
        return this.size;
    }

    public ItemStack getContent(int pos) {
        return this.contents.get(pos);
    }

    public Map<Integer, ItemStack> getContents() {
        return this.contents;
    }

    public String getTitle() {
        return this.title;
    }

    private void copyContents(Inventory inventory) {
        Map<Integer, ItemStack> contents = Maps.newHashMap();
        for(int i = 0; i < inventory.getSize(); i++) {
            contents.put(i, inventory.getItem(i));
        }
        this.contents = contents;
    }

    public void setContent(int pos, ItemStack item) {
        this.contents.put(pos, item);
    }

    public void setContents(ItemStack item, List<Integer> values) {
        for(int pos : values) {
            this.contents.put(pos, item);
        }
    }

    public void setContents(ItemStack item, int from, int to) {
        for(int i = from; i <= to; i++) {
            this.contents.put(i, item);
        }
    }

    public Inventory get() {
        Inventory inventory = Bukkit.createInventory(null, this.size, this.title);
        for(Entry<Integer, ItemStack> entry : contents.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        return inventory;
    }
}
