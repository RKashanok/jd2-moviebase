package com.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHelper {
    public static String getProperty(String propertyFileName, String key) {
        Properties properties = new Properties();

        try (InputStream inputStream =
                     PropertyHelper.class.getResourceAsStream(String.format("/%s", propertyFileName))) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Failed to load properties file. Error: " + e.getMessage());
        }

        return properties.getProperty(key);
    }
}
