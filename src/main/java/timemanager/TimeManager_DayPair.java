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
    
    public String raw_data;
    public long duration;
    
    public boolean validation_flag;
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
    
    public TimeManager_DayPair(String line){
        try{
            if ( line.isEmpty()){
                date_of_start = null;
                date_of_end = null;
                validation_flag = false;
            }
            else{
                raw_data = line;
                String date = line.split("\\[")[0];
                String time = line.split("\\[")[1].substring(0,line.split("\\[")[1].length()-1);
                //yyyy-MM-dd HH:mm

                String[] date_parts = date.split("\\.");

                String DATE_STRING = date_parts[2]+"-"+date_parts[1]+"-"+date_parts[0];
                String [] time_parts = time.split("-");

                String time_enter = time_parts[0];
                String time_exit = time_parts[1];

                time_enter = time_validate(time_enter);
                time_exit = time_validate(time_exit);

                String enter_string = DATE_STRING + " " + time_enter;
                String exit_string = DATE_STRING + " " + time_exit;
                exit_string = exit_string.substring(0, exit_string.length());

                date_of_start = new TimeManager_Object(enter_string);
                date_of_end = new TimeManager_Object(exit_string);
                validation_flag = validate_data();

                if (validation_flag){
                    duration = count_minutes_difference();
                }
                else{
                    duration = 0;
                }
            }

            
        }
        catch(Exception e){
            System.out.println("Parse failed ("+e.toString()+")");
            System.exit(0);

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
