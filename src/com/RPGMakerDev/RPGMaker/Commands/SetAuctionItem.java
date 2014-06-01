/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.Commands;

import com.RPGMakerDev.RPGMaker.StoredData.Database;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetAuctionItem implements CommandExecutor {
    private Database database = new Database();
    /*
     * Auctions the item in hand
     *  "/auction <cost>
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length == 1) {
                try {
                    int cost = Integer.parseInt(args[0]);
                    player.sendMessage("You auctioned your item for " + cost);
                    if (player.getItemInHand() != null) {
                        final ItemStack item = player.getItemInHand();
                        player.getInventory().remove(item);
                        saveItem(player, item, cost);
                        
                        return true;
                    }
                    else {
                        player.sendMessage("You can't auction that");
                    }
                }
                catch(NumberFormatException e) {
                    player.sendMessage("The cost you entered is not a number");
                    return false;
                }
            }
        }
        return false;
    }
    private void saveItem(Player player, ItemStack item, int cost) {
        ItemMeta meta = item.getItemMeta();
        String name = meta.getDisplayName();
        name = ChatColor.stripColor(name);
        try {
            int id = 1, bytes = 0;
            this.database.getConnection();
            String Query = "select ID, BYTE from `items` where `ITEMNAME` = '" + name + "';";
            this.database.Query = this.database.connection.prepareStatement(Query);
            this.database.Results = this.database.Query.executeQuery();
            while (this.database.Results.next()) {
                id = this.database.Results.getInt("ID");
                bytes = this.database.Results.getInt("BYTE");
            }
            //Date date = new Date();
            Calendar calendar = new GregorianCalendar();
            Date date = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
            String start = format.format(date);
            calendar.add(Calendar.HOUR, 12);
            date = calendar.getTime();
            String end = format.format(date);
            
            Query = "insert into `auctionhouse` (`SELLER`, `COST`, `START`, `END`,"
                    + " `ITEMID`, `BYTE`, `AMOUNT`)"
                    + " values ('"+player.getUniqueId().toString()+"', " +cost+ ", " + "'"
                    + start + "', '" +end+ "', " +id+ ", " +bytes+ ", " +item.getAmount()+ ");";
            this.database.Query = this.database.connection.prepareStatement(Query);
            this.database.Query.execute();
            
            this.database.Query.close();
            this.database.Results.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
