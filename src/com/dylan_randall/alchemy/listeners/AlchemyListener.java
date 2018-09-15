package com.dylan_randall.alchemy.listeners;

import com.dylan_randall.alchemy.items.AlchemyStaff;
import com.dylan_randall.alchemy.utils.Utils;
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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.Random;

public class AlchemyListener implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractEvent event)
    {
        // check if player is right clicking on a block with their main hand
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND)
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
                handleDirtToGrassAlchemy(p, block);
                handleBookEnchantAlchemy(p, block);
            }
        }
    }

    private void transmuteEffect(Player p, Location loc)
    {
        // volume 0 - 1, pitch 0.5 - 2 where 1 = normal speed
        p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.7f, 1.0f);

        // particle effects
        p.spawnParticle(Particle.SPELL, loc, 6);
        p.spawnParticle(Particle.ENCHANTMENT_TABLE, loc, 8);
        p.spawnParticle(Particle.REDSTONE, loc, 7, 0, 0.2, 0, 16, new Particle.DustOptions(Color.RED, 1));
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

                if (boot != null && itmFeather != null)
                {
                    boot.addEnchantment(Enchantment.PROTECTION_FALL, enchantLevel);
                    block.setType(Material.AIR); // destroying the block
                    Utils.removeItem(entFeather, itmFeather, 1); // destroying feather

                    Location particleLoc = block.getLocation().clone().add(0.5, 0.5, 0.5);
                    transmuteEffect(p, particleLoc);
                    break;
                }
            }
        }
    }

    /* The dirt to grass ritual requires: a dirt block and any seed dropped on top of any block and then right click
       the block with the Alchemist's Staff */
    private void handleDirtToGrassAlchemy(Player p, Block block)
    {
        ItemStack dirtBlock = null, seed = null;
        Entity dirtEntity = null, seedEntity = null;

        Collection<Entity> entities = p.getLocation().getWorld().getNearbyEntities(block.getLocation(), 1.5, 1.5, 1.5);
        for (Entity entity : entities)
        {
            if (entity.getType() == EntityType.DROPPED_ITEM)
            {
                ItemStack itm = ((Item) entity).getItemStack();

                switch(itm.getType())
                {
                    case DIRT:
                        dirtEntity = entity;
                        dirtBlock = itm;
                        break;

                    case WHEAT_SEEDS:
                        seedEntity = entity;
                        seed = itm;
                        break;
                }

                if (dirtEntity != null && seedEntity != null) // checking if recipe is complete
                {
                    // removing dirt block and seed
                    int grassAmount;
                    int dirtAmount = dirtBlock.getAmount();
                    int seedAmount = seed.getAmount();

                    // calculating ratio to produce the correct amount of grass blocks
                    if (dirtAmount <= seedAmount)
                    {
                        grassAmount = dirtAmount;
                    }
                    else
                    {
                        grassAmount = seedAmount;
                    }

                    Utils.removeItem(dirtEntity, dirtBlock, grassAmount);
                    Utils.removeItem(seedEntity, seed, grassAmount);

                    // spawn grass block as a dropped item
                    p.getWorld().dropItem(block.getLocation().clone().add(0.5, 1, 0.5),
                                            new ItemStack(Material.GRASS_BLOCK, grassAmount));
                    transmuteEffect(p, block.getLocation().clone().add(0.5, 1, 0.5));
                    break;
                }
            }
        }
    }

    /* the book enchant ritual requires a book and a ingot of any of the following: coal, iron, gold, lapis-lazuli,
       or diamond dropped on top of a block and then right click the block with the Alchemist's Staff */
    private void handleBookEnchantAlchemy(Player p, Block block)
    {
        ItemStack itmBook = null, itmOre = null;
        Entity entBook = null, entOre = null;

        boolean bookHasEnchants = false; // does the book already have enchants ?

        Collection<Entity> entities = p.getWorld().getNearbyEntities(block.getLocation(), 2, 2, 2);
        for (Entity entity : entities)
        {
            if (entity.getType() == EntityType.DROPPED_ITEM)
            {
                // getting the entities, items and enchantment level (which is based on the ore item)
                ItemStack itm = ((Item) entity).getItemStack();
                int enchantLevel = -1;
                switch(itm.getType())
                {
                    case COAL:
                        enchantLevel = 1;
                        itmOre = itm;
                        entOre = entity;
                        break;

                    case IRON_INGOT:
                        enchantLevel = 2;
                        itmOre = itm;
                        entOre = entity;
                        break;

                    case GOLD_INGOT:
                        enchantLevel = 3;
                        itmOre = itm;
                        entOre = entity;
                        break;

                    case LAPIS_LAZULI:
                        enchantLevel = 4;
                        itmOre = itm;
                        entOre = entity;
                        break;

                    case DIAMOND:
                        enchantLevel = 5;
                        itmOre = itm;
                        entOre = entity;
                        break;

                    case BOOK:
                        itmBook = itm;
                        entBook = entity;
                        break;

                    case ENCHANTED_BOOK:
                        itmBook = itm;
                        entBook = entity;
                        bookHasEnchants = true;
                        break;
                }

                if (itmOre != null && itmBook != null && enchantLevel > -1)
                {
                    ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                    Utils.removeItem(entOre, itmOre, 1);

                    if (!bookHasEnchants)
                    {
                        Utils.removeItem(entBook, itmBook, 1);
                    }
                    else
                    {
                        enchantedBook = itmBook;
                    }

                    int clampedEnchantLevel = 1;
                    Enchantment enchantment = Enchantment.DAMAGE_ALL;
                    Enchantment prevEnchantment = enchantment;
                    boolean enchanted = false;
                    int maxAttempts = 50;

                    // applying the enchant with this fault tolerant method to handle incompatible item type - enchants
                    while (!enchanted && maxAttempts > 0)
                    {
                        try
                        {
                            switch(enchantLevel)
                            {
                                case 1:
                                    while (enchantment == prevEnchantment)
                                    {
                                        enchantment = Utils.LEVEL_1_ENCHANTS[new Random().nextInt(Utils.LEVEL_1_ENCHANTS.length)];
                                    }
                                    break;

                                case 2:
                                    while (enchantment == prevEnchantment)
                                    {
                                        enchantment = Utils.LEVEL_2_ENCHANTS[new Random().nextInt(Utils.LEVEL_2_ENCHANTS.length)];
                                    }
                                    break;

                                case 3:
                                    while (enchantment == prevEnchantment)
                                    {
                                        enchantment = Utils.LEVEL_3_ENCHANTS[new Random().nextInt(Utils.LEVEL_3_ENCHANTS.length)];
                                    }
                                    break;

                                case 4:
                                    while (enchantment == prevEnchantment)
                                    {
                                        enchantment = Utils.LEVEL_4_ENCHANTS[new Random().nextInt(Utils.LEVEL_4_ENCHANTS.length)];
                                    }
                                    break;

                                case 5:
                                    while (enchantment == prevEnchantment)
                                    {
                                        enchantment = Utils.LEVEL_5_ENCHANTS[new Random().nextInt(Utils.LEVEL_5_ENCHANTS.length)];
                                    }
                                    break;
                            }

                            // clamping enchant level before applying it to avoid invalid enchantment level errors
                            clampedEnchantLevel = Utils.clampEnchantLevel(enchantment, enchantLevel);

                            // applying enchantment to the book
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
                            meta.addStoredEnchant(enchantment, clampedEnchantLevel, true);
                            enchantedBook.setItemMeta(meta);
                            enchanted = true;

                            // if the book was previously not enchanted we need to drop a new enchanted book in it's place
                            if (!bookHasEnchants)
                            {
                                // dropping the new enchanted book
                                p.getWorld().dropItem(block.getLocation().clone().add(0.5, 1, 0.5), enchantedBook);
                            }
                        }
                        catch (IllegalArgumentException e)
                        {
                            maxAttempts--;
                            prevEnchantment = enchantment;
                        }
                    }

                    if(!enchanted)
                    {
                        Utils.error(String.format("failed to enchant book!\n Details:\nPlayer: %s\nEnchantment: %s\n" +
                                "Enchantment Level: %s\n", p.getDisplayName(), enchantment.getKey().getKey(),
                                Integer.toString(clampedEnchantLevel)));
                        return;
                    }

                    // applying transmutation effects
                    transmuteEffect(p, block.getLocation().clone().add(0.5, 1.0, 0.5));
                    break;
                }
            }
        }
    }
}
