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
        return properties.getProperty(Key.BASE_URL.getKey(), "https://api.github.com");
    }

    public static String getAccessToken() {
        return properties.getProperty(Key.ACCESS_TOKEN.getKey());
    }

    public static String getRepoUserName() {
        return properties.getProperty(Key.REPO_USERNAME.getKey(), "GitHubUserNameDefault");
    }

    public static int getResponseTimeThresholdInMilli() {
        return Integer.parseInt(properties.getProperty(Key.RESPONSE_THRESHOLD.getKey(), "3000"));
    }
}
