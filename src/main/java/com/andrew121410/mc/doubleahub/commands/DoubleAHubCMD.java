package com.andrew121410.mc.doubleahub.commands;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.utils.TabUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class DoubleAHubCMD implements CommandExecutor {

    private final DoubleAHub plugin;

    public DoubleAHubCMD(DoubleAHub plugin) {
        this.plugin = plugin;

        this.plugin.getCommand("doubleahub").setExecutor(this);
        this.plugin.getCommand("doubleahub").setTabCompleter((sender, command, s, args) -> {
            if (!(sender instanceof Player player)) return null;
            if (!player.hasPermission("doubleahub.debug")) return null;

            if (args.length == 1) {
                return TabUtils.getContainsString(args[0], Arrays.asList("clearservercache"));
            }
            return null;
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        if (!player.hasPermission("doubleahub.debug")) {
            player.sendMessage("Yeah, you don't have the permission sorry mate.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("clearservercache");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("clearservercache")) {
            this.plugin.getSetListMap().getBungeeCordServers().clear();
            this.plugin.getBungeecordServers().getServers();
            player.sendMessage(Translate.miniMessage("<green>Server Cache Cleared!"));
            return true;
        }
        return true;
    }
}