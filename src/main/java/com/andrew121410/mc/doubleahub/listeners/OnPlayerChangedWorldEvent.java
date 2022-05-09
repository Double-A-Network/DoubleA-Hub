package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class OnPlayerChangedWorldEvent implements Listener {

    private DoubleAHub plugin;

    public OnPlayerChangedWorldEvent(DoubleAHub plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase("plots_60by60")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "invsave " + player.getName() + " 1 -s");
            player.setGameMode(GameMode.CREATIVE);
            player.getInventory().clear();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "invload a " + player.getName() + " 2");
        } else if (player.getWorld().getName().equalsIgnoreCase("world")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "invsave " + player.getName() + " 2 -s");
            player.setGameMode(GameMode.SURVIVAL);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "invload a " + player.getName() + " 1");
        }
    }
}
