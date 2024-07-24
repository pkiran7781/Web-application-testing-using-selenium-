package task1;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Login_Logout {
	WebDriver driver;
	
	//Data Provider is used to pass credentials 
	@DataProvider(name="Login_Credentials")
	public Object[][] getData()
	{
		//Object is created with two parameters as user name and password
		Object [][] loginObj = new Object[6][2];
		
		loginObj[0][0] = "don";
		loginObj[0][1] = "don@123";
		
		loginObj[1][0] = "donhere";
		loginObj[1][1] = "don123";
		
		loginObj[2][0] = "don11";
		loginObj[2][1] = "don@2";
				
		loginObj[3][0] = "";
		loginObj[3][1] = "don34";
		
		loginObj[4][0] = "don22";
		loginObj[4][1] = "";
		
		
		loginObj[5][0] = "donhere";
		loginObj[5][1] = "don@123";
		
		return loginObj;
		
	}
	
	
	//Test method is used to execute after before method 
	//It is executed first as priority is 1
	//This method is used for sign-up page
	@Test(priority=1)
	public void signUp() throws Exception {
		driver.findElement(By.id("body_lbtSignUp")).click();
		Thread.sleep(1000);
		String curUrl = driver.getCurrentUrl();
		String expUrl = "http://10.82.180.36/Common/CustomerRegisterPage.aspx";
		Assert.assertEquals(curUrl, expUrl);
		driver.navigate().back();
		driver.findElement(By.id("body_lbtForgotPassword")).click();
		Thread.sleep(1000);
		String actualTitle = "FORGOT PASSWORD/UNLOCK ACCOUNT";
		String expTitle = driver.findElement(By.id("body_header_divHeader")).getText();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actualTitle, expTitle,"Page Not Found" );
		driver.navigate().back();
		Thread.sleep(1000);
		sa.assertAll();
		//Soft assert is used to check whether page is redirected to sign-up or not 
		
	}
	
	
	//This method is used to login to the IEBI and credentials are provided
	//by using data provider
  @Test(priority=2,dataProvider="Login_Credentials")
  public void Login_toAccount(String userName, String password) throws Exception {
	  
	  //To take screenshot the below method is used
	  TakesScreenshot ss_home = (TakesScreenshot) driver;
	  File source = ss_home.getScreenshotAs(OutputType.FILE);
	  File destination = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Home.png");
	  Thread.sleep(1000);;
	  FileUtils.copyFile(source, destination);
	  
	  
	  //user name and password are entered by using below web elements
	  driver.findElement(By.id("body_txtUserID")).clear();
	  Thread.sleep(1000);
	  driver.findElement(By.id("body_txtUserID")).sendKeys(userName);
	  Thread.sleep(2000);
	  
	  driver.findElement(By.id("body_txtPassword")).clear();
	  driver.findElement(By.id("body_txtPassword")).sendKeys(password);
	  Thread.sleep(2000);
	  	
	  driver.findElement(By.xpath("//*[@id=\"body_btnLogin\"]")).click();
	  
	  
	  //Validating the login credentials
	  //if username is empty then it returns username is mandatory
	  //and takes screenshot
	  if(userName.isEmpty())
	  {
		  TakesScreenshot ss_Uname = (TakesScreenshot) driver;
		  File source_Uname = ss_Uname.getScreenshotAs(OutputType.FILE);
		  File dest_Uname = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\UnameEmpty.png");
		  Thread.sleep(1000);
		  FileUtils.copyFile(source_Uname, dest_Uname);
	  }
	  
	  
	  
	  //Validating the login credentials
	  //if Password is empty then it returns Password is mandatory
	  //and takes screenshot
	  else if(password.isEmpty())
	  {
		  TakesScreenshot ss_Uname = (TakesScreenshot) driver;
		  File source_Uname = ss_Uname.getScreenshotAs(OutputType.FILE);
		  File dest_Uname = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\PWDEmpty.png");
		  Thread.sleep(1000);
		  FileUtils.copyFile(source_Uname, dest_Uname);
	  }
	  
	  
	  
	  //If the username and password are given but are incorrect then 
	  //below block displays message on console
	  else if(driver.getCurrentUrl().equals("http://10.82.180.36/Common/Login.aspx"))
	  {
		  String msg = driver.findElement(By.id("body_lblStatus")).getText();
		  System.out.println(msg);
	  }  
  }
  
  
  //This Test() is used for logout from the account
  //and user cannot access the profile once logged out
  //If the user wants to access account, the user need to login again with
  //valid credentials
  @Test(priority=3)
  public void Logout() throws Exception
  {
	  driver.findElement(By.linkText("Log Out")).click();
	  Thread.sleep(1000);
	  
	  driver.manage().deleteAllCookies();
	  
	  String actualUrl = driver.getCurrentUrl();
	  String expUrl = "http://10.82.180.36/Common/Login.aspx";
	  Assert.assertEquals(actualUrl, expUrl, "Logout failed");
	  driver.navigate().back();
	  Thread.sleep(1000);
	  
	  driver.findElement(By.xpath("//*[@id=\"nav\"]/div[1]/a")).click();
	  Thread.sleep(1000);
	  
	  driver.findElement(By.id("body_txtUserID")).sendKeys("donhere");
	  Thread.sleep(2000);
	  
	  driver.findElement(By.id("body_txtPassword")).sendKeys("don@123");
	  Thread.sleep(2000);
	  	
	  driver.findElement(By.xpath("//*[@id=\"body_btnLogin\"]")).click();
	  
  }
  
  //Once the user login to account 
  //To access validate the loans page and perform certain actions the 
  //below loans() is used
  @Test(priority=4) 
  public void Loans() throws Exception
  {
	  //Screenshot is taken once loans web element is clicked
	  driver.findElement(By.linkText("Loans")).click();
	  TakesScreenshot ss_Loans = (TakesScreenshot) driver;
	  File source = ss_Loans.getScreenshotAs(OutputType.FILE);
	  File dest = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\LoansHomePage.png");
	  Thread.sleep(1000);
	  FileUtils.copyFile(source, dest);
	  
	  //To print the elements present in the drop down menu
	  WebElement dropdownElement = driver.findElement(By.id("body_cph_Loans_ddlLoanType"));
      Select dropdown = new Select(dropdownElement);
      List<WebElement> options = dropdown.getOptions();
      for (WebElement option : options) {
          System.out.println(option.getText());
      }
      
      driver.findElement(By.id("body_cph_Loans_ddlLoanType")).click();
     
      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      File dest_Drop = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Dropdown.png");
      try {
          // Save the screenshot to a file
          FileUtils.copyFile(screenshot, dest_Drop);
          System.out.println("Screenshot saved successfully.");
      } catch (IOException e) {
          System.out.println("Failed to save the screenshot: " + e.getMessage());
      }

      driver.findElement(By.linkText("Loans")).click();
      Thread.sleep(2000);
      
      //to validate the both emi_months and request amount 
      //Passing the values by using Excel 
	  //File Input Stream() is used to pass Excel file
	  FileInputStream fis = new FileInputStream("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\LoansData.xlsx");
	  XSSFWorkbook wb = new XSSFWorkbook(fis);
	  XSSFSheet sheet = wb.getSheet("Sheet1");
	  
	  int noofRows = sheet.getLastRowNum() - sheet.getFirstRowNum();
	  for(int i =1;i<=noofRows;i++)
	  {
		  String loanType = sheet.getRow(i).getCell(0).getStringCellValue();
		  String loanName = sheet.getRow(i).getCell(1).getStringCellValue();
		  int reqAmount =(int) sheet.getRow(i).getCell(2).getNumericCellValue();
		  int emi_Months = (int) sheet.getRow(i).getCell(3).getNumericCellValue();
		  
		  
		  //Selecting the loan type drop down menu and selecting 
		  //the drop down value by using visible text 
		  WebElement we = driver.findElement(By.id("body_cph_Loans_ddlLoanType"));
		  Select ss = new Select(we);
		  ss.selectByVisibleText(loanType);

		 
		  
		  WebDriverWait wait2 = new WebDriverWait(driver,30);
		  wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Loans_ddlLoanName\"]/option[2]")));
		  
		  
		  //Selecting the loan name drop down menu and selecting 
		  //the drop down value by using visible text 
		  WebElement we1 = driver.findElement(By.id("body_cph_Loans_ddlLoanName"));
		  Select ss1 = new Select(we1);
		  ss1.selectByVisibleText(loanName);
		  
		  
		  //Passing the values of excel into request amount and number of emi months
		  driver.findElement(By.id("body_cph_Loans_txtReqLoanAmount")).sendKeys(String.valueOf(reqAmount));
		  Thread.sleep(1000);
		  driver.findElement(By.id("body_cph_Loans_txtNoOfEMI")).sendKeys(String.valueOf(emi_Months));
		  Thread.sleep(1000);
		  driver.findElement(By.id("body_cph_Loans_btnViewEMI")).click();
		  Thread.sleep(2000);
		  
		  
		  //Validating the conditions of request amount and number of emi months
		  //and taking screenshots if incorrect
		  if(reqAmount<10000 || reqAmount>10000000)
		  {
			  TakesScreenshot ss_reqAmount = (TakesScreenshot) driver;
			  File source_Amount = ss_reqAmount.getScreenshotAs(OutputType.FILE);
			  File dest_Amount = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Loans_ReqAmount.png");
			  Thread.sleep(1000);
			  FileUtils.copyFile(source_Amount, dest_Amount);
		  }
		  
		  if(emi_Months<1 || emi_Months>70 )
		  {
			  TakesScreenshot ss_Emi = (TakesScreenshot) driver;
			  File source_Emi = ss_Emi.getScreenshotAs(OutputType.FILE);
			  File dest_Emi = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Loans_EmiMonths.png");
			  Thread.sleep(1000);
			  FileUtils.copyFile(source_Emi, dest_Emi);
		  }
		  driver.findElement(By.id("body_cph_Loans_btnReset")).click();
		  Thread.sleep(2000);
		  wb.close();
	  }
		
	  
	  //manually selecting a value from drop down and calculating the emi
	  WebElement dd_LoanType = driver.findElement(By.id("body_cph_Loans_ddlLoanType"));
	  Select s = new Select(dd_LoanType);
	  s.selectByIndex(2);
	  
	  
	  WebDriverWait wait1=new WebDriverWait(driver,30);
	  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Loans_ddlLoanName\"]/option[2]")));
	  
	 
	  WebElement dd_LoanName = driver.findElement(By.id("body_cph_Loans_ddlLoanName"));
	  Select s1 = new Select(dd_LoanName);
	  s1.selectByIndex(2);
	  
	  Thread.sleep(2000);
	  
	  driver.findElement(By.id("body_cph_Loans_txtReqLoanAmount")).sendKeys("100000");
	  Thread.sleep(1000);
	  
	  driver.findElement(By.id("body_cph_Loans_txtNoOfEMI")).sendKeys("14");
	  Thread.sleep(1000);
	  
	  driver.findElement(By.id("body_cph_Loans_btnViewEMI")).click();
	  Thread.sleep(1000);
	  
	  //Taking screen shot of the emi that is calculated
	  TakesScreenshot ss_Emi = (TakesScreenshot) driver;
	  File source1 = ss_Emi.getScreenshotAs(OutputType.FILE);
	  File dest1 = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Emi_Amount.png");
	  Thread.sleep(1000);
	  FileUtils.copyFile(source1, dest1);
	  
	  Thread.sleep(1000);
	  
	  
	  //Clicking on the view re-payment Schedule 
	  driver.findElement(By.xpath("//*[@id=\"body_cph_Loans_lbViewRepayment\"]")).click();
	  Thread.sleep(1000);
	  
	  
	  //Hovering to the respective view re-payment schedule table
	  WebElement hover = driver.findElement(By.id("body_cph_Loans_gvRepayment"));
	  Actions act = new Actions(driver);
	  act.moveToElement(hover).perform();
	  
	  //Taking the screen shot of view re-payment schedule table
	  TakesScreenshot ss_Repay = (TakesScreenshot) driver;
	  File source_Repay = ss_Repay.getScreenshotAs(OutputType.FILE);
	  File dest_Repay = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Emi_Repay.png");
	  Thread.sleep(1000);
	  FileUtils.copyFile(source_Repay, dest_Repay);
	  Thread.sleep(2000);
	  
  }
 
  //Test case for loan request status
  //selecting the type of status and taking the screen shot of the page
  @Test(priority = 5)
  public void loan_Request_Status() throws Exception
  {
	  //selecting the drop down menu of loan request status 
	  driver.findElement(By.xpath("//*[@id=\"body_pnlCustomer_SubMenu\"]/div/div/ul/li[3]/a")).click();
	  Thread.sleep(1000);
	  
	  WebElement dropdownElement = driver.findElement(By.id("body_cph_Loans_ddlStatus"));
	  Select dropdown = new Select(dropdownElement);
	  List<WebElement> options = dropdown.getOptions();
	  
	  //To select all the types from the drop down menu
	  for (int i=0;i<=4;i++) {
		  
		  //Explicit wait for the web driver to find out web element drop down
		  WebDriverWait wait = new WebDriverWait(driver,30);
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"body_cph_Loans_ddlStatus\"]/option[2]")));
		  
		  WebElement dropdown_loan = driver.findElement(By.xpath("//*[@id=\"body_cph_Loans_ddlStatus\"]"));
		  Select s = new Select(dropdown_loan);
		  s.selectByIndex(i);
		  Thread.sleep(2000);
		   
		  //Screen shot for the status type all
		  if(i==0) {
			  TakesScreenshot ss_All = (TakesScreenshot) driver;
			  File source_All = ss_All.getScreenshotAs(OutputType.FILE);
			  File dest_All = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\All_status.png");
			  Thread.sleep(1000);
			  FileUtils.copyFile(source_All, dest_All);
			  Thread.sleep(2000);
		  }
		  
		  //Screenshot for the status type Approved
		  else if(i==1){
			  Thread.sleep(2000);
			  TakesScreenshot ss_Approved = (TakesScreenshot) driver;
			  File source_Approved = ss_Approved.getScreenshotAs(OutputType.FILE);
			  File dest_Approved = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Approved_status.png");
			  Thread.sleep(1000);
			  FileUtils.copyFile(source_Approved, dest_Approved);
			  Thread.sleep(2000);
		  }
		  
		  //Screenshot for the status type pending
		  else if(i==2)
		  {	
			  Thread.sleep(2000);
			  TakesScreenshot ss_Pending = (TakesScreenshot) driver;
			  File source_Pending = ss_Pending.getScreenshotAs(OutputType.FILE);
			  File dest_Pending = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Pending_status.png");
			  Thread.sleep(1000);
			  FileUtils.copyFile(source_Pending, dest_Pending);
			  Thread.sleep(2000);
		  }
		  //Screenshot for the status type Rejected
		  else if(i==3)
		  {
			  Thread.sleep(2000);
			  TakesScreenshot ss_Rejected = (TakesScreenshot) driver;
			  File source_Rejected = ss_Rejected.getScreenshotAs(OutputType.FILE);
			  File dest_Rejected = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Rejected_status.png");
			  Thread.sleep(1000);
			  FileUtils.copyFile(source_Rejected, dest_Rejected);
			  Thread.sleep(2000);
		  }
		  
		  //screenshot for the status type withdrawn
		  else
		  {
			  Thread.sleep(2000);
			  TakesScreenshot ss_wdrawn = (TakesScreenshot) driver;
			  File source_wdrawn = ss_wdrawn.getScreenshotAs(OutputType.FILE);
			  File dest_wdrawn = new File("C:\\Users\\parchuri.kumar\\Music\\Capstone_1324887\\Screenshot\\Withdrawn_status.png");
			  Thread.sleep(1000);
			  FileUtils.copyFile(source_wdrawn, dest_wdrawn);
			  Thread.sleep(2000);
		  }
				  
	  }    
	  //Upon successful completion of all tasks the user logout from the account
	  driver.findElement(By.linkText("Deposit")).click();
	  Thread.sleep(1000);
	  
	  
	  
}
  
  
  @BeforeClass
  @Parameters({"browserPath","url"})
  public void beforeClass(String bp,String url) {
	  
	  //passing the chrome driver and url using xml
	  System.setProperty("webdriver.chrome.driver",bp);
	  //initializing the chrome driver 
	  driver = new  ChromeDriver();
	  //navigating to the given url
	  driver.get(url);
	  //to maximize the chrome window
	  driver.manage().window().maximize();
	  //initializing the implicit wait 
	  driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
  }
  
  @AfterClass
  public void afterClass() {
	  
	  //To close the web browser upon completion of all tests
	  driver.close();
  }

}
