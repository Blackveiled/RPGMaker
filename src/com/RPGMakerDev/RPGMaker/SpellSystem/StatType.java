package com.RPGMakerDev.RPGMaker.SpellSystem;

import org.bukkit.entity.Entity;

public class StatType extends SpellType {
    private String stat;
    private double amount;
    /*
     * Health, mana, strength, agility, intelligence, vitality, resilience,
     * critical chance, spell critical chance, dodge chance, resist chance,
     * hit chance, parry chance
     */
    
    /*
     * Stat   - String of which stat to effect
     * Amount - How much to effect stat by
     */
    public StatType(String stat, double amount) {
        this.stat = stat;
        this.amount = amount;
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
