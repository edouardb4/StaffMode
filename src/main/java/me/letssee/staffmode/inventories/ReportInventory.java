package me.letssee.staffmode.inventories;

import com.google.common.collect.Lists;
import me.letssee.staffmode.storage.ReportStorage;
import me.letssee.staffmode.struct.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class ReportInventory {
    public static Inventory getInventory(int page) {
        Map<Integer, List<Report>> split = ReportStorage.split(45);
        List<Report> reports = split.containsKey(page) ? split.get(page) : Lists.newArrayList();
        int size = (reports.size() / 9) * 9;
        if(size < reports.size() || size == 0) {
            size += 9;
        }
        InventoryWrapper w_Inv = new InventoryWrapper("&7Reports", (int) size + 9);
        for(int i = 0; i < reports.size(); i++) {
            w_Inv.setContent(i, reportToItemStack(reports.get(i)));
        }
        w_Inv.setContents(new ItemBuilder(Material.STAINED_GLASS_PANE).durability((short)7).displayname("&7").build(),
                w_Inv.getSize() - 9, w_Inv.getSize() - 1);
        if(page > 0) {
            w_Inv.setContent(w_Inv.getSize() - 9, getPreviousPageItemStack(page - 1));
        }
        if(split.size() - 1 > page) {
            w_Inv.setContent(w_Inv.getSize() - 1, getNextPageItemStack(page + 1));
        }
        return w_Inv.get();
    }

    private static ItemStack getNextPageItemStack(int page) {
        ItemStack item = new ItemBuilder(Material.ARROW).displayname(ChatColor.translate("&a&l------->")).build();
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagWrapper tag = new NBTTagWrapper();
        tag.setInt("page", page);
        wrapper.setNBTBase("page", tag.get());
        return wrapper.get();
    }

    private static ItemStack getPreviousPageItemStack(int page) {
        ItemStack item = new ItemBuilder(Material.ARROW).displayname(ChatColor.translate("&a&l<-------")).build();
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagWrapper tag = new NBTTagWrapper();
        tag.setInt("page", page);
        wrapper.setNBTBase("page", tag.get());
        return wrapper.get();
    }

    private static ItemStack reportToItemStack(Report report) {
        List<String> message = Lists.newArrayList();
        String s_Message = report.getMessage();
        int i;
        for(i = 0; i < s_Message.length() - 40; i+= 40) {
            message.add(ChatColor.translate("&f") + s_Message.substring(i, i + 40));
        }
        if(i < s_Message.length()) {
            message.add(ChatColor.translate("&f") + s_Message.substring(i, s_Message.length()));
        }
        message.add("");
        message.add(ChatColor.translate("&f&l~ &f" + report.getReporter().getName()));
        ItemStack item = new ItemBuilder(Material.PAPER).displayname(ChatColor.translate("&a&l" + report.getReported().getName()))
        .lore(message).build();
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagWrapper tag = new NBTTagWrapper();
        tag.setString("report_string", report.stringVal());
        wrapper.setNBTBase("report_nbt", tag.get());
        return wrapper.get();
    }
}
