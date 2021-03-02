/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package timemanager;

import java.io.IOException;
import java.util.ArrayList;

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
             time_objects.add(data_file.parse_line(line));
        }
        collection_size = time_objects.size();
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
            tdp.show_data();
        }   
    }
}
