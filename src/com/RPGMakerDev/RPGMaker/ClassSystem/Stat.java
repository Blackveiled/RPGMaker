/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.ClassSystem;

public enum Stat {

    Strength(),
    Agility(),
    Intelligence(),
    Vitality(),
    Resilience();

    public Integer value;

    Stat() {
        ClassType classtype = ClassType.Mage;
    }

}
