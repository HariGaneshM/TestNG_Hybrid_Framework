package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utilities.Configs;

public class BaseTest {
	
	public static String env;
	
	private static ThreadLocal<WebDriver> threadSafeDriver = new ThreadLocal<WebDriver>();
	
	public static WebDriver getDriver() {
		
		return threadSafeDriver.get();
	}
	
	@BeforeMethod
	public void setDriver() {
		
		Configs.readConfigs();
		
		String browser = System.getProperty("browser", Configs.getProperty("browser"));
		env = System.getProperty("env", Configs.getProperty("env"));
		
		WebDriver driver = null;
		
		if (browser.equalsIgnoreCase("edge")) {
			
			driver = new EdgeDriver();
		}
		else if (browser.equalsIgnoreCase("chrome")) {
			
			driver = new ChromeDriver();
		}
		else {
			
			System.out.println("Invalid browser value: "+browser);
		}
		threadSafeDriver.set(driver);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}
	
	public void goToPage(String url) {
		
		getDriver().get(url);
	}
	
	public void navigateToPage(String url) {
		
		getDriver().navigate().to(url);
	}
	
	@AfterMethod
	public void closeDriver(ITestResult result) {
		
		getDriver().quit();
		threadSafeDriver.remove();
    }
}
