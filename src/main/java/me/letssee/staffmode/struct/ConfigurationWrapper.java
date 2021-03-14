package me.letssee.staffmode.struct;

import java.io.*;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationWrapper {
    private String configName;
    private JavaPlugin plugin;
    private String folder;
    public ConfigurationWrapper(String configName, JavaPlugin plugin) {
        this.configName = configName;
        this.plugin = plugin;
        this.folder = plugin.getDataFolder() + "/";
        this.load();
    }

    public File getFile() {
        return new File(folder + this.configName);
    }

    public String getConfigName() {
        return this.configName;
    }

    public boolean contains(String path) {
        return getYamlConf().contains(path);
    }

    public YamlConfiguration getYamlConf() {
        File file = getFile();
        return YamlConfiguration.loadConfiguration(file);
    }

    public String getString(String path) {
        return getYamlConf().getString(path);
    }

    public int getInt(String path) {
        return getYamlConf().getInt(path);
    }

    public float getFloat(String path) {
        return (float) getYamlConf().getDouble(path);
    }

    public short getShort(String path) {
        return (short) getYamlConf().getInt(path);
    }

    public double getDouble(String path) {
        return getYamlConf().getDouble(path);
    }

    public long getLong(String path) {
        return getYamlConf().getLong(path);
    }

    public boolean getBoolean(String path) {
        return getYamlConf().getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return getYamlConf().getStringList(path);
    }

    public List<Integer> getIntList(String path) {
        return getYamlConf().getIntegerList(path);
    }

    public void setShort(String path, short value) {
        YamlConfiguration config = getYamlConf();
        config.set(path, value);
        save(config);
    }

    public void setInt(String path, int value) {
        YamlConfiguration config = getYamlConf();
        config.set(path, value);
        save(config);
    }

    public void setLong(String path, long value) {
        YamlConfiguration config = getYamlConf();
        config.set(path, value);
        save(config);
    }

    public void setString(String path, String value) {
        YamlConfiguration config = getYamlConf();
        config.set(path, value);
        save(config);
    }

    public void setFloat(String path, float value) {
        YamlConfiguration config = getYamlConf();
        config.set(path, value);
        save(config);
    }

    public void setDouble(String path, double value) {
        YamlConfiguration config = getYamlConf();
        config.set(path, value);
        save(config);
    }

    public void setIntegerList(String path, List<Integer> value) {
        YamlConfiguration config = getYamlConf();
        config.set(path, value);
        save(config);
    }

    public void setStringList(String path, List<String> value) {
        YamlConfiguration config = getYamlConf();
        config.set(path, value);
        save(config);
    }

    public void setBoolean(String path, boolean value) {
        YamlConfiguration config = getYamlConf();
        config.set(path, value);
        save(config);
    }

    public void reload() {
        load();
    }

    public void load() {
        if(!getFile().exists()) {
            this.plugin.saveResource(this.configName, false);
        }
    }

    public void save(YamlConfiguration config) {
        File file = getFile();
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        new File(folder + this.configName).delete();
    }
}
