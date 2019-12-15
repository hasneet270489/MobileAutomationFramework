package com.mobileAutomationFramework.pages;

import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class NavigationBarMenu {
	
	AndroidDriver<MobileElement> adriver;
	  ExtentTest test;
	  
	
	public NavigationBarMenu(AndroidDriver<MobileElement> adriver, ExtentTest test) {
		this.adriver=adriver;
		this.test=test;
		PageFactory.initElements(new AppiumFieldDecorator(adriver), this);
		}
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Electronics']")
	public AndroidElement Electronics_Menu;
	
	
	public void navigatingMenus() {
		
	}

}
