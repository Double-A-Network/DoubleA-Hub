package com.andrew121410.mc.doubleahub.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SetListMap {

    private final List<UUID> noDoubleJumpUUID;
    private final List<String> bungeecordServers;

    private final List<String> vpnAddressesCache;
    private final List<String> validAddressesCache;

    public SetListMap() {
        this.noDoubleJumpUUID = new ArrayList<>();
        this.bungeecordServers = new ArrayList<>();
        this.vpnAddressesCache = new ArrayList<>();
        this.validAddressesCache = new ArrayList<>();
    }

    public List<UUID> getNoDoubleJumpUUID() {
        return noDoubleJumpUUID;
    }

    public List<String> getBungeecordServers() {
        return bungeecordServers;
    }

    public List<String> getVpnAddressesCache() {
        return vpnAddressesCache;
    }

    public List<String> getValidAddressesCache() {
        return validAddressesCache;
    }
}
