package com.mend.tests.github;

import com.mend.api.GitHubApiClient;
import com.mend.tests.BaseTest;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

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

    @Override
    public void setApiClient() {
        apiClient = new GitHubApiClient();
    }

    /**
     * Tests whether the provided GitHub token is valid.
     */
    @Test
    public void testUserAuthentication() {
        LOGGER.info("Running User Authentication Test");
        Response response = apiClient.login();
        validateResponseTime(response);
        Assert.assertEquals(response.statusCode(), 200, "GitHub authentication failed!");
    }

    /**
     * Tests if the created repository exists.
     */
    @Test
    public void testRepositoryExists() {
        LOGGER.info("Running Repository Creation Test");

        Response response = apiClient.repositoryExists(REPO_USERNAME, testRepoName);
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
        Response response = apiClient.createIssue(REPO_USERNAME, testRepoName, issueTitle, "This is a test issue.");
        validateResponseTime(response);

        Assert.assertEquals(response.statusCode(), 201, "Issue creation failed!");

        // Assert issue existence
        response = apiClient.issueExists(REPO_USERNAME, testRepoName);
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
        Response response = apiClient.searchCode(searchQuery);
        validateResponseTime(response);

        // Assert that the code search returned results
        Assert.assertFalse(response.jsonPath().getList("items").isEmpty(), "Code search returned no results!");
        Assert.assertEquals(response.statusCode(), 200, "Code search failed!");
    }
}