/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Social;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Post {

    private UUID poster;
    private String posterName;
    private String message;
    private List<Post> replies;

    public Post(UUID UUID, String Message) {
        poster = UUID;
        posterName = Bukkit.getPlayer(UUID).getName();
        message = ChatColor.translateAlternateColorCodes('&', Message);
    }

    public void addReply() {
        if (replies == null) {
            replies = new ArrayList<Post>();
        }
    }
}
