/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Social;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class Forum {

    public static List<Forum> forums = new ArrayList<Forum>();

    private Integer id; // The ID of the Forum
    private String name; // The display name of the Forum
    private List<Post> posts = new ArrayList<Post>(); // Posts contained inside the forum
    private Inventory gui;

    public Forum(String name) {
        gui = Bukkit.createInventory(null, 28, ChatColor.GOLD + "Viewing Forum: " + ChatColor.BLACK + name);
    }

    public void addPost() {
        if (posts == null) {
            posts = new ArrayList<Post>();
        }
    }
}
