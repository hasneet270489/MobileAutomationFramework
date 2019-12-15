package com.mobileAutomationFramework.utilities;

import java.awt.DisplayMode;
import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;



public class ExtentManager {
	
	private static ExtentReports extent;
	
	public static ExtentReports getInstance() {
		Date date = new Date();
		String fileName=date.toString().replace(":", "_").replace(" ", "_")+".html";
		if(extent==null) {
			extent = new ExtentReports(System.getProperty("user.dir")+"\\Reports\\"+fileName,true, DisplayOrder.NEWEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\src\\test\\java\\com\\mobileAutomationFramework\\utilities\\ExtentReportConfig.xml"));
			extent.addSystemInfo("Appium Framework", "7.0").addSystemInfo("Environment", "QA");
			
		}
		return extent;
	}

}
