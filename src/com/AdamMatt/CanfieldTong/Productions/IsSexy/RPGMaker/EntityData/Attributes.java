/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker.EntityData;

public class Attributes {

    // Base Stats
    public Integer strength;
    public Integer agility;
    public Integer intelligence;
    public Integer stamina;

    // Defensive Stats
    public Integer armor;
    public Integer resilience;
    public double hitChance = 75.00;
    public double criticalChance = 5.00;
    public double spellCriticalChance = 5.00;
    public double dodgeChance = 10;
    public double resistChance = 10;
    public double parryChance = 5;

    public Attributes() {

    }
}
