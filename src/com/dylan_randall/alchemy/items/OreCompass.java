package com.dylan_randall.alchemy.items;

import com.dylan_randall.alchemy.interfaces.Item;
import com.dylan_randall.alchemy.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class OreCompass implements Item {

    public static String key = "ore_compass";
    public static String displayName = Utils.chat(Utils.Colors.BLUE.getVal() + "Ore Compass");
    public ItemStack item;
    public ItemMeta meta;
    public ShapedRecipe recipe;
    public NamespacedKey nskey;

    public OreCompass(Plugin plugin)
    {
        this.item = new ItemStack(Material.COMPASS);
        this.meta = this.item.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.add("This compass is used for finding ore.");
        lore.add("Current Ore: Coal");

        this.meta.setDisplayName(OreCompass.displayName);
        this.meta.setLore(lore);
        this.item.setItemMeta(meta);

        this.nskey = new NamespacedKey(plugin, OreCompass.key);
        this.recipe = new ShapedRecipe(this.nskey, item);
        this.recipe.shape(" d ", "dcd", " d ");
        this.recipe.setIngredient('d', Material.DIAMOND_BLOCK);
        this.recipe.setIngredient('c', Material.COMPASS);

        Bukkit.getServer().addRecipe(this.recipe);
    }
}
