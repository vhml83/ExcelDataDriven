package com.titanium.testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.titanium.utils.ExcelUtils;

public class RegisterUsers {
	
	public WebDriver driver;
	Object[][] testObjArray;
	static String chromePath = System.getProperty("user.dir")+"\\drivers\\chromedriver.exe";
	String xpathLoc = ".//*[contains(text(),'Note: Your user name is')]";
	String testCaseWorkBook = System.getProperty("user.dir") + "\\testdata\\FlightRegisterData.xlsx";
	
	public void navigateTo(){
		System.setProperty("webdriver.chrome.driver", chromePath);	
		driver = new ChromeDriver();
		driver.get("http://newtours.demoaut.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@DataProvider(name = "UserRegistration")
	public Object[][] userRegister() throws Exception{
		testObjArray = ExcelUtils.getTableArray(testCaseWorkBook, "RegisterUser");
		return (testObjArray);
	}
	
	@BeforeTest
	public void setUp(){
		navigateTo();
	}
	
	@AfterTest
	public void tearDown(){
		driver.quit();
	}
	
	@BeforeMethod
	public void clickRegister(){
		driver.findElement(By.linkText("REGISTER")).click();
	}

	@AfterMethod
	public void verifyUserRegistered(){
		System.out.println(driver.findElement(By.xpath(xpathLoc)).getText());
	}
	
	@Test(dataProvider = "UserRegistration", description="Test Case for Register an user")
	public void registerUserInformation(String ... registerInfo){
		
		//Adding Contact Information
		driver.findElement(By.name("firstName")).sendKeys(registerInfo[0]);
		driver.findElement(By.name("lastName")).sendKeys(registerInfo[1]);
		driver.findElement(By.name("phone")).sendKeys(registerInfo[2]);
		driver.findElement(By.id("userName")).sendKeys(registerInfo[3]);

		
		//Adding Mailing Information
		driver.findElement(By.name("address1")).sendKeys(registerInfo[4]);
		driver.findElement(By.name("city")).sendKeys(registerInfo[5]);
		driver.findElement(By.name("state")).sendKeys(registerInfo[6]);
		driver.findElement(By.name("postalCode")).sendKeys(registerInfo[7]);
		driver.findElement(By.name("country")).sendKeys(registerInfo[8]);
		
		//Adding User Information
		driver.findElement(By.id("email")).sendKeys(registerInfo[9]);
		driver.findElement(By.name("password")).sendKeys(registerInfo[10]);
		driver.findElement(By.name("confirmPassword")).sendKeys(registerInfo[10]);
		driver.findElement(By.name("confirmPassword")).submit();
	}
	
}
