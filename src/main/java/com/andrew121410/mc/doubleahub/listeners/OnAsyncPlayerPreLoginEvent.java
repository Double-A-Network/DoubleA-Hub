package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.doubleahub.vpn.VpnManager;
import com.andrew121410.mc.doubleahub.vpn.VpnResponse;
import com.andrew121410.mc.world16utils.chat.Translate;
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

        this.blockedVPNIPAddresses = this.plugin.getMemoryHolder().getBlockedVPNIPAddresses();
        this.validVPNIPAddresses = this.plugin.getMemoryHolder().getValidVPNIPAddresses();

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void fuckBots(AsyncPlayerPreLoginEvent event) {
        String ipAddress = event.getAddress().getHostAddress();

        // No need to use another API request (blocked ip cache)
        if (this.blockedVPNIPAddresses.contains(ipAddress)) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Translate.miniMessage("<red>Please turn off your VPN [cached]"));
            return;
        }

        // No need to use another API request (allowed ip cache)
        if (this.validVPNIPAddresses.contains(ipAddress)) {
            event.allow();
            return;
        }

        VpnResponse vpnResponse = this.vpnManager.isVPNBlocking(ipAddress);

        if (vpnResponse.getVpnAPIResponse() == null) {
            event.allow(); // We shouldn't disallow login just because the API is down.
            return;
        }

        // The user is using a VPN (deny entry)
        if (vpnResponse.getWasFlaggedFor() != null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Translate.miniMessage("<red>Please turn off your VPN"));
            this.plugin.getMemoryHolder().getBlockedVPNIPAddresses().add(ipAddress);
            return;
        }

        // The user is not using a VPN (allow entry)
        event.allow();
        this.plugin.getMemoryHolder().getValidVPNIPAddresses().add(ipAddress);
    }
}
