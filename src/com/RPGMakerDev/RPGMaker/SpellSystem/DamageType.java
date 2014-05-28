package com.RPGMakerDev.RPGMaker.SpellSystem;

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
}
