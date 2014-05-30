package com.RPGMakerDev.RPGMaker.Commands;

import com.RPGMakerDev.RPGMaker.EntityData.RPGEntity;
import com.RPGMakerDev.RPGMaker.TeamSystem.Guild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	if (sender instanceof Player) {
	    Player p = (Player) sender;
	    if (args.length != 0) {
		switch (args[0]) {
		    case "create":
			if (args.length > 2) {
			    StringBuilder name = new StringBuilder("");

			    for (int i = 1; i < args.length; i++) {
				name.append(args[i]);
				name.append(" ");
			    }

			    Guild g = new Guild(RPGEntity.getRPGPlayer(p.getUniqueId()), name.toString().trim());

			} else {
			    p.sendMessage("Invalid arguments.");
			}
			break;

		    case "leave":
			break;

		    case "kick":
			break;

		    case "guildhall":
			break;

		    case "gh":
			break;

		    case "disband":
			break;

		    case "invite":
			break;

		    case "save":
			RPGEntity player = RPGEntity.getRPGPlayer(p.getUniqueId());
			player.getGuild().save();
			break;
		}
	    } else {
		RPGEntity player = RPGEntity.getRPGPlayer(p.getUniqueId());
		if (player.getGuild() == null) {
		    player.sendMessage("You are not in a guild! Create one by using \"/guild create <name>\"");
		}
	    }
	} else {
	    sender.sendMessage("This command can only be ran by a player");
	}
	return true;
    }

}
