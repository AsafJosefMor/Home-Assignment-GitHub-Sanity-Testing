package com.mend.tests;

import com.mend.api.IApiClient;
import com.mend.utils.TestProperties;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.UUID;

/**
 * Base test class for API tests.
 * This class handles common test setup, API authentication, response validation, and logging.
 * It ensures that each test has access to the necessary configuration properties.
 */
public abstract class BaseTest {
    protected static final String BASE_URL = TestProperties.getBaseUrl();
    protected static final String REPO_USERNAME = TestProperties.getRepoUserName();
    protected static final int RESPONSE_TIME_THRESHOLD = TestProperties.getResponseTimeThresholdInMilli();
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    protected IApiClient apiClient;
    protected String testRepoName;

    /**
     * Sets up the test environment before any test execution.
     * Initializes the API client and creates a unique repository before the class starts.
     * Logs essential configurations.
     */
    @BeforeClass
    public void setup() {
        LOGGER.info("Initializing Base Test Setup...");
        setApiClient();

        // Logging the loaded properties
        LOGGER.info("Test Properties Loaded:");
        LOGGER.info("Base URL: {}", BASE_URL);
        LOGGER.info("Repository Username: {}", REPO_USERNAME);
        LOGGER.info("Response Time Threshold: {}ms", RESPONSE_TIME_THRESHOLD);

        // Generate a new unique repository name
        testRepoName = "test-repo-" + UUID.randomUUID();
        LOGGER.info("Creating repository: {}", testRepoName);

        Response response = apiClient.createRepository(testRepoName);
        validateResponseTime(response);

        // Assert the repository creation was successful
        Assert.assertEquals(response.statusCode(), 201, "Repository creation failed!");

        // Assert the repository exists after creation
        response = apiClient.repositoryExists(REPO_USERNAME, testRepoName);
        validateResponseTime(response);
        Assert.assertEquals(response.statusCode(), 200, "Repository was created but does not exist!");
    }

    /**
     * Cleans up by deleting the created repository after all tests are finished.
     */
    @AfterClass(alwaysRun = true)
    public void cleanupTest() {
        LOGGER.info("Cleaning up: Deleting repository {}", testRepoName);
        Response response = apiClient.deleteRepository(REPO_USERNAME, testRepoName);
        validateResponseTime(response);

        // Assert the repository deletion was successful
        Assert.assertEquals(response.statusCode(), 204, "Failed to delete the test repository: " + testRepoName);
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
        Assert.assertTrue(timeTaken < RESPONSE_TIME_THRESHOLD,
                "Test failed due to slow response time: " + RESPONSE_TIME_THRESHOLD + "ms");
    }

    /**
     * Sets the API client implementation for the test class.
     */
    public abstract void setApiClient();
}