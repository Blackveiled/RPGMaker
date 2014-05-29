/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Inventory;

import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RPGInventory {

    public Database database;
    public Map<Integer, RPGItem> RPGInventory = new HashMap<>();
    public UUID uuid;

    public RPGInventory(UUID uuid) {
        this.uuid = uuid;
        try {
            database = new Database();
            database.getConnection();
            String query = "SELECT * FROM `players_inventories` WHERE `UUID`='" + uuid.toString() + "'";
            database.Query = database.connection.prepareStatement(query);
            database.Results = database.Query.executeQuery();
            int count = 0;
            while (database.Results.next()) {
                RPGItem retrieved = new RPGItem(database.Results.getInt("ITEMID"), database.Results.getInt("ITEMSLOT"), database.Results.getFloat("DURA"), database.Results.getInt("AMOUNT"));
                retrieved.updateItemLore();
                retrieved.updateItemMeta();
                addItem(database.Results.getInt("ITEMSLOT"), retrieved);
                count++;
            }
            Bukkit.getPlayer(uuid).getInventory().clear();
            for (int i = 0; i < 30; i++) {
                if (RPGInventory.containsKey(i)) {
                    Bukkit.getPlayer(uuid).getInventory().setItem(i, RPGInventory.get(i).getItemStack());
                }
            }
        } catch (Exception exception) {

        } finally {

        }
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void addItem(int Slot, RPGItem Item) {
        RPGInventory.put(Slot, Item);
        getPlayer().getInventory().setItem(Slot, Item.getItemStack());
    }

    public void removeItem(int Slot) {
        RPGInventory.remove(Slot);
        getPlayer().getInventory().clear(Slot);
        try {
            database.getConnection();
            String delete = "DELETE FROM `players_inventories` WHERE `UUID`='" + this.uuid + " AND `ITEMSLOT`=" + Slot + "';";
            database.Query.execute(delete);
        } catch (Exception exception) {

        }
    }

    public RPGItem getItem(int Slot) {
        if (RPGInventory.containsKey(Slot)) {
            return RPGInventory.get(Slot);
        }
        return null;
    }

    public void saveItem(int slot, RPGItem item) {
        RPGInventory.put(slot, item);
        try {
            database.getConnection();
            getPlayer().getInventory().setItem(slot, item.getItemStack());
            String importInventory = "INSERT INTO `players_inventories` (`UUID`, `ITEMSLOT`, `ITEMID`, `AMOUNT`, `DURA`) VALUES ('" + this.uuid + "', "
                    + slot + ", " + item.id + ", " + item.amount + ", " + item.durability + ");";
            this.database.Query.execute(importInventory);
        } catch (Exception exception) {

        }
    }
}
