package com.mobileAutomationFramework.pages;

import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MemoryCardsPage {
	
    AndroidDriver<MobileElement> adriver;
    ExtentTest test;
    
    
	public MemoryCardsPage(AndroidDriver<MobileElement> adriver, ExtentTest test) {
		this.adriver=adriver;
		this.test=test;
		PageFactory.initElements(new AppiumFieldDecorator(adriver), this);
		}
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='CONTINUE']")
	public AndroidElement accept_Popup;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Memory Cards']")
	public AndroidElement memorycard_label;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Filter']")
	public AndroidElement filter;
	
	
	
	
}
