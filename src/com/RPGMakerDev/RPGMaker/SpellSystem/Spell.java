package com.RPGMakerDev.RPGMaker.SpellSystem;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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
     * 
     * *TODO* Need spell regeants
     */
    public Spell(SpellEffect[] effects, SpellType[] types, double range, Entity target, Entity caster) {
        this.effects = effects;
        this.types = types;
        this.range = range;
        this.target = (LivingEntity)target;
        this.caster = (LivingEntity)caster;
    }
    
    public Spell(SpellEffect[] effects, SpellType[] types, Entity caster) {
        this.effects = effects;
        this.types = types;
        this.caster = (LivingEntity)caster;
        this.target = (LivingEntity)caster;
        this.range = -1;
    }
    
    /*
     * Returns true if ran correctly else false
     */
    public boolean castSpell() {
        if (range == -1 || ((range*range) >= getDistance(target, caster))) {
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
    
    /*
     * Helper function for each SpellType
     */
    private void castType(SpellType spell) {
        spell.applySpell(target, caster);
        
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
