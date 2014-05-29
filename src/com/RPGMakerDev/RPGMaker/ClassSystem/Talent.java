/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.ClassSystem;

import org.bukkit.Material;

public class Talent {

    private String talentName;
    private String talentDescription;
    private Integer talentPointsRequirement;
    private Integer talentPointsSpent;
    private Integer talentPointsMax;
    private Material talentPointIcon;

    /**
     * Creates a Talent with the name given in the constructor of the class.
     *
     * @param TalentName
     */
    public Talent(String TalentName) {
        this.talentName = TalentName;
    }

    /**
     * @return the talentName
     */
    public String getTalentName() {
        return talentName;
    }

    public void setTalentName(String TalentName) {
        this.talentName = TalentName;
    }

    /**
     * @return the talentDescription
     */
    public String getTalentDescription() {
        return talentDescription;
    }

    /**
     * @param talentDescription the talentDescription to set
     */
    public void setTalentDescription(String talentDescription) {
        this.talentDescription = talentDescription;
    }

    /**
     * @return the talentPointsRequirement
     */
    public Integer getTalentPointsRequirement() {
        return talentPointsRequirement;
    }

    /**
     * @param talentPointsRequirement the talentPointsRequirement to set
     */
    public void setTalentPointsRequirement(Integer talentPointsRequirement) {
        this.talentPointsRequirement = talentPointsRequirement;
    }

    /**
     * @return the talentPointsSpent
     */
    public Integer getTalentPointsSpent() {
        return talentPointsSpent;
    }

    /**
     * @param talentPointsSpent the talentPointsSpent to set
     */
    public void setTalentPointsSpent(Integer talentPointsSpent) {
        this.talentPointsSpent = talentPointsSpent;
    }

    /**
     * @return the talentPointsMax
     */
    public Integer getTalentPointsMax() {
        return talentPointsMax;
    }

    /**
     * @param talentPointsMax the talentPointsMax to set
     */
    public void setTalentPointsMax(Integer talentPointsMax) {
        this.talentPointsMax = talentPointsMax;
    }

    /**
     * @return the talentPointIcon
     */
    public Material getTalentPointIcon() {
        return talentPointIcon;
    }

    /**
     * @param talentPointIcon the talentPointIcon to set
     */
    public void setTalentPointIcon(Material talentPointIcon) {
        this.talentPointIcon = talentPointIcon;
    }
}
