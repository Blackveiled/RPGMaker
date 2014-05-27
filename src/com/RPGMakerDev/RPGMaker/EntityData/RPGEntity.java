/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData;

import com.RPGMakerDev.RPGMaker.Social.Guild;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class RPGEntity {

    public static enum RPGEntityType {

        PLAYER,
        NPC,
        MOB
    }

    public static enum RPGEntityClass {

        Warrior,
        Mage,
        Rogue,
        Hunter,
        Priest,
        Shaman,
        Paladin,
        Necromancer,
        Ranger,
    }

    public static HashMap<UUID, RPGEntity> players = new HashMap<>();
    private UUID uuid;
    private RPGEntityType type;
    private RPGEntityClass eclass;
    private Guild guild;
    private Attributes attributes = new Attributes();

    private int level = 1;

    /**
     * Creates an RPGEntity object for the server. This object contains all of
     * the player's game-play statistics as well as methods to affect the player
     * in-game.
     *
     * Use this method to create an RPGEntity for a player character.
     *
     * The player's UUID is required to retrieve data from the configured
     * database server. If the database does not contain that player's data, a
     * new database entry will be created for that player.
     *
     * @param uuid java.util.UUID
     */
    public RPGEntity(UUID uuid) {
        type = RPGEntityType.PLAYER;
        this.uuid = uuid;
    }

    /**
     * Creates an RPGEntity object for the server. This object contains all of
     * an NPC's game-play statistics as well as methods to affect the NPC
     * in-game.
     *
     * Use this method to create an RPGEntity for an NPC.
     *
     * @param RPGEntityType internal parameter
     */
    public RPGEntity(RPGEntityType RPGEntityType) {
        type = RPGEntityType;
    }

    /**
     * Gets the book belonging to the Player which shows the data
     *
     * @return the Book
     */
    public ItemStack getCharacterBook() {
        if (type == RPGEntityType.PLAYER) {
            ItemStack s = new ItemStack(Material.BOOK, 1);
            ItemMeta m = s.getItemMeta();
            BookMeta b = (BookMeta) m;
            List<String> bd = new ArrayList<String>();
            bd.add(getPlayer().getDisplayName() + "'s Character Information");
            bd.add("Level: 0 Class: Warrior");
            List<String> l = new ArrayList<String>();
            l.add(ChatColor.WHITE + "Use this item to view information about your character.");
            m.setDisplayName(ChatColor.YELLOW + "View Character Sheet");
            m.setLore(l);
            s.setItemMeta(m);
            return s;
        } else {
            throw new IllegalArgumentException("RPGEntity is not a player!");
        }
    }

    /**
     * Gets the player belonging to this RPGEntity
     *
     * @return the Player
     */
    public Player getPlayer() {
        if (type == RPGEntityType.PLAYER) {
            return Bukkit.getPlayer(uuid);
        } else {
            throw new IllegalArgumentException("RPGEntity is not a player!");
        }
    }

    public static RPGEntity getRPGPlayer(UUID uuid) {
        return players.get(uuid);
    }

    /**
     * Gets the type of the RPGEntity
     *
     * @return the RPGEntityType
     */
    public RPGEntityType getType() {
        return type;
    }

    /**
     * Gets the guild
     *
     * @return the guild
     */
    public Guild getGuild() {
        if (type == RPGEntityType.PLAYER) {
            return guild;
        } else {
            throw new IllegalArgumentException("RPGEntity is not a player!");
        }
    }

    /**
     * Sets the guild
     *
     * @param guild the guild
     */
    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    /**
     * Sends a message
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        getPlayer().sendMessage(message);
    }

}
