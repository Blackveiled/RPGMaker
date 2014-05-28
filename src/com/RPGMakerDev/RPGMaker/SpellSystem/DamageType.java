/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.SpellSystem;

/**
 *
 * @author matthew
 */
public class DamageType extends SpellType {
    double damage;
    
    public DamageType(double damage) {
        this.damage = damage;
    }
    
    public double getDamage() {
        return damage;
    }
}
