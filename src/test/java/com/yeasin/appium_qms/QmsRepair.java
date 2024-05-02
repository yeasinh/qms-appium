package com.yeasin.appium_qms;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class QmsRepair extends QmsTest {
	public static AppiumDriverLocalService service;
	public static AndroidDriver driver;
	public static int inputDelay = 5;
	public static int iteration = 1;
	
	@Test(priority = 1)
	public static void side_menu() {
		// click on the side menu icon
		try {
			WebElement menu = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
			menu.click();
			
		} catch(Exception e) {
			WebElement menu = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup"));
	        menu.click();
		}
		
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	@Test(priority = 2)
	public static void sync_web() {
		// click on the sync button
        WebElement sync = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Sync\"]"));
        sync.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	@Test(priority = 3)
	public static void set_line() {
		// access settings module
        WebElement settings = driver.findElement(By.xpath("//android.widget.Button[@content-desc=', Settings']"));
        settings.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    	
        try {
        	// select a line
            WebElement line = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\", Root\"]/android.view.ViewGroup"));
            line.click();
            driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Appium Line\"));"));
            WebElement selectedLine = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Appium Line\"]/android.view.ViewGroup"));
            selectedLine.click();
            
            // select an input delay
            WebElement delay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"5 Seconds\"]"));
            delay.click();
            WebElement selectedDelay = driver.findElement(By.xpath("(//android.widget.TextView[@text=\"5 Seconds\"])[3]"));
            selectedDelay.click();
            
            // continue with selected line and input delay
            WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
            next.click();
            
        } catch (Exception e) {
        	// continue with previously selected line and input delay
        	WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
            next.click();
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	@Test(priority = 4)
	public static void select_order() {
        try {
        	// continue with previously selected buyer, style, and order
        	WebElement prevOrderComfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
        	prevOrderComfirm.click();
        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        	
        } catch(Exception e) {
        	// access home module
            WebElement home = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", Home\"]"));
            home.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
            // access qc lunch pad
            WebElement endTable = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"End table check, (Sewing), \"]"));
            endTable.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
            // select a buyer
            driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Appium Buyer\"));"));
            WebElement selectedBuyer = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Appium Buyer\"]/android.view.ViewGroup"));
            selectedBuyer.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
            // select a style
            WebElement selectedStyle = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Appium Style\"]"));
            selectedStyle.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
            // select an order
            WebElement selectedOrder = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Appium Order\"]"));
            selectedOrder.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
            // continue with selected buyer, style, and order
            WebElement orderOkay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
            orderOkay.click();
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	@Test(priority = 5)
	public static void choose_variance() throws InterruptedException {
		try {
			// select a color
			WebElement color = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Choose a Color\"]"));
			color.click();
			WebElement selectedColor = driver.findElement(By.xpath("//android.widget.TextView[@text=\"BLACK\"]"));
			selectedColor.click();
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			
			// select a size
			WebElement size = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Choose a Size\"]"));
			size.click();
			WebElement selectedSize = driver.findElement(By.xpath("//android.widget.TextView[@text=\"L\"]"));
			selectedSize.click();
		
		} catch (Exception e) {
			// continue with already selected color and size
		}
		
		Thread.sleep(2000);
	}
	
	@Test(priority = 6)
	public static void repair_mode_on() throws InterruptedException {
		// toggle the repair button to turn on repair mode
		WebElement repairOn = driver.findElement(By.xpath("//android.widget.Switch[@text=\"OFF\"]"));
		repairOn.click();
	}
	
	@Test(priority = 7)
	public static void production_entry() throws InterruptedException {
		for(int i = 1; i <= iteration; i++) {
        	pass();
        	
        	if(i % 1 == 0) {
        		alter();
                reject();
                force_sync();
        	}
        }
	}
	
	@Test(priority = 8)
	public static void entry_undo() throws InterruptedException {
		inputDelay = 2;
        for(int i = 1; i <= iteration; i++) {
        	pass();
        	undo();
        	
        	if(i % 1 == 0) {
        		alter();
        		undo();
                reject();
                undo();
                force_sync();
        	}
        }
	}
	
	@Test(priority = 9)
	public static void repair_mode_off() throws InterruptedException {
		// toggle the repair button to turn off repair
		WebElement repairOff = driver.findElement(By.xpath("//android.widget.Switch[@text=\"ON\"]"));
		repairOff.click();
	}
}
