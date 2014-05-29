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

public class ProjectileType extends SpellType {
    private String projName;
    private Class<? extends Projectile> projClass;
    private float yaw;
    private double speed;
    
    /*
     * ProjName - name of the projectile to cast
     * Yaw      - horizontal spread of projectiles
     * Speed    - speed of the velocity
     */
    public ProjectileType(String projName, float yaw, double speed) {
        this.projName = projName;
        this.yaw = yaw;
        this.speed = speed;
        
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
        //Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
        Location loc = player.getEyeLocation();
        Location loc1 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw() + yaw, loc.getPitch());
        Projectile proj;
        if (projName.equals("fireball")) {
            proj = player.launchProjectile(projClass);
        }
        else {
            proj = player.getWorld().spawn(loc1, projClass);
        }
        proj.setShooter((ProjectileSource)player);        
        proj.setVelocity(loc1.getDirection().multiply(speed));
    }
}