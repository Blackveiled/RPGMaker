/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData;

public class State {

    public static enum ActivityState {

        IDLE,
        MOVING,
        INCOMBAT,
        STUNNED,
        ASLEEP
    }

    public static enum StateType {

        POISONED,
    }
}
