/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Commands;

import com.RPGMakerDev.RPGMaker.Inventory.RPGItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class item implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof Player) {
            Player p = (Player) s;
            if (args.length > 0) {
                int parsed = Integer.parseInt(args[0]);
                RPGItem give = new RPGItem(parsed, 1);
                give.updateItemLore();
                give.updateItemMeta();
                p.getInventory().addItem(give.getItemStack());
            }
            return true;
        }
        s.sendMessage("You must be logged in to use this command");
        return true;
    }
}
