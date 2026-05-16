package com.andrew121410.mc.doubleahub.utils;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ServerCompassSelector {

    private static final NamespacedKey KEY = new NamespacedKey(DoubleAHub.getPlugin(), "server_compass");

    public static void addItemToInventory(Player player) {
        ItemStack compass = InventoryUtils.createItem(Material.COMPASS, 1, Translate.miniMessage("<aqua>Servers!"), Translate.miniMessage("Click me to show a list of servers!"));
        ItemMeta meta = compass.getItemMeta();
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.BOOLEAN, true);
        compass.setItemMeta(meta);
        player.getInventory().setItem(0, compass);
        player.getInventory().setHeldItemSlot(0);
    }

    public static boolean isServerCompassSelector(ItemStack itemStack) {
        if (itemStack == null || itemStack.getItemMeta() == null) return false;
        return itemStack.getItemMeta().getPersistentDataContainer().has(KEY, PersistentDataType.BOOLEAN);
    }

    public static void removeFromInventory(Player player) {
        player.getInventory().remove(Material.COMPASS);
    }
}
