package com.yeasin.appium_qms.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class QmsMain extends Listeners {
	public static AppiumDriverLocalService service;
	public static AndroidDriver driver;
	public static int inputDelay = 5;
	public static int iteration = 1;
	
	// Start the Appium server
	public void start_server() {
		// Configure the server on the localhost at the default port
		service = new AppiumServiceBuilder()
				.withAppiumJS(new File("C:\\Users\\1600000205\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723)
				.withTimeout(Duration.ofSeconds(300)).build();
		
		// Start the server
		service.start();
	}
	
	// Launch the saved emulator
	public void launch_emulator() throws IOException {
		// Execute the batch file responsible to launch the emulator
		Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\src\\test\\java\\com\\yeasin\\appium_qms\\config_props\\launch_emulator.bat");
		
		// Wait for the device to boot
		String bootCompleted;
		do {
			Process process = Runtime.getRuntime().exec("adb shell getprop sys.boot_completed");
		    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    bootCompleted = reader.readLine();
		} while (bootCompleted == null || !bootCompleted.equals("1"));
	}

	// Set up the server, the device, the driver, and the app
	public void set_up() throws IOException {
		// Set the device
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("intellier-tab");
					
		// Set the driver
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
				    
		try {
			// If the app is already installed, just open it without reinstalling
			driver.activateApp("com.nidleqms");

		} catch(Exception e) {
			// Install the app using the apk from the specified location
			options.setApp(System.getProperty("user.dir") + "\\src\\test\\java\\com\\yeasin\\appium_qms\\resources\\qms-24.04.30.apk");
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	// Log in to the app
	public void log_in() throws IOException, ParseException {
		// Log in to QMS app using the username and the password
		WebElement username = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Username\"]"));
		username.sendKeys(DataReader.getTestData("username"));
		WebElement password = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Password\"]"));
		password.sendKeys(DataReader.getTestData("password"));
		WebElement login = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Login\"]"));
		login.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(5));
	}
	
	// Expand the side menu
	public void expand_side_menu() {
		try {
			// Click on the side menu icon on the Home page
			WebElement menu = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
			menu.click();
			test.log(Status.INFO, "Expanding the side menu");
			
		} catch(Exception e) {
			// Click on the side menu icon on the Production entry page
			WebElement menu = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup"));
	        menu.click();
	        test.log(Status.INFO, "Expanding the side menu");
		}
		
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	// Sync web data for the latest Master Data and Settings
	public void sync_web() {
		// Click on the Sync button
        WebElement sync = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Sync\"]"));
        sync.click();
        
        test.log(Status.INFO, "Syncing web data");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	// Access the Settings module
	public void access_settings() {
        WebElement settings = driver.findElement(By.xpath("//android.widget.Button[@content-desc=', Settings']"));
        settings.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test.log(Status.INFO, "Entering the Setting module");
	}
	
	// Select a Line
	public void select_line() throws InterruptedException {
    	// Expand the Line drop-down
        WebElement line = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\", Root\"]/android.view.ViewGroup"));
    	line.click();
    	// Scroll to the desired Line
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Appium Line\"));"));
        // Select the desired Line
        WebElement selectedLine = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Appium Line\"]/android.view.ViewGroup"));
        selectedLine.click();
        
        Thread.sleep(1000);
        test.log(Status.INFO, "Selected a Line");
	}
	
	// Select an Input Delay
	public void select_input_delay() throws InterruptedException {
		// Expand the Input Delay drop-down
        WebElement delay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"5 Seconds\"]"));
        delay.click();
        // Scroll to the desired Input Delay
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"5 Seconds\"));"));
        // Select the desired Input Delay
        WebElement selectedDelay = driver.findElement(By.xpath("(//android.widget.TextView[@text=\"5 Seconds\"])[3]"));
        selectedDelay.click();
        
        Thread.sleep(1000);
        test.log(Status.INFO, "Selected an Input Delay");
	}
	
	// Set the Line and the Input delay
	public void set_lid() throws InterruptedException {
		// Access the Settings module
		access_settings();
    	
        try {
        	// Select a Line
        	select_line();
        	
            // Select an Input Delay
        	select_input_delay();
            
        } catch (Exception e) {
        	// Continue with previously selected Line and Input Delay
            test.log(Status.INFO, "Continuing with the previously selected Line and Input Delay");
        }
        
        // Click on the Next button to continue with the selected Line and Input Delay
        WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
        next.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Continuing with a Line and an Input Delay");
	}
	
	// Access the Home module
	public void access_home() {
        WebElement home = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", Home\"]"));
        home.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Entering the Home module");
	}
	
	// Access the QC Lunch Pad
	public void access_pad() {
        WebElement endTable = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"End table check, (Sewing), \"]"));
        endTable.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Entered the QC Lunch pad");
	}
	
	// Continue with the selected Buyer, Style, and Order
	public void prev_order_confirm() {
		WebElement prevOrderComfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
		prevOrderComfirm.click();
		test.log(Status.INFO, "Continuing with the previously selected Buyer, Style, and Order");
	}
	
	// Select Buyer, Style, and Order again
	public void prev_order_deny() {
		WebElement prevOrderComfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\"]"));
		prevOrderComfirm.click();
		test.log(Status.INFO, "Continuing with the previously selected Buyer, Style, and Order");
	}
	
	// Select a Buyer
	public void select_buyer() {
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Appium Buyer\"));"));
        WebElement selectedBuyer = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Appium Buyer\"]/android.view.ViewGroup"));
        selectedBuyer.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Selected a Buyer");
	}
	
	// Select a Style
	public void select_style() {
        WebElement selectedStyle = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Appium Style\"]"));
        selectedStyle.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Selected a Style");
	}
	
	// Select an Order
	public void select_order() {
        WebElement selectedOrder = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Appium Order\"]"));
        selectedOrder.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Selected an Order");
	}
	
	// Set the Buyer, the Style, and the Order
	public void set_bso() {
		try {
			// Access the Home module
			access_home();
            
		} catch(Exception e) {
			// Continue when Home module is already entered
			test.log(Status.INFO, "Entered the Home module before");
		}
		
        try {
        	// Click on the pop-up to continue with the previously selected Buyer, Style, and Order
        	prev_order_confirm();
        	
        } catch(Exception e) {
            // Access the QC Lunch Pad
        	access_pad();
            
            // Select a Buyer
        	select_buyer();
            
            // Select a Style
        	select_style();
            
            // Select an Order
        	select_order();
            
            // Click on the Okay button to continue with the selected Buyer, Style, and Order
            WebElement okay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
            okay.click();
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            test.log(Status.INFO, "Continuing with a Buyer, a Style, and an Order");
        }
	}
	
	// Choose the Color and the Size
	public void choose_variance() throws InterruptedException {
		try {
			// Choose a Color
			choose_color();
			
			// Choose a Size
			choose_size();
		
		} catch (Exception e) {
			// Continue with the previously selected Color and Size
			test.log(Status.INFO, "Continuing with the previously selected Color and Size");
		}
	}
	
	// Choose a Color
	public void choose_color() throws InterruptedException {
		WebElement color = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Choose a Color\"]"));
		color.click();
		WebElement selectedColor = driver.findElement(By.xpath("//android.widget.TextView[@text=\"BLACK\"]"));
		selectedColor.click();
		
		Thread.sleep(2000);
		test.log(Status.INFO, "Chose a Color");
	}
	
	// Choose a Size
	public void choose_size() throws InterruptedException {
		WebElement size = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Choose a Size\"]"));
		size.click();
		WebElement selectedSize = driver.findElement(By.xpath("//android.widget.TextView[@text=\"L\"]"));
		selectedSize.click();
		
		Thread.sleep(2000);
		test.log(Status.INFO, "Chose a Size");
	}
	
	// Provide a Pass entry
	public void pass_action() throws InterruptedException {
		// Input Pass data from the Production entry page
		WebElement pass = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Pass\"]"));
		pass.click();
		
		Thread.sleep((inputDelay+2)*1000);
		test.log(Status.INFO, "Pass");
	}
	
	// Provide an Alter entry
	public void alter_action() throws InterruptedException {
		// Input Alter data from the Production entry page
		WebElement alter = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Alter\"]"));
		alter.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		test.log(Status.INFO, "Entering the Defect entry page");
		
		// Select a Position, an Operation, and a Defect from the Defect entry page
		enter_defect();
		
		Thread.sleep((inputDelay+2)*1000);
		test.log(Status.INFO, "Alter");
	}
	
	// Provide a Reject entry
	public void reject_action() throws InterruptedException {
		// Input Reject data from the Production entry page
		WebElement reject = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Reject\"]"));
		reject.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		test.log(Status.INFO, "Entering the Defect entry page");
		
		// Select a Position, an Operation, and a Defect from the Defect entry page
		enter_defect();
		
		Thread.sleep((inputDelay+2)*1000);
		test.log(Status.INFO, "Reject");
	}
	
	// Select a Position, an Operation, and a Defect for an Alter/Reject
	public void enter_defect() throws InterruptedException {
		try {
			// Select a Position on the Sketch
			select_position();
				
			// Select an Operation
			select_operation();
		
		} catch(Exception e) {
			// Without selecting a Position, select an Operation
			select_operation();
		}
	
		// Select a Defect
		select_defect();
				
		// Click on the Okay button to continue with the selected Position, Operation, and Defect
		WebElement okay = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup"));
		okay.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		test.log(Status.INFO, "Continuing with the selected Position, Operation, and Defect");
	}
	
	// Select a Position on the Sketch
	public void select_position() throws InterruptedException {
		WebElement sketch = driver.findElement(By.xpath("//com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[3]"));
		sketch.click();
	
		Thread.sleep(5000);
		test.log(Status.INFO, "Selected a Position on the Sketch");
	}
	
	// Select an Operation
	public void select_operation() throws InterruptedException {
		try {
			WebElement operation = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup"));
			operation.click();
			Thread.sleep(2000);
			
		} catch (Exception e) {
			// No sketch available
			test.log(Status.INFO, "Selecting an Operation without selecting a Position");
		}
		
		WebElement selectedOperation = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"APPIUM OPERATION\"]/android.view.ViewGroup"));
		selectedOperation.click();
		Thread.sleep(2000);
		test.log(Status.INFO, "Selected an Operation");
	}
	
	// Select a Defect
	public void select_defect() throws InterruptedException {
		WebElement defect = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Number Mark\"]"));
		defect.click();
		Thread.sleep(2000);
		test.log(Status.INFO, "Selected an Defect");
	}
	
	// Sync the Pass/Alter/Reject entries to the web
	public void force_sync() throws InterruptedException {
		// Press the Force Sync button
		WebElement fsButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\"]"));
		fsButton.click();
		
		Thread.sleep(5000);
		test.log(Status.INFO, "Syncing data to web");
	}
	
	// Perform an Undo after a Pass/Alter/Reject
	public void undo_action() throws InterruptedException {
		// Press the Undo button
		WebElement undoButton = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup"));
		undoButton.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		test.log(Status.INFO, "Clicked on the Undo button");
			
		// Click on the pop-up message to confirm the Undo 
		WebElement undoConfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
		undoConfirm.click();
		
		Thread.sleep(2000);
		test.log(Status.INFO, "Confirmed the Undo from the pop-up");
	}
	
	// Perform an undo after a Pass
	public void pass_undo() throws InterruptedException {
		inputDelay = 2;
		pass_action();
		test.log(Status.INFO, "Pass");
		
		undo_action();
		test.log(Status.INFO, "Undone Pass");
		inputDelay = 5;
	}
		
	// Perform an Undo after an Alter
	public void alter_undo() throws InterruptedException {
		inputDelay = 2;
		alter_action();
		test.log(Status.INFO, "Alter");
		
		undo_action();
		test.log(Status.INFO, "Undone Alter");
		inputDelay = 5;
	}
			
	// Perform an Undo after a Reject
	public void reject_undo() throws InterruptedException {
		inputDelay = 2;
		reject_action();
		test.log(Status.INFO, "Reject");
		
		undo_action();
		test.log(Status.INFO, "Undone Reject");
		inputDelay = 5;
	}

	// Turn on the Repair mode
	public void repair_mode_on() throws InterruptedException {
		// Toggle the Repair button to turn on the Repair mode
		WebElement repairOn = driver.findElement(By.xpath("//android.widget.Switch[@text=\"OFF\"]"));
		repairOn.click();
		
		Thread.sleep(2000);
		test.log(Status.INFO, "Turned on the Repair mode");
	}
		
	// Turn off the Repair mode
	public void repair_mode_off() throws InterruptedException {
		// Toggle the Repair button to turn off the Repair mode
		WebElement repairOff = driver.findElement(By.xpath("//android.widget.Switch[@text=\"ON\"]"));
		repairOff.click();
		
		Thread.sleep(2000);
		test.log(Status.INFO, "Turned off the Repair mode");
	}
	
	// Log out of the app
	public void log_out() throws InterruptedException {
		// Click on Logout from the menu
		WebElement logout = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", logout\"]"));
		logout.click();
		
		Thread.sleep(5000);
	}
	
	// Close the app, the driver, and the server
	public void close_down() throws InterruptedException {
		// Close the app and remove it from app history
		driver.terminateApp("com.nidleqms");
		
		// Close the driver
        driver.quit();
        
        Thread.sleep(5000);
	}
}
