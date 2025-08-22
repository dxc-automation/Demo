![This is an image](https://cms-cdn.katalon.com/Banner_6f1593807e.png)

# Automation Framework

## Tech Stack

1. Java
2. Rest Assured
3. TestNG
4. Maven
5. Selenium WebDriver
6. Jenkins

### Automation Framework with TestNG

1. Rest Assured with TestNG Framework to test and validate APIs
2. Examples on JSON Parsing, OAuth 2.0 Authentication and getting Token, GET, POST, PUT, PATCH operations
3. Extent Report 5.x for generating Reports for TestNG Test Run
4. POJO classes and relevant dto classes for generating Data and using in Tests
5. Properties file and relevant classes to utilise it
6. TestNG Custom Annotations and RetryAnalyzer listeners
7. Sample XML files to run Tests based on Groups/Parallel

### Maven Commands to Run Tests

To execute the test suite, run:

```bash
# For Maven
mvn test will spin up Chrome by default.

To run in Firefox, override the suite parameter:
mvn test -Dbrowser=firefox

To run specific XML file:
mvn clean package test -DsuiteXmlFile=xml-suites/$TESTSUITE***;
```

### Project Structure

```
├── src         
    └── main
        └── java
            └── utils
                ├── Excel
                ├── ScreenshotUtil
                ├── Utilities
├── test
    └── java
        └── config
            ├── BaseTest
            ├── TestListener
            ├── WebManager
      
        └── data
            ├── Constants
            ├── data.xlsx
      
        └── pages
            ├── RegistrationPage
      
        └── tests  
            ├── CreateUserTest
            ├── RegistrationTest 
```
