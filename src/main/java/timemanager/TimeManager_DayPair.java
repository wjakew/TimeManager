/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package timemanager;

/**
 *Object for creating range between dates
 * @author jakubwawak
 */
public class TimeManager_DayPair {
    TimeManager_Object date_of_start;
    TimeManager_Object date_of_end;
    
    public long duration;
    
    boolean validation_flag;
    /**
     * Main constructor
     * @param date_of_start
     * @param date_of_end 
     */
    public TimeManager_DayPair(TimeManager_Object date_of_start,TimeManager_Object date_of_end){
        
        this.date_of_start = date_of_start;
        this.date_of_end = date_of_end;
        validation_flag = validate_data();
        
        if (validation_flag){
            duration = count_minutes_difference();
        }
        else{
            duration = 0;
        }
    }
    
    /**
     * Function for validating given data
     * @return boolean
     */
    boolean validate_data(){
        return date_of_start.validate(date_of_end) == 1;
    }
    
    /**
     * Function for counting hour difference
     * @return Long
     */
    public long count_minutes_difference(){
        return date_of_start.minutes_difference(date_of_end);
    }
    
    /**
     * Function for preparing glances for raport making
     * @return String
     */
    public String prepare_glance(){
        return date_of_start.raw_time_object.toString() +" - " + date_of_end.raw_time_object.toString() + "["+duration+"]";
    }
    
    /**
     * Function for showing data
     */
    public void show_data(){
        System.out.println("Date of start: "+date_of_start.raw_time_object.toString());
        System.out.println("Date of end: "+date_of_end.raw_time_object.toString());
        System.out.println("Validation: "+validation_flag);
        if (validation_flag){
            System.out.println("Duration: "+duration);
        }
    }
}
