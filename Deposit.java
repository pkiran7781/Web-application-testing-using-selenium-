package task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;


import java.io.FileInputStream;
import java.io.IOException;
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

public class Deposit {
	WebDriver driver;
	String url = "http://10.82.180.36/Common/Login.aspx";
  @Test
  public void depositValidation() throws Exception {
	  driver.findElement(By.id("body_txtUserID")).sendKeys("donhere");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_txtPassword")).sendKeys("don@123");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_btnLogin")).click();
	  Thread.sleep(1500);
	  
	  
	  driver.findElement(By.linkText("Deposit")).click();
	  Thread.sleep(500);
	  
	  driver.findElement(By.xpath("//*[@id=\"body_pnlCustomer_SubMenu\"]/div/div/ul/li[1]/a")).click();
	  
	  
	  try {
	  FileInputStream fis = new FileInputStream("C:\\Users\\parchuri.kumar\\Music\\Deposit.xlsx");
	  XSSFWorkbook workBook = new XSSFWorkbook(fis);
	  XSSFSheet sheet = workBook.getSheet("Sheet1");
	  
	  int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
	  
	  for(int i =1;i<=rowCount;i++)
	  {
		  String state = sheet.getRow(i).getCell(0).getStringCellValue();
		  String city = sheet.getRow(i).getCell(1).getStringCellValue();
		  String branch = sheet.getRow(i).getCell(2).getStringCellValue();
		  String depositType = sheet.getRow(i).getCell(3).getStringCellValue();
		  int amount = (int) sheet.getRow(i).getCell(4).getNumericCellValue();
		  int period = (int) sheet.getRow(i).getCell(5).getNumericCellValue();
		  String withdrawl = sheet.getRow(i).getCell(6).getStringCellValue();
		  int accNo = (int) sheet.getRow(i).getCell(7).getNumericCellValue();
//		  String accNo = sheet.getRow(i).getCell(7).getStringCellValue();
		  
		
		    
		  WebDriverWait waitState = new WebDriverWait(driver,30);
		  waitState.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Deposit_ddlState\"]"))).click();
		  
		  WebElement stateDD = driver.findElement(By.xpath("//*[@id=\"body_cph_Deposit_ddlState\"]"));
		  Select state1 = new Select(stateDD);
		  state1.selectByVisibleText(state);
		  Thread.sleep(1000);
		  
		  if(state.equals("Karnataka")) {
		  
		  WebDriverWait waitCity = new WebDriverWait(driver,30);
		  waitCity.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Deposit_ddlCity\"]"))).click();
		  
		  WebElement cityDD = driver.findElement(By.xpath("//*[@id=\"body_cph_Deposit_ddlCity\"]"));
		  Select city1 = new Select(cityDD);
		  city1.selectByVisibleText(city);
		  Thread.sleep(1000);
		  
		  
		  WebDriverWait waitBranch = new WebDriverWait(driver,30);
		  waitBranch.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Deposit_ddlBranch\"]"))).click();
		  
		  WebElement branchDD = driver.findElement(By.xpath("//*[@id=\"body_cph_Deposit_ddlBranch\"]"));
		  Select branch1 = new Select(branchDD);
		  branch1.selectByVisibleText(branch);
		  Thread.sleep(1000);
		  
		  WebDriverWait waitDType = new WebDriverWait(driver,30);
		  waitDType.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Deposit_ddlDepositType\"]"))).click();
		  
		  WebElement dtypeDD = driver.findElement(By.xpath("//*[@id=\"body_cph_Deposit_ddlDepositType\"]"));
		  Select depositType1 = new Select(dtypeDD);
		  depositType1.selectByVisibleText(depositType);
		  Thread.sleep(1000);
		  
		  driver.findElement(By.id("body_cph_Deposit_txtAmount")).clear();
		  Thread.sleep(500);
		  
		  driver.findElement(By.id("body_cph_Deposit_txtAmount")).sendKeys(String.valueOf(amount));
		  Thread.sleep(500);
		  
		  driver.findElement(By.id("body_cph_Deposit_txtMaturityPeriod")).clear();
		  Thread.sleep(500);
		  
		  driver.findElement(By.id("body_cph_Deposit_txtMaturityPeriod")).sendKeys(String.valueOf(period));
		  Thread.sleep(500);
		  
		  
		  
		  WebDriverWait withdrawlWait = new WebDriverWait(driver, 30);
		  withdrawlWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Deposit_ddlDepositMode\"]")));
		  
		  WebElement withdrawlDD = driver.findElement(By.xpath("//*[@id=\"body_cph_Deposit_ddlDepositMode\"]"));
		  Select withDrawl = new Select(withdrawlDD);
		  withDrawl.selectByVisibleText(withdrawl);
		  
		  if(withdrawl.equals("Cash/Cheque")) {
			  driver.findElement(By.id("body_cph_Deposit_btnSubmit")).click();
			  driver.findElement(By.xpath("//*[@id=\"body_pnlCustomer_SubMenu\"]/div/div/ul/li[1]/a")).click();
		  }
		  else {
			  driver.findElement(By.id("body_cph_Deposit_txtTransferAccountNo")).sendKeys(String.valueOf(accNo));
			  driver.findElement(By.id("body_cph_Deposit_btnSubmit")).click();
		  }
		  }
		  else {
			  driver.findElement(By.id("body_cph_Deposit_btnReset")).click();
		  }
		  driver.findElement(By.xpath("//*[@id=\"body_pnlCustomer_SubMenu\"]/div/div/ul/li[1]/a")).click();
	  }
	  
	  
	 }
	  
	  catch (IOException E)
	  {
		  E.printStackTrace();
	  }
	  
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
