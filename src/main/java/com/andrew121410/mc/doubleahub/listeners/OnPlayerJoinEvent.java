package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.doubleahub.utils.ServerCompassSelector;
import com.andrew121410.mc.world16utils.chat.Translate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    private final DoubleAHub plugin;

    public OnPlayerJoinEvent(DoubleAHub plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.performCommand("spawn");
        player.getInventory().clear();

        // Clear the chat for the player
        for (int i = 0; i < 100; i++) player.sendMessage("");

        // Send welcome message
        player.sendMessage(Translate.chat("&7&m-----------------------------------------------------"));
        player.sendMessage(Component.text("Welcome to the Double-A Network!").color(TextColor.fromHexString("#FFA500"))
                .append(Component.newline())
                .append(Component.text("Discord: ").color(TextColor.fromHexString("#7289da")))
                .append(Translate.miniMessage("<rainbow>https://discord.gg/pbrueZB").decoration(TextDecoration.UNDERLINED, true).clickEvent(ClickEvent.openUrl("https://discord.gg/pbrueZB"))));
        player.sendMessage(Translate.chat("&7&m-----------------------------------------------------"));

        ServerCompassSelector.addItemToInventory(player);
    }
}
