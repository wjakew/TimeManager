/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package timemanager;

import com.jakubwawak.whours.Database_Connector;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *Object for storing large portions of date data
 * @author jakubwawak
 */
public class TimeManager_Container {
    
    /**
     * Represents way of object initialization
     * possible codes:
     * FileConnector - data comes from object (TimeManager_FileConnector <- file)
     * 
     */
    public String submitted_mode;
 
    TimeManager_FileConnector data_file;                   // raw object data
    
    ArrayList<String> raw_data;                            // raw data about dates 
    public ArrayList<TimeManager_DayPair> time_objects;    // prepared time objects
    public int collection_size;
    Database_Connector database_connector;
    int worker_id;
    /**
     * Constructor with FileConnector usage
     * @param file_object 
     */
    public TimeManager_Container(TimeManager_FileConnector file_object) throws IOException{
        data_file = file_object;
        submitted_mode = "File";
        worker_id = -1;
        data_file.read_file();
        raw_data = data_file.raw_file_lines;
        time_objects = new ArrayList<>();
        database_connector = null;
        for(String line : data_file.raw_file_lines){
            if ( line != null)
                time_objects.add(data_file.parse_line(line));
        }
        collection_size = time_objects.size();
    }
    
    /**
     * Constructor with TimeManager_DatabaseConnector
     * @param database_connector 
     */
    public TimeManager_Container(TimeManager_DatabaseConnector database_connector) throws SQLException{
        data_file = null;
        this.database_connector = database_connector.database;
        submitted_mode = "Database";
        worker_id = database_connector.worker_id;
        ResultSet raw_data = database_connector.prepare_raw_database_data();
        time_objects = new ArrayList<>();
        
        translate_database_data(raw_data);
        
    }
    /**
    Function for validating container
    */
    public void validate_container(){
        if ( submitted_mode.equals("File")){
            List<Integer> index_list = new ArrayList<>();
        
            for(TimeManager_DayPair tdp : time_objects){
                if (tdp == null){
                    index_list.add(time_objects.indexOf(tdp));
                }
            }

            for(Integer index : index_list){
                time_objects.remove(index);
            }
        }
        else if ( submitted_mode.equals("Database")){}
    }
    
    /**
     * Function for translating database data
     */
    void translate_database_data(ResultSet database_data) throws SQLException{
        /**
        in database_data all data from entrance table
        schema:
         * Columns:
            entrance_id int AI PK 
            worker_id int 
            log_id int 
            entrance_time timestamp 
            entrance_finished int
         */
        while ( database_data.next() ){
            TimeManager_Object enter_time = new TimeManager_Object(database_data.getObject("entrance_time", LocalDateTime.class));
            
            int entrance_finished = database_data.getInt("entrance_finished");
            
            String query = " SELECT * from entrance_exit where "
                    + "entrance_exit_id = ?";
            
            PreparedStatement ppst = database_connector.con.prepareStatement(query);
            
            /**
             Columns:
                entrance_exit_id int AI PK 
                worker_id int 
                user_log_id int 
                entrance_exit_time timestam
             */
            try{
                ppst.setInt(1,entrance_finished);
                
                ResultSet rs = ppst.executeQuery();
                
                if ( rs.next() ){
                    TimeManager_Object exit_time = 
                            new TimeManager_Object(rs.getObject("entrance_exit_time",LocalDateTime.class));
                    TimeManager_DayPair tdp = new TimeManager_DayPair(enter_time,exit_time);
                    if ( tdp.validate_data() ){
                        time_objects.add(tdp);
                    }
                }
            }catch(SQLException e){
                System.out.println("Error loading contaner "
                        + "data from database ("+e.toString()+")");
            }
        }
    }
        
    /**
     * Function for loading database 
     * @param database
     */
    public void load_to_database(Database_Connector database) throws SQLException{
        
        for(TimeManager_DayPair tdp : time_objects){
            
            database.set_entrance_event(1, tdp.date_of_start.raw_time_object);
            System.out.println("Loaded to database: "+tdp.date_of_start.raw_time_object.toString());
            
            database.set_exit_event(1, tdp.date_of_end.raw_time_object);
            System.out.println("Loaded to database: "+tdp.date_of_end.raw_time_object.toString());
        
        }
        
    }
    
    /**
     * Function for 
     * @param worker_id 
     */
    public void set_worker_id(int worker_id){
        this.worker_id = worker_id;
    }
    
    /**
     * Function for returning data of an object to the screen
     */
    public void show_container(){
        System.out.println("Amount of data elements: "+collection_size);
        for(TimeManager_DayPair tdp : time_objects){
            if ( tdp != null ){
                tdp.show_data();
            }
        }   
    }
}
