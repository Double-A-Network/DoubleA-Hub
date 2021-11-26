package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.doubleahub.vpn.VpnManager;
import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.utils.InventoryUtils;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class OnPlayerJoinEvent implements Listener {

    private final List<String> vpnAddressesCache;
    private final List<String> validAddressesCache;

    private final DoubleAHub plugin;
    private final VpnManager vpnManager;

    public OnPlayerJoinEvent(DoubleAHub plugin) {
        this.plugin = plugin;

        this.vpnAddressesCache = this.plugin.getSetListMap().getVpnAddressesCache();
        this.validAddressesCache = this.plugin.getSetListMap().getValidAddressesCache();

        this.vpnManager = this.plugin.getVpnManager();
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoinLow(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getAddress() == null) throw new NullPointerException("player.getAddress() was null?");
        String ipAddress = player.getAddress().getAddress().getHostAddress();

        //No need to use another API request
        if (this.vpnAddressesCache.contains(ipAddress)) {
            player.kickPlayer("Please disable your vpn... [cached]");
            return;
        }

        //No need to use another API request
        if (this.validAddressesCache.contains(ipAddress)) {
            return;
        }

        this.vpnManager.isVPN(ipAddress, (flaggedFor, vpnAPIResponse) -> {
            if (flaggedFor == null && vpnAPIResponse != null) {
                //Ran if not using a VPN
                this.validAddressesCache.add(ipAddress);
            } else if (flaggedFor != null && vpnAPIResponse != null) {
                //Ran if using a VPN
                player.kickPlayer("Please disable your vpn...");
                this.plugin.getSetListMap().getVpnAddressesCache().add(ipAddress);

                TextChannel textChannel = DiscordUtil.getJda().getTextChannelById(912807023756861460L);
                if (textChannel == null) return;

                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle(player.getDisplayName() + " Blocked!", "https://minecraftuuid.com/?search=" + player.getUniqueId())
                        .setThumbnail("https://crafatar.com/avatars/" + player.getUniqueId() + ".png")
                        .setDescription(player.getDisplayName() + "'s connection was blocked as it was flagged as a " + flaggedFor)
                        .addField("IP Information:", "```json\n" + vpnAPIResponse.toJsonPrettyPrint() + "\n```", false)
                        .setFooter("Double-A-Hub - (vpnapi.io)");

                textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
            } else {
                player.kickPlayer("Couldn't validate your IP Address");
            }
        });
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
