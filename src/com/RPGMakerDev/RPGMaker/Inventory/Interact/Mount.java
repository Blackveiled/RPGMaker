/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Inventory.Interact;

import com.RPGMakerDev.RPGMaker.EntityData.HorseMount;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_7_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Mount {

    public static Map<UUID, Mount> activeMounts = new HashMap<>();
    public UUID uuid;
    public UUID mountUUID;
    public long timeSpawned;

    public Mount(UUID uuid) {
        Date spawnedAt = new Date();
        timeSpawned = spawnedAt.getTime() + 600000;

        this.uuid = uuid;

    }

    public Player getOwner() {
        return Bukkit.getPlayer(uuid);
    }

    public Entity getMountEntity() throws NullPointerException {
        for (Entity e : getOwner().getWorld().getEntities()) {
            if (e.getUniqueId().equals(this.mountUUID)) {
                return e;
            }
        }
        return null;
    }

    public void spawnMount() {
        World world = ((CraftWorld) Bukkit.getPlayer(uuid).getWorld()).getHandle();
        Location loc = Bukkit.getPlayer(uuid).getLocation();

        HorseMount mount = new HorseMount(world); //Generally you design your custom entity to accept an nms world to call the superconstructor.
        mount.setPosition(loc.getX(), loc.getY(), loc.getZ());
        mount.setCustomName(ChatColor.GREEN + Bukkit.getPlayer(uuid).getName() + "'s Mount ");
        mount.setCustomNameVisible(true);
        world.addEntity(mount);
        this.mountUUID = mount.getBukkitEntity().getUniqueId();
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.GREEN + "Your mount has been summoned!");
        getMountEntity().setPassenger(getOwner());
        Horse hose = (Horse) getMountEntity();
        if (Bukkit.getPlayer(uuid).getName().equalsIgnoreCase("Blackveil")) {
            hose.setCustomName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "The Veiled Steed");
            hose.setStyle(Horse.Style.BLACK_DOTS);
            hose.setColor(Horse.Color.BLACK);
            hose.getInventory().setArmor(new ItemStack(Material.GOLD_BARDING, 1));
            hose.setOwner(getOwner());
        }
    }

    public void despawnOld() {
        for (Entity e : Bukkit.getPlayer(uuid).getWorld().getEntities()) {
            if (e.getUniqueId().equals(this.mountUUID)) {
                e.remove();
            }
        }
    }
}
