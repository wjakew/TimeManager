/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package com.jakubwawak.whours;

import generator_gui.Generator_DatePreparer;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *Main runnable class
 * @author jakubwawak
 */
public class Whours {
 
    public static void main(String[] args) throws ParseException, IOException{
        Generator_DatePreparer gdp = new Generator_DatePreparer("Marzec",2021);
        gdp.show_data();
        
        Generator_DatePreparer gdp2 = new Generator_DatePreparer(3,"Marzec",2021);
        gdp2.show_data();
        
        Generator_DatePreparer gdp3 = new Generator_DatePreparer(LocalDateTime.now( ZoneId.of( "Europe/Warsaw" ) ));
        gdp3.show_data();
    }
}
