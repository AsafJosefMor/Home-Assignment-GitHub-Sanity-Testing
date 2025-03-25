package com.mend.api;

import com.mend.core.enums.ApiHeaders;
import com.mend.core.enums.GitHubApiPaths;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Implementation of ApiClientInterface for GitHub API interactions.
 * This class provides methods to interact with GitHub's API, including authentication,
 * repository management, issue management, and code search.
 */
public class GitHubApiClient implements IApiClient {
    /**
     * Logs in to GitHub using the provided access token.
     *
     * @return Response object with the response data
     */
    @Override
    public Response login() {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), ApiHeaders.TOKEN_PREFIX.getHeaderName() + ACCESS_TOKEN)
                .when()
                .get(BASE_URL + GitHubApiPaths.USER.getPath());
    }

    /**
     * Creates a new repository.
     *
     * @param repoName    the name of the repository
     * @return Response object with the response data
     */
    @Override
    public Response createRepository(String repoName) {
        String requestBody = "{\"name\": \"" + repoName + "\", \"private\": false}";
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), ApiHeaders.TOKEN_PREFIX.getHeaderName() + ACCESS_TOKEN)
                .header(ApiHeaders.CONTENT_TYPE.getHeaderName(), ApiHeaders.JSON_CONTENT_TYPE.getHeaderName())
                .body(requestBody)
                .when()
                .post(BASE_URL + GitHubApiPaths.CREATE_REPO.getPath());
    }

    /**
     * Checks if a repository exists.
     *
     * @param repoUsername the GitHub username
     * @param repoName     the repository name
     * @return Response object with the response data
     */
    @Override
    public Response repositoryExists(String repoUsername, String repoName) {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), ApiHeaders.TOKEN_PREFIX.getHeaderName() + ACCESS_TOKEN)
                .when()
                .get(BASE_URL + GitHubApiPaths.REPO_EXISTS.getPath(repoUsername, repoName));
    }

    /**
     * Creates a new issue in a repository.
     *
     * @param repoUsername the GitHub username
     * @param repoName     the repository name
     * @param title        the title of the issue
     * @param body         the body of the issue
     * @return Response object with the response data
     */
    @Override
    public Response createIssue(String repoUsername, String repoName, String title, String body) {
        String requestBody = "{\"title\": \"" + title + "\", \"body\": \"" + body + "\"}";
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), ApiHeaders.TOKEN_PREFIX.getHeaderName() + ACCESS_TOKEN)
                .header(ApiHeaders.CONTENT_TYPE.getHeaderName(), ApiHeaders.JSON_CONTENT_TYPE.getHeaderName())
                .body(requestBody)
                .when()
                .post(BASE_URL + GitHubApiPaths.CREATE_ISSUE.getPath(repoUsername, repoName));
    }

    /**
     * Checks if a specified issue exists in a repository.
     *
     * @param repoUsername the GitHub username
     * @param repoName     the repository name
     * @return Response object with the response data
     */
    @Override
    public Response issueExists(String repoUsername, String repoName) {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), ApiHeaders.TOKEN_PREFIX.getHeaderName() + ACCESS_TOKEN)
                .when()
                .get(BASE_URL + GitHubApiPaths.ISSUE_EXISTS.getPath(repoUsername, repoName) + "?state=open");
    }

    /**
     * Searches code within repositories.
     *
     * @param query       the search query to look for in code
     * @return Response object with the response data
     */
    @Override
    public Response searchCode(String query) {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), ApiHeaders.TOKEN_PREFIX.getHeaderName() + ACCESS_TOKEN)
                .when()
                .get(BASE_URL + GitHubApiPaths.SEARCH_CODE.getPath() + "?q=" + query);
    }

    /**
     * Deletes a repository.
     *
     * @param repoUsername the GitHub username
     * @param repoName     the repository name
     * @return Response object with the response data
     */
    @Override
    public Response deleteRepository(String repoUsername, String repoName) {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), ApiHeaders.TOKEN_PREFIX.getHeaderName() + ACCESS_TOKEN)
                .when()
                .delete(BASE_URL + GitHubApiPaths.DELETE_REPO.getPath(repoUsername, repoName));
    }
}