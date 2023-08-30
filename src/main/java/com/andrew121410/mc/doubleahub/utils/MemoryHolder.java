package com.andrew121410.mc.doubleahub.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemoryHolder {

    private final List<UUID> noDoubleJumpUUID;
    private final List<String> bungeeCordServers;

    private final List<String> blockedVPNIPAddresses;
    private final List<String> validVPNIPAddresses;

    public MemoryHolder() {
        this.noDoubleJumpUUID = new ArrayList<>();
        this.bungeeCordServers = new ArrayList<>();
        this.blockedVPNIPAddresses = new ArrayList<>();
        this.validVPNIPAddresses = new ArrayList<>();
    }

    public List<UUID> getNoDoubleJumpUUID() {
        return noDoubleJumpUUID;
    }

    public List<String> getBungeeCordServers() {
        return bungeeCordServers;
    }

    public List<String> getBlockedVPNIPAddresses() {
        return blockedVPNIPAddresses;
    }

    public List<String> getValidVPNIPAddresses() {
        return validVPNIPAddresses;
    }
}
