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
        
        // Log in to QMS app with username = Test and password = test
        WebElement email = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Email\"]"));
        email.sendKeys("Test");
        WebElement password = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Password\"]"));
        password.sendKeys("test");
        WebElement login = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Login\"]"));
        login.click();
        Thread.sleep(10000);
        
        // Click on the side menu
        WebElement menu = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
        menu.click();
        Thread.sleep(5000);
        
        // Check the texts on menu container
        WebElement userName = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Text\"]"));
        System.out.println(userName.getText());
        WebElement userEmail = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Text@gmail.com\"]"));
        System.out.println(userEmail.getText());
        WebElement appVersion = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Version : v.2.0.2-ALPHA0\"]"));
        System.out.println(appVersion.getText());
        WebElement deviceID = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Device ID: 01a6ccc03cbb7a60\"]"));
        System.out.println(deviceID.getText());
        Thread.sleep(2000);
        
        // Sync web data
        WebElement sync = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Sync\"]"));
        sync.click();
        System.out.println(sync.getText());
        Thread.sleep(10000);
        
        // Access Settings module
        WebElement settings = driver.findElement(By.xpath("//android.widget.Button[@content-desc=', Settings']"));
        settings.click();
        System.out.println(settings.getText());
        Thread.sleep(10000);
        
        // Select Line
        WebElement line = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\", Root\"]/android.view.ViewGroup"));
        line.click();
        Thread.sleep(10000);
        WebElement selectedLine = driver.findElement(By.xpath("//android.widget.TextView[@text=\"App QC Test 1\"]"));
        selectedLine.click();
        
        // Select Input Delay
        WebElement delay = driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"5 Seconds\"])[1]"));
        delay.click();
        Thread.sleep(10000);
        WebElement selectedDelay = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[5]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]"));
        selectedDelay.click();
        Thread.sleep(10000);
        
        // Continue with selected Line and Input Delay
        WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
        next.click();
        Thread.sleep(10000);
	}
}
