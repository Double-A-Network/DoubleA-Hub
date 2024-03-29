package com.andrew121410.mc.doubleahub;

import com.andrew121410.mc.doubleahub.commands.DoubleAHubCMD;
import com.andrew121410.mc.doubleahub.listeners.*;
import com.andrew121410.mc.doubleahub.utils.BungeecordServers;
import com.andrew121410.mc.doubleahub.utils.MemoryHolder;
import com.andrew121410.mc.doubleahub.vpn.VpnManager;
import com.andrew121410.mc.doubleahub.worldguard.DoubleJumpFlagHandler;
import com.andrew121410.mc.world16utils.updater.UpdateManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleAHub extends JavaPlugin {

    private static DoubleAHub plugin;
    private MemoryHolder memoryHolder;

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
        this.memoryHolder = new MemoryHolder();

        this.getConfig().addDefault("VPN-API", "key");
        this.getConfig().options().copyDefaults(true);
        saveConfig();

        this.bungeecordServers = new BungeecordServers(this);
        this.bungeecordServers.getServers();

        this.vpnManager = new VpnManager(this);

        registerCommands();
        registerListeners();

        UpdateManager.registerUpdater(this, new com.andrew121410.mc.doubleahub.Updater(this));
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

    public MemoryHolder getMemoryHolder() {
        return memoryHolder;
    }

    public BungeecordServers getBungeecordServers() {
        return bungeecordServers;
    }

    public VpnManager getVpnManager() {
        return vpnManager;
    }
}
