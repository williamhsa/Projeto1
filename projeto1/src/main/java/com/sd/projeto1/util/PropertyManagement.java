package com.sd.projeto1.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author willi
 */
public class PropertyManagement {

    //private static final PropertyManagement instance = new PropertyManagement();

    Properties prop;

    public PropertyManagement() {
        prop = new Properties();
        load();
    }

   // public static PropertyManagement getInstance() {
   //     return instance;
   // }

    private void load() {
        InputStream input = null;

        try {
            input = new FileInputStream("/temp/projeto1/config.properties");
            prop.load(input);
        } catch (IOException ex) {
            // 
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    //
                }
            }
        }
    }

    public void persist() {
        OutputStream output = null;

        try {
            output = new FileOutputStream("/temp/projeto1/config.properties");
            prop.store(output, null);
        } catch (IOException io) {
            //
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ex) {
                    // 
                }
            }
        }
    }

    public void reload() {
        load();
    }

    public int getPort() {
        return Integer.parseInt(prop.getProperty("port", "13266"));
         // return Integer.parseInt(prop.getProperty("port", "13267"));
    }
    
    public void setPort(int port) {
        prop.setProperty("port", port + "");
    }

    public String getAddress() {
        return prop.getProperty("address");
    }
    
    public void setMailPassword(String address) {
        prop.setProperty("address",address);
    }

    public String getDataBaseName() {
        return prop.getProperty("dataBaseName", "bancoProjeto1.sql");
    }

}
