package com.RPGMakerDev.RPGMaker.SpellSystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.RPGMakerDev.RPGMaker.RPGMaker;

public class HOTType extends SpellType {
    private double heal;
    private int ticks;
    private int duration;
    private int id;
    
    /*
     * Heal     - How much healing to apply
     * Tick     - How often to apply healing, in ticks
     * Duration - How long this effect will last, how many iterations
     */
    public HOTType(double heal, double ticks, int duration) {
        this.heal = heal;
        this.ticks = (int)ticks;
        this.duration = duration;
    }
    
    /*
     * Sets up the scheduler to apply the healing to the target
     */
    public void applyHeal(Entity target, Entity caster) {
        Plugin plugin = RPGMaker.getPlugin(RPGMaker.class);
        final Damageable tar = (Damageable)target;
        
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        id = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (duration-- > 0) {
                    double newHeal = tar.getHealth() - heal;
                    double setHeal = newHeal < 0 ? 0 : newHeal;
                    if (tar.getHealth() == 0) {
                        Bukkit.getServer().getScheduler().cancelTask(id);
                    }
                    else {
                        tar.setHealth(setHeal);
                    }
                }
            }
        }, ticks, ticks);
    }
}
