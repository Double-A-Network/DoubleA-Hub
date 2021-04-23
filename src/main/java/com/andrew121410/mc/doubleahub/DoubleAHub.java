package com.andrew121410.mc.doubleahub;

import com.andrew121410.mc.doubleahub.commands.DoubleAHubCMD;
import com.andrew121410.mc.doubleahub.events.*;
import com.andrew121410.mc.doubleahub.utils.BungeecordServers;
import com.andrew121410.mc.doubleahub.utils.SetListMap;
import com.andrew121410.mc.doubleahub.worldguard.DoubleJumpFlagHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleAHub extends JavaPlugin {

    private static DoubleAHub plugin;
    private SetListMap setListMap;

    private BungeecordServers bungeecordServers;

    @Override
    public void onLoad() {
        DoubleJumpFlagHandler.register();
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.setListMap = new SetListMap();

        this.bungeecordServers = new BungeecordServers(this);
        this.bungeecordServers.getServers();

        regCommands();
        regEvents();
    }

    @Override
    public void onDisable() {
    }

    private void regCommands() {
        new DoubleAHubCMD(this);
    }

    private void regEvents() {
        new OnPlayerJoinEvent(this);
        new OnDoubleJump(this);
        new OnPlayerInteractEvent(this);
        new OnInventoryClickEvent(this);
        new OnPlayerDropItemEvent(this);
    }

    public SetListMap getSetListMap() {
        return setListMap;
    }

    public BungeecordServers getBungeecordServers() {
        return bungeecordServers;
    }

    public static DoubleAHub getPlugin() {
        return plugin;
    }
}
