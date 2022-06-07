package com.andrew121410.mc.doubleahub.utils;

import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ServerCompassSelector {

    public static void addItemToInventory(Player player) {
        player.getInventory().setItem(0, InventoryUtils.createItem(Material.COMPASS, 1, Translate.color("&bServers"), "Click me to show a list of servers!"));
        player.getInventory().setHeldItemSlot(0);
    }

    public static boolean isServerCompassSelector(ItemStack itemStack) {
        if (itemStack.getItemMeta() != null) {
            return itemStack.getItemMeta().getDisplayName().contains("Server");
        }
        return false;
    }
}
