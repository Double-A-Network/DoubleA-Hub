package com.andrew121410.mc.doubleahub.worldguard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.Location;

public class DoubleJumpFlagHandler {

    private static StateFlag STATE_FLAG;

    public DoubleJumpFlagHandler() {
    }

    public static void register() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            // create a flag with the name "my-custom-flag", defaulting to true
            StateFlag flag = new StateFlag("double-jump", true);
            registry.register(flag);
            STATE_FLAG = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            STATE_FLAG = null;
        }
    }

    public static boolean canJump(Location location) {
        if (STATE_FLAG == null) return true;
        return WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(location)).testState(null, STATE_FLAG);
    }
}
