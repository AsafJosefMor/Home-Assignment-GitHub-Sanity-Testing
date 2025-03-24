package com.mend.tests.github;

import com.mend.api.GitHubApi;
import com.mend.tests.BaseTest;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * Sanity tests for GitHub API interactions.
 * This test suite verifies GitHub API operations including:
 * - User authentication
 * - Repository creation
 * - Issue creation
 * - Code search
 * - Repository cleanup after tests
 */
public class SanityApiTest extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SanityApiTest.class);
    private String testRepoName;

    /**
     * Creates a unique repository before the class starts.
     */
    @BeforeClass
    public void createRepo() {
        // Generate a new unique repository name
        testRepoName = "test-repo-" + UUID.randomUUID();
        LOGGER.info("Creating repository: {}", testRepoName);

        Response response = GitHubApi.createRepository(testRepoName, ACCESS_TOKEN);
        validateResponseTime(response);

        // Assert the repository creation was successful
        Assert.assertEquals(response.statusCode(), 201, "Repository creation failed!");

        // Assert the repository exists after creation
        response = GitHubApi.repositoryExists(REPO_USERNAME, testRepoName, ACCESS_TOKEN);
        validateResponseTime(response);
        Assert.assertEquals(response.statusCode(), 200, "Repository was created but does not exist!");
    }

    /**
     * Cleans up by deleting the created repository after all tests are finished.
     */
    @AfterClass(alwaysRun = true)
    public void cleanupTest() {
        LOGGER.info("Cleaning up: Deleting repository {}", testRepoName);
        Response response = GitHubApi.deleteRepository(REPO_USERNAME, testRepoName, ACCESS_TOKEN);
        validateResponseTime(response);

        // Assert the repository deletion was successful
        Assert.assertEquals(response.statusCode(), 204, "Failed to delete the test repository: " + testRepoName);
    }

    /**
     * Tests whether the provided GitHub token is valid.
     */
    @Test
    public void testUserAuthentication() {
        LOGGER.info("Running User Authentication Test");
        Assert.assertNotNull(ACCESS_TOKEN, "GitHub token is not configured!");

        Response response = GitHubApi.login(ACCESS_TOKEN);
        validateResponseTime(response);

        Assert.assertEquals(response.statusCode(), 200, "GitHub authentication failed!");
    }

    /**
     * Tests if the created repository exists.
     */
    @Test
    public void testRepositoryExists() {
        LOGGER.info("Running Repository Existence Test");

        Response response = GitHubApi.repositoryExists(REPO_USERNAME, testRepoName, ACCESS_TOKEN);
        validateResponseTime(response);

        Assert.assertEquals(response.statusCode(), 200, "Repository does not exist!");
    }

    /**
     * Tests issue creation and verifies its existence.
     */
    @Test
    public void testIssueCreation() {
        LOGGER.info("Running Issue Creation Test");

        String issueTitle = "Test Issue";
        Response response = GitHubApi.createIssue(REPO_USERNAME, testRepoName, ACCESS_TOKEN, issueTitle, "This is a test issue.");
        validateResponseTime(response);

        Assert.assertEquals(response.statusCode(), 201, "Issue creation failed!");

        // Assert issue existence
        response = GitHubApi.issueExists(REPO_USERNAME, testRepoName, ACCESS_TOKEN);
        validateResponseTime(response);
        Assert.assertEquals(response.statusCode(), 200, "Issue was created but does not exist!");
    }

    /**
     * Tests GitHub code search functionality.
     */
    @Test
    public void testCodeSearch() {
        LOGGER.info("Running Code Search Test");

        String searchQuery = "public class";
        Response response = GitHubApi.searchCode(searchQuery, ACCESS_TOKEN);
        validateResponseTime(response);

        Assert.assertEquals(response.statusCode(), 200, "Code search failed!");
    }
}