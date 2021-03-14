package me.letssee.staffmode.utils;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

public class LocationUtils {
    public static Location toLocationObject(String sLocation) {
        String[] split = sLocation.split("@");
        Location location = new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
        location.setYaw(Float.parseFloat(split[4]));
        location.setPitch(Float.parseFloat(split[5]));
        return location;
    }

    public static String toLocationString(Location location) {
        return location.getWorld().getName() + "@" + location.getX() + "@" + location.getY() + "@" + location.getZ() + "@" + location.getYaw() + "@" + location.getPitch();
    }

    public static String toChunkString(Chunk chunk) {
        return chunk.getWorld().getName() + "@" + chunk.getX() + "@" + chunk.getZ();
    }

    public static Chunk toChunkObject(String sChunk) {
        String[] split = sChunk.split("@");
        Chunk chunk = new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), 0, Double.parseDouble(split[2])).getChunk();
        return chunk;
    }
}
