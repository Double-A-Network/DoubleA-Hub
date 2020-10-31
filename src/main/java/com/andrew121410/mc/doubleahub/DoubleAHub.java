package com.andrew121410.mc.doubleahub;

import com.andrew121410.mc.doubleahub.events.OnDoubleJump;
import com.andrew121410.mc.doubleahub.events.OnPlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleAHub extends JavaPlugin {

    private static DoubleAHub plugin;

    @Override
    public void onEnable() {
        plugin = this;
        regEvents();
    }

    @Override
    public void onDisable() {
    }

    public void regEvents() {
        new OnPlayerJoinEvent(this);
        new OnDoubleJump(this);
    }

    public static DoubleAHub getPlugin() {
        return plugin;
    }
}
