/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.AuctionHouse;

import com.RPGMakerDev.RPGMaker.Inventory.RPGItem;
import com.RPGMakerDev.RPGMaker.RPGMaker;
import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

/*
 * Item representation
 *  - ItemStack info (need material type n name for sorting)
 *  - Seller
 *  - Cost (put in lore also)
 *  - Start date
 *  - end date (organize by end dates in db)
 *  
 *  Database
 *   - Need to know how many items in AH
 *   - Be able to reverse through database for getting items
 *     - new -> old
 *     - old -> new
 *   -
 */
public class AuctionHouse {
    private int page;                 //What page number the player is on
    private Player player;            //The player...
    private final int SIZE = 54;      //Double chest size, for reference
    private final int PAGE_SIZE = 45; //How many items to put on a page
    private ItemStack nextpage;       //nextpage item to click
    private ItemStack sortname;       //sort by name item to click
    private ItemStack sortnew;        //sort by new item to click
    private ItemStack sortold;        //sort by old item to click
    private int sortingType = 0;          //Keep track of sorting
    private Database database;
    
    /*
     * Set up an auction house for the player
     */
    public AuctionHouse(Player player) {
        this.page = 0;
        this.player = player;
        database = new Database();
        setupStaticItems();
        
        //Add listener for next page
        RPGMaker plugin = RPGMaker.getPlugin(RPGMaker.class);
        plugin.getServer().getPluginManager().registerEvents(new AuctionHouseListener(), plugin);
    }
    
    private void setupStaticItems() {
        this.nextpage = new ItemStack(Material.WOOL);
        ItemMeta meta = this.nextpage.getItemMeta();
        meta.setDisplayName("Next Page");
        this.nextpage.setItemMeta(meta);
        
        this.sortname = new ItemStack(Material.WOOL);
        meta = this.sortname.getItemMeta();
        meta.setDisplayName("Sort by name");
        this.sortname.setItemMeta(meta);
        
        this.sortnew = new ItemStack(Material.WOOL);
        meta = this.sortnew.getItemMeta();
        meta.setDisplayName("Sort by newest");
        this.sortnew.setItemMeta(meta);
        
        this.sortold = new ItemStack(Material.WOOL);
        meta = this.sortold.getItemMeta();
        meta.setDisplayName("Sort by oldest");
        this.sortold.setItemMeta(meta);
    }
    
    /*
     * Opens up the first viewing of items for the player
     */
    public void startAuctionHouse() {
        player.openInventory(sortByNew());       
    }
    
