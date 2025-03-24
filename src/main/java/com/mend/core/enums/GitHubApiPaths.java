package com.mend.core.enums;

/**
 * Enum to store GitHub API endpoints.
 */
public enum GitHubApiPaths {
    // User-related endpoints
    USER("/user"),

    // Repository-related endpoints
    CREATE_REPO("/user/repos"),
    GET_REPO("/repos/%s/%s"), // Get details of a specific repository
    DELETE_REPO("/repos/%s/%s"), // Delete a specific repository

    // Issue-related endpoints
    CREATE_ISSUE("/repos/%s/%s/issues"),
    LIST_ISSUES("/repos/%s/%s/issues"), // List all issues in a repository

    // Pull Request-related endpoints
    CREATE_PR("/repos/%s/%s/pulls"),
    LIST_PRS("/repos/%s/%s/pulls"); // List all pull requests in a repository

    private final String path;

    GitHubApiPaths(String path) {
        this.path = path;
    }

    /**
     * Returns the full API path by formatting it with the provided arguments.
     *
     * @param args Arguments to replace placeholders in the API path.
     * @return The formatted API path.
     */
    public String getPath(Object... args) {
        return String.format(path, args);
    }
}