package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.utils.InventoryUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class OnPlayerJoinEvent implements Listener {

    private final DoubleAHub plugin;

    public OnPlayerJoinEvent(DoubleAHub plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //Firework
        new BukkitRunnable() {
            @Override
            public void run() {
                Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();
                fireworkMeta.addEffect(FireworkEffect.builder()
                        .flicker(false)
                        .trail(true)
                        .with(FireworkEffect.Type.CREEPER)
                        .withColor(Color.GREEN)
                        .withFade(Color.BLUE)
                        .build());
                fireworkMeta.setPower(3);
                firework.setFireworkMeta(fireworkMeta);
            }
        }.runTaskLater(this.plugin, 20L);

        player.getInventory().clear();
        player.getInventory().setItem(4, InventoryUtils.createItem(Material.COMPASS, 1, Translate.color("&bServers"), "Click me to show a list of servers!"));
        player.getInventory().setHeldItemSlot(4);
        player.performCommand("spawn");
        player.sendMessage(Translate.color("&2Welcome to the &6Double-A Network!"));
        player.sendMessage(Translate.color("&5Discord: &9https://discord.gg/pbrueZB"));
    }
}
