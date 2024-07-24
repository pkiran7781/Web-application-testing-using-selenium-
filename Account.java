package task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;

public class Account {
	WebDriver driver;
	String url = "http://10.82.180.36/Common/Login.aspx";
  @Test
  public void viewTransactions() throws Exception {
	  driver.findElement(By.id("body_txtUserID")).sendKeys("donhere");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_txtPassword")).sendKeys("don@123");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_btnLogin")).click();
	  Thread.sleep(1500);
	  
	  driver.findElement(By.xpath("//*[@id=\"AccountCustomer_ul\"]/li[4]/a")).click();
	  Thread.sleep(1000);
	  
	  FileInputStream fis = new FileInputStream("C:\\Users\\parchuri.kumar\\Music\\AccTransactions.xlsx");
	  XSSFWorkbook wb = new XSSFWorkbook(fis);
	  XSSFSheet sh = wb.getSheet("Sheet1");
	  
	  int noOfRows = sh.getLastRowNum() - sh.getFirstRowNum();
	  for(int i =1;i<=noOfRows;i++)
	  {
		  
		  String accNo = sh.getRow(i).getCell(0).getRawValue();
		  String stmntType = sh.getRow(i).getCell(1).getStringCellValue();
		  String transactionType = sh.getRow(i).getCell(2).getStringCellValue();
		  String fromDate = sh.getRow(i).getCell(3).getRawValue();
		  String toDate = sh.getRow(i).getCell(4).getRawValue();
		  
		    
		  
		  
		  Select s = new Select(driver.findElement(By.id("body_cph_MyAccount_ddlAccountNo")));
		  s.selectByValue(accNo);
		  
		  if (stmntType.equals("Mini"))
		  {
			  driver.findElement(By.id("body_cph_MyAccount_rblTransactions_0")).click();
			  Thread.sleep(500);
			  
			  driver.findElement(By.id("body_cph_MyAccount_btnViewTrancastions")).click();
			  Thread.sleep(1000);
			  //tableContent print
			  
			  WebElement table = driver.findElement(By.xpath("//*[@id=\"tblGrid\"]"));
			  Thread.sleep(500);
			  List<WebElement> rows = table.findElements(By.tagName("tr"));
			  Thread.sleep(1000);
			  for ( WebElement row : rows ) {
				  List<WebElement> columns = row.findElements(By.tagName("td"));
				  System.out.println( columns.get(0).getText()
						  + columns.get(1).getText()
						  + columns.get(2).getText()
						  + columns.get(3).getText()
						  + columns.get(4).getText());
				  
			  }
		  }
		  
		  else
		  {
			  driver.findElement(By.id("body_cph_MyAccount_rblTransactions_1")).click();
			  Thread.sleep(500);
			  
			 Select tType = new Select(driver.findElement(By.id("body_cph_MyAccount_ddlTransactionType")));
			 tType.selectByValue(transactionType);
			 Thread.sleep(1000);
			 
			 driver.findElement(By.id("body_cph_MyAccount_txtFromDate")).sendKeys(fromDate);
			 Thread.sleep(1000);
			  
			 driver.findElement(By.id("body_cph_MyAccount_txtToDate")).sendKeys(toDate);
			 Thread.sleep(1000);
			 
			 driver.findElement(By.id("body_cph_MyAccount_btnViewTrancastions")).click();
			 
			 //table content print
			 WebElement table = driver.findElement(By.xpath("//*[@id=\"tblGrid\"]"));
			 Thread.sleep(500);
			 List<WebElement> rows = table.findElements(By.tagName("tr"));
			 Thread.sleep(1000);
			 for ( WebElement row : rows ) {
				  List<WebElement> columns = row.findElements(By.tagName("td"));
				  System.out.println( columns.get(0).getText()
						  + columns.get(1).getText()
						  + columns.get(2).getText()
						  + columns.get(3).getText()
						  + columns.get(4).getText());
				  
			 
			 
		  }
		  driver.navigate().refresh();
	  }
	  wb.close();
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
