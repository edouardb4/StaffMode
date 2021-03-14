package me.letssee.staffmode.struct;
import java.lang.reflect.InvocationTargetException;

import me.letssee.staffmode.struct.NBTTagWrapper;
import me.letssee.staffmode.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class NBTWrapper {
    private Object item;
    private Object base;
    public NBTWrapper(ItemStack item) {
        try {
            Class<?> craftClazz = ReflectionUtils.getCBClass("inventory.CraftItemStack");
            Class<?> itemClazz = ReflectionUtils.getNMSClass("ItemStack");
            this.item = craftClazz.getMethod("asNMSCopy", ItemStack.class).invoke(null, new Object[] {item});
            this.base = itemClazz.getMethod("getTag").invoke(this.item, new Object[0]);
            if(this.base == null) {
                this.base = ReflectionUtils.getNMSClass("NBTTagCompound").getConstructor((Class[]) null).newInstance((Object[])null);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        InventoryCloseEvent
    }

    public NBTTagWrapper getTag(String name) {
        try {
            Class<?> clazz = ReflectionUtils.getNMSClass("NBTTagCompound");
            return new NBTTagWrapper(clazz.getDeclaredMethod("getCompound", String.class).invoke(this.base, new Object[] {name}));
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean hasKey(String path) {
        try {
            Class<?> clazz = ReflectionUtils.getNMSClass("NBTTagCompound");
            return (boolean) clazz.getDeclaredMethod("hasKey", String.class).invoke(this.base, new Object[] {path});
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    public NBTWrapper setNBTBase(String path, Object tag) {
        try {
            Class<?> clazz = ReflectionUtils.getNMSClass("NBTTagCompound");
            clazz.getDeclaredMethod("set", new Class[] {String.class, ReflectionUtils.getNMSClass("NBTBase")}).invoke(this.base, new Object[] {path, tag});
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ItemStack get() {
        try {
            Class<?> itemClazz = ReflectionUtils.getNMSClass("ItemStack");
            Class<?> craftClazz = ReflectionUtils.getCBClass("inventory.CraftItemStack");
            Class<?> clazz = ReflectionUtils.getNMSClass("NBTTagCompound");
            itemClazz.getDeclaredMethod("setTag", new Class[] {clazz}).invoke(this.item, new Object[]{this.base});
            return (ItemStack) craftClazz.getDeclaredMethod("asBukkitCopy", new Class[] {itemClazz}).invoke(null, new Object[] {this.item});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
