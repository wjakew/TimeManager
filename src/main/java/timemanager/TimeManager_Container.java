/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package timemanager;

import com.jakubwawak.whours.Database_Connector;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 
    TimeManager_FileConnector data_file;            // raw object data
    
    ArrayList<String> raw_data;                     // raw data about dates 
    public ArrayList<TimeManager_DayPair> time_objects;    // prepared time objects
    public int collection_size;
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
        ResultSet raw_data = database_connector.prepare_raw_database_data();
    }
    /**
    Function for validating container
    */
    public void validate_container(){
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
    
    /**
     * Function for translating database data
     */
    void translate_database_data(ResultSet database_data) throws SQLException{
        while ( database_data.next() ){
            
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
