/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Inventory;

import java.util.HashMap;
import java.util.Map;

public class RPGInventory {

    private Map<Integer, RPGItem> RPGInventory;

    public RPGInventory() {

    }

    public void createRPGInventory() {
        RPGInventory = new HashMap<Integer, RPGItem>();
    }

    public void addItem(int Slot, RPGItem Item) {
        RPGInventory.put(Slot, Item);
    }

    public void removeItem(int Slot) {
        RPGInventory.remove(Slot);
    }

    public RPGItem getItem(int Slot) {
        if (RPGInventory.containsKey(Slot)) {
            return RPGInventory.get(Slot);
        }
        return null;
    }
}
