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
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;

public class ServicesBillPay {
	WebDriver driver;
	String url = "http://10.82.180.36/Common/Login.aspx";
  @Test
  public void BillPay() throws Exception {
	  driver.findElement(By.id("body_txtUserID")).sendKeys("donhere");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_txtPassword")).sendKeys("don@123");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_btnLogin")).click();
	  Thread.sleep(1500);
	  
	  driver.findElement(By.linkText("Services")).click();
	  Thread.sleep(500);
	  
	  driver.findElement(By.xpath("//*[@id=\"body_pnlCustomer_SubMenu\"]/div/div/ul/li[5]/a")).click();
	  Thread.sleep(200);
	  
	  FileInputStream fis = new FileInputStream("C:\\Users\\parchuri.kumar\\Music\\BillPay.xlsx");
	  XSSFWorkbook wb = new XSSFWorkbook(fis);
	  XSSFSheet sh = wb.getSheet("Sheet1");
	  
	  int rowCount = sh.getLastRowNum() - sh.getFirstRowNum();
	  
	  for(int i=1;i<=rowCount;i++) {
		  String billPayType = sh.getRow(i).getCell(0).getStringCellValue();
		  String serviceProvider = sh.getRow(i).getCell(1).getStringCellValue();
		  String accNo =  sh.getRow(i).getCell(2).getRawValue();
		  String mobNo = sh.getRow(i).getCell(3).getRawValue();
		  String billAmount = sh.getRow(i).getCell(4).getRawValue();
		  String remarks = sh.getRow(i).getCell(5).getStringCellValue();		  
		  
		  System.out.println(billPayType);
		  System.out.println(serviceProvider);
		  System.out.println(accNo);
		  System.out.println(mobNo);
		  System.out.println(billAmount);
		  System.out.println(remarks);
		  //bill Type
		  WebElement typeDD = driver.findElement(By.id("body_cph_Services_ddlBillPay"));
		  Select Type = new Select(typeDD);
		  Type.selectByVisibleText(billPayType);
		  Thread.sleep(500);
		  
		  //Service Provider
		  WebElement providerDD = driver.findElement(By.xpath("//*[@id=\"body_cph_Services_ddlServiceProvider\"]"));
		  Select provider = new Select(providerDD);
		  provider.selectByVisibleText(serviceProvider);
		  
		  Thread.sleep(1000);
		  
		  Select accountNo = new Select(driver.findElement(By.id("body_cph_Services_ddlFromAccount")));
		  accountNo.selectByVisibleText(accNo);		  

		  Thread.sleep(1000);
		  
		  driver.findElement(By.id("body_cph_Services_txtMobileNumber")).sendKeys(String.valueOf(mobNo));
		  Thread.sleep(1000);
		  
		  driver.findElement(By.id("body_cph_Services_txtBillAmount")).sendKeys(String.valueOf(billAmount));
		  Thread.sleep(1000);
		  
		  driver.findElement(By.id("body_cph_Services_txtRemarks")).sendKeys(remarks);
		  Thread.sleep(500);
		  
		  driver.findElement(By.id("body_cph_Services_btnMakePayment")).click();
		  String status = driver.findElement(By.id("body_cph_Services_lblTransactionId")).getText();
		  
		  sh.getRow(i).createCell(6).setCellValue(status);
		  Thread.sleep(1000);
		  driver.findElement(By.linkText("Bill Pay")).click();
		  driver.navigate().refresh();
	  }
	  FileOutputStream fos = new FileOutputStream("C:\\Users\\parchuri.kumar\\Music\\Bill Pay.xlsx");
	  wb.write(fos);
	  wb.close();
	  fos.close();
  }
  @BeforeClass
  public void beforeClass() {
	  System.setProperty("webdriver.chrome.driver","C:\\Users\\parchuri.kumar\\Music\\chromedriver.exe");
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
