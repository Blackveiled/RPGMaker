/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.Commands;

import com.RPGMakerDev.RPGMaker.Economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author matthew
 */
public class EconomyCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (!(cs instanceof Player)) {return false;}
        Player player = (Player)cs;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("balance")) {
                Economy e = new Economy(player);
                player.sendMessage(e.getMoneyString(e.getPlayer().gold));
                return true;
            }
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                Economy e= new Economy(player);
                try {
                    e.addMoney(Integer.parseInt(args[1]));
                    return true;
                }
                catch(NumberFormatException exc) {
                    player.sendMessage("You have entered an invalid number");
                }
            }
            else if (args[0].equalsIgnoreCase("subtract")) {
                Economy e= new Economy(player);
                try {
                    e.subtractMoney(Integer.parseInt(args[1]));
                    return true;
                }
                catch(NumberFormatException exc) {
                    player.sendMessage("You have entered an invalid number");
                }
            }
        }
        
        return false;
    }
    
}
