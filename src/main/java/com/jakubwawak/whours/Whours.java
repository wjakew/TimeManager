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
    
    static String version = "1.0.0B4";
    static int debug = 0;
 
    public static void main(String[] args) throws ParseException, IOException, SQLException, ClassNotFoundException{
        show_header();
        
        if ( debug == 1 ){
            System.out.println("Debug session is on..");
            File_Creator fc = new File_Creator();
            fc.show_data();
            
            System.out.println("Debug session ended");
        }
        else{
            FileCrawler crawl = new FileCrawler("");
        
            System.out.println(crawl.currentDirectory);

            crawl.run();

            if ( !crawl.crawl_result.equals("") ){
                new whours_window(version,crawl.crawl_result);
            }
            else{
                new whours_window(version,"");
            }
        }
        

    }
    
    /**
     * Function for showing header
     */
    static void show_header(){
        String header = "          _                          \n" +
                        "__      _| |__   ___  _   _ _ __ ___ \n" +
                        "\\ \\ /\\ / / '_ \\ / _ \\| | | | '__/ __|\n" +
                        " \\ V  V /| | | | (_) | |_| | |  \\__ \\\n" +
                        "  \\_/\\_/ |_| |_|\\___/ \\__,_|_|  |___/\n";
        header = header+"by JAKUB WAWAK   2021  version"+version+"\n";
        
        System.out.println(header);
    }
}
