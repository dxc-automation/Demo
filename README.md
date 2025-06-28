````markdown
## Prerequisites

Before you begin, ensure you have the following installed:

- [JDK](https://www.oracle.com/java/technologies/downloads/#jdk24-windows) (version 24 or higher)
- [Maven](https://maven.apache.org/)

````

## Running the Tests

To execute the test suite, run:

```bash
# For Maven
mvn test will spin up Chrome by default.

To run in Firefox, override the suite parameter:
mvn test -Dbrowser=firefox
```

## Project Structure

```
egt_test_lab

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


