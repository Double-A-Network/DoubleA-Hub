package com.andrew121410.mc.doubleahub.events;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.gui.AdvanceGUIWindow;
import com.andrew121410.mc.world16utils.gui.buttons.GUIButton;
import com.andrew121410.mc.world16utils.gui.buttons.defaults.ClickEventButton;
import com.andrew121410.mc.world16utils.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

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
            if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
                theForm(player, FloodgateApi.getInstance().getPlayer(player.getUniqueId()));
            } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                AdvanceGUIWindow advanceGUIWindow = new AdvanceGUIWindow() {
                    @Override
                    public void onCreate(Player player) {
                        List<GUIButton> guiButtons = new ArrayList<>();
                        int guiSlots = bungeecordServers.size() + (9 - (bungeecordServers.size() % 9));
                        int slot = 2;
                        for (String server : bungeecordServers) {
                            guiButtons.add(new ClickEventButton(slot - 1, InventoryUtils.createItem(Material.ENCHANTED_BOOK, 1, server, "Click me to join the server!"), guiClickEvent -> player.performCommand("server " + server)));
                            slot = slot + 2;
                        }
                        this.update(guiButtons, Translate.color("&bServers!"), guiSlots);
                    }

                    @Override
                    public void onClose(InventoryCloseEvent inventoryCloseEvent) {

                    }
                };
                advanceGUIWindow.open(player);
            }
        }
    }

    private void theForm(Player player, FloodgatePlayer floodgatePlayer) {
        SimpleForm.Builder simpleForm = SimpleForm.builder().title("Servers!").content("List of servers!");
        for (String server : this.bungeecordServers) simpleForm.button(server);
        simpleForm.responseHandler((form, data) -> {
            SimpleFormResponse simpleFormResponse = form.parseResponse(data);
            if (!simpleFormResponse.isCorrect()) return;
            player.performCommand("server " + simpleFormResponse.getClickedButton().getText());
        });

        floodgatePlayer.sendForm(simpleForm);
    }
}
