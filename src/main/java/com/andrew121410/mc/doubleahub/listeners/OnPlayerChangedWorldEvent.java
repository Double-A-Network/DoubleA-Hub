package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.doubleahub.utils.ServerCompassSelector;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class OnPlayerChangedWorldEvent implements Listener {

    private final DoubleAHub plugin;

    public OnPlayerChangedWorldEvent(DoubleAHub plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        // Remove server selector compass from their inventory if they come from the hub world
        if (event.getFrom().getName().equalsIgnoreCase("world")) {
            ServerCompassSelector.removeFromInventory(player);
        }

        if (player.getWorld().getName().equalsIgnoreCase("plots_60by60")) {
            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "invload " + player.getName() + " " + player.getName() + " 2");
        } else if (player.getWorld().getName().equalsIgnoreCase("world")) {
            if (event.getFrom().getName().equalsIgnoreCase("plots_60by60")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "invsave " + player.getName() + " 2 -s");
                player.setGameMode(GameMode.SURVIVAL);
            }
            player.getInventory().clear();
            ServerCompassSelector.addItemToInventory(player);
        }
    }
}
