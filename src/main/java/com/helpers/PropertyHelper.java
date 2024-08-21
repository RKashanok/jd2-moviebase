package com.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.helpers.ConstantsHelper.APP;

public class PropertyHelper {
    private final Properties properties;

    public PropertyHelper() {
        properties = new Properties();
        try (InputStream inputStream = PropertyHelper.class.getResourceAsStream(String.format("/%s", APP))) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file");
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
