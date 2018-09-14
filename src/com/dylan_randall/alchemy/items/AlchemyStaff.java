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

public class AlchemyStaff implements Item {

    public static String key = "alchemists_staff";
    public static String displayName = Utils.chat(Utils.Colors.GOLD.getVal() + "Alchemist's Staff");
    public ItemStack item;
    public ItemMeta meta;
    public ShapedRecipe recipe;
    public NamespacedKey nskey;

    public AlchemyStaff(Plugin plugin)
    {
        this.item = new ItemStack(Material.STICK);
        this.meta = this.item.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.add("This staff is used for basic transmutation.");

        this.meta.setDisplayName(AlchemyStaff.displayName);
        this.meta.setLore(lore);
        this.meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
        this.item.setItemMeta(meta);

        this.nskey = new NamespacedKey(plugin, AlchemyStaff.key);
        this.recipe = new ShapedRecipe(this.nskey, item);
        this.recipe.shape("g  ", " s ", "  s");
        this.recipe.setIngredient('g', Material.GOLD_NUGGET);
        this.recipe.setIngredient('s', Material.STICK);

        Bukkit.getServer().addRecipe(this.recipe);
    }
}
