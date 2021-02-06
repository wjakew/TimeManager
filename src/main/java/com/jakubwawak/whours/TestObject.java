
/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package com.jakubwawak.whours;

import java.io.IOException;
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
    TestObject() throws ParseException, IOException{
        
        test_object = LocalDateTime.now();
        
        TimeManager_FileConnector tfc = new TimeManager_FileConnector("test.txt");
        
        TimeManager_Container tmc = new TimeManager_Container(tfc);
        
        tmc.show_container();
        
    }
}
