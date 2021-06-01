package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.doubleahub.worldguard.DoubleJumpFlagHandler;
import com.andrew121410.mc.world16utils.chat.Translate;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
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
        Player player = event.getPlayer();
        if (this.plugin.getSetListMap().getNoDoubleJumpUUID().contains(player.getUniqueId())) return;
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
        if (this.plugin.getSetListMap().getNoDoubleJumpUUID().contains(player.getUniqueId())) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (!DoubleJumpFlagHandler.canJump(player.getLocation())) {
            event.setCancelled(true);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Translate.color("&cYou are in a no DoubleJump zone!")));
            return;
        }

        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(1.0D).setY(1.0D));
        player.playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 0);
    }
}