/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package com.jakubwawak.whours;

import generator.Raport_Generator;
import generator_gui.generator_window;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import timemanager.TimeManager_Container;
import timemanager.TimeManager_DatabaseConnector;
import timemanager.TimeManager_FileConnector;

/**
 *Main runnable class
 * @author jakubwawak
 */
public class Whours {
 
    public static void main(String[] args) throws ParseException, IOException, SQLException, ClassNotFoundException{
        new generator_window();

    }
}
