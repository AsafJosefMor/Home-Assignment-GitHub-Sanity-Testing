package com.mend.api;

import com.mend.core.enums.ApiHeaders;
import com.mend.core.enums.GitHubApiPaths;
import com.mend.utils.TestProperties;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GitHubApi {
    private static final String BASE_URL = TestProperties.getBaseUrl();
    private static final String TOKEN_PREFIX = "token ";

    /**
     * Logs in to GitHub using the provided access token.
     *
     * @param githubToken the GitHub access token
     * @return Response object with the response data
     */
    public static Response login(String githubToken) {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), TOKEN_PREFIX + githubToken)
                .when()
                .get(BASE_URL + "/user");
    }

    /**
     * Creates a new repository.
     *
     * @param repoName    the name of the repository
     * @param githubToken the GitHub access token
     * @return Response object with the response data
     */
    public static Response createRepository(String repoName, String githubToken) {
        String requestBody = "{\"name\": \"" + repoName + "\", \"private\": false}";
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), TOKEN_PREFIX + githubToken)
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
     * @param githubToken  the GitHub access token
     * @return Response object with the response data
     */
    public static Response repositoryExists(String repoUsername, String repoName, String githubToken) {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), TOKEN_PREFIX + githubToken)
                .when()
                .get(BASE_URL + "/repos/" + repoUsername + "/" + repoName);
    }

    /**
     * Creates a new issue in a repository.
     *
     * @param repoUsername the GitHub username
     * @param repoName     the repository name
     * @param githubToken  the GitHub access token
     * @param title        the title of the issue
     * @param body         the body of the issue
     * @return Response object with the response data
     */
    public static Response createIssue(String repoUsername, String repoName, String githubToken, String title, String body) {
        String requestBody = "{\"title\": \"" + title + "\", \"body\": \"" + body + "\"}";
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), TOKEN_PREFIX + githubToken)
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
     * @param githubToken  the GitHub access token
     * @return Response object with the response data
     */
    public static Response issueExists(String repoUsername, String repoName, String githubToken) {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), TOKEN_PREFIX + githubToken)
                .when()
                .get(BASE_URL + "/repos/" + repoUsername + "/" + repoName + "/issues?state=open");
    }

    /**
     * Searches code within repositories.
     *
     * @param query       the search query to look for in code
     * @param githubToken the GitHub access token
     * @return Response object with the response data
     */
    public static Response searchCode(String query, String githubToken) {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), TOKEN_PREFIX + githubToken)
                .when()
                .get(BASE_URL + "/search/code?q=" + query);
    }

    /**
     * Deletes a repository.
     *
     * @param repoUsername the GitHub username
     * @param repoName     the repository name
     * @param githubToken  the GitHub access token
     * @return Response object with the response data
     */
    public static Response deleteRepository(String repoUsername, String repoName, String githubToken) {
        return given()
                .header(ApiHeaders.AUTHORIZATION.getHeaderName(), TOKEN_PREFIX + githubToken)
                .when()
                .delete(BASE_URL + GitHubApiPaths.DELETE_REPO.getPath(repoUsername, repoName));
    }
}