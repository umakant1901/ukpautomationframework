package com.ukp.at.managers;

import java.net.URL;
import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.ukp.at.enums.DriverType;

public class WebDriverManager {
	
	private WebDriver driver;
	private DriverType driverType;
	static WebDriverManager getDriverObj;
	private final String CHROME_DRIVER_PROPERTY="webdriver.chrome.driver";
	
	public WebDriverManager() {
		driverType=FileReaderManager.getInstance().getConfigReader().getBrowser();
	}
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public WebDriver createDriver()
	{
		driver= createLocalDriver();
		return driver;
	}
	
	
	public static WebDriverManager getInstance() {
		if(getDriverObj==null)
		{
			getDriverObj=new WebDriverManager();
		}
		return getDriverObj;
	}
	
	public WebDriver createLocalDriver()
	{
		switch(driverType) {
		case CHROME:
			ChromeOptions wdOptions=new ChromeOptions();
			wdOptions.addArguments("test-type");
			wdOptions.addArguments("--disable-notifications");
			wdOptions.addArguments("disable-extensions");
			wdOptions.addArguments("disable-popup-blocking");
			wdOptions.addArguments("--no-sandbox");
			wdOptions.setCapability("applicationCacheEnabled",false);
			wdOptions.setCapability("acceptSslCerts", true);
			wdOptions.setCapability("acceptInsecureCerts", true);
			wdOptions.addArguments("--ignore-certificate-errors");
			wdOptions.addArguments("start-maximized");
			wdOptions.addArguments("--js-flags=--expose-gc");
			wdOptions.addArguments("--enable-precise-memory-info");
			wdOptions.addArguments("--disable-default-apps");
			wdOptions.addArguments("test-type=browser");
			wdOptions.addArguments("disable-infobars");
			wdOptions.addArguments("--allow-running-insure-content");
			wdOptions.addArguments("--remote-allow-origins=*");
			wdOptions.setExperimentalOption("useAutomationExtension", false);
			wdOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			wdOptions.addArguments("--disable-dev-shm-usage");
			wdOptions.addArguments("--incognito");
			System.setProperty(CHROME_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getDriverPath());
			driver =new ChromeDriver(wdOptions);
			break;
			
		case EDGE:
			driver=new EdgeDriver();
			break;
		
		case REMOTECHROME:
			ChromeOptions chromeOptions=new ChromeOptions();
			chromeOptions.setCapability("UseChromium", true);
			chromeOptions.addArguments("test-type");
			chromeOptions.addArguments("--incognito");
			chromeOptions.addArguments("disable-extensions");
			chromeOptions.setCapability("InPrivate", true);
			chromeOptions.addArguments("disable-popup-blocking");
			chromeOptions.setCapability("applicationCacheEnabled", false);
			chromeOptions.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			chromeOptions.addArguments("disable-infobars");
			try {
				driver=new RemoteWebDriver(new URL("http://selenium-grid-4-hub-cto-cate-testautomation-163333.apps.namtigtd22d.ecs.dyn.nsroot.net/wd/hub"),chromeOptions);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		
		if(FileReaderManager.getInstance().getConfigReader().getWindowBrowserSize())
		{
			driver.manage().window().maximize();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait()));
		return driver;
	}
	
}
