/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData.LootingSystem;

import com.RPGMakerDev.RPGMaker.Inventory.RPGItem;
import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootChest {

    public Database database;
    public static Map<Location, LootChest> chests = new HashMap<>();
    public List<Loot> loots = new ArrayList<>();
    public List<Loot> wins;
    public Location lootChestLocation;
    public long chestExpiration;
    public Material previousBlock;
    public Chest lootChest;
    public UUID uuid; // The guy who is allowed to open this chest.

    /**
     * Use this method if you wish to create another LootChest alongside a
     * lootchest which is nearby.
     *
     * @param uuid
     * @param lootChest
     */
    public LootChest(UUID uuid, Location location, List<Loot> loots) {
        this.uuid = uuid;

        for (int i = 0; i < loots.size(); i++) {
            if (loots.get(i).roll()) {
                if (wins == null) {
                    wins = new ArrayList<>();
                }
                wins.add(loots.get(i));
            }
        }
        if (wins != null) {
            location.getBlock().setType(Material.CHEST);
            for (int i = 0; i < wins.size(); i++) {
                RPGItem rpg = wins.get(i).item;
                rpg.updateItemLore();
                rpg.updateItemMeta();
                ItemStack item = rpg.getItemStack();
                Chest chest = (Chest) location.getBlock().getState();
                chest.getBlockInventory().addItem(item);

                Date time = new Date();
                chestExpiration = time.getTime() + 300000;
                LootChest.chests.put(location, this);
            }
        }
    }

    public Inventory openChest() {
        Inventory Loot = Bukkit.createInventory(Bukkit.getPlayer(uuid), 27, "Loot Chest");
        for (int i = 9; i < wins.size(); i++) {
            RPGItem rpg = wins.get(i).item;
            rpg.updateItemLore();
            rpg.updateItemMeta();
            ItemStack item = rpg.getItemStack();
        }
        return Loot;
    }

    public void linkChest(Location loc) {
        if (loc.getBlock().getType().equals(Material.CHEST)) {
            this.lootChest = (Chest) loc.getBlock();
        }
    }

    public void addLootChestEntry() {
        try {
            database = new Database();
            database.getConnection();
            String Query = "INSERT INTO `lootchesttimers` (`X`, `Y`, `Z`, `PREVIOUSBLOCK`, `TIMESET`) VALUES ("
                    + Double.toString(lootChestLocation.getX()) + ", "
                    + Double.toString(lootChestLocation.getY()) + ", "
                    + Double.toString(lootChestLocation.getZ()) + ", "
                    + "'Material.AIR', "
                    + this.chestExpiration + ");";
            database.Query.execute(Query);

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
        }
    }
}
