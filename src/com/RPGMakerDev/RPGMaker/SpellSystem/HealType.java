package com.RPGMakerDev.RPGMaker.SpellSystem;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;

public class HealType extends SpellType {
    private double heal;
    
    /*
     * Heal - How much to heal
     */
    public HealType(double heal) {
        this.heal = heal;
    }
    
    /*
     * Returns how much this spell is going to heal
     */
    public double getHeal() {
        return heal;
    }
    
    @Override
    public void applySpell(Entity target, Entity caster) {
        Damageable tar = (Damageable)target; 
        double newHeal = tar.getHealth() + heal;
        double setHeal = newHeal > tar.getMaxHealth() ? tar.getMaxHealth() : newHeal;
        tar.setHealth(setHeal);
    }
}
