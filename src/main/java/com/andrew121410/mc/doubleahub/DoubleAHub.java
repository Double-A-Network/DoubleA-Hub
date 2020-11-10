package com.andrew121410.mc.doubleahub;

import com.andrew121410.mc.doubleahub.events.OnDoubleJump;
import com.andrew121410.mc.doubleahub.events.OnPlayerJoinEvent;
import com.andrew121410.mc.doubleahub.utils.SetListMap;
import com.andrew121410.mc.doubleahub.worldguard.DoubleJumpFlagHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleAHub extends JavaPlugin {

    private static DoubleAHub plugin;

    private SetListMap setListMap;

    @Override
    public void onLoad() {
        DoubleJumpFlagHandler.register();
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.setListMap = new SetListMap();
        regEvents();
    }

    @Override
    public void onDisable() {
    }

    public void regEvents() {
        new OnPlayerJoinEvent(this);
        new OnDoubleJump(this);
    }

    public SetListMap getSetListMap() {
        return setListMap;
    }

    public static DoubleAHub getPlugin() {
        return plugin;
    }
}
