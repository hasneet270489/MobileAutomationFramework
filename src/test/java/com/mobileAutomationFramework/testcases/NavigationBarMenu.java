package com.mobileAutomationFramework.testcases;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mobileAutomationFramework.base.BaseDriverClass;
import com.mobileAutomationFramework.pages.HomePage;
import com.mobileAutomationFramework.pages.MemoryCardsPage;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class NavigationBarMenu extends BaseDriverClass {
	
	AndroidDriver<MobileElement> adriver;
	
	
	@Test
	public void search_text() throws InterruptedException {
		try {
			adriver=initiate();
		} catch (MalformedURLException e) {
	     System.out.println(e.getMessage());
		}
	    test = rep.startTest("search_text");
		test.log(LogStatus.INFO, "search_text-Test is starting");
		HomePage homepage = new HomePage(adriver, test);
		waitForElement(adriver,homepage.navBar);
		click(homepage.search_bar);
		waitForElement(adriver, homepage.autoComplete_text_box);
		test.log(LogStatus.INFO, "Entering text in search box");
		type(homepage.autoComplete_text_box, "Hello Hasneet");
		test.log(LogStatus.INFO, "Entering text in search box");
		test.log(LogStatus.PASS, "Test Passed");
		System.out.println("Launching appp");
	}

	
	@Test
	public void validateNavigationMenu() {
		try {
			adriver=initiate();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		test = rep.startTest("validateNavigationMenu");
		test.log(LogStatus.INFO, "search_text-Test is starting");
		HomePage homepage = new HomePage(adriver, test);
		waitForElement(adriver,homepage.navBar);
		homepage.openNavigationBar(adriver);
		homepage.scrollTo(adriver,"Mobiles");
		Object resultPage= homepage.navigatingMenus("Electronics|Mobile Accessories|Headphones",adriver);
		Assert.assertTrue(resultPage!=null, "Could not load memory card section");
		if(!(resultPage instanceof MemoryCardsPage))
			Assert.fail("Result page was not memory card page");
		
		test.log(LogStatus.INFO, "Reached memory card page");
		MemoryCardsPage memoryCardPage = new MemoryCardsPage(adriver, test);
		click(memoryCardPage.filter);
		
		
		}
	}

