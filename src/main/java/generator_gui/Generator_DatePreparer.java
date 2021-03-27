/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package generator_gui;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import timemanager.TimeManager_Object;

/**
 *Object for creating data for 
 * @author jakubwawak
 */
public class Generator_DatePreparer {
    
    TimeManager_Object tmo;
    
    int day;
    String month_name;
    int year;
    
    List<Integer> days;     // list of days in month
    String [] months;       // list of month
    List<Integer> years;    // list of years
    
    int[] index;        // for storing actual state of the comobobox
    
    /**
     * Constructor using simple 
     * @param month_name 
     */
    public Generator_DatePreparer(String month_name,int year){
        
        index = new int[] {0,0,0};
        years = new ArrayList<>();
        days = new ArrayList<>();
        
        day = 1;
        this.year = year;
        this.month_name = month_name;
        
        
        months = new String[]{"Styczeń","Luty","Marzec","Kwiecień","Maj",
        "Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"};
        
        index[1] = translate_month_to_number(month_name);
        
        for(int i = year - 10; i< year ;i++){
            years.add(i);
        }

        index[2] = 9;
        
        populate_days();
    }
    
    /**
     * Constructor for using in combobox update
     * @param day_number
     * @param month_name
     * @param year_number 
     */
    public Generator_DatePreparer(int day_number,String month_name,int year_number){
        index = new int[] {day_number-1,0,9};
        years = new ArrayList<>();
        days = new ArrayList<>();
        
        this.day = day_number;
        this.year = year_number;
        this.month_name = month_name;
        
        months = new String[]{"Styczeń","Luty","Marzec","Kwiecień","Maj",
        "Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"};
        
        index[1] = Arrays.asList(months).indexOf(month_name);
        
        for(int i = year-10; i<year ; i++){
            years.add(i);
        }
        
        populate_days();
    }
    
    /**
     * Constructor using LocalDateTime object
     * @param ldt 
     */
    public Generator_DatePreparer(LocalDateTime ldt){
        String to_parse = ldt.toString();
        // eq: 2021-02-02T18:02:28.714353
        String date_portion = to_parse.split("T")[0];
        
        String[] date_elements = date_portion.split("-");
        
        try{
            index = new int[] {Integer.parseInt(date_elements[2])-1,0,9};
            years = new ArrayList<>();
            days = new ArrayList<>();
            this.day = Integer.parseInt(date_elements[2]);
            this.year = Integer.parseInt(date_elements[0]);
            
            index[1] = Integer.parseInt(date_elements[1])-1;
            
            months = new String[]{"Styczeń","Luty","Marzec","Kwiecień","Maj",
            "Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"};
            
            this.month_name = months[index[1]];
            
            for(int i = year-9; i<=year ; i++){
                years.add(i);
            }
        
        populate_days();
            
            
        }catch(NumberFormatException e){
            System.out.println("Generator_DatePreparer failed ("+e.toString()+")");
        }
        
    }
    
    /**
     * Function for showing data of the object
     */
    public void show_data(){
        System.out.println("Generator_DatePreparer raw data:");
        System.out.println("day number: "+day);
        System.out.println("day index: "+ index[0]);
        System.out.println("month name: "+month_name);
        System.out.println("month index: "+index[1]);
        System.out.println("month number of days: "+get_number_ofdays(month_name));
        System.out.println("year: "+year);
        System.out.println("year index: "+index[2]);
        System.out.println("leap year?"+leap_year(year));
        System.out.println("--------------------------");
        System.out.println("days[]: "+days);
        System.out.println("months[]: "+Arrays.asList(months));
        System.out.println("years[]:"+years);
    }
    
    /**
     * Function for checking leap year
     * @param year
     * @return boolean
     */
    boolean leap_year(int year){
        
        if (year % 4 == 0){
            
            if (year % 100 == 0){
            
                if (year % 400 == 0){
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
        
    }
    
    /**
     * Function for getting number of days
     * @param month_name
     * @return Integer
     */
    int get_number_ofdays(String month_name){
        Month month = null;
        switch(month_name){
            case"Styczeń":
                month = Month.JANUARY;
                break;
            case"Luty":
                month = Month.FEBRUARY;
                break;
            case"Marzec":
                month = Month.MARCH;
                break;
            case"Kwiecień":
                month = Month.APRIL;
                break;
            case"Maj":
                month = Month.MAY;
                break;
            case"Czerwiec":
                month = Month.JUNE;
                break;
            case"Lipiec":
                month = Month.JULY;
                break;
            case"Sierpień":
                month = Month.AUGUST;
                break;
            case"Wrzesień":
                month = Month.SEPTEMBER;
                break;
            case"Październik":
                month = Month.OCTOBER;
                break;
            case"Listopad":
                month = Month.NOVEMBER;
                break;
            case"Grudzień":
                month = Month.DECEMBER;
                break;
            default:
                month = null;
                break;
        }
        
        if ( month != null){
            return month.length(leap_year(year));
        }
        return -1;
    }
    
    /**
     * Function for loading data to combobox
     * @param object_to_load
     * @return JComboBox
     */
    void load_comboboxdata(ArrayList<JComboBox> object_to_load){
        
        DefaultComboBoxModel dcm_day = new DefaultComboBoxModel();
        DefaultComboBoxModel dcm_month = new DefaultComboBoxModel();
        DefaultComboBoxModel dcm_year = new DefaultComboBoxModel();
        
        dcm_day.addAll(days);
        dcm_month.addAll(Arrays.asList(months));
        dcm_year.addAll(years);
        
        object_to_load.get(0).setModel(dcm_day);
        object_to_load.get(0).setSelectedIndex(index[0]);
        object_to_load.get(1).setModel(dcm_month);
        object_to_load.get(1).setSelectedIndex(index[1]);
        object_to_load.get(2).setModel(dcm_year);
        object_to_load.get(2).setSelectedIndex(index[2]);
    }
    
    /**
     * Function for translating month to number
     * @param month_name
     * @return Integer
     * -1 - no month in collection
     * any - number of month
     */
    int translate_month_to_number(String month_name){
        List<String> months_list = Arrays.asList(months);
        
        if ( months_list.contains(month_name) ){
            return months_list.indexOf(month_name);
        }
        return -1;
    }
    
    /**
     * Function for populating days in collection
     */
    void populate_days(){
        for(int i = 1; i <= get_number_ofdays(month_name); i++){
            days.add(i);
        }
    }
    
}
