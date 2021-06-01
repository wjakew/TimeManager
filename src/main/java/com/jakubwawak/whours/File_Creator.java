/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package com.jakubwawak.whours;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import timemanager.TimeManager_Object;

/**
 *Object for creating file
 * @author jakubwawak
 */
public class File_Creator {
    
    public String file_path = "czas_pracy.txt";
    LocalDateTime first_day;
    LocalDateTime last_day;
    
    ArrayList<TimeManager_Object> tmo_array;
    ArrayList<String> raw_lines;
    
    /**
     * Constructor
     * @param month_number 
     */
    public File_Creator() {
        first_day = LocalDateTime.now();
        last_day  = first_day.with(TemporalAdjusters.lastDayOfMonth());
        first_day = last_day.with(TemporalAdjusters.firstDayOfMonth());
        tmo_array = load_data();
        raw_lines = generate_lines();
        prepare_name();
    }
    
    /**
     * Function for preparing file name
     */
    void prepare_name(){
        TimeManager_Object obj = new TimeManager_Object(first_day);
        file_path = "czas_pracy_"+obj.get_month_name()+".txt";
    }
    
    /**
     * Preparing all days of the month
     * @return ArrayList
     */
    ArrayList<TimeManager_Object> load_data(){
        ArrayList<TimeManager_Object> raw_data = new ArrayList<>();
        LocalDateTime runner = first_day;
        
        while(runner.isBefore(last_day) || runner.equals(last_day)){
            raw_data.add(new TimeManager_Object(runner));
            runner = runner.plusDays(1);
        }
        return raw_data;
    }
    
    /**
     * Function for getting absoluth path
     * @return String
     */
    public String abs_path(){
        File f = new File(file_path);
        return f.getAbsolutePath();        
    }
    /**
     * Function for writting data to file
     * @throws IOException 
     */
    public void write_file() throws IOException{
        FileWriter writer = new FileWriter(file_path);
        for(String line : raw_lines){
            writer.write(line);
        }
        writer.close();
    }
    
    /**
     * Function for generating 
     * @return ArrayList
     */
    ArrayList<String> generate_lines(){
        ArrayList<String> lines = new ArrayList<>();
        
        for(TimeManager_Object obj : tmo_array){
            lines.add(obj.get_date_glance_raport()+"\n");
        }
        return lines;
    }
    
    
    /**
     * Function for showing
     */
    public void show_data() throws ParseException{
        System.out.println("File name: "+file_path);
        System.out.println("First day: "+first_day.toString());
        System.out.println("Last day: "+last_day.toString());
        
        System.out.println("Raw data:");
        for(TimeManager_Object obj : tmo_array ){
            obj.show_object_data();
        }
        System.out.println("Raw lines:");
        for(String line : raw_lines){
            System.out.println(line);
        }
    }
    
}
