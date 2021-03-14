package me.letssee.staffmode.staff.items;

import me.letssee.staffmode.citems.CustomItem;
import me.letssee.staffmode.struct.NBTTagWrapper;
import me.letssee.staffmode.struct.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpeedBoostItem extends CustomItem {

    public SpeedBoostItem(String internalName) {
        super(internalName);
    }

    @Override
    public boolean isItem(ItemStack item) {
        if(item == null || item.getType() == Material.AIR) {
            return false;
        }
        NBTWrapper wrapper = new NBTWrapper(item);
        if(!wrapper.hasKey("staff_tag")) {
            return false;
        }
        NBTTagWrapper tag = wrapper.getTag("staff_tag");
        return tag.hasKey("speed_boost_item");
    }

    public ItemStack getItem() {
        ItemStack item = super.getItem(null);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagWrapper tag = new NBTTagWrapper();
        tag.setBoolean("speed_boost_item", true);
        wrapper.setNBTBase("staff_tag", tag.get());
        return wrapper.get();
    }
}
