package com.RPGMakerDev.RPGMaker.SpellSystem;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpellEffect {
    private PotionEffectType effect;
    private int duration;
    private int strength;
    private boolean self;
    
    /*
     * Effect   - a certain potion effect
     * Duration - How long the potion will last for in seconds
     * Strength - How potent the potion will be
     * Self     - Target self?
     */
    public SpellEffect(PotionEffectType effect, int duration, int strength, boolean self) {
        this.effect = effect;
        this.duration = duration;
        this.strength = strength;
        this.self = self;
    }
    
    /*
     * Effect - Potion effect but all together
     * Self   - Target self?
     */
    public SpellEffect(PotionEffect effect, boolean self) {
        this.effect = effect.getType();
        this.duration = effect.getDuration();
        this.strength = effect.getAmplifier();
        this.self = self;
    }
    
    /*
     * Effect - Enum name to do their respective effects
     */
    /*public SpellEffect(String effect) {
        EffectType type = EffectType.getName(effect);
        if (type != null) {
            //Do something here with enums
            //Not complete yet
            //*TODO* Complete for special effects
        }
        else {
            throw new IllegalArgumentException("Not an enum");
        }
    }*/
    
    /*
     * Returns the potion effect combined and ready for applying
     */
    public PotionEffect getEffect() {
        return new PotionEffect(effect, duration, strength);
    }
    
    /*
     * Returns to target self or not
     */
    public boolean getSelf() {
        return self;
    }
}
