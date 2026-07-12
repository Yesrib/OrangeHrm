package org.selenium.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("org/selenium/configs/Config.properties");
            if (inputStream == null) {
                throw new RuntimeException("Config.properties not found in classpath");
            }
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Config.properties: " + e.getMessage(), e);
        }
    }

    /**
     * Get a property value from the configuration file
     * @param key the property key
     * @return the property value, or null if not found
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

