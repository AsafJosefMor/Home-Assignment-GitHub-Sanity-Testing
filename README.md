
# GitHub Automation Tests (testing home assignment)

## Overview
This project contains automated tests for GitHub API functionality, including:
- User authentication (using a personal access token)
- Repository creation
- Issue creation
- Code search
- Repository deletion (cleanup)
- Response times are validated against a threshold

 Tests are written using **TestNG** and utilize **RestAssured** for API testing.  
Maven is used for dependency management and test execution.

## Setup & Configuration

### 1. Prerequisites
- Java 11+
- Maven installed (`mvn -v` to check)
- A **GitHub personal access token** with required API permissions

### 2. Token Setup
- To create a personal access token, visit [GitHub Personal Access Tokens](https://github.com/settings/personal-access-tokens/new), choose **"All repositories"**.
- Grant Repository permissions - Administration (Read & Write).

### 3. Configuring Properties or run Maven with `-D` parameters

`src/test/resources/Properties/test.properties` - is the default properties file, duplicates were created as an example for specific properties for different environments (same for test-suites)
The test properties can be configured inside `test.properties`:
```
base.url=https://api.github.com
github.token=your_personal_access_token
repo.username=your_github_username
response.time.threshold=2000  # in milliseconds
```

## Running Tests

### Install Maven dependencies and skip tests
```
mvn clean install -DskipTests
```

### Running the sanity tests
To execute all tests, simply run:
```
mvn test
```
To execute all tests with injected properties, for instance, you can run:
```
mvn test -Daccess.token=your_personal_access_token -Drepo.username=your_github_username
```

## Scope and Limitations
- This quick sanity prioritizes test time performance.
- Tests will fail if GitHub API rate limits are exceeded.
- The repository created during the tests is automatically deleted after execution.
