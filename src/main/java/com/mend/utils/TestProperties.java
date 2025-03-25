package com.mend.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to manage test properties.
 */
public class TestProperties {
    private static final String DEFAULT_PROPERTIES_FILE = "src/test/resources/Properties/test.properties";
    private static final Properties properties = new Properties();

    static {
        loadProperties(System.getProperty("test.properties", DEFAULT_PROPERTIES_FILE));
    }

    private static void loadProperties(String filePath) {
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + filePath, e);
        }
    }

    public enum Key  {
        BASE_URL("base.url"),
        ACCESS_TOKEN("access.token"),
        REPO_USERNAME("repo.username"),
        RESPONSE_THRESHOLD("response.time.threshold");

        private final String key;

        Key(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static String getBaseUrl() {
        String property = System.getProperty(Key.BASE_URL.getKey());
        if (property == null) {
            property = properties.getProperty(Key.BASE_URL.getKey(), "https://api.github.com");
        }
        return property;
    }

    public static String getAccessToken() {
        String property = System.getProperty(Key.ACCESS_TOKEN.getKey());
        if (property == null) {
            property = properties.getProperty(Key.ACCESS_TOKEN.getKey());
        }
        return property;
    }

    public static String getRepoUserName() {
        String property = System.getProperty(Key.REPO_USERNAME.getKey());
        if (property == null) {
            property = properties.getProperty(Key.REPO_USERNAME.getKey(), "UserNameDefault");
        }
        return property;
    }

    public static int getResponseTimeThresholdInMilli() {
        String property = System.getProperty(Key.RESPONSE_THRESHOLD.getKey());
        if (property == null) {
            property = properties.getProperty(Key.RESPONSE_THRESHOLD.getKey(), "3000");
        }
        return Integer.parseInt(property);
    }
}
