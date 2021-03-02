/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */

package com.jakubwawak.whours;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database_Connector {
    
    // version of database 
    final String version = "vC.0.6";
    // header for logging data
    // connection object for maintaing connection to the database
    public Connection con;
    
    // variable for debug purposes
    final int debug = 1;
    
    
    boolean connected;                      // flag for checking connection to the database
    String ip;                              // ip data for the connector
    String database_name;                   // name of the database
    String database_user,database_password; // user data for cred
    ArrayList<String> database_log;
    
    /**
     * Constructor
     */
    Database_Connector() throws SQLException, ClassNotFoundException{
        con = null;
        connected = false;
        ip = "";
        database_name = "";
        database_user = "";
        database_password = "";
        database_log = new ArrayList<>();
        //log("Started! Database Connector initzialazed");
    }
    /**
     * Function for connecting to the database
     * @param ip
     * @param database_name
     * @param user
     * @param password
     * @throws SQLException 
     */
    void connect(String ip,String database_name,String user,String password) throws SQLException{
        this.ip = ip;
        this.database_name = database_name;
        database_user = user;
        database_password = password;
        
        String login_data = "jdbc:mysql://"+this.ip+"/"+database_name+"?"
                + "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&" +
                                   "user="+database_user+"&password="+database_password;
        try{
            con = DriverManager.getConnection(login_data);
            connected = true;
            System.out.println("Connected succesfully");
        }catch(SQLException e){
            connected = false;
            System.out.println("Failed to connect to database ("+e.toString()+")");
        }
    }
    public void log(String log) throws SQLException{
        java.util.Date actual_date = new java.util.Date();
        database_log.add("("+actual_date.toString()+")"+" - "+log);
        // load log to database
        if ( debug == 1){
            String query = "INSERT INTO PROGRAM_LOG (program_log_desc) VALUES (?); ";
            System.out.println("ENTRC LOG: "+database_log.get(database_log.size()-1));
        if ( con == null){
            System.out.println("Bład bazy: con=null ("+log+")");
        }
        else{
            PreparedStatement ppst = con.prepareStatement(query);
            
            try{
                
                ppst.setString(1,log);
                
                ppst.execute();
                
            }catch(SQLException e){}
            }

            // after 100 records dump to file
            if(database_log.size() > 100){
                database_log.clear();
            }   
        }   
    }
}