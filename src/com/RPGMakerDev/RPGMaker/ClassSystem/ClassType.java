/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.ClassSystem;

public enum ClassType {

    Mage("Mage", Mage.class);

    private String name;
    private Class<? extends HeroClass> heroClass;

    ClassType(String name, Class<? extends HeroClass> heroClass) {
        this.name = name;
        this.heroClass = heroClass;
    }
}
