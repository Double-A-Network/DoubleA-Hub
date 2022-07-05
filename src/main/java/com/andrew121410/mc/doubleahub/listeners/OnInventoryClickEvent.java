package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.doubleahub.utils.ServerCompassSelector;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class OnInventoryClickEvent implements Listener {

    private final DoubleAHub plugin;

    public OnInventoryClickEvent(DoubleAHub plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if ((player.getGameMode() != GameMode.CREATIVE || !player.isOp()) && ServerCompassSelector.isServerCompassSelector(itemStack)) {
            event.setCancelled(true);
        }
    }
}
