/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package com.jakubwawak.whours;

import generator.Raport_Generator;
import generator_gui.generator_window;
import generator_gui.generator_window2;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import timemanager.TimeManager_Container;
import timemanager.TimeManager_DatabaseConnector;
import timemanager.TimeManager_FileConnector;
import timemanager.TimeManager_Object;

/**
 *Main runnable class
 * @author jakubwawak
 */
public class Whours {
 
    public static void main(String[] args) throws ParseException, IOException, SQLException, ClassNotFoundException{
        Database_Connector database = new Database_Connector();
        database.connect("localhost", "entrc_database", "entrc_admin", "password");
        new generator_window2(null,true,database);
    }
}
