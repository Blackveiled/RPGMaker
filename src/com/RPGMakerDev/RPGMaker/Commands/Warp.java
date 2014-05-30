/*
 * This plugin is licensed and owned by (myself) Blackveil also as known as Blackveiled
 * It is free to use, however no support nor database schemas are available
 * from (myself) Blackveil, you are responsible for maintaining your server's database
 * and keeping it up to date as new releases are given, and just to warn you again
 * I do NOT offer support for this plugin, you must modify everything yourself to
 * use it for your own experiments or Minecraft servers.
 */
/**
 *
 * @author Blackveiled
 */

/*
 * REALM OF CONQUEST SERVER FRAMEWORK
 * CRAFTBUKKIT PLUGIN
 * 
 * www.blackveiled.com
 *
 */
package com.RPGMakerDev.RPGMaker.Commands;

import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.sql.SQLException;
import net.minecraft.server.v1_7_R3.ChatClickable;
import net.minecraft.server.v1_7_R3.ChatHoverable;
import net.minecraft.server.v1_7_R3.ChatMessage;
import net.minecraft.server.v1_7_R3.ChatModifier;
import net.minecraft.server.v1_7_R3.EnumClickAction;
import net.minecraft.server.v1_7_R3.EnumHoverAction;
import net.minecraft.server.v1_7_R3.IChatBaseComponent;
import net.minecraft.server.v1_7_R3.MinecraftServer;
import net.minecraft.server.v1_7_R3.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Warp implements CommandExecutor {

    protected Plugin plugin;
    protected Database Warps = new Database();
    public String Message = null;
    public World world = null;
    public double X = 0;
    public double Y = 0;
    public double Z = 0;
    public float YAW = 0;
    public float PITCH = 0;
    public Location Loc = null;
    public int rows = 0;

    public Warp(Plugin plugin) {
        this.plugin = plugin;
        try {
            this.Warps.getConnection();
        } catch (SQLException Exception) {
            Exception.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof Player) {
            Player p = (Player) s;
            String playerName = p.getName();
            ///////////////////////////////////////////////////
            //////
            //////  USAGE:              /warp <action> <category> <name>
            //////  PURPOSE:            To implement a database powered Warp system.`
            //////  PERMISSION-NODE:    roc.warp, roc.setwarp, roc.delwarp
            //////
            //////////////////////////////////////////////////
            if (cmd.getName().equalsIgnoreCase("warp")) {
                if (args.length > 0) {
                    /////////////////////////////////////////////////////////
                    //////  SUBCOMMAND:         LIST                   //////
                    /////////////////////////////////////////////////////////
                    if (args[0].equalsIgnoreCase("list")) {
                        if (this.Warps.connection != null) {
                            if (args.length == 1) {
                                String Query = "SELECT * FROM `warpcategories`";
                                this.Warps.Query = null;
                                try {
                                    this.Warps.Query = this.Warps.connection.prepareStatement(Query);
                                    this.Warps.Results = this.Warps.Query.executeQuery();
                                    this.Message = ChatColor.GRAY + "Categories available: " + ChatColor.GOLD;
                                    rows = 0;
                                    while (this.Warps.Results.next()) {
                                        rows++;
                                        this.Message = this.Message + "" + this.Warps.Results.getString("CATEGORY") + ", ";
                                    }
                                    if (rows == 0) {
                                        this.Message = this.Message + "" + ChatColor.RED + "Query returned no results!";
                                    }
                                } catch (SQLException Exception) {
                                    Exception.printStackTrace();
                                } finally {
                                    p.sendMessage("");
                                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Realm of Conquest Beta" + ChatColor.RESET + ChatColor.YELLOW + " Warps");
                                    p.sendMessage("");
                                    p.sendMessage(this.Message);
                                    p.sendMessage(ChatColor.GRAY + "To view warps available of a certain category say: " + ChatColor.GOLD + "/warp list <category>");
                                    Warp.printHoverable(p.getName(), ChatColor.YELLOW + "" + ChatColor.BOLD + "[ ! ]  " + ChatColor.AQUA + "" + ChatColor.BOLD + "Hover Over" + ChatColor.RESET + ChatColor.GRAY + " to view Quick Command" + ChatColor.YELLOW + "" + ChatColor.BOLD + "  [ ! ]", "/warp list", ChatColor.GRAY + "To get started, say " + ChatColor.YELLOW + "/warp list" + ChatColor.GRAY + " to view a list of categories to navigate.");
                                    this.Message = null;
                                }
                            } /////////////////////////////////////////////////////////
                            //////  SUBCOMMAND:         CATEGORY               //////
                            /////////////////////////////////////////////////////////                                    
                            else if (args.length > 1) {
                                this.Warps.Query = null;
                                String Query = "SELECT * FROM `warps` WHERE `CATEGORY`='" + args[1] + "';";
                                try {
                                    this.Warps.Query = this.Warps.connection.prepareStatement(Query);
                                    this.Warps.Results = this.Warps.Query.executeQuery();
                                    this.Message = ChatColor.GRAY + "Warps available: " + ChatColor.GOLD;
                                    while (this.Warps.Results.next()) {
                                        this.Message = this.Message + this.Warps.Results.getString("NAME") + ", ";
                                    }
                                } catch (SQLException Exception) {

                                } finally {
                                    p.sendMessage("");
                                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Realm of Conquest Beta" + ChatColor.RESET + ChatColor.YELLOW + " Warps");
                                    p.sendMessage("");
                                    p.sendMessage(this.Message);
                                    p.sendMessage(ChatColor.GRAY + "To teleport to a warp of this category, say: " + ChatColor.GOLD + "/warp tp " + args[1] + " <warp name>");
                                    Warp.printHoverable(p.getName(), ChatColor.YELLOW + "" + ChatColor.BOLD + "[ ! ]  " + ChatColor.AQUA + "" + ChatColor.BOLD + "Hover Over" + ChatColor.RESET + ChatColor.GRAY + " to view Quick Command" + ChatColor.YELLOW + "" + ChatColor.BOLD + "  [ ! ]", "/warp list", ChatColor.GRAY + "To get started, say " + ChatColor.YELLOW + "/warp list" + ChatColor.GRAY + " to view a list of categories to navigate.");
                                    this.Message = null;
                                }
                            }
                        } else {
                            return false;
                        }
                    } else /////////////////////////////////////////////////////////
                    //////  SUBCOMMAND:         TP                     //////
                    /////////////////////////////////////////////////////////
                    if (args[0].equalsIgnoreCase("tp")) {
                        if (args.length > 0) {
                            if (this.Warps.connection != null) {
                                if (args.length == 3) {
                                    String Query = "SELECT * FROM `warps` WHERE `CATEGORY`='" + args[1] + "' AND `NAME`='" + args[2] + "' LIMIT 0, 1;";
                                    this.Warps.Query = null;
                                    try {
                                        this.Loc = null;
                                        this.world = null;
                                        this.X = 0;
                                        this.Y = 0;
                                        this.Z = 0;
                                        this.YAW = 0;
                                        this.PITCH = 0;
                                        this.Warps.Query = this.Warps.connection.prepareStatement(Query);
                                        this.Warps.Results = this.Warps.Query.executeQuery();
                                        this.rows = 0;
                                        while (this.Warps.Results.next()) {
                                            this.rows++;
                                            this.world = Bukkit.getWorld(this.Warps.Results.getString("WORLD"));
                                            this.X = this.Warps.Results.getDouble("X");
                                            this.Y = this.Warps.Results.getDouble("Y");
                                            this.Z = this.Warps.Results.getDouble("Z");
                                            this.YAW = this.Warps.Results.getFloat("YAW");
                                            this.PITCH = this.Warps.Results.getFloat("PITCH");
                                            this.Loc = new Location(this.world, this.X, this.Y, this.Z);
                                            this.Loc.setPitch(PITCH);
                                            this.Loc.setYaw(this.YAW);
                                            p.teleport(this.Loc);
                                            p.sendMessage(ChatColor.YELLOW + "You are teleporting to " + args[2] + "!");
                                            return true;
                                        }
                                        if (this.rows == 0) {
                                            p.sendMessage(ChatColor.RED + "That category or warp does not exist!");
                                        }

                                    } catch (SQLException Exception) {
                                        Exception.printStackTrace();
                                        p.sendMessage(ChatColor.RED + "That category or warp does not exist!");
                                    } finally {

                                        this.Message = null;
                                    }
                                    return true;
                                } else if (args.length == 2 || args.length == 1) {
                                    p.sendMessage(ChatColor.RED + "That category or warp does not exist!");
                                }
                            }
                        }
                    } else //////////////////////////////////////////////////////////
                    //////  SUBCOMMAND:         set                     //////
                    //////////////////////////////////////////////////////////
                    if (args[0].equalsIgnoreCase("set")) {
                        if (args.length > 0) {
                            if (this.Warps.connection != null) {
                                if (args.length > 1) {
                                    this.Loc = p.getLocation();
                                    this.world = this.Loc.getWorld();
                                    String worldname = this.world.getName();
                                    this.X = this.Loc.getX();
                                    this.Y = this.Loc.getY();
                                    this.Z = this.Loc.getZ();
                                    this.YAW = this.Loc.getYaw();
                                    this.PITCH = this.Loc.getPitch();
                                    String Query = "INSERT INTO `warps` (`WORLD`, `CATEGORY`, `NAME`, `X`, `Y`, `Z`, `PITCH`, `YAW`) VALUES('" + worldname + "', '" + args[1] + "', '" + args[2] + "', " + this.X + ", " + this.Y + ", " + this.Z + ", " + this.PITCH + ", " + this.YAW + ");";
                                    this.Warps.Query = null;
                                    try {
                                        this.Warps.Query = this.Warps.connection.prepareStatement(Query);
                                        this.Warps.Query.executeUpdate();
                                        this.Warps.Query.close();
                                        s.sendMessage(ChatColor.GREEN + "Your warp has been created!");
                                    } catch (SQLException Exception) {
                                        Exception.printStackTrace();
                                        p.sendMessage(ChatColor.RED + "Failed to create Warp!");
                                    }
                                    return true;
                                } else if (args.length == 2 || args.length == 1) {
                                    p.sendMessage(ChatColor.RED + "Failed to create Warp!");
                                }
                            }
                        } else {

                            this.Message = ChatColor.GRAY + "Categories available: " + ChatColor.RED + "Query returned no results!";
                            p.sendMessage("");
                            p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Realm of Conquest Beta" + ChatColor.RESET + ChatColor.YELLOW + " Warps");
                            p.sendMessage("");
                            p.sendMessage(this.Message);
                            p.sendMessage(ChatColor.GRAY + "To view warps available of a certain category say: " + ChatColor.GOLD + "/warp list <category>");
                            Warp.printHoverable(p.getName(), ChatColor.YELLOW + "" + ChatColor.BOLD + "[ ! ]  " + ChatColor.AQUA + "" + ChatColor.BOLD + "Hover Over" + ChatColor.RESET + ChatColor.GRAY + " to view Quick Command" + ChatColor.YELLOW + "" + ChatColor.BOLD + "  [ ! ]", "/warp list", ChatColor.GRAY + "To get started, say " + ChatColor.YELLOW + "/warp list" + ChatColor.GRAY + " to view a list of categories to navigate.");
                            this.Message = null;
                            return true;
                        }
                    } else {

                        p.sendMessage("");
                        this.Message = ChatColor.GRAY + "Categories available: " + ChatColor.RED + "Query returned no results!";
                        p.sendMessage(ChatColor.YELLOW + "=============== " + ChatColor.RED + "" + ChatColor.BOLD + "[ROC] " + ChatColor.RESET + ChatColor.UNDERLINE + "Warps System" + ChatColor.YELLOW + " =============== ");
                        p.sendMessage("");
                        p.sendMessage(this.Message);
                        p.sendMessage(ChatColor.GRAY + "To view warps available of a certain category say: " + ChatColor.GOLD + "/warp list <category>");
                        Warp.printHoverable(p.getName(), ChatColor.YELLOW + "" + ChatColor.BOLD + "[ ! ]  " + ChatColor.AQUA + "" + ChatColor.BOLD + "Hover Over" + ChatColor.RESET + ChatColor.GRAY + " to view Quick Command" + ChatColor.YELLOW + "" + ChatColor.BOLD + "  [ ! ]", "/warp list", ChatColor.GRAY + "To get started, say " + ChatColor.YELLOW + "/warp list" + ChatColor.GRAY + " to view a list of categories to navigate.");
                        this.Message = null;
                        return true;
                    }
                } else {
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Realm of Conquest Beta" + ChatColor.RESET + ChatColor.YELLOW + " Warps");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GRAY + "Command Usage: " + ChatColor.GOLD + "/warp" + ChatColor.GRAY + " <action> <category> <name>");
                    p.sendMessage(ChatColor.GRAY + "Actions: " + ChatColor.GOLD + "set, del, tp, list");
                    Warp.printHoverable(p.getName(), ChatColor.YELLOW + "" + ChatColor.BOLD + "[ ! ]  " + ChatColor.AQUA + "" + ChatColor.BOLD + "Hover Over" + ChatColor.RESET + ChatColor.GRAY + " to view Quick Command" + ChatColor.YELLOW + "" + ChatColor.BOLD + "  [ ! ]", "/warp list", ChatColor.GRAY + "To get started, say " + ChatColor.YELLOW + "/warp list" + ChatColor.GRAY + " to view a list of categories to navigate.");
                    return true;
                }
            } else {
                p.sendMessage("");
                p.sendMessage(ChatColor.YELLOW + "=============== " + ChatColor.RED + "" + ChatColor.BOLD + "[ROC] " + ChatColor.RESET + ChatColor.UNDERLINE + "Warps System" + ChatColor.YELLOW + " =============== ");
                p.sendMessage("");
                p.sendMessage(ChatColor.GRAY + "Command Usage: " + ChatColor.GOLD + "/warp" + ChatColor.GRAY + " <action> <category> <name>");
                p.sendMessage(ChatColor.GRAY + "Actions: " + ChatColor.GOLD + "set, del, tp, list");
                Warp.printHoverable(p.getName(), ChatColor.YELLOW + "" + ChatColor.BOLD + "[ ! ]  " + ChatColor.AQUA + "" + ChatColor.BOLD + "Hover Over" + ChatColor.RESET + ChatColor.GRAY + " to view Quick Command" + ChatColor.YELLOW + "" + ChatColor.BOLD + "  [ ! ]", "/warp list", ChatColor.GRAY + "To get started, say " + ChatColor.YELLOW + "/warp list" + ChatColor.GRAY + " to view a list of categories to navigate.");
                return true;
            }
        }
        return true;
    }

    public static void printHoverable(String pname, String ChatMsg, String ClickableMsg, String HoverMsg) {
        IChatBaseComponent base;
        base = new ChatMessage(ChatMsg);
        base.setChatModifier(new ChatModifier());
        base.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.SUGGEST_COMMAND, ClickableMsg));
        base.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_TEXT, new ChatMessage(HoverMsg)));
        PlayerList list = MinecraftServer.getServer().getPlayerList();
        list.getPlayer(pname).sendMessage(base);
    }
}
