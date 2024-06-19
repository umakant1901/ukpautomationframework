package selenium;

import org.checkerframework.checker.initialization.qual.Initialized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class InstanseAccess {

	public WebDriver driver;
	static JavascriptExecutor jsDriver;
	static String browser;
	static int waitTime;
	public final int TIMEOUT=10*60*1000;
	
	public InstanseAccess() {
		driver=WebDriverManager.getInstanse().getDriver();
		jsDriver=(JavascriptExecutor)driver;
	}
	public void initialize()
	{
		System.out.println("Initialize in progress...!");
		if(WebDriverManager.getInstanse().getDriver()==null)
		{
			WebDriverManager.getInstanse().createLocalDriver();
		}
	}
	public String getBrowser() {
		return browser;
	}
	public void closeDriver() {
		driver.close();
		driver.quit();
	}

	public WebDriver getDriver()
	{
		return driver;
	}
	
	public JavascriptExecutor getJSDriver() {
		return jsDriver;
	}
	
}
