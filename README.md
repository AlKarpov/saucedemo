# Selenium+Java+TestNG+MVN automation project for 'www.saucedemo.com'

## Description
This project implements part of the tests for the functionality of 'www.saucedemo.com'.
Obviously, this project isn't an ideal example of how to automate, but was made for obtaining a dream job.

## Project structure
### 'com.example.saucedemo' package contains runnable tests:
    * HappyPath test, that goes through processes from Login to purchase confirmation. 
      Test performs for the several users via TestNG DataProvider;
    * Fast test for checking, that locked out users can't login;
    * MainPage tests for desktop and mobile view. Consists of different tests with 
      interaction of inventory page (add/remove items etc.)
### 'Fixtures' contains ...(drumroll)... Fixtures! (users data, in our case);
### 'Pages' - object repository for all the pages, that used in tests;
### 'Utils' contains some of the helper functions.

## How to run:
1. Install Maven, Java etc.
2. Clone project
3. Run tests via command:
   ```
   mvn surefire-report:report
   ```
4. Test result can be viewed by opening file ./target/surefire-reports/index.html or running allure-report via command:
   ```
   allure serve target/allure-results
   ```