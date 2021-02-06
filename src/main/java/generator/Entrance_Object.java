/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package generator;

import com.jakubwawak.whours.Database_Connector;
import java.sql.ResultSet;

/**
 *Object for creating entrance from database data
 * @author jakubwawak
 */
public class Entrance_Object {
    
    Database_Connector database;        // database connector
    int worker_id;                      // id of worker
    
    ResultSet worker_query;             // raw database data;
    
    // Constructor
    Entrance_Object(int worker_id,Database_Connector database){
        this.database = database;
        this.worker_id = worker_id;
    }
    
    
    /**
     * Function for gathering data from database
     * @return ResultSet
     */
    ResultSet prepare_query(){
        
    }
    
}
