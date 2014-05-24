/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Inventory;

public class Socket {

    private RPGItem itemSocketed;

    public Socket() {

    }

    public Socket(RPGItem Insert) {
        itemSocketed = Insert;
    }

    public boolean isSocketEmpty() {
        if (itemSocketed != null) {
            return false;
        }
        return true;
    }

    public RPGItem getSocketedItem() {
        return itemSocketed;
    }

}
