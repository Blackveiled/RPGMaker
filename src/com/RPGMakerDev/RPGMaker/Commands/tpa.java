/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Commands;

import com.RPGMakerDev.RPGMaker.EntityData.RPGEntity;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpa implements CommandExecutor {

    CommandChatMenu tpa;

    public tpa() {
        tpa = new CommandChatMenu("TPA");
        tpa.setCommandUsage("/rpgmaker");
        List<String> description = new ArrayList<String>();
        description.add("Teleport to the name of the player that you wish to teleport to.  This teleportation command runs on a timer, and will"
                + " not provide instant-trans.  Please wait a moment after your request has been sent.");
        tpa.setDescription(description);
        CommandChatMenu social = new CommandChatMenu("TPA");
        social.setCommandUsage("/tpa <name>");
        social.setQuickDescription("Teleport to the requested player.");
        tpa.addSubCommand(0, social);
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof Player) {
            if (args.length > 0) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    if (RPGEntity.players.containsKey(Bukkit.getPlayer(args[0]).getUniqueId())) {
                        RPGEntity.players.get(Bukkit.getPlayer(args[0]).getUniqueId()).getPlayer().sendMessage(ChatColor.YELLOW + s.getName() + " is sending you a teleportation request.  To accept their request, say /tpaccept.");
                        RPGEntity.players.get(Bukkit.getPlayer(args[0]).getUniqueId()).state.requesterName = s.getName();
                        RPGEntity.players.get(Bukkit.getPlayer(args[0]).getUniqueId()).state.requestDuration = 7;
                    }
                }
            }
            List<String> output = tpa.getChatMenu();
            for (int i = 0; i < output.size(); i++) {
                s.sendMessage(output.get(i));
            }
            return true;
        }
        s.sendMessage("You must be logged in to use this command.");
        return true;
    }
}
