
/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package com.jakubwawak.whours;

import generator.Raport_Generator;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import timemanager.*;
/**
 *Object for managing tests
 * @author jakubwawak
 */
public class TestObject {
    
    LocalDateTime test_object;
    
    
    
    //Constructor
    TestObject() throws ParseException, IOException, SQLException, ClassNotFoundException{
        
        TimeManager_FileConnector tfc = new TimeManager_FileConnector("czas_pracy.txt");
        
        TimeManager_Container tc =  new TimeManager_Container(tfc);
        
        Raport_Generator rg = new Raport_Generator(tc,0);
        
        System.out.println(rg.generate_raw());
        
    }
}
