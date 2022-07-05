package com.andrew121410.mc.doubleahub.listeners;

import com.andrew121410.mc.doubleahub.DoubleAHub;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class OnFoodLevelChangeEvent implements Listener {

    private final DoubleAHub plugin;

    public OnFoodLevelChangeEvent(DoubleAHub plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getFoodLevel() < 20) event.setFoodLevel(20);
    }
}
