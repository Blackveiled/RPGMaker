/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.ClassSystem;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class HeroClass {

    private String className;
    public Map<String, TalentSpecialization> specializations;
    private String selectedSpecialization;

    public HeroClass(String ClassName) {
        this.className = ClassName;
        specializations = new HashMap<>();
    }

    public Inventory getTalentsGUI(Player player) {
        Inventory output = Bukkit.createInventory(player, InventoryType.CHEST, className + " Specializations");
        if (!(specializations.values().isEmpty())) {
            for (int i = 0; i < specializations.size(); i++) {
                specializations.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return className;
    }

}
