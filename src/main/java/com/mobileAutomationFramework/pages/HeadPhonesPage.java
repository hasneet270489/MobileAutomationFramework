package com.mobileAutomationFramework.pages;

import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HeadPhonesPage {
	
	AndroidDriver<MobileElement> adriver;
	ExtentTest test;
	
	
	@AndroidFindBy(id = "com.flipkart.android:id/search_icon")
	public AndroidElement search_icon;
	
	public HeadPhonesPage(AndroidDriver<MobileElement> adriver, ExtentTest test) {
		this.adriver= adriver;
		this.test=test;
		PageFactory.initElements(new AppiumFieldDecorator(adriver), this);
		}

}
