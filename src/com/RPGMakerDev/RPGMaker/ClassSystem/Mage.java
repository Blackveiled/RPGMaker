/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.ClassSystem;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Mage extends HeroClass {

    public Mage() {
        super(ChatColor.LIGHT_PURPLE + "Mage");
        List<Talent> fireTalents = new ArrayList<>();
        String FireSpecialization = "Fire";
        Talent Burner = new Talent("Burner");
        Burner.setTalentDescription("Increases the duration of your damage over time effects by 1 second per point.");
        Burner.setTalentPointsMax(5);
        Burner.setTalentPointsSpent(0);
        Burner.setTalentPointsRequirement(0);
        Burner.setTalentPointIcon(Material.FLINT_AND_STEEL);
        Talent SevereBurns = new Talent("Severe Burns");
        SevereBurns.setTalentDescription("Increases the total damage dealt by your damage over time effects by 5% per point.");
        SevereBurns.setTalentPointsMax(5);
        SevereBurns.setTalentPointsSpent(0);
        SevereBurns.setTalentPointsRequirement(0);
        SevereBurns.setTalentPointIcon(Material.BLAZE_POWDER);
    }

}
