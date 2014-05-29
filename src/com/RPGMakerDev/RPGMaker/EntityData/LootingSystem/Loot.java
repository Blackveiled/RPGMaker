/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData.LootingSystem;

import com.RPGMakerDev.RPGMaker.Inventory.RPGItem;
import org.bukkit.inventory.ItemStack;

public class Loot {

    public Integer id; // The RPGItem ID Number which is being claimed
    public double chance;
    public RPGItem item;

    public Loot(RPGItem item, double DropChance) {
        this.id = item.id;
        this.chance = DropChance;
        this.item = item;
    }

    public boolean roll() {
        if ((Math.random() * 100) <= this.chance) {
            return true;
        }
        return false;
    }

    public ItemStack getItem() {
        return item.getItemStack();
    }
}
