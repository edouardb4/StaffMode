package me.letssee.staffmode.struct;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import me.letssee.staffmode.utils.ReflectionUtils;
import org.bukkit.Bukkit;
public class NBTTagWrapper {
    private Object tag;
    private Class<?> clazz;
    public NBTTagWrapper() {
        try {
            this.clazz = ReflectionUtils.getNMSClass("NBTTagCompound");
            this.tag = clazz.getConstructor((Class[])null).newInstance((Object[])null);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public NBTTagWrapper(Object tag) {
        this.tag = tag;
        this.clazz = ReflectionUtils.getNMSClass("NBTTagCompound");
    }

    public void setInt(String path, int value) {
        try {
            Method handle = clazz.getDeclaredMethod("setInt", new Class[] {String.class, int.class});
            handle.invoke(this.tag, new Object[] {path, value});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public int getInt(String path) {
        try {
            Method handle = clazz.getDeclaredMethod("getInt", new Class[] {String.class});
            return (int) handle.invoke(this.tag, path);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setString(String path, String value) {
        try {
            Method handle = clazz.getDeclaredMethod("setString", new Class[] {String.class, String.class});
            handle.invoke(this.tag, new Object[] {path, value});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        try {
            Method handle = clazz.getDeclaredMethod("getString", new Class[] {String.class});
            return (String) handle.invoke(this.tag, new Object[] {path});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setFloat(String path, float value) {
        try {
            Method handle = clazz.getDeclaredMethod("setFloat", new Class[] {String.class, float.class});
            handle.invoke(this.tag, new Object[] {path, value});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public float getFloat(String path) {
        try {
            Method handle = clazz.getDeclaredMethod("getFloat", new Class[] {String.class});
            return (float) handle.invoke(this.tag, new Object[] {path});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setLong(String path, long value) {
        try {
            Method handle = clazz.getDeclaredMethod("setLong", new Class[] {String.class, long.class});
            handle.invoke(this.tag, new Object[] {path, value});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public long getLong(String path) {
        try {
            Method handle = clazz.getDeclaredMethod("getLong", new Class[] {String.class});
            return (long) handle.invoke(this.tag, new Object[] {path});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setDouble(String path, double value) {
        try {
            Method handle = clazz.getDeclaredMethod("setDouble", new Class[] {String.class, double.class});
            handle.invoke(this.tag, new Object[] {path, value});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public double getDouble(String path) {
        try {
            Method handle = clazz.getDeclaredMethod("getDouble", new Class[] {String.class});
            return (double) handle.invoke(this.tag, new Object[] {path});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setShort(String path, short value) {
        try {
            Method handle = clazz.getDeclaredMethod("setShort", new Class[] {String.class, short.class});
            handle.invoke(this.tag, new Object[] {path, value});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public short getShort(String path) {
        try {
            Method handle = clazz.getDeclaredMethod("getShort", new Class[] {String.class});
            return (short) handle.invoke(this.tag, new Object[] {path});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setBoolean(String path, boolean value) {
        try {
            Method handle = clazz.getDeclaredMethod("setBoolean", new Class[] {String.class, boolean.class});
            handle.invoke(this.tag, new Object[] {path, value});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(String path) {
        try {
            Method handle = clazz.getDeclaredMethod("getBoolean", new Class[] {String.class});
            return (boolean) handle.invoke(this.tag, new Object[] {path});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasKey(String path) {
        try {
            Method handle = clazz.getDeclaredMethod("hasKey", new Class[] {String.class});
            return (boolean) handle.invoke(this.tag, new Object[] {path});
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Object get() {
        return tag;
    }
}
