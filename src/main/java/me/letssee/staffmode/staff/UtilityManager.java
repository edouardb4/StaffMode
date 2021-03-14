package me.letssee.staffmode.staff;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UtilityManager {

    private static Map<String, StaffUtility> utilities = Maps.newHashMap();

    public static void registerUtility(StaffUtility utility) {
        utilities.put(utility.getName(), utility);
    }

    public static void unregisterUtility(String name) {
        utilities.remove(name);
    }

    public static StaffUtility findUtility(String name) {
        for(Map.Entry<String, StaffUtility> entry : utilities.entrySet()) {
            if (entry.getKey().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static List<StaffUtility> getRegisteredUtilities() {
        List<StaffUtility> list = Lists.newArrayList();
        list.addAll(utilities.values());
        return list;
    }

    public static void unregisterAllUtilities() {
        utilities.values().stream().forEach(util -> util.unregister());
    }

    public static boolean isRegistered(String name) {
        return utilities.containsKey(name);
    }
}
