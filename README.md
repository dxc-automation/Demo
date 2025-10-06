![This is an image](https://cms-cdn.katalon.com/Banner_6f1593807e.png)

# Automation Framework

## 📘 Overview

This project is a **modular Java-based Test Automation Framework** designed to support automated testing across **Web, API, and Desktop** platforms.
It integrates with **TestNG**, **Maven**, **ExtentReports**, and **Jenkins CI/CD**, providing a complete, scalable, and maintainable testing solution.

The framework follows a layered architecture to separate concerns and improve reusability.

---


## 🧩 Architecture & Modules

### 1. Core

- Common utilities and configuration management
- WebDriver and API initialization
- Logging and reporting integration

### 2. Web Module

- Page Object Model (POM) design
- Supports Chrome, Firefox, Edge (via Selenium WebDriver)
- Handles authentication, synchronization, and data-driven testing

### 3. API Module

- REST API automation using **Rest Assured**
- JSON Schema validation and response verification
- Request/response logging and reporting

### 4. Desktop Module

- Windows desktop automation via **WinAppDriver** or equivalent tools
- Centralized element locators and reusable actions

### 5. Reporting

- Generates **HTML reports** using ExtentReports
- Includes screenshots on failure and step-level logging
- Data-driven reports (via Excel/CSV)

---

## ⚙️ Installation & Setup

### Prerequisites

- Java 17+
- Maven 3.9+
- Chrome / Edge / Firefox (latest version)
- Node.js (for optional WebDriver Manager)
- Jenkins (for CI/CD pipeline execution)

---

# 🚀 Running Tests

Test results are automatically generated under:
/test-output/Automation_Report.html

### Run All Tests

```
 mvn clean test
```

### Run Specific Suite

```
 mvn test -DsuiteXmlFile=testng.xml
```

---

## 🧰 Technologies Used

- Java 17
- TestNG
- Maven
- Rest Assured
- Selenium WebDriver
- ExtentReports
- Jenkins
- Log4j / SLF4J
