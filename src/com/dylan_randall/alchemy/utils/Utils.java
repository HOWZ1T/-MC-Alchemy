package com.dylan_randall.alchemy.utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class Utils {

    public enum Colors
    {
        DARK_RED("&4"),
        RED("&c"),
        GOLD("&6"),
        YELLOW("&e"),
        DARK_GREEN("&2"),
        GREEN("&a"),
        AQUA("&b"),
        DARK_AQUA("&3"),
        DARK_BLUE("&1"),
        BLUE("&9"),
        LIGHT_PURPLE("&d"),
        DARK_PURPLE("&5"),
        WHITE("&f"),
        GRAY("&7"),
        DARK_GRAY("&8"),
        BLACK("&0");

        private String val;
        Colors(String val)
        {
            this.val = val;
        }

        public String getVal()
        {
            return val;
        }
    }

    public static Enchantment[] LEVEL_1_ENCHANTS = new Enchantment[]{
            // Level 1 enchants only
            Enchantment.WATER_WORKER, // Aqua Affinity
            Enchantment.CHANNELING,
            Enchantment.BINDING_CURSE,
            Enchantment.VANISHING_CURSE,
            Enchantment.ARROW_FIRE,
            Enchantment.ARROW_INFINITE,
            Enchantment.MENDING,
            Enchantment.SILK_TOUCH,

            // enchants that can be >= 1 level
            Enchantment.DAMAGE_ARTHROPODS, // Bane of Arthropods
            Enchantment.PROTECTION_EXPLOSIONS, // Blast Protection
            Enchantment.DEPTH_STRIDER,
            Enchantment.DURABILITY,
            Enchantment.PROTECTION_FALL,
            Enchantment.FIRE_ASPECT,
            Enchantment.PROTECTION_FIRE,
            Enchantment.LOOT_BONUS_BLOCKS,
            Enchantment.FROST_WALKER,
            Enchantment.IMPALING,
            Enchantment.KNOCKBACK,
            Enchantment.LOOT_BONUS_MOBS,
            Enchantment.LOYALTY,
            Enchantment.LUCK,
            Enchantment.LURE,
            Enchantment.ARROW_DAMAGE,
            Enchantment.PROTECTION_PROJECTILE,
            Enchantment.PROTECTION_ENVIRONMENTAL,
            Enchantment.ARROW_KNOCKBACK,
            Enchantment.OXYGEN,
            Enchantment.RIPTIDE,
            Enchantment.DAMAGE_ALL,
            Enchantment.DAMAGE_UNDEAD,
            Enchantment.SWEEPING_EDGE,
            Enchantment.THORNS,
            Enchantment.DIG_SPEED
    };

    public static Enchantment[] LEVEL_2_ENCHANTS = new Enchantment[] {
            // level 2 enchants only
            Enchantment.FIRE_ASPECT, // 2
            Enchantment.FROST_WALKER, // 2
            Enchantment.KNOCKBACK, // 2
            Enchantment.ARROW_KNOCKBACK, // 2

            // enchants that can be >= 2 level
            Enchantment.DAMAGE_ARTHROPODS, // Bane of Arthropods, 5
            Enchantment.PROTECTION_EXPLOSIONS, // Blast Protection, 4
            Enchantment.DEPTH_STRIDER, // 3
            Enchantment.DURABILITY, // 3
            Enchantment.PROTECTION_FALL, // 4
            Enchantment.PROTECTION_FIRE, // 4
            Enchantment.LOOT_BONUS_BLOCKS, // 3
            Enchantment.IMPALING, // 5
            Enchantment.LOOT_BONUS_MOBS, // 3
            Enchantment.LOYALTY, // 3
            Enchantment.LUCK, // 3
            Enchantment.LURE, // 3
            Enchantment.ARROW_DAMAGE, // 5
            Enchantment.PROTECTION_PROJECTILE, // 4
            Enchantment.PROTECTION_ENVIRONMENTAL, // 4
            Enchantment.OXYGEN, // 3
            Enchantment.RIPTIDE, // 3
            Enchantment.DAMAGE_ALL, // 5
            Enchantment.DAMAGE_UNDEAD, // 5
            Enchantment.SWEEPING_EDGE, // 3
            Enchantment.THORNS, // 3
            Enchantment.DIG_SPEED // 5
    };

    public static Enchantment[] LEVEL_3_ENCHANTS = new Enchantment[] {
            // Level 3 enchants only
            Enchantment.DEPTH_STRIDER, // 3
            Enchantment.DURABILITY, // 3
            Enchantment.LOOT_BONUS_BLOCKS, // 3
            Enchantment.LOOT_BONUS_MOBS, // 3
            Enchantment.LOYALTY, // 3
            Enchantment.LUCK, // 3
            Enchantment.LURE, // 3
            Enchantment.OXYGEN, // 3
            Enchantment.RIPTIDE, // 3
            Enchantment.SWEEPING_EDGE, // 3
            Enchantment.THORNS, // 3

            // enchants that can be >= 3 level
            Enchantment.DAMAGE_ARTHROPODS, // Bane of Arthropods, 5
            Enchantment.PROTECTION_EXPLOSIONS, // Blast Protection, 4
            Enchantment.PROTECTION_FALL, // 4
            Enchantment.PROTECTION_FIRE, // 4
            Enchantment.IMPALING, // 5
            Enchantment.ARROW_DAMAGE, // 5
            Enchantment.PROTECTION_PROJECTILE, // 4
            Enchantment.PROTECTION_ENVIRONMENTAL, // 4
            Enchantment.DAMAGE_ALL, // 5
            Enchantment.DAMAGE_UNDEAD, // 5
            Enchantment.DIG_SPEED // 5
    };

    public static Enchantment[] LEVEL_4_ENCHANTS = new Enchantment[] {
            // Level 4 enchants only
            Enchantment.PROTECTION_EXPLOSIONS, // Blast Protection, 4
            Enchantment.PROTECTION_FALL, // 4
            Enchantment.PROTECTION_FIRE, // 4
            Enchantment.PROTECTION_PROJECTILE, // 4
            Enchantment.PROTECTION_ENVIRONMENTAL, // 4

            // enchants that can be >= 4 level
            Enchantment.DAMAGE_ARTHROPODS, // Bane of Arthropods, 5
            Enchantment.IMPALING, // 5
            Enchantment.ARROW_DAMAGE, // 5
            Enchantment.DAMAGE_ALL, // 5
            Enchantment.DAMAGE_UNDEAD, // 5
            Enchantment.DIG_SPEED // 5
    };

    public static Enchantment[] LEVEL_5_ENCHANTS = new Enchantment[] {
            // Level 5 enchants only
            Enchantment.DAMAGE_ARTHROPODS, // Bane of Arthropods, 5
            Enchantment.IMPALING, // 5
            Enchantment.ARROW_DAMAGE, // 5
            Enchantment.DAMAGE_ALL, // 5
            Enchantment.DAMAGE_UNDEAD, // 5
            Enchantment.DIG_SPEED // 5
    };

    // clamps the enchantment level to avoid invalid enchanment level errors when applying enchants to items
    // returns int (the clamped enchantment level)
    public static int clampEnchantLevel(Enchantment enchantment, int level)
    {
        if (level > enchantment.getMaxLevel())
        {
            return enchantment.getMaxLevel();
        }
        else if (level < enchantment.getStartLevel())
        {
            return enchantment.getStartLevel();
        }

        return level;
    }

    public static void println(String s) {System.out.println(Utils.tag(s));}
    public static void error(String s) {System.err.println(Utils.tag(s));}
    public static String tag(String s) {return "[Alchemy] " + s;}

    public static String chat(String s) {return ChatColor.translateAlternateColorCodes('&', s);}

    public static void removeItem(Entity entity, ItemStack item, int amount)
    {
        if (item.getAmount()-amount >= 1)
        {
            item.setAmount(item.getAmount()-amount);
        }
        else
        {
            entity.remove();
        }
    }

    public static Location toSafeBlockLocation(Location loc) // converts a location to a safe block coordinate
    {
        Location newLoc = new Location(loc.getWorld(), Location.locToBlock(loc.getX()), Location.locToBlock(loc.getY()),
                Location.locToBlock(loc.getZ()));

        return newLoc;
    }

    // returns the result of a comparison on two locations x, y and z coordinates.
    public static boolean areLocationCoordsEqual(Location locA, Location locB)
    {
        if (locA.getX() == locB.getX() && locA.getY() == locB.getY() && locA.getZ() == locB.getZ())
        {
            return true;
        }

        return false;
    }
}
