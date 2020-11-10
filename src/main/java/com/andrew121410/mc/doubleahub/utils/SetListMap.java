package com.andrew121410.mc.doubleahub.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SetListMap {

    private List<UUID> noDoubleJumpUUID;

    public SetListMap() {
        this.noDoubleJumpUUID = new ArrayList<>();
    }

    public List<UUID> getNoDoubleJumpUUID() {
        return noDoubleJumpUUID;
    }
}
