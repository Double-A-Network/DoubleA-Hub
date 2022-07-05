package com.andrew121410.mc.doubleahub;


import com.andrew121410.mc.world16utils.utils.ccutils.utils.AbstractBasicSelfUpdater;

public class Updater extends AbstractBasicSelfUpdater {

    private static final String JAR_URL = "https://github.com/Double-A-Network/DoubleA-Hub/releases/download/latest/DoubleA-Hub.jar";
    private static final String HASH_URL = "https://github.com/Double-A-Network/DoubleA-Hub/releases/download/latest/hash.txt";

    public Updater(DoubleAHub plugin) {
        super(plugin.getClass(), JAR_URL, HASH_URL);
    }
}
