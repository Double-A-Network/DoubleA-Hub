package com.andrew121410.mc.doubleahub.vpn;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.entity.Player;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;

public class VpnManager {

    private DoubleAHub plugin;

    private final String vpnKey;

    public VpnManager(DoubleAHub plugin) {
        this.plugin = plugin;
        this.vpnKey = this.plugin.getConfig().getString("VPN-API");
    }

    public void doesPlayerHaveVPN(Player player, BiConsumer<String, VpnAPIResponse> consumer) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            VpnAPIResponse vpnAPIResponse = getVpnAPIResponse(player);
            if (vpnAPIResponse == null) {
                this.plugin.getServer().getScheduler().runTask(this.plugin, () -> consumer.accept(null, null));
                plugin.getLogger().log(Level.SEVERE, "vpnAPIResponse was null.");
                return;
            }
            String wasFlaggedFor = null;
            for (Map.Entry<String, Boolean> stringBooleanEntry : vpnAPIResponse.getSecurity().entrySet()) {
                if (stringBooleanEntry.getValue()) wasFlaggedFor = stringBooleanEntry.getKey();
            }
            String finalWasFlaggedFor = wasFlaggedFor; //Lambda moment
            plugin.getServer().getScheduler().runTask(plugin, () -> consumer.accept(finalWasFlaggedFor, vpnAPIResponse));
        });
    }

    private VpnAPIResponse getVpnAPIResponse(Player player) {
        if (player.getAddress() == null) return null;

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://vpnapi.io/api/" + player.getAddress().getAddress().getHostAddress() + "?key=" + this.vpnKey))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), VpnAPIResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
