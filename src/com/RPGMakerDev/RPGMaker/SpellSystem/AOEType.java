package com.RPGMakerDev.RPGMaker.SpellSystem;

import java.util.List;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;

public class AOEType extends SpellType {
    private double damage;
    private int radius;
    
    /*
     * Damage - How much damage to apply to targets
     * Radius - Radius of the aoe attack, will form a square
     */
    public AOEType(double damage, int radius) {
        this.damage = damage;
        this.radius = radius;
    }
    
    @Override
    public void applySpell(Entity target, Entity caster) {
        List<Entity> list = caster.getNearbyEntities(radius, radius, radius);
        for (int i = 0; i < list.size(); i++) {
            Damageable tar = (Damageable)list.get(i);
            double newDamage = tar.getHealth() - damage;
            double setDamage = newDamage < 0 ? 0 : newDamage;
            tar.setHealth(setDamage);
        }
        return;
    }
}
