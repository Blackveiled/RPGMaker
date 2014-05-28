package com.RPGMakerDev.RPGMaker.SpellSystem;

public class HealType extends SpellType {
    private double heal;
    
    /*
     * Heal - How much to heal
     */
    public HealType(double heal) {
        this.heal = heal;
    }
    
    /*
     * Returns how much this spell is going to heal
     */
    public double getHeal() {
        return heal;
    }
}
