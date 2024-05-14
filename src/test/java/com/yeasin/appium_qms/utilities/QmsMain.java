package com.yeasin.appium_qms.utilities;

import java.net.URL;
import java.time.Duration;
import java.net.MalformedURLException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class QmsMain {
	static AppiumDriverLocalService service;
	protected static AndroidDriver driver;
	ExtentTest test;
	static int inputDelay = 5;
	static int iteration = 1;

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		QmsMain qmsMain = new QmsMain();
		
		qmsMain.set_up();
		qmsMain.log_in();
		qmsMain.expand_side_menu();
		qmsMain.sync_web();
		qmsMain.set_line();
		qmsMain.select_order();
		qmsMain.choose_variance();
        /**/
        // basic workflow
        inputDelay = 5;
        qmsMain.production_entry();
        inputDelay = 1;
        qmsMain.perform_undo();
        
        // repair mode
        qmsMain.repair_mode_on();
        inputDelay = 5;
        qmsMain.production_entry();
        inputDelay = 1;
        qmsMain.perform_undo();
        qmsMain.repair_mode_off();
        
        // offline mode
        driver.toggleWifi();
        Thread.sleep(5000);
        inputDelay = 5;
        qmsMain.production_entry();
        inputDelay = 1;
        qmsMain.perform_undo();
        qmsMain.repair_mode_on();
        inputDelay = 5;
        qmsMain.production_entry();
        inputDelay = 1;
        qmsMain.perform_undo();
        qmsMain.repair_mode_off();
        driver.toggleWifi();
        Thread.sleep(5000);
        
        qmsMain.expand_side_menu();
        qmsMain.log_out();
        qmsMain.close_down();
	}
	
	// set up the server, driver, and app
	public void set_up() throws MalformedURLException {
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
			test.log(Status.INFO, "Opening already installed app");

		} catch(Exception e) {
			// set the app
			options.setApp(System.getProperty("user.dir") + "\\src\\test\\java\\com\\yeasin\\appium_qms\\resources\\qms-24.04.30.apk");
			test.log(Status.INFO, "Installing app");
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	// log in to the app
	public void log_in() {
		// log in to qms app with username = Test and password = test
		WebElement username = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Username\"]"));
		username.sendKeys("Test");
		WebElement password = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Password\"]"));
		password.sendKeys("test");
		WebElement login = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Login\"]"));
		login.click();
		test.log(Status.INFO, "Logging in to app with username and password");
		
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(5));
	}
	
	// expand the side menu
	public void expand_side_menu() {
		// click on the side menu icon
		try {
			WebElement menu = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
			menu.click();
			test.log(Status.INFO, "Expanding side menu");
			
		} catch(Exception e) {
			WebElement menu = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup"));
	        menu.click();
	        test.log(Status.INFO, "Expanding side menu");
		}
		
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	// sync web data for the latest master data and settings
	public void sync_web() {
		// click on the sync button
        WebElement sync = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Sync\"]"));
        sync.click();
        test.log(Status.INFO, "Syncing web data");
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	// set the line and input delay
	public void set_line() throws InterruptedException {
		// access settings module
        WebElement settings = driver.findElement(By.xpath("//android.widget.Button[@content-desc=', Settings']"));
        settings.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        test.log(Status.INFO, "Entering Setting module");
    	
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
            
            test.log(Status.INFO, "Selected Line and Input Delay");
            
        } catch (Exception e) {
        	// continue with previously selected line and input delay
        	WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
            next.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
            test.log(Status.INFO, "Continuing with selected Line and Input Delay");
        }
	}
	
	// select the buyer, style, and order
	public void select_order() {
		try {
			// access home module
            WebElement home = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", Home\"]"));
            home.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
            test.log(Status.INFO, "Entering Home module");
            
		} catch(Exception e) {
			// home module already entered
			test.log(Status.INFO, "Entered Home module before");
		}
		
        try {
        	// continue with previously selected buyer, style, and order
        	WebElement prevOrderComfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
        	prevOrderComfirm.click();
        	
        	test.log(Status.INFO, "Continuing with selected Buyer, Style, and Order");
        	
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
            
            test.log(Status.INFO, "Selected Buyer, Style, and Order");
        }
	}
	
	// choose color and size
	public void choose_variance() throws InterruptedException {
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
			
			test.log(Status.INFO, "Chose Color and Size");
		
		} catch (Exception e) {
			// continue with already selected color and size
			test.log(Status.INFO, "Continuing with selected Color and Size");
		}
	}
	
	// provide pass entries
	public void pass_action() throws InterruptedException {
		// input pass data from production entry page
		WebElement pass = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Pass\"]"));
		pass.click();
		
		Thread.sleep((inputDelay+2)*1000);
		
		test.log(Status.INFO, "Pass");
	}
	
	// provide alter entries
	public void alter_action() throws InterruptedException {
		// input alter data from production entry page
		WebElement alter = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Alter\"]"));
		alter.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		enter_defect();
		
		Thread.sleep((inputDelay+2)*1000);
		
		test.log(Status.INFO, "Alter");
	}
	
	// provide reject entries
	public void reject_action() throws InterruptedException {
		// input reject data from production entry page
		WebElement reject = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Reject\"]"));
		reject.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		enter_defect();
		
		Thread.sleep((inputDelay+2)*1000);
		
		test.log(Status.INFO, "Reject");
	}
	
	// select positon, operation, and defect for alter/reject
	public void enter_defect() throws InterruptedException {
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
			
			test.log(Status.INFO, "Selected Position and Operation");
			
		} catch(Exception e) {
			// select an operation and continue
			WebElement selectedOperation = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"APPIUM OPERATION\"]/android.view.ViewGroup"));
			selectedOperation.click();
			Thread.sleep(2000);
			
			test.log(Status.INFO, "Selected Operation without Position");
		}
		
		// select a defect
		WebElement defect = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Number Mark\"]"));
		defect.click();
		Thread.sleep(2000);
				
		// continue with the selected position and defect
		WebElement okay = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup"));
		okay.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		test.log(Status.INFO, "Selected Defect");
	}
	
	// sync pass/alter/reject entries to the web
	public void force_sync() throws InterruptedException {
		// press force sync button
		WebElement fsButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\"]"));
		fsButton.click();
		
		Thread.sleep(5000);
		
		test.log(Status.INFO, "Syncing data to web");
	}
	
	// perform undo after pass/alter/reject
	public void undo_action() throws InterruptedException {
		// press undo button
		WebElement undoButton = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup"));
		undoButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			
		// confirm undo from pop-up message
		WebElement undoConfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
		undoConfirm.click();
		Thread.sleep(2000);
		
		test.log(Status.INFO, "Undoing latest input");
	}

	// turn on repair mode
	public void repair_mode_on() throws InterruptedException {
		// toggle the repair button to turn on repair mode
		WebElement repairOn = driver.findElement(By.xpath("//android.widget.Switch[@text=\"OFF\"]"));
		repairOn.click();
		Thread.sleep(2000);
		
		test.log(Status.INFO, "Turned on repair mode");
	}
		
	// turn off repair mode
	public void repair_mode_off() throws InterruptedException {
		// toggle the repair button to turn off repair
		WebElement repairOff = driver.findElement(By.xpath("//android.widget.Switch[@text=\"ON\"]"));
		repairOff.click();
		Thread.sleep(2000);
		
		test.log(Status.INFO, "Turned off repair mode");
	}
		
	public void production_entry() throws InterruptedException {
		test.log(Status.INFO, "Performing Production entry");
		
		inputDelay = 5;
		for(int i = 1; i <= iteration; i++) {
        	pass_action();
        	
        	if(i % 1 == 0) {
        		alter_action();
                reject_action();
        	}
        }
		
        force_sync();
	}
	
	public void perform_undo() throws InterruptedException {
		test.log(Status.INFO, "Performing Undo operation");
		
		inputDelay = 2;
        for(int i = 1; i <= iteration; i++) {
        	pass_action();
        	undo_action();
        	
        	if(i % 1 == 0) {
        		alter_action();
        		undo_action();
                reject_action();
                undo_action();
        	}
        }
        
        force_sync();
	}
	
	// log out of the app
	public void log_out() throws InterruptedException {
		// click on logout menu
		WebElement logout = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", logout\"]"));
		logout.click();
		
		Thread.sleep(5000);
		test.log(Status.INFO, "Logging out of app");
	}
	
	// close the driver and stop the server
	public void close_down() throws InterruptedException {
		// close the app and remove it from app history
		driver.terminateApp("com.nidleqms");
		
		// close the driver
        driver.quit();
        
        // stop the server
        // service.stop();
        
        Thread.sleep(5000);
	}
}
