/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Commands;

import com.RPGMakerDev.RPGMaker.RPGMaker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CommandChatMenu {

    private String commandName;
    private String quickDescription;
    private String commandUsage;
    private List<String> commandDescription;
    private Map<Integer, CommandChatMenu> subCommands;

    public CommandChatMenu(String CommandName) {
        this.commandName = CommandName;
    }

    /**
     * Gets the command's chat menu, adds spacing at the top and at the end.
     *
     * @return List<String>
     */
    public List<String> getChatMenu() {
        List<String> out = new ArrayList<String>();
        out.add("");
        out.add(ChatColor.DARK_GRAY + "▬▬▬▬▬ " + ChatColor.YELLOW + "" + ChatColor.BOLD + commandName + ChatColor.DARK_GRAY + " Command Information ▬▬▬▬▬");
        for (int i = 0; i < commandDescription.size(); i++) {
            out.add(ChatColor.GRAY + commandDescription.get(i));
        }
        if (subCommands != null) {
            out.add("");
            for (int i = 0; i < subCommands.size(); i++) {
                out.add(ChatColor.YELLOW + subCommands.get(i).commandUsage + ChatColor.GRAY + " - " + subCommands.get(i).quickDescription);
            }
        }
        out.add("");
        return out;
    }

    /**
     * Sets the way that the command has to be typed.
     *
     * @param Usage String
     */
    public void setCommandUsage(String Usage) {
        commandUsage = Usage;
        return;
    }

    /**
     * Sets the description of the command in the help menu.
     *
     * @param Description String
     */
    public void setQuickDescription(String Description) {
        quickDescription = Description;
        return;
    }

    /**
     * Sets the description of this command to the inputted parameter.
     *
     * @param Description String
     */
    public void setDescription(List<String> Description) {
        commandDescription = Description;
        return;
    }

    /**
     * Adds a subcommand to the command you are making. This subcommand will
     * appear on your command's help menu.
     *
     * @param id Integer
     * @param subCommand CommandChatMenu
     * com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker.Commands.CommandChatMenu
     */
    public void addSubCommand(Integer id, CommandChatMenu subCommand) {
        if (subCommands == null) {
            subCommands = new HashMap<Integer, CommandChatMenu>();

        }
        if (subCommands.containsKey(id)) {
            if (RPGMaker.debugMode()) {
                Bukkit.getLogger().log(Level.SEVERE, this.commandName + " already contains the subCommand " + commandName
                        + "!  Failed to add subCommand!");
                return;
            }
        }
        subCommands.put(id, subCommand);
        return;
    }
}
