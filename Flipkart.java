package task1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class Flipkart {
	WebDriver driver;
	String url = "https://www.flipkart.com/";
	
  @Test
  public void f() throws Exception {
	  WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div/div/div[1]/div/div[1]/div/div[1]/div[1]/header/div[1]/div[2]/form/div/div/input"));
	  searchBox.sendKeys("Samsung Galaxy S10");
	  searchBox.submit();
	  
	  
	  //Mobiles category
	  driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[1]/div/div/div[1]/div[3]/a")).click();
	  
	  //samsung brand selection
	  driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div/div[1]/div/section[3]/div[2]/div/div/div/label/div[1]")).click();
	  
	  Thread.sleep(1000);
	  //Flipkart Assured
	  driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div/div[1]/div/div[1]/div/section[4]/label/div[1]")).click();
	  Thread.sleep(5000);
	  //sort by high to low
	  driver.findElement(By.xpath("/html/body/div/div/div[3]/div/div[2]/div[1]/div/div/div[3]/div[3]")).click();
	  Thread.sleep(200);
	  
	  List<WebElement> products = driver.findElements(By.xpath("//div[@class = 'col col-7-12']"));
	  Thread.sleep(500);
	  //Printing the product details
	  for ( WebElement product : products ) {
		  String productName = product.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div/div[2]/div[2]/div/div/div/a/div[2]/div[1]/div[1]")).getText();
		  String displayPrice = product.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div/div[2]/div[2]/div/div/div/a/div[2]/div[2]/div[1]/div[1]/div[1]")).getText();
		  String productLink = product.getAttribute("href");
		  
		  System.out.println("Product Name:" + productName);
		  System.out.println("Display Proce:" + displayPrice);
		  System.out.println("Product Link: " + productLink);
 	  }
  }
  @BeforeClass
  public void beforeClass() {
	  //Initializing Chrome Driver
	  System.setProperty("webdriver.chrome.driver","C:\\Users\\parchuri.kumar\\Desktop\\Selenium_Accelerate\\chromedriver.exe");
	  driver = new ChromeDriver();
	  //Navigating to the URL 
	  driver.get(url);
	  //Maximize the window
	  driver.manage().window().maximize();
	  //Implicit Wait for the driver
	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
  }

  @AfterClass
  public void afterClass() {
	//  driver.close();
  }

}
