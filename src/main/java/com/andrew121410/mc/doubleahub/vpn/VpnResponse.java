package com.andrew121410.mc.doubleahub.vpn;

public class VpnResponse {

    private final String wasFlaggedFor;
    private final VpnAPIResponse vpnAPIResponse;

    public VpnResponse(String wasFlaggedFor, VpnAPIResponse vpnAPIResponse) {
        this.wasFlaggedFor = wasFlaggedFor;
        this.vpnAPIResponse = vpnAPIResponse;
    }

    public String getWasFlaggedFor() {
        return wasFlaggedFor;
    }

    public VpnAPIResponse getVpnAPIResponse() {
        return vpnAPIResponse;
    }
}
