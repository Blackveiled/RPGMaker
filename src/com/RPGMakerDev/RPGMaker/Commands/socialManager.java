/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Commands;

import com.RPGMakerDev.RPGMaker.Social.SocialManager;
import com.RPGMakerDev.RPGMaker.Social.SocialPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class socialManager implements CommandExecutor {

    CommandChatMenu menu;

    public socialManager() {

        menu = new CommandChatMenu("SocialManager");
        menu.setCommandUsage("/socialmanager");
        List<String> description = new ArrayList<String>();
        description.add("The Social Manager handles all social interaction that runs on the server.  It is an adminstrative tool"
                + " aswell as a useful tool for players to take advantage of as they play.  If you are looking for more information on how"
                + " to use this command for administrative purposes, refer to the admin subcommand.");
        menu.setDescription(description);
        CommandChatMenu like = new CommandChatMenu("Like");
        like.setCommandUsage("/socialmanager like <uuid> <id>");
        like.setQuickDescription("Adds a Like to a user");
        CommandChatMenu dislike = new CommandChatMenu("Dislike");
        dislike.setCommandUsage("/socialmanager dislike <uuid> <id>");
        dislike.setQuickDescription("Adds a Dislike to a user");
        CommandChatMenu admin = new CommandChatMenu("SocialManager Admininstration");
        admin.setCommandUsage("/socialmanager admin");
        admin.setQuickDescription("Displays the administrative tools for the SocialManager.");
        menu.addSubCommand(0, like);
        menu.addSubCommand(1, dislike);
        menu.addSubCommand(2, admin);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length != 0) {
                switch (args[0]) {
                    case "like":
                        if (args.length > 2) {
                            if (SocialPlayer.socialPlayers.containsKey(UUID.fromString(args[1]))) {
                                try {
                                    try {
                                        int parsed = Integer.parseInt(args[2]);
                                        SocialManager.chatLog.get(parsed);
                                        SocialPlayer player = SocialPlayer.socialPlayers.get(UUID.fromString(args[1]));
                                        player.addLike();
                                        SocialPlayer.socialPlayers.put(player.getPlayer().getUniqueId(), player);
                                        player.getPlayer().sendMessage(ChatColor.GREEN + "Someone liked your message.");
                                        sender.sendMessage(ChatColor.GREEN + "You liked " + player.getPlayer().getName() + "'s message!");
                                    } catch (NumberFormatException exception) {
                                        sender.sendMessage(ChatColor.RED + "Numeric value required.");
                                        return true;
                                    }
                                } catch (IndexOutOfBoundsException exception) {
                                    sender.sendMessage(ChatColor.RED + "Invalid Message ID");
                                    return true;
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "Invalid UUID entered!");
                                return true;
                            }
                        } else {
                            p.sendMessage("Invalid arguments.");
                        }
                        break;

                    case "dislike":
                        break;

                    case "channel":
                        break;

                }
            } else {
                List<String> output = menu.getChatMenu();
                for (int i = 0; i < output.size(); i++) {
                    sender.sendMessage(output.get(i));
                }
            }
        } else {
            sender.sendMessage("This command can only be ran by a player");
        }
        return true;
    }
}
