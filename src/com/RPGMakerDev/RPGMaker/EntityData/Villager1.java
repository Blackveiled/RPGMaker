/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData;

import java.lang.reflect.Field;
import net.minecraft.server.v1_7_R3.EntityVillager;
import net.minecraft.server.v1_7_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R3.World;
import org.bukkit.craftbukkit.v1_7_R3.util.UnsafeList;

public class Villager1 extends EntityVillager {

    public Villager1(World world) {
        super(world);

        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
        } catch (Exception exc) {
            exc.printStackTrace();
// This means that the name of one of the fields changed names or declaration and will have to be re-examined.
        }

    }

}
