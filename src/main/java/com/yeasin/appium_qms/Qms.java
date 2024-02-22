package com.yeasin.appium_qms;

import java.net.URL;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class Qms {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		// Set the device and the app
		UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("emulator-5554");
	    options.setApp("D:\\QMS\\qms-24.02.15.apk");
	    
	    // Set the driver
	    AndroidDriver driver = new AndroidDriver(new URL("http://172.17.8.14:4723"), options);
        
        // Log in to QMS app with username = Test and password = test
        WebElement email = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Email\"]"));
        email.sendKeys("Test");
        WebElement password = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Password\"]"));
        password.sendKeys("test");
        WebElement login = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Login\"]"));
        login.click();
        Thread.sleep(200000);
        
        // Click on the side menu
        WebElement menu = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
        menu.click();
        Thread.sleep(5000);
        
        // Sync web data
        WebElement sync = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Sync\"]"));
        sync.click();
        System.out.println(sync.getText());
        Thread.sleep(60000);
        
        // Access Settings module
        WebElement settings = driver.findElement(By.xpath("//android.widget.Button[@content-desc=', Settings']"));
        settings.click();
        System.out.println(settings.getText());
        
        // Select Line
        WebElement line = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\", Root\"]/android.view.ViewGroup"));
        line.click();
        WebElement selectedLine = driver.findElement(By.xpath("(//android.widget.TextView[@text=\"App Test 101\"])[2]"));
        selectedLine.click();
        
        // Select Input Delay
        WebElement delay = driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"5 Seconds\"])[1]"));
        delay.click();
        WebElement selectedDelay = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[5]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]"));
        selectedDelay.click();
        
        // Continue with selected Line and Input Delay
        WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
        next.click();
        
        // Access Home module
        WebElement home = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"End table check, (Sewing), \"]"));
        home.click();
        System.out.println(home.getText());
        
        // Access QC Lunch Pad
        WebElement lunchPad = driver.findElement(By.xpath("//android.widget.TextView[@text=\"QC Lunch Pad\"]"));
        System.out.println(lunchPad.getText());
        WebElement endTable = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"End table check, (Sewing), \"]"));
        System.out.println(endTable.getText());
        endTable.click();
        
        // Select Buyer, Style, and Order
        WebElement selectedBuyer = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"New york\"]"));
        selectedBuyer.click();
        WebElement selectedStyle = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"5670\"]"));
        selectedStyle.click();
        WebElement selectedOrder = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"CR Test order\"]"));
        selectedOrder.click();
        WebElement Orderokay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
        Orderokay.click();
        
        // Input data from Production Entry page
        
	    // Close the app
        driver.quit();
	}
}
