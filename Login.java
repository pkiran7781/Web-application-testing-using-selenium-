package task1;

import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Login {
	WebDriver driver;
	String url = "http://10.82.180.36/Common/HomePage.aspx";
	
  @Test
  public void test1() throws Exception {
	  driver.findElement(By.xpath("//*[@id=\"nav\"]/div[1]/a")).click();
	  
	  String curUrl = driver.getCurrentUrl();
	  String actualUrl = "http://10.82.180.36/Common/Login.aspx";
	  
	  
	  Assert.assertEquals(actualUrl, curUrl);
	  
	  FileInputStream fis = new FileInputStream("C:\\Users\\parchuri.kumar\\Music\\Login.xlsx");
	  XSSFWorkbook workBook = new XSSFWorkbook(fis);
	  XSSFSheet sheet = workBook.getSheet("Sheet1");
	  
	  int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
	  for ( int i =1;i<=rowCount;i++)
	  {
		  String userid = sheet.getRow(i).getCell(0).getStringCellValue();
		  String pwd = sheet.getRow(i).getCell(1).getStringCellValue();
		  
		  
		  driver.findElement(By.id("body_txtUserID")).sendKeys(userid);
		  driver.findElement(By.id("body_txtPassword")).sendKeys(pwd);
		  
		  driver.findElement(By.id("body_btnLogin")).click();
		  
		  String userName = driver.findElement(By.id("divWelcome")).getText();
		  if(userName.equals("Welcome donhere"))
		  {
			  System.out.println("login succesful");
			  String userName1 = driver.findElement(By.id("divWelcome")).getText();
			  System.out.println(userName1);
			  driver.get("http://10.82.180.36/Common/Login.aspx");
		  }
		  else
		  {
			  System.out.println("Incorrect credentials");
			  driver.get("http://10.82.180.36/Common/Login.aspx");
		  }
	  }
	  workBook.close();
  }
  
  
  @BeforeClass
  public void beforeClass() {
	  System.setProperty("webdriver.chrome.driver","C:\\Users\\parchuri.kumar\\Desktop\\Selenium_Accelerate\\chromedriver.exe");
	  driver = new ChromeDriver();
	  driver.get(url);
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	  
  }

  @AfterClass
  public void afterClass() {
	  driver.close();
  }

}
