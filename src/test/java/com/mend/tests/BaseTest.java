package com.mend.tests;

import com.mend.utils.TestProperties;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

/**
 * Base test class for API tests.
 * <p>
 * This class handles common test setup, API authentication, response validation, and logging.
 * It ensures that each test has access to the necessary configuration properties.
 * </p>
 */
public class BaseTest {
    protected static final String BASE_URL = TestProperties.getBaseUrl();
    protected static final String ACCESS_TOKEN = TestProperties.getAccessToken();
    protected static final String REPO_USERNAME = TestProperties.getRepoUserName();
    protected static final int RESPONSE_TIME_THRESHOLD = TestProperties.getResponseTimeThresholdInMilli();
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    /**
     * Sets up the test environment before any test execution.
     * Logs essential configurations and ensures that a valid GitHub access token is available.
     */
    @BeforeClass
    public void setup() {
        LOGGER.info("Initializing Base Test Setup...");

        // Logging the loaded properties
        LOGGER.info("Test Properties Loaded:");
        LOGGER.info("Base URL: {}", BASE_URL);
        LOGGER.info("Repository Username: {}", REPO_USERNAME);
        LOGGER.info("Response Time Threshold: {}ms", RESPONSE_TIME_THRESHOLD);

        if (ACCESS_TOKEN == null || ACCESS_TOKEN.isEmpty()) {
            LOGGER.error("GitHub Access Token is missing! Ensure it's provided in the properties.");
            throw new IllegalStateException("GitHub Access Token is missing!");
        }
    }

    /**
     * Validates that the API response time does not exceed the defined threshold.
     *
     * @param response The API response object.
     * @throws AssertionError if the response time exceeds the acceptable limit.
     */
    protected void validateResponseTime(Response response) {
        long timeTaken = response.getTime();
        LOGGER.info("Response time: {}ms", timeTaken);
        Assert.assertTrue(timeTaken < RESPONSE_TIME_THRESHOLD, "Test failed due to slow response time: %dms (Threshold: %dms)");
    }
}