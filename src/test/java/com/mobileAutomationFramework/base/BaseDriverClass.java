package com.mobileAutomationFramework.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.mobileAutomationFramework.utilities.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


public class BaseDriverClass {
	
	public static WebDriver driver;
	public static AndroidDriver<MobileElement> adriver;
	public static AppiumDriverLocalService service;
	public static AppiumServiceBuilder builder;
	public static DesiredCapabilities cap;
	public static File app;
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;	
	public WebDriverWait wait;
	public SoftAssert soft;
	
	
	@BeforeSuite
	public AndroidDriver<MobileElement> initiate() throws MalformedURLException, InterruptedException {
		
		//Desired Capabilities
		System.out.println("Setting desired caps");
		app = new File(System.getProperty("user.dir")+"//Flipkart Online Shopping App_v6.17_apkpure.com.apk");
		cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		cap.setCapability(MobileCapabilityType.UDID, "emulator-5554");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiAutomator2");
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION,"9.0");
		cap.setCapability("appActivity", "com.flipkart.android.SplashActivity");
		cap.setCapability("appPackage", "com.flipkart.android");
	    cap.setCapability("app", app.getAbsolutePath());
		cap.setCapability("noReset", true);
		cap.setCapability("autoGrantPermissions", "true");
		cap.setCapability("autoAcceptAlerts", "true");
		//cap.setCapability("chromedriverUseSystemExecutable", true);
		
		test=rep.startTest("initiating driver");
		test.log(LogStatus.INFO, "Driver initiating");
		// Building Appium service 
		builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		builder.withCapabilities(cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"info");
		
		
// start appium service with required paramters
		service = AppiumDriverLocalService.buildService(builder);
		boolean serverStatus =checkIfServerIsRunnning(4723);
		if(serverStatus==false) {
		service.start();
		System.out.println("Start appium service");
		}
		else {
			System.out.println("Server is already running");
		}
		test.log(LogStatus.INFO, "Launching app");
		adriver = new AndroidDriver<MobileElement>(service,cap);
	    System.out.println("Launch app");
	    test.log(LogStatus.INFO, "App Launched");
	    return adriver;
	}
	
	
	
	public static boolean checkIfServerIsRunnning(int port) {

	    boolean isServerRunning = false;
	    ServerSocket serverSocket;
	    try {
	        serverSocket = new ServerSocket(port);
	        serverSocket.close();
	    } catch (IOException e) {
	        //If control comes here, then it means that the port is in use
	        isServerRunning = true;
	    } finally {
	        serverSocket = null;
	    }
	    return isServerRunning;
	}
	
	@AfterMethod
	public void stopServer() {
		    //Runtime runtime = Runtime.getRuntime();
//		    try {
//		        runtime.exec("taskkill /F /IM node.exe");
//		        runtime.exec("taskkill /F /IM cmd.exe");
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
		    
		    if(adriver!=null) {
				adriver.quit();
				System.out.println("Shutting down driver");
			}
		    
		    if(rep!=null) {
		    	rep.endTest(test);
		    	rep.flush();
		    	System.out.println("Creating report");
		    }
		}
	
	public void waitForElement(AndroidDriver<MobileElement> adriver, AndroidElement element) {
		wait = new WebDriverWait(adriver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	
	public void takeScreenshot() {
	  Date d = new Date();
	  String screenshotFile=d.toString().replace(":", "_").replace(" ", "_");
	  String filePath= System.getProperty("user.dir")+"//Screenshots"+screenshotFile;
	  File srcFile= ((TakesScreenshot)adriver).getScreenshotAs(OutputType.FILE);
	  try {
		FileUtils.copyDirectory(srcFile, new File(filePath));
	} catch (IOException e) {
		e.printStackTrace();
	}
	  //adding screenshot to report
	  
	  test.log(LogStatus.INFO, "Snapshot: ("+screenshotFile+")"+test.addScreenCapture(filePath));
	
	}
	
	public void type(AndroidElement element, String data) {
		
		element.sendKeys(data);
	}
	
	public void click(AndroidElement element) {
		element.click();
	}
	
	public void waitUntil(String xpath, String type) {
		if (type.trim().toLowerCase().equals("visible")) {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				//dataCollector.setTimeoutValue(0);
			} catch (NoSuchElementException noSuchElementException) {
				soft.fail("fail to display a webelement with xpath: " + xpath);
				//noSuchElementException.printStackTrace();
			} catch (TimeoutException tOut) {
				soft.fail("Timed Out waiting for visibility of webelement with xpath: " + xpath);

			}
		} else if (type.trim().toLowerCase().equals("clickable")) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			} catch (NoSuchElementException noSuchElementException) {
				soft.fail("fail to click a webelement with xpath: " + xpath);

			} catch (TimeoutException tOut) {
				soft.fail("Timed Out waiting for clickablility of webelement with xpath: " + xpath);
			}
		} else if (type.trim().toLowerCase().equals("invisible")) {
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
			} catch (NoSuchElementException noSuchElementException) {
				soft.fail("fail to wait for a webelement with xpath: " + xpath + " to disappear");
				//noSuchElementException.printStackTrace();
			} catch (TimeoutException tOut) {
				soft.fail("Timed Out waiting for invisibility of webelement with xpath: " + xpath);

			}
		} else if (type.trim().toLowerCase().equals("all")) {
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
			} catch (NoSuchElementException noSuchElementException) {
				soft.fail("fail to wait for all webelement to be visible with xpath: " + xpath);

			} catch (TimeoutException tOut) {
				soft.fail("Timed Out waiting for all webelements to be visible with xpath: " + xpath);

			}
		} else if (type.trim().toLowerCase().equals("present")) {
			try {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
			} catch (NoSuchElementException noSuchElementException) {
				soft.fail("fail to wait for all webelement to present with xpath: " + xpath);
				noSuchElementException.printStackTrace();
			} catch (TimeoutException tOut) {
				soft.fail("Timed Out waiting for all webelements to present with xpath: " + xpath);

			}
		}
	}

}