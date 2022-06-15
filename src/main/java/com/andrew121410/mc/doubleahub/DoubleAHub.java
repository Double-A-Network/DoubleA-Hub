package com.andrew121410.mc.doubleahub;

import com.andrew121410.mc.doubleahub.commands.DoubleAHubCMD;
import com.andrew121410.mc.doubleahub.listeners.*;
import com.andrew121410.mc.doubleahub.utils.BungeecordServers;
import com.andrew121410.mc.doubleahub.utils.SetListMap;
import com.andrew121410.mc.doubleahub.vpn.VpnManager;
import com.andrew121410.mc.doubleahub.worldguard.DoubleJumpFlagHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleAHub extends JavaPlugin {

    private static DoubleAHub plugin;
    private SetListMap setListMap;

    private BungeecordServers bungeecordServers;
    private VpnManager vpnManager;

    public static DoubleAHub getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        DoubleJumpFlagHandler.register();
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.setListMap = new SetListMap();

        this.getConfig().addDefault("VPN-API", "key");
        this.getConfig().options().copyDefaults(true);
        saveConfig();

        this.bungeecordServers = new BungeecordServers(this);
        this.bungeecordServers.getServers();

        this.vpnManager = new VpnManager(this);

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands() {
        new DoubleAHubCMD(this);
    }

    private void registerListeners() {
        new OnAsyncPlayerPreLoginEvent(this);
        new OnPlayerJoinEvent(this);
        new OnDoubleJump(this);
        new OnPlayerInteractEvent(this);
        new OnInventoryClickEvent(this);
        new OnPlayerDropItemEvent(this);
        new OnFoodLevelChangeEvent(this);
        new OnPlayerChangedWorldEvent(this);
    }

    public SetListMap getSetListMap() {
        return setListMap;
    }

    public BungeecordServers getBungeecordServers() {
        return bungeecordServers;
    }

    public VpnManager getVpnManager() {
        return vpnManager;
    }
}
