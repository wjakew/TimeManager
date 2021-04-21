/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package com.jakubwawak.whours;

import generator_gui.whours_window;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *Main runnable class
 * @author jakubwawak
 */
public class Whours {
    
    static String version = "1.0.0B1";
 
    public static void main(String[] args) throws ParseException, IOException, SQLException, ClassNotFoundException{
        new whours_window(version);
    }
}
