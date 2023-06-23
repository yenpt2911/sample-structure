package com.nopcommerce.register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import dataTest.EmployeeData;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.RegisterPageObject;

public class User_01_Register extends BaseTest {


    WebDriver driver;
    String emailAddress, password;
    HomePageObject homePage;
    RegisterPageObject registerPage;
    EmployeeData employeeData;

    @Parameters({"browser","url"})

    @BeforeClass
    public void beforeClass(String browserName, String appUrl) {
        log.info("Pre-condition - STEP 01: Open browser'" + browserName + "' and navigate to' " + appUrl + "'");
        driver = getBrowserDriver(browserName, appUrl);
        homePage = PageGeneratorManager.getHomePage(driver);

        employeeData = EmployeeData.getEmployeeData();


        log.info("Pre-condition - STEP 02: Click to 'Register' link");
        homePage.clickToRegisterLink();
        registerPage = PageGeneratorManager.getRegisterPage(driver);
    }


        @Test
        public void User_Register_01_Register_User_Successful() {
        log.info("User_Register_01 - STEP 01: Select data at Gender radio");
        registerPage.selectRadioOptionByID(driver, "gender-male");

        log.info("User_Register_01 - STEP 02: Enter data to 'First name' textbox with value: " + employeeData.getFirstName());
        registerPage.enterToTextBoxByID(driver, employeeData.getFirstName(), "FirstName");

        log.info("User_Register_01 - STEP 03: Enter data to 'Last name' textbox with value: " + employeeData.getLastName());
        registerPage.enterToTextBoxByID(driver, employeeData.getLastName(), "LastName");

        log.info("User_Register_01 - STEP 04: Select day of birth at 'Day' dropdown with value: " + employeeData.getDayOfBirth());
        registerPage.selectDefaultDropdownByName(driver, employeeData.getDayOfBirth(), "DateOfBirthDay");

        log.info("User_Register_01 - STEP 05: Select month of birth at 'Month' dropdown with value: " + employeeData.getMonthOfBirth());
        registerPage.selectDefaultDropdownByName(driver, employeeData.getMonthOfBirth(), "DateOfBirthMonth");

        log.info("User_Register_01 - STEP 06: Select month of birth at 'Year' dropdown with value: " + employeeData.getYearOfBirth());
        registerPage.selectDefaultDropdownByName(driver, employeeData.getYearOfBirth(), "DateOfBirthYear");

        log.info("User_Register_01 - STEP 07: Enter data to 'Email' textbox with value: " + employeeData.getEmail() + getRandomEmail());
        registerPage.enterToTextBoxByID(driver, employeeData.getEmail() + getRandomEmail(), "Email");

        log.info("User_Register_01 - STEP 08: Enter data to 'Company name' textbox with value: " + employeeData.getCompanyName());
        registerPage.enterToTextBoxByID(driver, employeeData.getCompanyName(), "Company");

        log.info("User_Register_01 - STEP 09: Enter data to 'Password' textbox with value: " + employeeData.getPassWord());
        registerPage.enterToTextBoxByID(driver, employeeData.getPassWord(), "Password");

        log.info("User_Register_01 - STEP 10: Enter data to 'ConfirmPassword' textbox with value: " + employeeData.getPassWord());
        registerPage.enterToTextBoxByID(driver, employeeData.getPassWord(), "ConfirmPassword");

        log.info("User_Register_01 - STEP 11: Click to 'Register' button");
        registerPage.clickToButtonByText();


        }

   /*     @Parameters({"browser"})
        @AfterClass(alwaysRun = true)
    public void afterClass(String browserName) {
        cleanDriverInstance();
        }*/



}
