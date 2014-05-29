package com.RPGMakerDev.RPGMaker.SpellSystem;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.projectiles.ProjectileSource;

public class ProjSpreadType extends SpellType {
    private String projName;
    private Class<? extends Projectile> projClass;
    private float yaw;
    private double speed;
    private int amount;
    
    /*
     * ProjName - name of the projectile to cast
     * Yaw      - horizontal spread of projectiles
     * Speed    - speed of the velocity
     * Amount   - amount of projectiles to throw
     */
    public ProjSpreadType(String projName, float yaw, double speed, int amount) {
        this.projName = projName;
        this.yaw = yaw;
        this.speed = speed;
        this.amount = amount;
        
        if (projName.equalsIgnoreCase("arrow")) {
            projClass = Arrow.class;
        }
        else if (projName.equalsIgnoreCase("snowball")) {
            projClass = Snowball.class;
        }
        else if (projName.equalsIgnoreCase("fireball")) {
            projClass = SmallFireball.class;
        }
        else if (projName.equalsIgnoreCase("enderpearl")) {
            projClass = EnderPearl.class;
        }
        else if (projName.equalsIgnoreCase("potion")) {
            projClass = ThrownPotion.class;
        }
        else if (projName.equalsIgnoreCase("snowball")) {
            projClass = Egg.class;
        }
    }
    
    @Override
    public void applySpell(Entity target, Entity caster) {
        Player player = (Player)caster;
        for (int i = 1; i <= amount; i++) {
            Location loc = player.getEyeLocation();
            Location loc1 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw() + yaw * i, loc.getPitch());
            Location loc2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw() - yaw * i, loc.getPitch());
            Projectile proj1;
            Projectile proj2;
            if (projName.equals("fireball")) {
                proj1 = player.launchProjectile(projClass);
                proj2 = player.launchProjectile(projClass);
            }
            else {
                proj1 = player.getWorld().spawn(loc1, projClass);
                proj2 = player.getWorld().spawn(loc2, projClass);
            }
            proj1.setShooter((ProjectileSource)player);     
            proj2.setShooter((ProjectileSource)player);
            proj1.setVelocity(loc1.getDirection().multiply(speed));
            proj2.setVelocity(loc2.getDirection().multiply(speed));
        }
    }
}
