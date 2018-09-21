package com.dylan_randall.alchemy.managers;

import com.dylan_randall.alchemy.items.AlchemyStaff;
import com.dylan_randall.alchemy.interfaces.Item;
import com.dylan_randall.alchemy.items.OreCompass;
import com.dylan_randall.alchemy.utils.Utils;
import org.bukkit.plugin.Plugin;

import java.util.Dictionary;
import java.util.Hashtable;

public class ItemManager {

    private Dictionary<String, Item> items = new Hashtable<>();
    private Plugin plugin;

    public ItemManager(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public void loadItems()
    {
        this.addItem(AlchemyStaff.key, new AlchemyStaff(this.plugin));
        this.addItem(OreCompass.key, new OreCompass(this.plugin));
    }

    public void addItem(String key, Item item)
    {
        this.items.put(key, item);
        Utils.println("[item] added " + key);
    }

    public Item getItem(String key)
    {
        return this.items.get(key);
    }
}
