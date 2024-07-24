package task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class Services {
	WebDriver driver;
	String url = "http://10.82.180.36/Common/Login.aspx";
  @Test
  public void ServicesVaildation() throws Exception {
	  driver.findElement(By.id("body_txtUserID")).sendKeys("donhere");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_txtPassword")).sendKeys("don@123");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_btnLogin")).click();
	  Thread.sleep(1500);
	  
	  driver.findElement(By.linkText("Services")).click();
	  Thread.sleep(500);
	  
	  driver.findElement(By.xpath("//*[@id=\"body_pnlCustomer_SubMenu\"]/div/div/ul/li[3]/a")).click();
	  
	  
	  FileInputStream fis = new FileInputStream("C:\\Users\\parchuri.kumar\\Music\\ServicesLockerReq.xlsx");
	  XSSFWorkbook wb = new XSSFWorkbook(fis);
	  XSSFSheet sh = wb.getSheet("Sheet1");
	  int noofRows = sh.getLastRowNum() - sh.getFirstRowNum();
	  sh.getRow(0).createCell(3).setCellValue("Locker Deatails");
	  for (int i=1;i<=noofRows;i++)
	  {
		  String reqType = sh.getRow(i).getCell(0).getStringCellValue();
		  int lockId = (int) sh.getRow(i).getCell(1).getNumericCellValue();
		  
		  WebDriverWait reqWait = new WebDriverWait(driver,30);
		  reqWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Services_ddRequestType\"]")));
		  
		  WebElement reqDD = driver.findElement(By.xpath("//*[@id=\"body_cph_Services_ddRequestType\"]"));
		  Select request = new Select(reqDD);
		  request.selectByVisibleText(reqType);
		  
		  
		  WebDriverWait lockidWait = new WebDriverWait(driver,30);
		  lockidWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Services_ddlLockerId\"]")));
		  
		  WebElement lockDD = driver.findElement(By.xpath("//*[@id=\"body_cph_Services_ddlLockerId\"]"));
		  Select locker = new Select(lockDD);
		  locker.selectByVisibleText(String.valueOf(lockId));
		  
		  String lockDetails = driver.findElement(By.xpath("//*[@id=\"body_cph_Services_pnlLockerDetails\"]/fieldset")).getText();
		  System.out.println(lockDetails);
		  
		  sh.getRow(1).createCell(3).setCellValue(lockDetails);
	  }
	  FileOutputStream fos = new FileOutputStream("C:\\Users\\parchuri.kumar\\Music\\ServicesLockerReq.xlsx");
	  wb.write(fos);
	  wb.close(); 
	  fos.close();
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
