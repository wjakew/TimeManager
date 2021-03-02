/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package generator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import timemanager.TimeManager_Container;
import timemanager.TimeManager_DayPair;

/**
 *Object for generating raport
 * @author jakubwawak
 */
public class Raport_Generator {
    
    TimeManager_Container tmc;
    LocalDateTime date;
    int owner;
    long calculated_minutes;
    long converted_hours;
    
    // Constructor
    public Raport_Generator(TimeManager_Container data_container,int owner){
        tmc = data_container;
        date = LocalDateTime.now( ZoneId.of( "Europe/Warsaw" ) );
        this.owner = owner;
        calculated_minutes = 0;
        calculate_time();
        convert_hours();
    }
    
    /**
     * Function for calculating minutes
     */
    void calculate_time(){
        for(TimeManager_DayPair tdp : tmc.time_objects){
            calculated_minutes = calculated_minutes + tdp.duration;
        }
    }
    
    /**
     * Function for converting hours
     */
    void convert_hours(){
        converted_hours = calculated_minutes / 60;
        long minutes_left = calculated_minutes - ( converted_hours *60);
        
        if ( minutes_left > 30){
            converted_hours ++;
        }
    }
    
    
    /**
     * Function for generating raw raport data
     * @return String
     */
    public String generate_raw(){
        String raport = "";
     
        raport = "Raport Generated: "+date.toString()+"\n";
        raport = raport + "Owner: "+owner +"\n";
        raport = raport +"RAW DATA: \n";
        
        for(TimeManager_DayPair tdp : tmc.time_objects){
            raport = raport + tdp.prepare_glance() +"\n";
        }
        
        raport = raport + "-------------------------\n";
        raport = raport + "Time summary:\n";
        raport = raport + "Minutes: "+calculated_minutes+"\n";
        raport = raport + "Hours: "+converted_hours+"\n";
        raport = raport + "END.";
        return raport;
    }
    
    
    
            
    
    
}
