package com.dylan_randall.alchemy;

import com.dylan_randall.alchemy.listeners.AlchemyListener;
import com.dylan_randall.alchemy.managers.ItemManager;
import com.dylan_randall.alchemy.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    private FileConfiguration config = getConfig();
    private ItemManager itemManager;

    @Override
    public void onEnable()
    {
        Utils.println("registering event listeners...");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new AlchemyListener(), this);
        Utils.println("registering items...");
        itemManager = new ItemManager(this);
        itemManager.loadItems();
        Utils.println("successfully loaded.");
    }

    @Override
    public void onDisable() {}
}
