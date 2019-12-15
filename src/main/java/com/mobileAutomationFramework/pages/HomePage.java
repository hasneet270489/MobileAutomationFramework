package com.mobileAutomationFramework.pages;

import java.awt.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage {
	
  AndroidDriver<MobileElement> adriver;
  ExtentTest test;
  WebDriverWait wait;
  TouchActions action;
  
//  @FindBy(xpath ="//android.widget.ImageButton[@content-desc='Open Drawer']")
//  public AndroidElement navigationBarMenu;
  
  @AndroidFindBy(accessibility ="Open Drawer")
  public AndroidElement navBar;
  
  @AndroidFindBy(id = "com.flipkart.android:id/search_widget_textbox")
  public AndroidElement search_bar;
  
  @AndroidFindBy(id = "com.flipkart.android:id/in_app_notification_bell")
  public AndroidElement notification_bell;
  
  @AndroidFindBy(id = "com.flipkart.android:id/cart_bg_icon")
  public AndroidElement cart_icon;
  
  @AndroidFindBy(id = "com.flipkart.android:id/search_autoCompleteTextView")
  public AndroidElement autoComplete_text_box;  
  
  @AndroidFindBy(xpath = "//android.widget.TextView[@text='Mobiles']")
  public AndroidElement mobileSection;
  
  @AndroidFindBy(xpath = "//android.widget.TextView[@text='Electronics']")
  public AndroidElement ElectronicsSection;
  
  

  
  
  
  public HomePage(AndroidDriver<MobileElement> adriver,ExtentTest test) {
	  this.adriver=adriver;
	  this.test=test;
	  PageFactory.initElements(new AppiumFieldDecorator(adriver), this);
	  }
	
	
	public HomePage openNavigationBar(AndroidDriver<MobileElement> adriver) {
		test.log(LogStatus.INFO, "Selecting from navigation menu");
		//navigationBarMenu.click();
		adriver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Open Drawer']")).click();
        test.log(LogStatus.INFO,"Selected navigation menu");
        return new HomePage(adriver, test);
        }


	public Object navigatingMenus(String navigationItems, AndroidDriver<MobileElement> adriver) {
		wait= new WebDriverWait(adriver, 30);
		String[] items= navigationItems.split("\\|");
		for(int i=0;i<items.length;i++) {
		   adriver.findElement(By.xpath("//android.widget.TextView[@text='"+items[i]+"']")).click();
		   if(i<(items.length)-1)
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[contans(@text,'"+items[i+1]+"']")));
		}
		test.log(LogStatus.INFO, "Selected items from the menu");
		wait= new WebDriverWait(adriver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.flipkart.android:id/title_action_bar")));
		if(adriver.findElement(By.xpath("//android.widget.TextView[@text='Memory Cards']")).isDisplayed()) {
			MemoryCardsPage memoryCardPage= new MemoryCardsPage(adriver,test);
			return memoryCardPage;
		}
		return null;
		}

	
	public void scrollTo(AndroidDriver<MobileElement> adriver, String text) {
		adriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Mobiles\"))");
		
		
	}
}
