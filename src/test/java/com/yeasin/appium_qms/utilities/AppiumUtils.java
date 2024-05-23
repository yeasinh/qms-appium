package com.yeasin.appium_qms.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import io.appium.java_client.AppiumDriver;

public class AppiumUtils {
	public String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException {
		// Capture the screenshot
		File source = driver.getScreenshotAs(OutputType.FILE);
		// Define the destination path
		String destinationFile = System.getProperty("user.dir") + "\\src\\test\\java\\com\\yeasin\\appium_qms\\reports\\" + testCaseName + ".png";
		// Save the screenshot
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}
}
