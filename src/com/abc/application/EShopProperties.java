package com.abc.application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class EShopProperties {

    private static Properties prop;
    static {
        try (InputStream input = new FileInputStream("resources/config.properties")) {

            prop = new Properties();
            // load a properties file
            prop.load(input);
        }
        catch (Exception e) {

        }
    }

    public static String getProperty(String input) {
        return prop.getProperty(input);
    }
}
