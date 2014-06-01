/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.Economy;

import com.RPGMakerDev.RPGMaker.EntityData.RPGEntity;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author matthew
 * 
 * Get the players information from RPGEntity, getRPGPlayer(uuid)
 * Will have his money
 * Add support functions there, main economy ones here
 */
public class Economy {
    private final int GOLD_RATE = 10000;
    private final int SILVER_RATE = 100;
    private final int COPPER_RATE = 1;
    private Player player;
    private UUID uuid;
    private int money;
    
    public Economy() {
        
    }
    public Economy(UUID player) {
        this.uuid = player;
        this.player = Bukkit.getPlayer(player);
    }
    public Economy(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }
    
    public int[] convertMoney(int value) {        //12345
        int gold = value / GOLD_RATE;                    //1.2345
        int remaining = value % GOLD_RATE;       //2345
        int silver = remaining / SILVER_RATE;       //23.45
        remaining = remaining % SILVER_RATE;  //45
        int copper = remaining;                                 //45
        
        return new int[] {gold, silver, copper};
    }
    
    public String getMoneyString(int value) {
        int[] money = convertMoney(value);
        return "Balance: " + money[0] + "g " + money[1] + "s " + money[2] + "c";
    }
    
    public void addMoney(int value) {
        RPGEntity.getRPGPlayer(this.uuid).addMoney(value);
    }
    
    public void subtractMoney(int value) {
        RPGEntity.getRPGPlayer(this.uuid).subtractMoney(value);
    }
    
    public RPGEntity getPlayer() {
        return RPGEntity.getRPGPlayer(this.uuid);
    }
}
