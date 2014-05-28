/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.SpellSystem;

import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 *
 * @author matthew
 */
public class Spell {
    private SpellEffect[] effects;
    private SpellType[] types;
    private LivingEntity target;
    private LivingEntity caster;
    private double range;
    
    /*
     * Effects - list of effects to apply
     * Types   - list of spells to apply
     * Range   - How far the spell can be applied
     * Target  - Target to apply spells too
     * Caster  - Player who casts the spells
     * 
     * Not using square-root due to how long it takes to calculate
     * Not rooting will give the same comparison but different distance
     */
    public Spell(SpellEffect[] effects, SpellType[] types, double range, Entity target, Entity caster) {
        this.effects = effects;
        this.types = types;
        this.range = range;
        this.target = (LivingEntity)target;
        this.caster = (LivingEntity)caster;
    }
    
    /*
     * Returns true if ran correctly else false
     */
    public boolean castSpell() {
        if (range*range >= getDistance(target, caster)) {
            //Target is in range
            for (int i = 0; i < effects.length; i++) {
                if (effects[i].getSelf())
                    caster.addPotionEffect(effects[i].getEffect());
                else
                    target.addPotionEffect(effects[i].getEffect());
            }
            for (int i = 0; i < types.length; i++) {
                castType(types[i]);
            }
            return true;
        }
        else {
            //Not in range
            if (caster instanceof Player) {
                ((Player)caster).sendMessage("Not in range");
            }
        }
        
        return false;
    }
    
    private void castType(SpellType spell) {
        Damageable tar = (Damageable)target; 
        if (spell instanceof DamageType) {
            double damage = tar.getHealth() - ((DamageType)spell).getDamage();
            double setDamage = damage < 0 ? 0 : damage;
            tar.setHealth(setDamage);
        }
        
        return;
    }
    
    /*
     * Distance between two things
     */
    private double getDistance(LivingEntity target, LivingEntity caster) {
        Location tLoc = target.getLocation();
        Location cLoc = caster.getLocation();
        double x = tLoc.getX() - cLoc.getX();
        double y = tLoc.getY() - cLoc.getY();
        double z = tLoc.getZ() - cLoc.getZ();
        
        double distance = x*x + y*y + z*z;
        return distance;
    }
}
