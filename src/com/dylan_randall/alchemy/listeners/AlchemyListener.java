package com.dylan_randall.alchemy.listeners;

import com.dylan_randall.alchemy.items.AlchemyStaff;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

public class AlchemyListener implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractEvent event)
    {
        // check if player is right clicking on a block
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            // get player & player's inventory
            Player p = event.getPlayer();
            PlayerInventory inv = p.getInventory();
            if (inv == null) {return;}

            // Check if holding alchemist's staff
            ItemStack item = inv.getItemInMainHand();
            if(item == null) {return;}
            ItemMeta meta = item.getItemMeta();
            if(meta == null) {return;}
            String itemDisplayName = meta.getDisplayName();

            if (itemDisplayName.equals(AlchemyStaff.displayName))
            {
                // get clicked block
                Block block = event.getClickedBlock();
                if (block == null) {return;}
                Material material = block.getType();
                if (material == null) {return;}

                // handling alchemy effects
                handleBootEnchantAlchemy(p, material, block);
            }
        }
    }

    /* The boot enchanting ritual requires: a boot and a feather dropped on top of a coal, iron, gold or diamond block
       and then right click the block with the Alchemist's Staff */
    private void handleBootEnchantAlchemy(Player p, Material material, Block block)
    {
        // checking if the block clicked can give a feather falling level
        int enchantLevel = -1;
        switch(material)
        {
            case COAL_BLOCK:
                enchantLevel = 1;
                break;

            case IRON_BLOCK:
                enchantLevel = 2;
                break;

            case GOLD_BLOCK:
                enchantLevel = 3;
                break;

            case DIAMOND_BLOCK:
                enchantLevel = 4;
                break;
        }
        if (enchantLevel == -1) {return;}

        ItemStack boot = null;
        ItemStack itmFeather = null;
        Entity entFeather = null;
        // getting nearby entities
        Collection<Entity> entities = p.getLocation().getWorld().getNearbyEntities(block.getLocation(), 1.5, 1.5, 1.5);
        for (Entity entity : entities)
        {
            if (entity.getType() == EntityType.DROPPED_ITEM)
            {
                ItemStack itm = ((Item) entity).getItemStack();
                switch (itm.getType())
                {
                    case LEATHER_BOOTS:
                    case IRON_BOOTS:
                    case GOLDEN_BOOTS:
                    case DIAMOND_BOOTS:
                    case CHAINMAIL_BOOTS:
                        boot = itm;
                        break;

                    case FEATHER:
                        itmFeather = itm;
                        entFeather = entity;
                        break;
                }

                if (boot != null && itmFeather != null && entFeather != null)
                {
                    boot.addEnchantment(Enchantment.PROTECTION_FALL, enchantLevel);
                    // volume 0 - 1, pitch 0.5 - 2 where 1 = normal speed
                    p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.7f, 1.0f);
                    block.setType(Material.AIR); // destroying the block

                    // removing 1 feather
                    if (itmFeather.getAmount() > 1)
                    {
                        itmFeather.setAmount(itmFeather.getAmount()-1);
                    }
                    else
                    {
                        entFeather.remove(); // remove the feather entity if there is only one feather in the item stack
                    }

                    Location particleLoc = block.getLocation().clone().add(0.5, 0.5, 0.5);
                    p.spawnParticle(Particle.SPELL, particleLoc, 6);
                    p.spawnParticle(Particle.ENCHANTMENT_TABLE, particleLoc, 8);
                    p.spawnParticle(Particle.REDSTONE, particleLoc, 7, 0, 0.2, 0, 16, new Particle.DustOptions(Color.RED, 1));
                    break;
                }
            }
        }
    }
}
