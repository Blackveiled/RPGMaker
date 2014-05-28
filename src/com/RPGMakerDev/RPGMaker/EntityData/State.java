/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData;

import org.bukkit.Location;

public class State {

    public static enum ActivityState {

        IDLE,
        MOVING,
        INCOMBAT,
        STUNNED,
        ASLEEP,
        TELEPORTING
    }

    public static enum StateType {

        POISONED,
    }

    public ActivityState activityState = ActivityState.IDLE;
    public Location teleportDestination;
    public Integer teleportCooldown = 0;
    public String requesterName;
    public Integer requestDuration = 0;

}
