package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import com.andrew121410.mc.doubleahub.utils.ServerCompassSelector;
import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.gui.GUIWindow;
import com.andrew121410.mc.world16utils.gui.buttons.AbstractGUIButton;
import com.andrew121410.mc.world16utils.gui.buttons.defaults.ClickEventButton;
import com.andrew121410.mc.world16utils.gui.buttons.defaults.NoEventButton;
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
import org.bukkit.inventory.ItemStack;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.ArrayList;
import java.util.List;

public class OnPlayerInteractEvent implements Listener {

    private final DoubleAHub plugin;
    private final List<String> bungeeCordServers;

    public OnPlayerInteractEvent(DoubleAHub plugin) {
        this.plugin = plugin;
        this.bungeeCordServers = this.plugin.getMemoryHolder().getBungeeCordServers();
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (ServerCompassSelector.isServerCompassSelector(itemInHand)) {
            event.setCancelled(true);

            if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
                // Bedrock player
                openBedrockForm(player);
            } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                // Java player
                openGui(player);
            }
        }
    }

    private void openBedrockForm(Player player) {
        SimpleForm.Builder simpleFormBuilder = SimpleForm.builder().title("Servers!").content("List of servers!");

        // Create the buttons
        for (String server : this.bungeeCordServers) simpleFormBuilder.button(server);

        // Handle the response
        simpleFormBuilder.validResultHandler((simpleForm, simpleFormResponse) -> {
            String serverName = simpleFormResponse.clickedButton().text();
            sendPlayerToServer(player, serverName);
        });

        FloodgateApi.getInstance().sendForm(player.getUniqueId(), simpleFormBuilder.build());
    }

    private void openGui(Player player) {
        GUIWindow guiWindow = new GUIWindow() {
            @Override
            public void onCreate(Player player) {
                List<AbstractGUIButton> guiButtons = new ArrayList<>();
                int guiSlots = bungeeCordServers.size() + (9 - (bungeeCordServers.size() % 9));

                int slot = switch (bungeeCordServers.size()) {
                    case 3 -> 3;
                    case 2 -> 4;
                    case 1 -> 5;
                    default -> 2;
                };

                for (String server : bungeeCordServers) {
                    guiButtons.add(new ClickEventButton(slot - 1, InventoryUtils.createItem(Material.ENCHANTED_BOOK, 1, server, "Click me to join the server!"), guiClickEvent -> sendPlayerToServer(player, server)));
                    slot = slot + 2;
                }

                List<Integer> integers = guiButtons.stream().map(AbstractGUIButton::getSlot).toList();
                for (int i = 0; i < 9; i++) {
                    if (!integers.contains(i)) {
                        guiButtons.add(new NoEventButton(i, InventoryUtils.createItem(Material.GRAY_STAINED_GLASS_PANE, 1, " ")));
                    }
                }

                this.update(guiButtons, Translate.colorc("Servers"), guiSlots);

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
            }

            @Override
            public void onClose(InventoryCloseEvent inventoryCloseEvent) {

            }
        };
        guiWindow.open(player);
    }

    private void sendPlayerToServer(Player player, String server) {
        if (player == null || !player.isOnline()) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());
    }
}
