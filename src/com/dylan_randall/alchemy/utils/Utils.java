package com.dylan_randall.alchemy.utils;

import org.bukkit.ChatColor;

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

    public static void println(String s) {System.out.println(Utils.tag(s));}
    public static void error(String s) {System.err.println(Utils.tag(s));}
    public static String tag(String s) {return "[Alchemy] " + s;}

    public static String chat(String s) {return ChatColor.translateAlternateColorCodes('&', s);}
}
