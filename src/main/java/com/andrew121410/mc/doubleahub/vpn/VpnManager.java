package com.andrew121410.mc.doubleahub.vpn;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.function.BiConsumer;

public class VpnManager {

    private final DoubleAHub plugin;
    private final String vpnKey;

    public VpnManager(DoubleAHub plugin) {
        this.plugin = plugin;
        this.vpnKey = this.plugin.getConfig().getString("VPN-API");
    }

    public void isVPN(String ipAddress, BiConsumer<String, VpnAPIResponse> consumer) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            VpnAPIResponse vpnAPIResponse = getVpnAPIResponse(ipAddress);
            String wasFlaggedFor = null;

            if (vpnAPIResponse != null) {
                for (Map.Entry<String, Boolean> stringBooleanEntry : vpnAPIResponse.getSecurity().entrySet()) {
                    if (stringBooleanEntry.getValue()) wasFlaggedFor = stringBooleanEntry.getKey();
                }
            }
            String finalWasFlaggedFor = wasFlaggedFor; //Lambda moment

            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> consumer.accept(finalWasFlaggedFor, vpnAPIResponse));
        });
    }

    private VpnAPIResponse getVpnAPIResponse(String ipAddress) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://vpnapi.io/api/" + ipAddress + "?key=" + this.vpnKey))
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
