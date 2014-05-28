package com.RPGMakerDev.RPGMaker.SpellSystem;

import com.RPGMakerDev.RPGMaker.RPGMaker;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class DOTType extends SpellType {
    private double damage;
    private int ticks;
    private int duration;
    private int id;
    
    /*
     * Damage   - How much damage to apply
     * Tick     - How often to apply damage
     * Duration - How long this effect will last, how many iterations
     */
    public DOTType(double damage, double ticks, int duration) {
        this.damage = damage;
        this.ticks = (int)ticks;
        this.duration = duration;
    }
    
    /*
     * Sets up the scheduler to apply the damage to the target
     */
    public void applyDamage(Entity target, Entity caster) {
        Plugin plugin = RPGMaker.getPlugin(RPGMaker.class);
        final Damageable tar = (Damageable)target;
        
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        id = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (duration-- > 0) {
                    double newDamage = tar.getHealth() - damage;
                    double setDamage = newDamage < 0 ? 0 : newDamage;
                    if (tar.getHealth() == 0) {
                        Bukkit.getServer().getScheduler().cancelTask(id);
                    }
                    else {
                        tar.setHealth(setDamage);
                    }
                }
            }
        }, ticks, ticks);
    }
}