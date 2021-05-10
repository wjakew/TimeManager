/*
Jakub Wawak
kubawawak@gmail.com
all rights reserved
 */
package com.jakubwawak.whours;

import java.io.File;

/**
 *Object for crawling and searching for file in the current directory
 * @author jakubwawak
 */
public class FileCrawler {
    
    File current_object;     // field for storing object for current directory
    
    String currentDirectory;    // field for storing current working directory
    
    String crawl_result;        // field for storing current result
    
    /**
     * Main constructor
     * @param directory 
     */
    FileCrawler(String directory){
        
        if ( directory.equals("") ){
            currentDirectory = System.getProperty("user.dir");
        }
        else{
            currentDirectory = directory;
        }
        
        crawl_result = "";
    }
    
    /**
     * Function for running crawl through files 
     */
    void run(){
        System.out.println("Running FileCrawler run");
        current_object = new File(currentDirectory);
        
        File[] files = current_object.listFiles();
        System.out.println("Setting directory to: "+ current_object.getAbsolutePath());
        System.out.println("Found "+files.length+" in directory");
        for(File file : files){
            System.out.println("File: "+file.getAbsolutePath()+" checking...");
            if ( file.isFile() && file.getName().contains("czas_pracy")){
                System.out.println("----->File is passing check");
                crawl_result = file.getAbsolutePath();
                System.out.println("Crawl result: "+crawl_result);
                break;
            }
            else{
                System.out.println("---> File is not passing check");
            }
        }
        System.out.println("Crawl ended");
    }
    
}
