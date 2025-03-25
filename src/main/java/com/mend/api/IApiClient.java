package com.mend.api;

import com.mend.utils.TestProperties;
import io.restassured.response.Response;

/**
 * Interface for API clients.
 */
public interface IApiClient {

    String BASE_URL = TestProperties.getBaseUrl();
    String ACCESS_TOKEN = TestProperties.getAccessToken();

    Response login();
    Response createRepository(String repoName);
    Response repositoryExists(String repoUsername, String repoName);
    Response createIssue(String repoUsername, String repoName, String title, String body);
    Response issueExists(String repoUsername, String repoName);
    Response searchCode(String query);
    Response deleteRepository(String repoUsername, String repoName);
}