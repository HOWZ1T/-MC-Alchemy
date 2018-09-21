package com.dylan_randall.alchemy.listeners;

import com.dylan_randall.alchemy.items.OreCompass;
import com.dylan_randall.alchemy.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class OreCompassListener implements Listener {

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event)
    {
        // get player & player's inventory
        Player p = event.getPlayer();
        PlayerInventory inv = p.getInventory();
        if (inv == null) {return;}

        // Check if holding ore compass staff
        ItemStack item = inv.getItemInMainHand();
        if(item == null) {return;}
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {return;}
        String itemDisplayName = meta.getDisplayName();

        if (itemDisplayName.equals(OreCompass.displayName))
        {
            // if sneaking find the ore
            if(event.isSneaking())
            {
                String currentOre = null;
                List<String> lore = meta.getLore();
                for (String line : lore)
                {
                    String[] parts = line.split(":");
                    if (parts.length == 2)
                    {
                        if(parts[0].toLowerCase().equals("current ore"))
                        {
                            currentOre = parts[1].toLowerCase().substring(1);
                            break;
                        }
                    }
                }

                if (currentOre == null) {return;}

                Material targetMat = null;
                switch(currentOre)
                {
                    case "coal":
                        targetMat = Material.COAL_ORE;
                        break;

                    case "iron":
                        targetMat = Material.IRON_ORE;
                        break;

                    case "gold":
                        targetMat = Material.GOLD_ORE;
                        break;

                    case "lapis":
                        targetMat = Material.LAPIS_ORE;
                        break;

                    case "redstone":
                        targetMat = Material.REDSTONE_ORE;
                        break;

                    case "diamond":
                        targetMat = Material.DIAMOND_ORE;
                        break;

                    case "emerald":
                        targetMat = Material.EMERALD_ORE;
                        break;

                    default:
                        Utils.error("[Ore Compass] Unknown ore: " + currentOre);
                        break;
                }

                if (targetMat == null) {return;}
                Location targetLoc = null;
                Location playerLoc = p.getLocation();
                World world = p.getWorld();

                // searching in a 20 wide by 20 long by 10 high area: 20 x 20 x 10 = 4000 blocks
                int sideLength = 20, height = 10;
                int sideRadius = sideLength/2;
                int heightRadius = height/2;

                int xStart = playerLoc.getBlockX() - sideRadius;
                int yStart = playerLoc.getBlockY() - heightRadius;
                int zStart = playerLoc.getBlockZ() - sideRadius;

                int xEnd = playerLoc.getBlockX() + sideRadius;
                int yEnd = playerLoc.getBlockY() + heightRadius;
                int zEnd = playerLoc.getBlockZ() + sideRadius;

                findBlock:
                for (int y = yStart; y <= yEnd; y++)
                {
                    for(int x = xStart; x <= xEnd; x++)
                    {
                        for(int z = zStart; z <= zEnd; z++)
                        {
                            Material mat = world.getBlockAt(x, y, z).getType();

                            if (mat == targetMat)
                            {
                                targetLoc = new Location(world, x, y, z);
                                break findBlock;
                            }
                        }
                    }
                }

                if (targetLoc == null)
                {
                    p.sendMessage(Utils.chat(Utils.Colors.RED.getVal() + "Couldn't find any " + currentOre + " in " +
                            "the nearby area."));
                    return;
                }

                p.setCompassTarget(targetLoc);
                p.sendMessage(Utils.chat(Utils.Colors.GREEN.getVal() + "Tracking: " + currentOre + " ore"));
            }
            else
            {
                // resetting the compass to world spawn
                p.setCompassTarget(p.getWorld().getSpawnLocation());
                p.sendMessage(Utils.chat(Utils.Colors.AQUA.getVal() + "Compass Reset! Not tracking ore."));
            }
        }
    }

    // cycling the tracked ore on right clicking with the ore detector
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractEvent event)
    {
        // checking if player right clicks with the Ore Compass in their main hand
        if (event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR))
        {
            // get player & player's inventory
            Player p = event.getPlayer();
            PlayerInventory inv = p.getInventory();
            if (inv == null) {return;}

            // Check if holding ore compass staff
            ItemStack item = inv.getItemInMainHand();
            if(item == null) {return;}
            ItemMeta meta = item.getItemMeta();
            if(meta == null) {return;}
            String itemDisplayName = meta.getDisplayName();

            if (itemDisplayName.equals(OreCompass.displayName))
            {
                // getting current ore
                String currentOre = null;
                List<String> lore = meta.getLore();
                int index = 0;

                for (String line : lore)
                {
                    String[] parts = line.split(":");
                    if (parts.length == 2)
                    {
                        if(parts[0].toLowerCase().equals("current ore"))
                        {
                            currentOre = parts[1].toLowerCase().substring(1);
                            break;
                        }
                    }
                    index++;
                }

                // indirectly setting the next ore to coal if no initial ore was found by setting the current ore to emerald
                if (currentOre == null) { currentOre = "emerald"; }

                String nextOre = null;
                switch(currentOre) // cycling to the next ore
                {
                    case "coal":
                        nextOre = "iron";
                        break;

                    case "iron":
                        nextOre = "gold";
                        break;

                    case "gold":
                        nextOre = "lapis";
                        break;

                    case "lapis":
                        nextOre = "redstone";
                        break;

                    case "redstone":
                        nextOre = "diamond";
                        break;

                    case "diamond":
                        nextOre = "emerald";
                        break;

                    case "emerald":
                        nextOre = "coal";
                        break;

                    default:
                        Utils.error("[Ore Compass] Unknown ore: " + currentOre);
                        break;
                }

                if (nextOre == null)
                {
                    p.sendMessage(Utils.chat(Utils.Colors.RED.getVal() + "[Ore Compass] An unknown error occurred while trying to change the tracked ore :("));
                    Utils.error("[Ore Compass] Unknown error! Could not cycle to track next ore!");
                    return;
                }

                lore.set(index, "Current Ore: " + nextOre.toUpperCase().charAt(0) + nextOre.substring(1));
                meta.setLore(lore);
                item.setItemMeta(meta);
                p.sendMessage(Utils.chat(Utils.Colors.AQUA.getVal() + "Ore Compass target set to: " + nextOre + " ore."));
            }
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event)  // ensuring compass is reset on player quitting the server
    {
        Player p = event.getPlayer();
        p.setCompassTarget(p.getWorld().getSpawnLocation());
    }
}
