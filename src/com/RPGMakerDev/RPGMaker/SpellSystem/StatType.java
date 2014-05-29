package com.RPGMakerDev.RPGMaker.SpellSystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.RPGMakerDev.RPGMaker.RPGMaker;

public class StatType extends SpellType {
    private String stat;
    private double amount;
    private int duration;
    /*
     * Health, mana, strength, agility, intelligence, vitality, resilience,
     * critical chance, spell critical chance, dodge chance, resist chance,
     * hit chance, parry chance
     */
    
    /*
     * Stat     - String of which stat to effect
     * Amount   - How much to effect stat by
     * Duration - How long this stat change will last, in ticks
     */
    public StatType(String stat, double amount, int duration) {
        this.stat = stat;
        this.amount = amount;
        this.duration = duration;
    }
    
    public void changeStat(Entity caster) {
        Player player = (Player)caster;
        Plugin plugin = RPGMaker.getPlugin(RPGMaker.class);
        //Change Stat here, stat + amou
        
        //Reset stat back after duration
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                //set stat back, stat - amount
            }
        }, duration);
    }
    
    @Override
    public void applySpell(Entity target, Entity caster) {
        if (stat.equalsIgnoreCase("health")) {
            
        }
        else if (stat.equalsIgnoreCase("mana")) {
            
        }
        else if (stat.equalsIgnoreCase("strength")) {
            
        }
        else if (stat.equalsIgnoreCase("agility")) {
            
        }
        else if (stat.equalsIgnoreCase("intelligence")) {
            
        }
        else if (stat.equalsIgnoreCase("vitality")) {
            
        }
        else if (stat.equalsIgnoreCase("resilience")) {
            
        }
        else if (stat.equalsIgnoreCase("critical chance")) {
            
        }
        else if (stat.equalsIgnoreCase("spell critical chance")) {
            
        }
        else if (stat.equalsIgnoreCase("dodge chance")) {
            
        }
        else if (stat.equalsIgnoreCase("resist chance")) {
            
        }
        else if (stat.equalsIgnoreCase("hit chance")) {
            
        }
        else if (stat.equalsIgnoreCase("parry chance")) {
            
        }
        return;
    }
}
