/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.EntityData;

import com.RPGMakerDev.RPGMaker.Inventory.RPGInventory;
import com.RPGMakerDev.RPGMaker.Inventory.RPGItem;
import com.RPGMakerDev.RPGMaker.TeamSystem.Guild;
import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.sql.SQLException;
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
    public Database database;
    public State state = new State();
    private UUID uuid;
    public RPGInventory rpgInventory;
    public RPGEntityType type;
    public RPGEntityClass eclass;
    public Guild guild;
    public Attributes attributes = new Attributes();

    public int level = 1;
    public int experience = 0;
    public int gold = 0;
    public String storedClass;

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
        try {
            type = RPGEntityType.PLAYER;
            eclass = RPGEntityClass.Rogue;
            rpgInventory = new RPGInventory(uuid);
            this.uuid = uuid;
            database = new Database();
            database.getConnection();
            String query = "SELECT * FROM `players` WHERE `UUID`='" + uuid.toString() + "'";
            database.Query = database.connection.prepareStatement(query);
            database.Results = database.Query.executeQuery();
            int count = 0;
            while (database.Results.next()) {

                storedClass = database.Results.getString("CLASS");
                level = database.Results.getInt("LEVEL");
                experience = database.Results.getInt("EXP");
                gold = database.Results.getInt("GOLD");
                attributes.strength = database.Results.getInt("STR");
                attributes.agility = database.Results.getInt("AGI");
                attributes.intelligence = database.Results.getInt("INT");
                attributes.vitality = database.Results.getInt("VIT");
                attributes.resilience = database.Results.getInt("RES");
                count++;
            }
            if (count == 0) {
                this.importNewCharacter();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }

    }

    /**
     * Imports a new character to the database, only do this for players who are
     * joining for their first time to the server.
     */
    public void importNewCharacter() {
        try {
            this.database.getConnection();
            if (this.database.connection == null) {
                this.database.getConnection();
                return; // Failed to get connection. End here.
            }
            String importCharacter = "INSERT INTO `players` (`UUID`, `USERNAME`) VALUES('" + this.uuid + "', '" + Bukkit.getPlayer(uuid).getName() + "');";
            this.database.Query = this.database.connection.prepareStatement(importCharacter);
            int count = this.database.Query.executeUpdate(importCharacter);
            this.database.Query.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Saves this RPGEntity's data to the database server provided.
     */
    public void importToDatabase() {
        try {
            String importCharacter = "UPDATE `players` SET `LEVEL`=" + Integer.toString(level) + ","
                    + " `EXP`=" + Integer.toString(experience)
                    + ", `STR`=" + Integer.toString(attributes.strength)
                    + ", `AGI`=" + Integer.toString(attributes.agility)
                    + ", `INT`=" + Integer.toString(attributes.intelligence)
                    + ", `VIT`=" + Integer.toString(attributes.vitality)
                    + ", `RES`=" + Integer.toString(attributes.resilience)
                    + ", `GOLD`=" + Integer.toString(gold)
                    + ", `CLASS`='" + ChatColor.stripColor(storedClass) + "'"
                    + " WHERE `UUID`='" + this.uuid + "';";
            this.database.Query = this.database.connection.prepareStatement(importCharacter);
            this.database.Query.executeUpdate();

            // Importing Inventory
            String importInventory = null;
            String delete = "DELETE FROM `players_inventories` WHERE `UUID`='" + this.uuid + "';";
            this.database.Query.execute(delete);
            for (int i = 0; i < 30; i++) {
                if (this.rpgInventory.RPGInventory.containsKey(i)) {
                    RPGItem item = this.rpgInventory.RPGInventory.get(i);
                    importInventory = "INSERT INTO `players_inventories` (`UUID`, `ITEMSLOT`, `ITEMID`, `AMOUNT`, `DURA`) VALUES ('" + this.uuid + "', "
                            + i + ", " + item.id + ", " + item.amount + ", " + item.durability + ");";
                    this.database.Query.execute(importInventory);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
            bd.add("Class:");
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
    
    public void addMoney(int value) {
        gold += value;
    }
    public void subtractMoney(int value) {
        gold -= value;
    }
}
