/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.AuctionHouse;

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

import com.RPGMakerDev.RPGMaker.RPGMaker;

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
    private ItemStack nextpage;       //nextpage item to click
    private ItemStack sortname;       //sort by name item to click
    private ItemStack sortnew;        //sort by new item to click
    private ItemStack sortold;        //sort by old item to click
    private int ndxDatabase;          //Keep track of where you are at
    
    /*
     * Set up an auction house for the player
     */
    public AuctionHouse(Player player) {
        this.page = 0;
        this.player = player;
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
        player.openInventory(getInventory());       
    }
    
    /*
     * Helper function to construct the inventory for the player
     * Create inventory, get the items from database
     * corresponding to which page player is on
     */
    public Inventory getInventory() {
        //player.sendMessage("Getting inventory");
        Inventory inv = Bukkit.createInventory(null, SIZE, "Auction House");
        inv.addItem(new ItemStack(Material.ICE));
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
        player.sendMessage("Sorting by name");
        return;
    }
    
    /*
     * Sorts the listing by newest items auctioned first
     */
    public void sortByNew() {
        player.sendMessage("Sorting by newest");
        return;
    }
    /*
     * Sorts the listing by oldest items auctioned first
     */
    public void sortByOld() {
        player.sendMessage("Sorting by oldest");
        return;
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
                    if (event.getCurrentItem().equals(nextpage)) {
                        //Get next page
                        player.sendMessage("Getting next page");
                        
                        Plugin plugin = RPGMaker.getPlugin(RPGMaker.class);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                player.closeInventory();
                                player.openInventory(getInventory());
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
            return;
        }
    }
}
