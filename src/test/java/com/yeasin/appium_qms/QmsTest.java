package com.yeasin.appium_qms;

import java.net.URL;
import java.time.Duration;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

import com.yeasin.appium_qms.Qms;

public class QmsTest {
	public static AppiumDriverLocalService service;
	public static AndroidDriver driver;
	public static int inputDelay = 5;
	public static int iteration = 1;
	
	@BeforeTest
	public static void setup() throws MalformedURLException {
		// build and start the server
		// service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\1600000205\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")).withIPAddress("172.17.8.14").usingPort(4723).build();
		// service.start();
		
		// set the device
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("intellier-tab");
					
		// set the driver
		driver = new AndroidDriver(new URL("http://172.17.8.14:4723"), options);
				    
		try {
			// if the app is already installed, just open it without reinstalling
			driver.activateApp("com.nidleqms");

		} catch(Exception e) {
			// set the app
			options.setApp("D:\\APK\\qms-24.03.11.apk");
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	@BeforeClass
	public static void login() {
		// log in to qms app with username = Test and password = test
		WebElement username = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Username\"]"));
		username.sendKeys("Test");
		WebElement password = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Password\"]"));
		password.sendKeys("test");
		WebElement login = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Login\"]"));
		login.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(5));
	}
	
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
	public static void set_line() throws InterruptedException {
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
            Thread.sleep(1000);
            
            // select an input delay
            WebElement delay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"5 Seconds\"]"));
            delay.click();
            WebElement selectedDelay = driver.findElement(By.xpath("(//android.widget.TextView[@text=\"5 Seconds\"])[3]"));
            selectedDelay.click();
            Thread.sleep(1000);
            
            // continue with selected line and input delay
            WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
            next.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
        } catch (Exception e) {
        	// continue with previously selected line and input delay
        	WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
            next.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
	}
	
	@Test(priority = 4)
	public static void select_order() {
		try {
			// access home module
            WebElement home = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", Home\"]"));
            home.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
		} catch(Exception e) {
			// home module already entered
		}
		
        try {
        	// continue with previously selected buyer, style, and order
        	WebElement prevOrderComfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
        	prevOrderComfirm.click();
        	
        } catch(Exception e) {
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
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
	}
	
	@Test(priority = 5)
	public static void choose_variance() throws InterruptedException {
		try {
			// select a color
			WebElement color = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Choose a Color\"]"));
			color.click();
			WebElement selectedColor = driver.findElement(By.xpath("//android.widget.TextView[@text=\"BLACK\"]"));
			selectedColor.click();
			Thread.sleep(2000);
			
			// select a size
			WebElement size = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Choose a Size\"]"));
			size.click();
			WebElement selectedSize = driver.findElement(By.xpath("//android.widget.TextView[@text=\"L\"]"));
			selectedSize.click();
			Thread.sleep(2000);
		
		} catch (Exception e) {
			// continue with already selected color and size
		}
	}
	
	@Test(priority = 6)
	public static void pass() throws InterruptedException {
		// input pass data from production entry page
		WebElement pass = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Pass\"]"));
		pass.click();
		
		Thread.sleep((inputDelay+2)*1000);
	}
	
	@Test(priority = 7)
	public static void alter() throws InterruptedException {
		// input alter data from production entry page
		WebElement alter = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Alter\"]"));
		alter.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		enter_defect();
		
		Thread.sleep((inputDelay+2)*1000);
	}
	
	@Test(priority = 8)
	public static void reject() throws InterruptedException {
		// input reject data from production entry page
		WebElement reject = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Reject\"]"));
		reject.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		enter_defect();
		
		Thread.sleep((inputDelay+2)*1000);
	}
	
	@Test(enabled = false)
	public static void enter_defect() throws InterruptedException {
		try {
			// select a position on the sketch
			WebElement sketch = driver.findElement(By.xpath("//com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[3]"));
			sketch.click();
			Thread.sleep(5000);
					
			// select an operation
			WebElement operation = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup"));
			operation.click();
			Thread.sleep(2000);
			WebElement selectedOperation = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"APPIUM OPERATION\"]/android.view.ViewGroup"));
			selectedOperation.click();
			Thread.sleep(2000);
			
		} catch(Exception e) {
			// select an operation and continue
			WebElement selectedOperation = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"APPIUM OPERATION\"]/android.view.ViewGroup"));
			selectedOperation.click();
			Thread.sleep(2000);
		}
		
		// select a defect
		WebElement defect = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.view.ViewGroup"));
		defect.click();
		Thread.sleep(2000);
				
		// continue with the selected position and defect
		WebElement okay = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup"));
		okay.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	public static void force_sync() throws InterruptedException {
		// press force sync button
		WebElement fsButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\"]"));
		fsButton.click();
		
		Thread.sleep(5000);
	}
	
	@Test(enabled = false)
	public static void undo() throws InterruptedException {
		// press undo button
		WebElement undoButton = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup"));
		undoButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			
		// confirm undo from pop-up message
		WebElement undoConfirm = driver.findElements(AppiumBy.className("android.widget.Button")).get(1);
		undoConfirm.click();
		Thread.sleep(2000);
	}
	
	
	@Test(enabled = false)
	public static void production_entry() throws InterruptedException {
		inputDelay = 5;
		for(int i = 1; i <= iteration; i++) {
        	pass();
        	
        	if(i % 1 == 0) {
        		alter();
                reject();
        	}
        }
		
        force_sync();
	}

	@Test(priority = 9)
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
        	}
        }
        
        force_sync();
	}
	
	@AfterClass
	public static void logout() throws InterruptedException {
		// click on logout menu
		WebElement logout = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", logout\"]"));
		logout.click();
		
		Thread.sleep(5000);
	}
	
	@AfterTest
	public static void close_app() throws InterruptedException {
		// close the app and remove it from app history
		driver.terminateApp("com.nidleqms");
		
		// close the driver
        driver.quit();
        
        // stop the server
        // service.stop();
        
        Thread.sleep(5000);
	}
}
