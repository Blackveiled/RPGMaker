package com.RPGMakerDev.RPGMaker.SpellSystem;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;

public class DamageType extends SpellType {
    double damage;
    
    /*
     * Damage - How much to damage
     */
    public DamageType(double damage) {
        this.damage = damage;
    }
    
    /*
     * Returns how much damage this spell will do
     */
    public double getDamage() {
        return damage;
    }
    
    @Override
    public void applySpell(Entity target, Entity caster) {
        Damageable tar = (Damageable)target; 
        double newDamage = tar.getHealth() - damage;
        double setDamage = newDamage < 0 ? 0 : newDamage;
        tar.setHealth(setDamage);
    }
}
