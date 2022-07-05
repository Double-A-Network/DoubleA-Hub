package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.doubleahub.vpn.VpnManager;
import com.andrew121410.mc.doubleahub.vpn.VpnResponse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.List;

public class OnAsyncPlayerPreLoginEvent implements Listener {

    private final List<String> blockedVPNIPAddresses;
    private final List<String> validVPNIPAddresses;

    private final DoubleAHub plugin;
    private final VpnManager vpnManager;

    public OnAsyncPlayerPreLoginEvent(DoubleAHub plugin) {
        this.plugin = plugin;
        this.vpnManager = this.plugin.getVpnManager();

        this.blockedVPNIPAddresses = this.plugin.getSetListMap().getBlockedVPNIPAddresses();
        this.validVPNIPAddresses = this.plugin.getSetListMap().getValidVPNIPAddresses();

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void fuckBots(AsyncPlayerPreLoginEvent event) {
        String ipAddress = event.getAddress().getHostAddress();

        // No need to use another API request
        if (this.blockedVPNIPAddresses.contains(ipAddress)) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Please turn off your VPN [cached]");
            return;
        }

        // No need to use another API request
        if (this.validVPNIPAddresses.contains(ipAddress)) {
            event.allow();
            return;
        }

        VpnResponse vpnResponse = this.vpnManager.isVPNBlocking(ipAddress);

        if (vpnResponse.getVpnAPIResponse() == null) {
            event.allow(); // We shouldn't disallow login just because the API is down.
//            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Couldn't validate your IP Address");
            return;
        }

        if (vpnResponse.getWasFlaggedFor() == null) {
            // Ran If not using a VPN
            event.allow();
        } else {
            //Ran if using a VPN
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Please turn off your VPN");
            this.plugin.getSetListMap().getBlockedVPNIPAddresses().add(ipAddress);

//            TextChannel textChannel = DiscordUtil.getJda().getTextChannelById(912807023756861460L);
//            if (textChannel == null) return;
//
//            EmbedBuilder embedBuilder = new EmbedBuilder()
//                    .setTitle(event.getName() + " Blocked!", "https://minecraftuuid.com/?search=" + event.getUniqueId())
//                    .setThumbnail("https://crafatar.com/avatars/" + event.getUniqueId() + ".png")
//                    .setDescription(event.getName() + "'s connection was blocked as it was flagged as a " + vpnResponse.getWasFlaggedFor())
//                    .addField("IP Information:", "```json\n" + vpnResponse.getVpnAPIResponse().toJsonPrettyPrint() + "\n```", false)
//                    .setFooter("Double-A-Hub - (vpnapi.io)");
//
//            textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
