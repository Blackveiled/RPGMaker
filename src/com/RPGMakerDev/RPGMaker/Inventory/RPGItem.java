/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RPGMakerDev.RPGMaker.Inventory;

import com.RPGMakerDev.RPGMaker.StoredData.Database;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RPGItem {

    private Database database = new Database();

    public static enum ItemFlag {

        SOULBOUND,
        QUESTITEM,
        UNIQUE,
    }

    public static enum ItemType {

        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        SWORD,
        PICKAXE,
        AXE,
        HOE,
        SHOVEL,
        BLOCK,
    }

    // Item Information
    public int entityid = 0;
    public int id = 0;
    public int itemid = 0;
    public String name = null;
    public String quality = "Common";
    public String itemtype = "Block";
    public String description = null;
    public String owner = null; // Only referenced if the field "soulbound" is referenced.
    public int slotid = 0;
    public double durability = 0;
    public double maxdurability = 0;
    public int amount = 0;

    // Item Flags
    public boolean indestructible = false;
    public boolean soulbound = false;
    public boolean questitem = false;

    // Item Attunements
    public String classrequirement = null;
    public int levelrequirement = 0;
    public boolean broken = false;

    // Item Statistics
    public int mindamage = 0;
    public int maxdamage = 0;
    public int armor = 0;
    public int ilvl = 1;
    public int str = 0;
    public int agi = 0;
    public int intell = 0;
    public int vit = 0;
    public int res = 0;

    // Item Modifications
    public List<Socket> Sockets;
    public int Enchantment = 0;

    // Economy Values
    public int goldsellvalue = 0;
    public int goldbuyvalue = 0;
    public int pvpsellvalue = 0;
    public int pvpbuyvalue = 0;
    public int donorsellvalue = 0;
    public int donorbuyvalue = 0;

    // MineCraft Entity Information
    public ItemStack item;
    public ItemMeta meta;
    public List<String> lore = new ArrayList<String>();

    public RPGItem(int id, int amount) {
        this.id = id;
        this.amount = amount;
        try {
            this.database.getConnection();
            String Query = "SELECT * FROM `items` WHERE `ID`=" + id + ";";
            this.database.Query = this.database.connection.prepareStatement(Query);
            this.database.Results = this.database.Query.executeQuery();
            while (this.database.Results.next()) {
                this.quality = this.database.Results.getString("QUALITY");
                this.name = this.database.Results.getString("ITEMNAME");
                this.maxdurability = this.database.Results.getInt("DURA");
                this.durability = this.database.Results.getInt("DURA");
                this.description = this.database.Results.getString("DESC");
                this.mindamage = this.database.Results.getInt("MINDAMAGE");
                this.maxdamage = this.database.Results.getInt("MAXDAMAGE");
                this.armor = this.database.Results.getInt("ARMOR");
                this.ilvl = this.database.Results.getInt("ITEMLEVEL");
                this.levelrequirement = this.database.Results.getInt("LEVELREQ");
                this.itemtype = this.database.Results.getString("ITEMTYPE");
                this.str = this.database.Results.getInt("STR");
                this.agi = this.database.Results.getInt("AGI");
                this.intell = this.database.Results.getInt("INT");
                this.vit = this.database.Results.getInt("VIT");
                this.res = this.database.Results.getInt("RES");
                this.itemid = this.database.Results.getInt("ITEMID");
            }
            this.item = new ItemStack(Material.getMaterial(this.itemid), amount);
            if (this.maxdurability == 0) {
                this.indestructible = true;
            }
            if (this.durability == 0) {
                this.broken = true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public RPGItem(int id, int slot, int dura, int amount) {
        this.id = id;
        this.durability = dura;
        this.slotid = slot;
        this.amount = amount;
        try {
            this.database.getConnection();
            String Query = "SELECT * FROM `items` WHERE `ID`=" + id + ";";
            this.database.Query = this.database.connection.prepareStatement(Query);
            this.database.Results = this.database.Query.executeQuery();
            while (this.database.Results.next()) {
                this.quality = this.database.Results.getString("QUALITY");
                this.name = this.database.Results.getString("ITEMNAME");
                this.maxdurability = this.database.Results.getInt("DURA");
                this.description = this.database.Results.getString("DESC");
                this.mindamage = this.database.Results.getInt("MINDAMAGE");
                this.maxdamage = this.database.Results.getInt("MAXDAMAGE");
                this.armor = this.database.Results.getInt("ARMOR");
                this.ilvl = this.database.Results.getInt("ITEMLEVEL");
                this.levelrequirement = this.database.Results.getInt("LEVELREQ");
                this.itemtype = this.database.Results.getString("ITEMTYPE");
                this.str = this.database.Results.getInt("STR");
                this.agi = this.database.Results.getInt("AGI");
                this.intell = this.database.Results.getInt("INT");
                this.vit = this.database.Results.getInt("VIT");
                this.res = this.database.Results.getInt("RES");
                this.itemid = this.database.Results.getInt("ITEMID");
            }
            this.item = new ItemStack(Material.getMaterial(this.itemid), amount);
            if (this.maxdurability == 0) {
                this.indestructible = true;
            }
            if (this.durability == 0) {
                this.broken = true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateItemLore() {
        if (this.quality.equalsIgnoreCase("Common")) {
            this.quality = ChatColor.RESET + this.quality;
            this.name = ChatColor.RESET + "" + ChatColor.BOLD + this.name;
        } else if (this.quality.equalsIgnoreCase("Uncommon")) {
            this.quality = ChatColor.GREEN + this.quality;
            this.name = ChatColor.GREEN + "" + ChatColor.BOLD + this.name;
        } else if (this.quality.equalsIgnoreCase("Rare")) {
            this.quality = ChatColor.BLUE + this.quality;
            this.name = ChatColor.BLUE + "" + ChatColor.BOLD + this.name;
        } else if (this.quality.equalsIgnoreCase("Epic")) {
            this.quality = ChatColor.LIGHT_PURPLE + this.quality;
            this.name = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + this.name;
        } else if (this.quality.equalsIgnoreCase("Legendary")) {
            this.quality = ChatColor.GOLD + this.quality;
            this.name = ChatColor.GOLD + "" + ChatColor.BOLD + this.name;
        }
        if (!this.indestructible) {
            if (this.broken) {
                this.quality = ChatColor.DARK_RED + "Broken";
                this.name = ChatColor.stripColor(this.name);
                this.name = ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + this.name;
            }
        }

        // Check if indestructible, if false then check durability level.
        this.lore.clear();
        this.lore.add(this.quality + " " + this.itemtype);

        if (this.soulbound) {
            this.lore.add(ChatColor.DARK_GRAY + "Soulbound");
        }
        this.lore.add(ChatColor.GRAY + "Item Level " + Integer.toString(this.ilvl));
        int totaldamage = this.mindamage + this.maxdamage;
        if (totaldamage > 0) {
            this.lore.add(ChatColor.RESET + "" + Integer.toString(this.mindamage) + "-" + Integer.toString(this.maxdamage) + " Damage");
        }
        if (this.armor > 0) {
            this.lore.add(ChatColor.RESET + "" + Integer.toString(this.armor) + " Armor");
        }

        int statsTotal = this.str + this.agi + this.intell + this.vit + this.res;
        if (statsTotal > 0) {
            this.lore.add("");
            this.lore.add(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "Item Attributes");
            if (this.str > 0) {
                this.lore.add(ChatColor.WHITE + Integer.toString(this.str) + " Strength");
            }
            if (this.agi > 0) {
                this.lore.add(ChatColor.WHITE + Integer.toString(this.agi) + " Agility");
            }
            if (this.intell > 0) {
                this.lore.add(ChatColor.WHITE + Integer.toString(this.intell) + " Intelligence");
            }
            if (this.vit > 0) {
                this.lore.add(ChatColor.WHITE + Integer.toString(this.vit) + " Vitality");
            }
            if (this.res > 0) {
                this.lore.add(ChatColor.WHITE + Integer.toString(this.res) + " Resilience");
            }
        }
        this.lore.add("");
        /*if (this.EmptySockets == 1) {
         if (this.Socket1 == null) {
         this.lore.add(ChatColor.DARK_GRAY + "Empty Socket");
         }
         }
         if (this.EmptySockets == 2) {
         if (this.Socket1 == null) {
         this.lore.add(ChatColor.DARK_GRAY + "Empty Socket");
         }
         }
         if (this.EmptySockets == 3) {
         if (this.Socket1 == null) {
         this.lore.add(ChatColor.DARK_GRAY + "Empty Socket");
         }
         }
         if (this.EmptySockets > 0) {
         this.lore.add("");
         }*/
        if (!this.indestructible) {
            if (this.durability == 0) {
                this.description = ChatColor.GRAY + "This item is damaged.  Take it to a blacksmith to be repaired";
            }
        }
        if (this.description != null) {
            // Add descriptions later
            // this.lore.add("");
        }
        if (!this.indestructible) {
            this.lore.add(ChatColor.GRAY + "" + this.durability + "/" + this.maxdurability + " Durability");
        }
        if (this.levelrequirement > 0) {
            this.lore.add(ChatColor.GRAY + "Requires Level: " + this.levelrequirement);
        }
        if (this.classrequirement != null) {
            this.lore.add(ChatColor.GRAY + "Requires Class :" + this.classrequirement);
        }
        this.lore.add(ChatColor.YELLOW + "Sell Value: " + ChatColor.GRAY + "" + this.goldsellvalue);
        this.lore.add(ChatColor.AQUA + "Item ID: " + this.id);
    }

    public void updateItemMeta() {
        this.meta = this.item.getItemMeta();
        this.meta.setLore(this.lore);
        this.meta.setDisplayName(this.name);
        this.item.setItemMeta(this.meta);
    }

    public ItemStack getItemStack() {
        this.meta = this.item.getItemMeta();
        this.meta.setLore(this.lore);
        this.meta.setDisplayName(this.name);
        this.item.setItemMeta(this.meta);
        return this.item;
    }

    public boolean equalsItemStack(ItemStack stack) {
        if (this.item.equals(stack)) {
            return true;
        }
        return false;
    }

    public int getStr() {
        return this.str;
    }

    public int getAgi() {
        return this.agi;
    }

    public int getIntell() {
        return this.intell;
    }

    public int getVit() {
        return this.vit;
    }

    public int getRes() {
        return this.res;
    }

    public int getGoldBuyValue() {
        return this.goldbuyvalue;
    }

    public int getGoldSellValue() {
        return this.goldsellvalue;
    }

    public int getDonorBuyValue() {
        return this.donorbuyvalue;
    }

    public int DurabilityHit() {
        this.durability = this.durability - 0.01;
        long duraout = Math.round(this.durability);
        return Integer.parseInt(Long.toString(duraout));
    }

}
