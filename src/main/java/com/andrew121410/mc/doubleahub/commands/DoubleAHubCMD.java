package com.andrew121410.mc.doubleahub.commands;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DoubleAHubCMD implements CommandExecutor {

    private final DoubleAHub plugin;

    public DoubleAHubCMD(DoubleAHub plugin) {
        this.plugin = plugin;

        this.plugin.getCommand("doubleahub").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        if (!player.hasPermission("doubleahub.use")) {
            player.sendMessage("Yeah, you don't have the permission sorry mate.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("clearservercache");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("clearservercache")) {
            this.plugin.getSetListMap().getBungeeCordServers().clear();
            this.plugin.getBungeecordServers().getServers();
            player.sendMessage("Cleared bungeecord servers cache!");
            return true;
        }
        return true;
    }
}