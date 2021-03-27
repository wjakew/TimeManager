/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */

package com.jakubwawak.whours;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    
    /**
     * Function for setting log
     * @param log
     * @throws SQLException 
     */
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
    
    /**
     * Function for setting entrance to the database
     * @param worker_id
     * @param photo_src
     * @return Boolean
     * @throws SQLException 
     */
    public boolean set_entrance_event(int worker_id,LocalDateTime time) throws SQLException{
        log_ENTER(worker_id,"no photo src");
        LocalDateTime entrance_time = time;
        String query =  "INSERT INTO ENTRANCE\n" +
                        "(worker_id,log_id,entrance_time,entrance_finished)\n" +
                        "VALUE\n" +
                        "(?,?,?,?);";
        
        PreparedStatement ppst = con.prepareStatement(query);
        
        try{
            ppst.setInt(1,worker_id);
            ppst.setInt(2,get_lastid_logENTER(worker_id));
            ppst.setObject(3, entrance_time);
            ppst.setInt(4, 0);
            
            log("Query: "+ppst.toString());
            ppst.execute();
            return true;
        }catch(SQLException e){
            log("Failed to set entrance user(id:"+worker_id+") time: "+entrance_time.toString());
            return false;
        }
    }
        /**
     * Function for logging ENTER data
     * @param worker_id
     * @param photo_src
     * @return Integer
     * return codes:
     * 1 - successfully added log record
     * -1 - error on the database
     */
    public int log_ENTER(int worker_id,String photo_src) throws SQLException{
        LocalDateTime todayLocalDate = LocalDateTime.now( ZoneId.of( "Europe/Warsaw" ) );
        String query = "INSERT INTO USER_LOG\n" +
                       "(user_log_date,worker_id,user_log_action,user_log_desc,user_log_photo_src)\n" +
                       "VALUES\n" +
                       "(?,?,?,?,?);";
        
        PreparedStatement ppst = con.prepareStatement(query);
        
        try{
            ppst.setObject(1, todayLocalDate);
            ppst.setInt(2,worker_id);
            ppst.setString(3,"ENTER");
            ppst.setString(4,"User entered.");
            ppst.setString(5,photo_src);
            
            log("Trying to add:"+ppst.toString());
            ppst.execute();
            return 1;
        
        }catch(SQLException e){
            log("Failed to log LOGIN_FAILED ("+e.toString());
            return -1;
        }  
    }
    
    /**
     * Function for getting last ENTER log id
     * @param worker_id
     * @return Integer
     * @throws SQLException
     * return codes:
     * any - last id of USER_LOG table
     * 0 - ENTER log for given worker_id not found
     * -1 - database error
     */
    public int get_lastid_logENTER(int worker_id) throws SQLException{
        String query = "SELECT * from USER_LOG WHERE worker_id = ? AND user_log_action = 'ENTER' ORDER BY user_log_id DESC LIMIT 1;";
        PreparedStatement ppst = con.prepareStatement(query);
        try{
            ppst.setInt(1, worker_id);
            
            ResultSet rs = ppst.executeQuery();
            
            if( rs.next() ){
                return rs.getInt("user_log_id");
            }
            else{
                return 0;
            }
        }catch(SQLException e){
            log("Failed to get last id of USER_LOG table ("+e.toString());
            return -1;
        }
    }
    
    /**
     * Function for updating entrance event (by giving exit_id)
     * @param worker_id
     * @param exit_id
     * @return Integer
     * @throws SQLException
     * return codes:
     *  1 - successfully updated ENTRANCE
     * -1 - last entrance not found
     * -2 - database error
     */
    int update_entrance_event(int worker_id,int exit_id) throws SQLException{
        String query = "SELECT * from ENTRANCE WHERE worker_id = ? and entrance_finished = 0 ORDER BY entrance_id DESC LIMIT 1;";
        PreparedStatement ppst = con.prepareStatement(query);
        int entrance_id;
        
        try{
            
            ppst.setInt(1,worker_id);
            
            ResultSet rs = ppst.executeQuery();
            
            if(rs.next()){
                // found last user entrance
                entrance_id = rs.getInt("entrance_id");
                
                query = "UPDATE ENTRANCE SET entrance_finished = ? WHERE entrance_id = ? ;";
                ppst = con.prepareStatement(query);
                ppst.setInt(1,exit_id);
                ppst.setInt(2,entrance_id);
                
                ppst.execute();
                return 1;
                
            }
            else{
                log("Can't find uptade entrance event for user(id:"+worker_id+")");
                return -1;
            }

        }catch(SQLException e){
            log("Failed to update entrance event! ("+e.toString()+")");
            return -2;
        }
    }
    
    /**
     * Function for setting exit event
     * @param worker_id
     * @param photo_src
     * @return Integer
     * @throws SQLException
     * return codes:
     * 1 - succesfully added exit event
     * -1 - database fail
     */
    public int set_exit_event(int worker_id,LocalDateTime time) throws SQLException{
        log_EXIT(worker_id,"");
        LocalDateTime exit_time = time;
        
        String query = "INSERT INTO ENTRANCE_EXIT\n" +
                "(worker_id,user_log_id,entrance_exit_time)\n" +
                "VALUES\n" +
                "(?,?,?);";
        try{
            PreparedStatement ppst = con.prepareStatement(query);

            ppst.setInt(1,worker_id);
            ppst.setInt(2,get_lastid_logEXIT(worker_id));
            ppst.setObject(3,exit_time);
            
            ppst.execute();

            update_entrance_event(worker_id,get_lastid_EXIT(worker_id));    // making pair with entrance
            return 1;
        
        }catch(SQLException e){
            log("Failed to set exit event for user(id:"+worker_id+") ("+e.toString()+")");
            return -1;
        }
    }
    
        /**
     * Function for logging ENTER data
     * @param worker_id
     * @param photo_src
     * @return Integer
     * return codes:
     * 1 - successfully added log record
     * -1 - error on the database
     */
    public int log_EXIT(int worker_id,String photo_src) throws SQLException{
        LocalDateTime todayLocalDate = LocalDateTime.now( ZoneId.of( "Europe/Warsaw" ) );
        String query = "INSERT INTO USER_LOG\n" +
                       "(user_log_date,worker_id,user_log_action,user_log_desc,user_log_photo_src)\n" +
                       "VALUES\n" +
                       "(?,?,?,?,?);";
        
        PreparedStatement ppst = con.prepareStatement(query);
        
        try{
            ppst.setObject(1, todayLocalDate);
            ppst.setInt(2,worker_id);
            ppst.setString(3,"EXIT");
            ppst.setString(4,"User exit.");
            ppst.setString(5,photo_src);
            
            log("Trying to add:"+ppst.toString());
            ppst.execute();
            return 1;
        
        }catch(SQLException e){
            log("Failed to log LOGIN_FAILED ("+e.toString());
            return -1;
        }  
    }
    /**
     * Function for getting id of last entrance of the user
     * @param worker_id
     * @return Integer
     * @throws SQLException 
     * any - returned last entrance_id
     *  0 - last entrance not found
     * -1 - error on the database
     */
    public int get_lastid_EXIT(int worker_id) throws SQLException{
        String query = "SELECT * from ENTRANCE_EXIT WHERE worker_id = ? ORDER BY user_log_id DESC LIMIT 1;";
        PreparedStatement ppst = con.prepareStatement(query);
        try{
            ppst.setInt(1, worker_id);
            
            ResultSet rs = ppst.executeQuery();
            
            if( rs.next() ){
                return rs.getInt("entrance_exit_id");
            }
            else{
                return 0;
            }
        }catch(SQLException e){
            log("Failed to get last id of ENTRANCE_EXIT table ("+e.toString()+")");
            return -1;
        }
    }
    /**
     * Function for getting last ENTER log id
     * @param worker_id
     * @return Integer
     * @throws SQLException
     * return codes:
     * any - last id of USER_LOG table
     * 0 - ENTER log for given worker_id not found
     * -1 - database error
     */
    public int get_lastid_logEXIT(int worker_id) throws SQLException{
        String query = "SELECT * from USER_LOG WHERE worker_id = ? AND user_log_action = 'EXIT' ORDER BY user_log_id DESC LIMIT 1;";
        PreparedStatement ppst = con.prepareStatement(query);
        try{
            ppst.setInt(1, worker_id);
            
            ResultSet rs = ppst.executeQuery();
            
            if( rs.next() ){
                return rs.getInt("user_log_id");
            }
            else{
                return 0;
            }
        }catch(SQLException e){
            log("Failed to get last id of USER_LOG table ("+e.toString());
            return -1;
        }
    }
}