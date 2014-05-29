package com.RPGMakerDev.RPGMaker.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.RPGMakerDev.RPGMaker.RPGMaker;

public class SetGraveyard implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
     String[] args) {
        
        if (!(sender instanceof Player)) {return false;}
        Player player = (Player)sender;
        
        if (args.length == 0) {
            RPGMaker.gy.addGraveyard(player.getLocation());
            player.sendMessage("Added graveyard");
            return true;
        }
        
        
        return false;
    }
}
