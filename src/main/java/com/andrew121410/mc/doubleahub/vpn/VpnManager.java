package com.andrew121410.mc.doubleahub.vpn;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

public class VpnManager {

    private final DoubleAHub plugin;
    private final String vpnKey;

    public VpnManager(DoubleAHub plugin) {
        this.plugin = plugin;
        this.vpnKey = this.plugin.getConfig().getString("VPN-API");
    }

    public void isVPNAsync(String ipAddress, Consumer<VpnResponse> consumer) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            VpnResponse vpnResponse = isVPNBlocking(ipAddress);
            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> consumer.accept(vpnResponse));
        });
    }

    public VpnResponse isVPNBlocking(String ipAddress) {
        VpnAPIResponse vpnAPIResponse = getVpnAPIResponse(ipAddress);
        String wasFlaggedFor = null;
        if (vpnAPIResponse != null) {
            for (Map.Entry<String, Boolean> stringBooleanEntry : vpnAPIResponse.getSecurity().entrySet()) {
                if (stringBooleanEntry.getValue()) wasFlaggedFor = stringBooleanEntry.getKey();
            }
        }
        return new VpnResponse(wasFlaggedFor, vpnAPIResponse);
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