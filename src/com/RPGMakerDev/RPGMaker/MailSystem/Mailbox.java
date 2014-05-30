/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.MailSystem;

import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Mailbox {

    public static Map<UUID, List<Mail>> playerMailbox = new HashMap<>();
    public List<Mail> mail = new ArrayList<>();

    public Database database;
    public UUID UUID;
    public boolean newMail;

    /**
     * Creates the player's mailbox. Only create a mailbox object IF the
     * player's mailbox does not exist ingame, otherwise do not create multiple
     * mailboxes for the same player.
     *
     * @param UUID
     */
    public Mailbox(UUID UUID) {
        try {
            database = new Database();
            database.getConnection();
            this.UUID = UUID;

            // Retrieve a total count so that we can generate pages of mail for the player
            // to be able to iterate through.
            String countQuery = "SELECT COUNT(*) FROM `mail` WHERE `UUID`='?';";
            database.Query.setString(1, this.UUID.toString());
            Integer totalMailCount = 0;
            Integer pageSize = 10; // The amount of mail which can be saved in one page.
            database.Query = database.connection.prepareStatement(countQuery);
            database.Results = database.Query.executeQuery();
            while (database.Results.next()) {
                totalMailCount = database.Results.getInt("COUNT(*)");
            }
            if (totalMailCount > 0) {
                for (int i = 0; i <= totalMailCount;) {

                    String Query = "SELECT * FROM `mail` WHERE `UUID`='?' ORDER BY `ID` DESC LIMIT ?";
                    database.Query = database.connection.prepareStatement(Query);
                    database.Query.setString(1, this.UUID.toString());
                    database.Query.setInt(2, pageSize);
                    database.Results = database.Query.executeQuery();
                    while (database.Results.next()) {
                        if (mail == null) {
                            mail = new ArrayList<>();
                        }
                        Mail retrieved = new Mail(UUID,
                                database.Results.getString("SENDER"),
                                database.Results.getString("SUBJECT"),
                                database.Results.getString("CONTENTS"));

                        retrieved.date = database.Results.getLong("DATE");
                        retrieved.read = database.Results.getBoolean("READ");

                        // Add attachments soon
                        mail.add(retrieved);
                        if (database.Results.getBoolean("READ")) {
                            newMail = true;
                        }
                    }
                    i = i + pageSize;
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            Mailbox.playerMailbox.put(UUID, mail);
            if (newMail) {
                Bukkit.getPlayer(UUID).sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You have unread mail!  Check your mailbox to read your unread messages.");
            }
        }
    }
}
