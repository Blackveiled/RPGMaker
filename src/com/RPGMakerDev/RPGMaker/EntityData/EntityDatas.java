/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.EntityType;

public class EntityDatas {

    public static Map<Integer, EntityDatas> entityData = new HashMap<>();

    public Integer id;
    public String name;
    public Integer damage;
    public Integer moveSpeed;
    public String entityType;

    public EntityDatas() {

    }

    public EntityType getType() {
        return EntityType.valueOf(entityType);
    }
}
