package com.andrew121410.mc.doubleahub.utils;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;
import java.util.List;

public class BungeecordServers implements PluginMessageListener {

    private DoubleAHub plugin;
    private List<String> bungeecordServers;

    public BungeecordServers(DoubleAHub plugin) {
        this.plugin = plugin;
        this.bungeecordServers = this.plugin.getSetListMap().getBungeecordServers();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, "BungeeCord", this);
        getServers();
    }

    private void getServers() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        this.plugin.getServer().sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();
        if (subChannel.equals("GetServers")) {
            String[] serverList = in.readUTF().split(", ");
            this.bungeecordServers.clear();
            this.bungeecordServers.addAll(Arrays.asList(serverList));
            this.bungeecordServers.removeIf(server -> server.contains("lobby") || server.contains("hub"));
        }
    }
}