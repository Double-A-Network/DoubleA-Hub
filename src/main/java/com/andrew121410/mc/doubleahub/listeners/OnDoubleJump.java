package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.doubleahub.worldguard.DoubleJumpFlagHandler;
import com.andrew121410.mc.world16utils.chat.Translate;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class OnDoubleJump implements Listener {

    private final DoubleAHub plugin;

    public OnDoubleJump(DoubleAHub plugin) {
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (this.plugin.getMemoryHolder().getNoDoubleJumpUUID().contains(player.getUniqueId())) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) return;
        if (player.isFlying()) return;
        if (!DoubleJumpFlagHandler.canJump(player.getLocation())) {
            player.setAllowFlight(false);
            return;
        }

        player.setAllowFlight(true);
    }

    @EventHandler
    public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (this.plugin.getMemoryHolder().getNoDoubleJumpUUID().contains(player.getUniqueId())) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (!DoubleJumpFlagHandler.canJump(player.getLocation())) {
            event.setCancelled(true);
            player.sendActionBar(Translate.miniMessage("<red>You are in a no DoubleJump zone!"));
            return;
        }

        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(1.0D).setY(1.0D));
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0f, 1.0f);
    }
}