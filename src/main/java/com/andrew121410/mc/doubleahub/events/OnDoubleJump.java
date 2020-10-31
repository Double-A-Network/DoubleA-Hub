package com.andrew121410.mc.doubleahub.events;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class OnDoubleJump implements Listener {

    private DoubleAHub plugin;

    public OnDoubleJump(DoubleAHub plugin) {
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.AIR) {
            return;
        }

        if (p.isFlying()) {
            return;
        }

        p.setAllowFlight(true);
    }

    @EventHandler
    public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(1.0D).setY(0.5D));
        player.playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 0);
    }
}