/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.ClassSystem;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class TalentSpecialization {

    public String name;
    public ChatColor nameColor;
    public ChatColor descColor;

    public List<Talent> talents = new ArrayList<>();

    public TalentSpecialization(String Specialization) {
        this.name = Specialization;
    }
}
