package com.dylan_randall.alchemy.interfaces;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public interface Item {
    public ItemStack item = null;
    public ItemMeta meta = null;
    public ShapedRecipe recipe = null;
    public NamespacedKey key = null;
}
