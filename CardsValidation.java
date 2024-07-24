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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;

public class CardsValidation {
	WebDriver driver;
	String url = "http://10.82.180.36/Common/Login.aspx";
  @Test
  public void CardServices() throws Exception {
	  driver.findElement(By.id("body_txtUserID")).sendKeys("donhere");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_txtPassword")).sendKeys("don@123");
	  Thread.sleep(500);
	  driver.findElement(By.id("body_btnLogin")).click();
	  Thread.sleep(1500);
	  
	  driver.findElement(By.xpath("//*[@id=\"GeneralTabMenu_Cards_li_Cust\"]/span")).click();
	  Thread.sleep(1000);
	  
	  
	  driver.findElement(By.xpath("//*[@id=\"body_pnlCustomer_SubMenu\"]/div/div/ul/li[4]/a")).click();
	  Thread.sleep(500);
	  
	  FileInputStream fis = new FileInputStream("C:\\Users\\parchuri.kumar\\Music\\cards.xlsx");
	  XSSFWorkbook wkb = new XSSFWorkbook(fis);
	  XSSFSheet sht = wkb.getSheet("Sheet1");
	  int rowsCount = sht.getLastRowNum() - sht.getFirstRowNum();
	  sht.getRow(0).createCell(3).setCellValue("Status");
	  for( int i=1;i<=rowsCount;i++)
	  {
		  String reqType = sht.getRow(i).getCell(0).getStringCellValue();
		  String cardType = sht.getRow(i).getCell(1).getStringCellValue();
		  String cardNumber = sht.getRow(i).getCell(2).getStringCellValue();
		  
		  System.out.println(cardNumber);
		  
		  
		  if(reqType.equals("Block"))
		  {
			  driver.findElement(By.id("body_cph_Cards_rblRequestType_0")).click();
		  }
		  else {
			  driver.findElement(By.id("body_cph_Cards_rblRequestType_1")).click();
		  }
		  Select s=new Select(driver.findElement(By.id("body_cph_Cards_ddlCardType")));
		  s.selectByVisibleText(cardType);
		  Thread.sleep(2000);
		    
		  //selecting card number
		  Select s1=new Select(driver.findElement(By.id("body_cph_Cards_ddlCardNo")));
		  s1.selectByVisibleText(cardNumber);
		  Thread.sleep(2000);
		    
		    //checking for send request button
		    boolean isdisplayed1=driver.findElement(By.id("body_cph_Cards_btnSendRequest")).isDisplayed();
		    if(isdisplayed1==true) {
				  System.out.println("Request button is displayed, click on Request");
				 //clicking on send request button
				  driver.findElement(By.id("body_cph_Cards_btnSendRequest")).click();
				  Thread.sleep(2000);
				  //reading message
				  String Status1=driver.findElement(By.id("body_cph_Cards_lblStatus")).getText();  
				  Thread.sleep(2000);
				  //writing message into excel
				  sht.getRow(i).createCell(3).setCellValue(Status1);
				  Thread.sleep(2000);
		    }
		    else {
		    	//message to print if request button not found
		    	System.out.println("Request button is not displayed");
		    }

		  //clicking on reset
		  driver.findElement(By.id("body_cph_Cards_btnReset")).click();
		  Thread.sleep(2000);
		  
	  }
	  driver.navigate().refresh();
	  FileOutputStream fos = new FileOutputStream("C:\\Users\\parchuri.kumar\\Music\\cards.xlsx");
	  wkb.write(fos);
	  wkb.close();
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
