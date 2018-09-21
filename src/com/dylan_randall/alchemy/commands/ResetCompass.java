package com.dylan_randall.alchemy.commands;

import com.dylan_randall.alchemy.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCompass implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player p = (Player) sender;
            p.setCompassTarget(p.getWorld().getSpawnLocation());
            p.sendMessage(Utils.chat(Utils.Colors.AQUA.getVal() + "Your compass has been reset to the world's spawnpoint."));
            return true;
        }

        sender.sendMessage(Utils.chat(Utils.Colors.RED.getVal() + "Only players can use this command!"));
        return false;
    }
}
