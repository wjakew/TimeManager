/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package timemanager;

import com.jakubwawak.whours.Database_Connector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *Object for loading data from database
 * @author jakubwawak
 */
public class TimeManager_DatabaseConnector {
    
    Database_Connector database;
    LocalDateTime from,to;
    ResultSet raw_data;
    int worker_id;
    String query;
    /**
     * Constructor
     * @param database
     * @param mode
     * @param from
     * @param to
     * @param worker_id 
     */
    public TimeManager_DatabaseConnector(Database_Connector database,LocalDateTime from,LocalDateTime to,int worker_id) throws SQLException{
        this.database = database;
        this.from = from;
        this.to = to;
        this.worker_id = worker_id;
        query = "";
        raw_data = prepare_raw_database_data();
    }
    
    /**
     * Possible modes:
     * 1
     * worker_id != null,from=null,to=null
     * Object generates worker data, getting worker hours since the begining
     * 2
     * worker_id != null,from!=null,to=null
     * Object generates worker data since given date to now
     * 3
     * worker_id != null,from!=null,to!=null
     * Object generates worker data in given time
    */
    
    /**
     * Function for preparing raw data from database
     * @return ResultSet
     */
    public ResultSet prepare_raw_database_data() throws SQLException{
        int possibility = 0;
        // 1 possibility
        // worker_id != null,from=null,to=null
        if ( worker_id != -1 && from == null && to == null){
            query = "SELECT * FROM ENTRANCE WHERE worker_id = ?;";
            possibility = 1;
        }
        // 2 possibility
        // worker_id != null,from!=null,to=null
        if ( worker_id != -1 && from != null && to == null){
            query = "SELECT * FROM ENTRANCE WHERE worker_id = ? and entrance_time < ?;";
            possibility = 2;
        }
        // 3 possibility
        // worker_id != null,from!=null,to!=null
        else if ( worker_id != -1 && from != null && to != null){
            query = "SELECT * FROM ENTRANCE WHERE worker_id = ? and entrance_time < ? and entrance_time > ?;";
        }
        

        try{
            PreparedStatement ppst = database.con.prepareStatement(query);
            
            switch(possibility){
                case 1:
                    ppst.setInt(1,worker_id);
                    break;
                case 2:
                    ppst.setInt(1,worker_id);
                    ppst.setObject(2,to);
                    break;
                case 3:
                    ppst.setInt(1,worker_id);
                    ppst.setObject(2,to);
                    ppst.setObject(3,from);
                    break;
            }
            
            return ppst.executeQuery();
        }catch(SQLException e){
            database.log("Failed to get entrance data for user(id:"+worker_id+")");
            return null;
        }
    }
    
    /**
     * Function for showing raw data
     */
    public void show_raw_data() throws SQLException{
        int counter = 0;
        System.out.println("Reading raw data");
        while(raw_data.next()){
            System.out.println(raw_data.toString());
            counter++;
        }
        System.out.println("Amount of data: "+counter);
    }
}
