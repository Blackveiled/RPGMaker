/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public class SpawnPoint {

    public static List<SpawnPoint> spawnpoints = new ArrayList<>();
    public Location spawnLocation;
    public int id;

    public SpawnPoint() {

    }

    public void spawnMob() {
    }
}
