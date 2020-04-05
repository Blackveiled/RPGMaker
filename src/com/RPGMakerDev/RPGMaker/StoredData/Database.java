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
package com.RPGMakerDev.RPGMaker.StoredData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    public Connection connection;
    public PreparedStatement Query;
    public ResultSet Results;
    public String Hostname;
    public String Schema;
    public String Username;
    public String Password;
    public String Port;

    public Database() {
        this.Hostname = "xxxxxxxxx";
        this.Schema = "xxxxxxxx";
        this.Username = "root";
        this.Password = "xxxxx";
        this.Port = "3306";
    }

    public Connection getConnection() throws SQLException {
        this.connection = null;
        try {
            return this.connection = DriverManager.getConnection("jdbc:mysql://" + this.Hostname + ":" + this.Port + "/" + this.Schema + "?allowMultiQueries=true", this.Username, this.Password);
        } catch (SQLException Exception) {
            Exception.printStackTrace();
            return this.connection = null;

        }
    }

    public int getRowCount(String table, String col, String equals) throws SQLException {
        if (this.connection != null) {
            this.Query = null;
            String Countfor = "SELECT COUNT(*) FROM `" + table + "` WHERE `" + col + "`='" + equals + "';";
            try {
                this.Query = this.connection.prepareStatement(Countfor);
                this.Results = this.Query.executeQuery();
                while (this.Results.next()) {
                    return this.Results.getInt("COUNT(*)");
                }
            } catch (SQLException Exception) {
                Exception.printStackTrace();
            } finally {
                this.Query.close();
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getRowCountAnd(String table, String col, String equals, String andCol, String andEquals) throws SQLException {
        if (this.connection != null) {
            this.Query = null;
            String Countfor = "SELECT COUNT(*) FROM `" + table + "` WHERE `" + col + "`='" + equals + "' AND `" + andCol + "`='" + andEquals + "';";
            try {
                this.Query = this.connection.prepareStatement(Countfor);
                this.Results = this.Query.executeQuery();
                while (this.Results.next()) {
                    return this.Results.getInt("COUNT(*)");
                }
            } catch (SQLException Exception) {
                Exception.printStackTrace();
            } finally {
                this.Query.close();
            }
        } else {
            return -1;
        }
        return -1;
    }

    public void createUser(String UUID, String username) throws SQLException {
        if (this.connection != null) {
            this.Query = null;
            String insert = "INSERT INTO `players` (`UUID`, `USERNAME`) VALUES('" + UUID + "', '" + username + "');";
            this.Query = this.connection.prepareStatement(insert);
            int rows = this.Query.executeUpdate();
            this.Query.close();
        }

    }

}
