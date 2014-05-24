/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AdamMatt.CanfieldTong.Productions.IsSexy.RPGMaker.EntityData;

import java.util.ArrayList;
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

        WARRIOR,
        MAGE,
        ROGUE,
        HUNTER,
        PRIEST,
        SHAMAN,
        PALADIN,
        NECROMANCER,
        RANGER,
    }

    private UUID uuid;
    private RPGEntityType type;
    private Attributes attributes = new Attributes();

    /**
     * Creates an RPGEntity object for the server. This object contains all of
     * the player's gameplay stats aswell as methods to affect the player
     * ingame.
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
    }

    /**
     * Creates an RPGEntity object for the server. This object contains all of
     * an NPC's gameplay stats aswell as methods to affect the NPC ingame.
     *
     * Use this method to create an RPGEntity for an NPC.
     *
     * @param RPGEntityType internal parameter
     */
    public RPGEntity(RPGEntityType RPGEntityType) {
        type = RPGEntityType;
    }

    public ItemStack getCharacterBook() {
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
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}
