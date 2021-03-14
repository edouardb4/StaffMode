package me.letssee.staffmode.struct;

import me.letssee.staffmode.utils.LocationUtils;
import org.bukkit.Location;

public class FrozenPlayer {

    private String location;
    public FrozenPlayer(Location location) {
        this.location = LocationUtils.toLocationString(location);
    }

    public Location getLocation() {
        return LocationUtils.toLocationObject(this.location);
    }

    public boolean isSimilarLocation(Location location) {
        Location from = LocationUtils.toLocationObject(this.location);
        return from.distance(location) <= 1;
    }
}
