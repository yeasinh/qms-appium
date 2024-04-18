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

public class Qms {
	public static AppiumDriverLocalService service;
	public static AndroidDriver driver;
	public static int delay = 5;

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
        setup();
        login();
        side_menu();
        sync_web();
        set_line();
        side_menu();
        select_order();
        choose_variance();
        
        for(int i = 1; i <= 50; i++) {
        	pass();
        	
        	if(i % 10 == 0) {
        		alter();
                reject();
                force_sync();
        	}
        }
        
        /*input_delay = 3;
        pass();
        undo();
        alter();
        undo();
        reject();
        undo();
        
        repair_mode();
        
        force_sync();
        
        side_menu();
        logout();
        close_app();*/
	}
	
	public static void setup() throws MalformedURLException {
		// build and start the server
		// service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\1600000205\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")).withIPAddress("172.17.8.14").usingPort(4723).build();
		// service.start();
				
		// set the device
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("intellier-tab");
		
		// set the app
		options.setApp("D:\\APK\\qms-24.03.11.apk");
			    
		// set the driver
		driver = new AndroidDriver(new URL("http://172.17.8.14:4723"), options);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	public static void login() {
		// log in to qms app with username = Test and password = test
		WebElement username = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Username\"]"));
		username.sendKeys("Test");
		WebElement password = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Password\"]"));
		password.sendKeys("test");
		WebElement login = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Login\"]"));
		login.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
	}
	
	public static void side_menu() {
		try {
			// click on the side menu
	        WebElement menu = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
	        menu.click();
	        
		} catch(Exception e) {
			// go back and then click on the side menu
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			WebElement menu = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
	        menu.click();
		}
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	public static void sync_web() {
		// sync web data
        WebElement sync = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Sync\"]"));
        sync.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	public static void set_line() {
		// access settings module
        WebElement settings = driver.findElement(By.xpath("//android.widget.Button[@content-desc=', Settings']"));
        settings.click();
    	
        try {
        	// select a line
            WebElement line = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\", Root\"]/android.view.ViewGroup"));
            line.click();
            driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Appium Line\"));"));
            WebElement selectedLine = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Appium Line\"]/android.view.ViewGroup"));
            selectedLine.click();
            
            // select an input delay
            WebElement delay = driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"5 Seconds\"])[1]"));
            delay.click();
            WebElement selectedDelay = driver.findElement(By.xpath("//android.widget.TextView[@text=\"2 Seconds\"]"));
            selectedDelay.click();
            
            // continue with selected line and input delay
            WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
            next.click();
            
        } catch (Exception e) {
        	WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
            next.click();
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	public static void select_order() {
		// access home module
        WebElement home = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", Home\"]"));
        home.click();
        
        try {
        	// continue with previously selected buyer, style, and order
        	WebElement prevOrderComfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
        	prevOrderComfirm.click();
        	
        } catch(Exception e) {
        	// access qc lunch pad
            WebElement endTable = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"End table check, (Sewing), \"]"));
            endTable.click();
            
            // select a buyer
            driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Appium Buyer\"));"));
            WebElement selectedBuyer = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Appium Buyer\"]/android.view.ViewGroup"));
            selectedBuyer.click();
            
            // select a style
            WebElement selectedStyle = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Appium Style\"]"));
            selectedStyle.click();
            
            // select an order
            WebElement selectedOrder = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Appium Order\"]"));
            selectedOrder.click();
            
            // continue with selected buyer, style, and order
            WebElement orderOkay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
            orderOkay.click();
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	public static void choose_variance() {
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
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	public static void pass() throws InterruptedException {
		// input pass data from production entry page
		WebElement pass = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Pass\"]"));
		pass.click();
		
		Thread.sleep(delay*1000);
	}
	
	public static void alter() throws InterruptedException {
		// input alter data from production entry page
		WebElement alter = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Alter\"]"));
		alter.click();
		Thread.sleep(3000);
		
		enter_defect();
		
		Thread.sleep(delay*1000);
	}
	
	public static void reject() throws InterruptedException {
		// input reject data from production entry page
		WebElement reject = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Reject\"]"));
		reject.click();
		Thread.sleep(3000);
		
		enter_defect();
		
		Thread.sleep(delay*1000);
	}
	
	public static void enter_defect() throws InterruptedException {
		try {
			// select a position on the sketch
			WebElement sketch = driver.findElement(By.xpath("//com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[3]"));
			sketch.click();
			Thread.sleep(5000);
					
			// select an operation
			WebElement opTab = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup"));
			opTab.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			WebElement operation = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"APPIUM OPERATION\"]/android.view.ViewGroup"));
			operation.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		} catch(Exception e) {
			WebElement operation = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"APPIUM OPERATION\"]/android.view.ViewGroup"));
			operation.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		}
				
		// select a defect
		WebElement defect = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup[2]/android.view.ViewGroup"));
		defect.click();
		Thread.sleep(2000);
				
		// continue with the selected position and defect
		WebElement okay = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup"));
		okay.click();
	}
	
	public static void undo() throws InterruptedException {
		// press undo button
		WebElement undoButton = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup"));
		undoButton.click();
		
		// confirm undo from pop-up message
		WebElement undoConfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
		undoConfirm.click();
		
		Thread.sleep(5000);
	}
	
	public static void force_sync() throws InterruptedException {
		// press force sync button
		WebElement fsButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\"]"));
		fsButton.click();
		
		Thread.sleep(5000);
	}
	
	public static void repair_mode() throws InterruptedException {
		// toggle the repair button to turn on repair mode
		WebElement repairOn = driver.findElement(By.xpath("//android.widget.Switch[@text=\"OFF\"]"));
		repairOn.click();
		
		pass();
        alter();
        reject();
		
		// toggle the repair button to turn off repair
		WebElement repairOff = driver.findElement(By.xpath("//android.widget.Switch[@text=\"ON\"]"));
		repairOff.click();
	}
	
	public static void logout() throws InterruptedException {
		// click on logout menu
		WebElement logout = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", logout\"]"));
		logout.click();
		
		Thread.sleep(5000);
	}
	
	public static void close_app() throws InterruptedException {
		// close the app
        driver.quit();
        
        Thread.sleep(5000);
	}
}
