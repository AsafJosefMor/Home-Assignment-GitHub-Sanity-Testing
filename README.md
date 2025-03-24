
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

### 3. Configuring Properties

#### Internal for local testing (`src/test/resources/Properties/test.properties` - is the default properties file, a duplicate was created as an example to specific properties for different environments)
The test properties can be configured inside `test.properties` and will be ignored by Git:
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

## Scope
- Directories for GitLab and Azure was created for example of extendability.
- `CONTRIBUTING.md` was not implemented.
- Code style rules are not implemented - a `CODING-STYLE.md` file exist for example.
- Tests will fail if GitHub API rate limits are exceeded (a retry mechanism is out of scope).
- The repository created during the tests is automatically deleted after execution.
- This quick sanity prioritizes test time performance.
- For this solution, a personal access token is used, but in a real-world scenario, it should be stored securely and not hardcoded in the properties file (more information under [GitHub: managing your personal access tokens](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens)).

