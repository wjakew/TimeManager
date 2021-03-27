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
import timemanager.TimeManager_Container;
import timemanager.TimeManager_DatabaseConnector;
import timemanager.TimeManager_FileConnector;

/**
 *Main runnable class
 * @author jakubwawak
 */
public class Whours {
 
    public static void main(String[] args) throws ParseException, IOException, SQLException, ClassNotFoundException{
        //new generator_window();
        Database_Connector database = new Database_Connector();
        database.connect("localhost", "entrc_database", "entrc_admin", "password");
        
        TimeManager_DatabaseConnector tmdc = new TimeManager_DatabaseConnector(database,null,null,1);
        
        tmdc.prepare_raw_database_data();
    
    }
}
