package com.yeasin.appium_qms.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import io.appium.java_client.AppiumDriver;

public class AppiumUtils {
	public String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException
	{
		//1. capture and place in folder 
		//2. extent report pick file and attach to report
		File source = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\src\\test\\java\\com\\yeasin\\appium_qms\\reports\\" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}
}
