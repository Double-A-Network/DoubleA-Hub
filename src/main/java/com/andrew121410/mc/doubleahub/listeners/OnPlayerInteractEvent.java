package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.gui.AdvanceGUIWindow;
import com.andrew121410.mc.world16utils.gui.buttons.GUIButton;
import com.andrew121410.mc.world16utils.gui.buttons.defaults.ClickEventButton;
import com.andrew121410.mc.world16utils.utils.InventoryUtils;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.ArrayList;
import java.util.List;

public class OnPlayerInteractEvent implements Listener {

    private DoubleAHub plugin;
    private List<String> bungeecordServers;

    public OnPlayerInteractEvent(DoubleAHub plugin) {
        this.plugin = plugin;
        this.bungeecordServers = this.plugin.getSetListMap().getBungeecordServers();
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (player.getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Server")) {
            event.setCancelled(true);
            //Bedrock player
            if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
                sendServersForm(player);
            } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                AdvanceGUIWindow advanceGUIWindow = new AdvanceGUIWindow() {
                    @Override
                    public void onCreate(Player player) {
                        List<GUIButton> guiButtons = new ArrayList<>();
                        int guiSlots = bungeecordServers.size() + (9 - (bungeecordServers.size() % 9));
                        int slot = 2;
                        for (String server : bungeecordServers) {
                            guiButtons.add(new ClickEventButton(slot - 1, InventoryUtils.createItem(Material.ENCHANTED_BOOK, 1, server, "Click me to join the server!"), guiClickEvent -> sendPlayerToServer(player, server)));
                            slot = slot + 2;
                        }
                        this.update(guiButtons, Translate.color("&bServers!"), guiSlots);

                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                    }

                    @Override
                    public void onClose(InventoryCloseEvent inventoryCloseEvent) {

                    }
                };
                advanceGUIWindow.open(player);
            }
        }
    }

    private void sendServersForm(Player player) {
        SimpleForm.Builder simpleForm = SimpleForm.builder().title("Servers!").content("List of servers!");
        for (String server : this.bungeecordServers) simpleForm.button(server);
        simpleForm.responseHandler((form, data) -> {
            SimpleFormResponse simpleFormResponse = form.parseResponse(data);
            if (!simpleFormResponse.isCorrect()) return;
            sendPlayerToServer(player, simpleFormResponse.getClickedButton().getText());
        });
        FloodgateApi.getInstance().sendForm(player.getUniqueId(), simpleForm.build());
    }

    private void sendPlayerToServer(Player player, String server) {
        if (player == null || !player.isOnline()) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());
    }
}