    /*
     * Helper function to construct the inventory for the player
     * Create inventory, get the items from database
     * corresponding to which page player is on
     *
     * sortingType():  0 = sortByNew()
     *                 1 = sortByOld()
     *                 2 = sortByName()
     */
    public Inventory getInventory() {
        //player.sendMessage("Getting inventory");
        Inventory inv = Bukkit.createInventory(null, SIZE, "Auction House");
        try {
            this.database.getConnection();
            String Query = "";
            if (sortingType == 0) {
                Query = "select * from `auctionhouse` order by `ID` desc limit " + 
                    PAGE_SIZE + " offset " + (page * PAGE_SIZE);
            }
            else if (sortingType == 1) {
                Query = "select * from `auctionhouse` order by `ID` limit " + 
                    PAGE_SIZE + " offset " + (page * PAGE_SIZE);
            }
            
            this.database.Query = this.database.connection.prepareStatement(Query);
            this.database.Results = this.database.Query.executeQuery();
            while (this.database.Results.next()) {
                //Put items into inv to display
                int id = this.database.Results.getInt("ITEMID");
                float dur = this.database.Results.getFloat("DUR");
                int amount = this.database.Results.getInt("AMOUNT");
                RPGItem item = new RPGItem(id, 0, dur, amount);
                item.updateItemLore();
                item.updateItemMeta();
                inv.addItem(item.getItemStack());
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        inv.setItem(SIZE-3, nextpage);
        inv.setItem(SIZE-4, sortname);
        inv.setItem(SIZE-5, sortnew);
        inv.setItem(SIZE-6, sortold);
        return inv;
    }
    
    /*
     * Sorts item by the name the player entered
     */
    public void sortByName() {
        page = 0;
        player.sendMessage("Sorting by name");
        return;
    }
    
    /*
     * Sorts the listing by newest items auctioned first
     */
    public Inventory sortByNew() {
        page = 0;
        Inventory inv = Bukkit.createInventory(null, SIZE, "Auction House");
        try {
            this.database.getConnection();
            String Query = "select * from `auctionhouse` order by `ID` desc limit " + 
                    PAGE_SIZE + " offset " + (page * PAGE_SIZE);
            this.database.Query = this.database.connection.prepareStatement(Query);
            this.database.Results = this.database.Query.executeQuery();
            while (this.database.Results.next()) {
                //Put items into inv to display
                int id = this.database.Results.getInt("ITEMID");
                float dur = this.database.Results.getFloat("DUR");
                int amount = this.database.Results.getInt("AMOUNT");
                RPGItem item = new RPGItem(id, 0, dur, amount);
                item.updateItemLore();
                item.updateItemMeta();
                inv.addItem(item.getItemStack());
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        inv.setItem(SIZE-3, nextpage);
        inv.setItem(SIZE-4, sortname);
        inv.setItem(SIZE-5, sortnew);
        inv.setItem(SIZE-6, sortold);
        player.sendMessage("Sorting by newest");
        return inv;
    }
    /*
     * Sorts the listing by oldest items auctioned first
     */
    public Inventory sortByOld() {
        page = 0;
        Inventory inv = Bukkit.createInventory(null, SIZE, "Auction House");
        try {
            this.database.getConnection();
            String Query = "select * from `auctionhouse` order by `ID` limit " + 
                    PAGE_SIZE + " offset " + (page * PAGE_SIZE);
            this.database.Query = this.database.connection.prepareStatement(Query);
            this.database.Results = this.database.Query.executeQuery();
            while (this.database.Results.next()) {
                //Put items into inv to display
                int id = this.database.Results.getInt("ITEMID");
                float dur = this.database.Results.getFloat("DUR");
                int amount = this.database.Results.getInt("AMOUNT");
                RPGItem item = new RPGItem(id, 0, dur, amount);
                item.updateItemLore();
                item.updateItemMeta();
                inv.addItem(item.getItemStack());
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        inv.setItem(SIZE-3, nextpage);
        inv.setItem(SIZE-4, sortname);
        inv.setItem(SIZE-5, sortnew);
        inv.setItem(SIZE-6, sortold);
        player.sendMessage("Sorting by oldest");
        return inv;
    }
    
    /*
     * Class that implements listeners for the auction house
     */
    public class AuctionHouseListener implements Listener {
        @EventHandler
        public void NextPageEvent(InventoryClickEvent event) {
            //Get next items when next page item is clicked
            if (event.getInventory().getTitle().equals("Auction House")) {
                System.out.println(player.getName() + " " + page);
                if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
                    Plugin plugin = RPGMaker.getPlugin(RPGMaker.class);
                    boolean flag = true;
                    Inventory inv = null;
                    
                    if (event.getCurrentItem().equals(nextpage)) {
                        //Get next page
                        player.sendMessage("Getting next page");
                        page++;
                        inv = getInventory();
                    }
                    else if (event.getCurrentItem().equals(sortname)) {
                        sortingType = 2;
                        sortByName();
                    }
                    else if (event.getCurrentItem().equals(sortnew)) {
                        sortingType = 0;
                        inv = sortByNew();
                    }
                    else if (event.getCurrentItem().equals(sortold)) {
                        sortingType = 1;
                        inv = sortByOld();
                    }
                    else {
                        flag = false;
                        player.sendMessage("You are trying to buy something");
                    }
                    final Inventory savedInv = inv;
                    if (flag) {
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    player.closeInventory();
                                    player.openInventory(savedInv);
                                }
                            }, 2);
                    }
                }
                event.setCancelled(true);           
            }
        }
    
        @EventHandler
        public void closeEvent(InventoryCloseEvent event) {
            //System.out.println("Closing something");
            if (event.getInventory().getTitle().equals("Auction House")) {
                System.out.println("I am closed");
                HandlerList.unregisterAll(this);
            }
        }
    }
}
