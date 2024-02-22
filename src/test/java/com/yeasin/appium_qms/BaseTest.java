package com.yeasin.appium_qms;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {
	public AppiumDriverLocalService service;
	public AndroidDriver driver;
	
	@BeforeClass
	public void configure_appium() throws MalformedURLException {
		// build and start the server
		service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\1600000205\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
						.withIPAddress("172.17.8.14").usingPort(4723).build();
		service.start();
				
		// set the device and the app
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("intellier-tab");
		options.setApp("D:\\QMS\\qms-24.02.20.apk");
				
		// set the driver
		driver = new AndroidDriver(new URL("http://172.17.8.14:4723"), options);
	}
	
	@AfterClass
	public void close_appium() {
		// close the driver
		driver.quit();
		
		// close the server
		service.stop();
	}
}
