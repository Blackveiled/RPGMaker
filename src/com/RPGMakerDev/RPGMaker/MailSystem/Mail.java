/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.MailSystem;

import com.RPGMakerDev.RPGMaker.Inventory.RPGItem;
import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Mail {
    
    public Database database;
    
    public UUID UUID;
    public String sender;
    public String subject;
    public String contents;
    public RPGItem[] attachments;
    public boolean read;
    public long date;
    
    public Mail(UUID UUID, String Sender, String Subject, String Contents) {
        try {
            this.database = new Database();
            database.getConnection();
            
            this.UUID = UUID;
            this.sender = Sender;
            this.subject = Subject;
            this.contents = Contents;
            this.date = new Date().getTime();
            this.read = false;
            
            String query = "INSERT INTO `mail` (`UUID`, `SENDER`, `SUBJECT`, `CONTENTS`, `DATE`) "
                    + "VALUES ('?', '?', '?', '?', '?');";
            query = query.replace("'", "");
            database.Query = database.connection.prepareStatement(query);
            database.Query.setString(1, UUID.toString());
            database.Query.setString(2, sender);
            database.Query.setString(3, subject);
            database.Query.setString(4, contents);
            database.Query.setLong(5, date);
            database.Query.execute();
            Bukkit.getPlayer(UUID).sendMessage(ChatColor.YELLOW + "You have received mail!  Check your mailbox to retrieve it.");
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            
        }
    }
    
    public ItemStack getLetter() {
        ItemStack stack = new ItemStack(Material.BOOK, 1);
        ItemMeta meta = stack.getItemMeta();
        BookMeta bmeta = (BookMeta) meta;
        meta.setDisplayName(this.subject);
        bmeta.setAuthor(this.sender);
        bmeta.setTitle(this.subject);
        char[] reader = this.contents.toCharArray();
        bmeta.addPage(strings);
        int pageCharacterCount = 0;
        int lineCharacterCount = 0;
        for (int i = 0; i < reader.length; i++) {
            if (pageCharacterCount == 256) {
                pageCharacterCount = 0;
                bmeta.setPage(i, java.util.Arrays.copyOfRange(reader, i, i));
            }
            java.util.Arrays.toString(reader);
        }
        return null;
    }
    
}
