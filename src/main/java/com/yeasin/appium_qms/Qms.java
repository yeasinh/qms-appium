package com.yeasin.appium_qms;

import java.net.URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class Qms {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		UiAutomator2Options options = new UiAutomator2Options();
		
		// Set the AppiumOptions for the Android emulator/device
        options.setDeviceName("emulator-5554");
	    options.setApp("D:\\QMS\\qms-24.01.30.apk");
	    
	    URL url = new URL("http://172.17.8.14:4723");
	    AndroidDriver driver = new AndroidDriver(url, options);
	    
	    // Wait for the app to load
        Thread.sleep(5000);
	}
}
