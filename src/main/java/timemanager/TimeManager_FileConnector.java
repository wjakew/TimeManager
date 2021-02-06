/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package timemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *Object for loading data from object
 * @author jakubwawak
 */
public class TimeManager_FileConnector {
    
    /**
     * Objects read files with formatting:
     * DD-MM-YYYY[HH:MM/HH:MM]
     */
    
    public ArrayList<String> raw_file_lines;       //object for storing raw data files
    
    String file_path;
    File file;
    boolean exist_flag;
    
    /**
     * Main constructor
     * @param file_src 
     */
    public TimeManager_FileConnector(String file_src){
        file = new File(file_src);
        file_path = file_src;
        
        raw_file_lines = new ArrayList<>();
        
        exist_flag = file.exists();
    }
    
    
    /**
     * Function for reading file
     */
    public void read_file() throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(file_path));
        
        String line = br.readLine();
        while ( line != null ){
            raw_file_lines.add(line);
            line = br.readLine();
        }
        br.close();
    }
    
    
    /**
     * Function for showing raw data from collection
     */
    public void show_data(){
        System.out.println("Showing raw file data:");
        for(String line : raw_file_lines){
            System.out.println(line);
        }
    }
    
    /**
     * Function for parasing line from file
     * @param line
     * @return TimeManager_Object
     */
    public TimeManager_DayPair parse_line(String line){
        try{
            String date = line.split("\\[")[0];
            String time = line.split("\\[")[1];
            //yyyy-MM-dd HH:mm
            
            String[] date_parts = date.split("\\.");
            
            String DATE_STRING = date_parts[2]+"-"+date_parts[1]+"-"+date_parts[0];
            String [] time_parts = time.split("/");
            
            String time_enter = time_parts[0];
            String time_exit = time_parts[1];
            
            time_enter = time_validate(time_enter);
            time_exit = time_validate(time_exit);
            
            String enter_string = DATE_STRING + " " + time_enter;
            String exit_string = DATE_STRING + " " + time_exit;
            exit_string = exit_string.substring(0, exit_string.length()-1);
            return new TimeManager_DayPair(new TimeManager_Object(enter_string),new TimeManager_Object(exit_string));
        }
        catch(Exception e){
            System.out.println("Parse failed ("+e.toString()+")");
            return null;
        }
    }
    
    
    /**
     * Function for formatting time
     * @param time
     * @return String
     */
    String time_validate(String time){
        String[] elements = time.split(":");
        
        if ( elements[0].length() == 1){
            elements[0] = "0"+elements[0];
        }

        return elements[0]+":"+elements[1];
    }
    
}
