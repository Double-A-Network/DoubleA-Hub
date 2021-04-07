package com.andrew121410.mc.doubleahub.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SetListMap {

    private List<UUID> noDoubleJumpUUID;
    private List<String> bungeecordServers;

    public SetListMap() {
        this.noDoubleJumpUUID = new ArrayList<>();
        this.bungeecordServers = new ArrayList<>();
    }

    public List<UUID> getNoDoubleJumpUUID() {
        return noDoubleJumpUUID;
    }

    public List<String> getBungeecordServers() {
        return bungeecordServers;
    }
}
