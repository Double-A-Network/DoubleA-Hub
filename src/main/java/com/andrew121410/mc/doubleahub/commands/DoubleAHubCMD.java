package com.andrew121410.mc.doubleahub.commands;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DoubleAHubCMD implements CommandExecutor {

    private DoubleAHub plugin;

    public DoubleAHubCMD(DoubleAHub getPlugin) {
        this.plugin = getPlugin;

        this.plugin.getCommand("doubleahub").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("doubleahub.use")) {
            p.sendMessage("Yeah, you don't have the permission sorry mate.");
            return true;
        }

        if (args.length == 0) {
            p.sendMessage("clearservercache");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("clearservercache")) {
            this.plugin.getSetListMap().getBungeecordServers().clear();
            this.plugin.getBungeecordServers().getServers();
            p.sendMessage("Cleared bungeecord servers cache!");
            return true;
        }
        return true;
    }
}