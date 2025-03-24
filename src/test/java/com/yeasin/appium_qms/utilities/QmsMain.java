package com.yeasin.appium_qms.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class QmsMain extends Listeners {
	public static AppiumDriverLocalService service;
	public static AndroidDriver driver;
	public static int inputDelay = 5;

	public static int iteration = 1;

	@AndroidFindBy(xpath = "//android.widget.EditText[@text=\"Username\"]")
	public WebElement username;



	// Start the Appium server
	public void start_server() {
		// Configure the server on the localhost at the default port
		service = new AppiumServiceBuilder()
				.withAppiumJS(new File("C:\\Users\\1600000273\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
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

	public int getIntDelayNumber() throws IOException, ParseException{
        return (Integer) DataReader.getIntTestData("inputDelayNumber");
	}

	// Set up the server, the device, the driver, and the app
	public void set_up() throws IOException, ParseException {
		// Set the device
		UiAutomator2Options options = new UiAutomator2Options();
		String deviceName;
		String usedDeviceName = (String) DataReader.getTestData("deviceName");
		
		// Check for connected device
	    if (isConnectedDevice()) {
	    	// Use the real device
	        //deviceName = "HA1DRQQE";

			//deviceName = "Lenovo TB-X306X";
//			deviceName = usedDeviceName;
			deviceName = "GS81TE";
	    } else {
	    	// Use the emulator
	        //deviceName = "intellier-tab";
			deviceName = usedDeviceName;
	        launch_emulator();
	    }
	    
	    // Set the device
	    options.setDeviceName(deviceName);
		options.setCapability("appium:plugin:gestures", true);
		/*
		options.setPlatformName("Android");
		options.setPlatformVersion("10");
		options.setAutomationName("uiautomator2");*/

					
		// Set the driver
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
				    
		if (driver.isAppInstalled("com.nidleqms")) {
			// Open the app
			System.out.println("App is already installed");
			driver.activateApp("com.nidleqms");
		} else {
			// Install the app
			System.out.println("App is installing");

			String apkpath = (String) DataReader.getTestData("apkVersion");
			String fullpath = String.format("\\src\\test\\java\\com\\yeasin\\appium_qms\\resources\\%s",apkpath);

			//driver.installApp(System.getProperty("user.dir") + "\\src\\test\\java\\com\\yeasin\\appium_qms\\resources\\2.2.9-QA-18-11-24-release.apk");
			driver.installApp(System.getProperty("user.dir") + fullpath);
			driver.activateApp("com.nidleqms");
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	private boolean isConnectedDevice() throws IOException {
	    Process process = Runtime.getRuntime().exec("adb devices");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    
	    String line;
	    while ((line = reader.readLine()) != null) {
	        if (line.contains("HA1DRQQE") && line.contains("device") ) {
	            return true;
	        }
			/*if (line.contains("0123456789ABCDEF") && line.contains("device")){
				return true;
			}*/

	    }
	    
	    reader.close();
	    return false;
	}
	
	// Log in to the app
	public void log_in() throws IOException, ParseException {
		// Log in to QMS app using the username and the password
		WebElement username = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Username\"]"));
		username.sendKeys(DataReader.getTestData("username"));
		WebElement password = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Password\"]"));
		password.sendKeys(DataReader.getTestData("password"));
		//WebElement login = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Login\"]"));
		WebElement login = driver.findElement(new AppiumBy.ByAccessibilityId("Login"));
		login.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(10));
	}

	public void swipeUpUntilElementFound(String elementID, String targetElementID) {
			// Locate the start element (or you can use coordinates)
		WebElement startElement = driver.findElement(By.id(elementID));

			// Perform a swipe (scroll) action by dragging the element vertically
		Actions actions = new Actions(driver);
		boolean elementfound = false;
		while (!elementfound){
			try{
				WebElement targetElement = driver.findElement(By.id(targetElementID));
				targetElement.click();
				elementfound = true;
			} catch (Exception e) {
				actions.clickAndHold(startElement)
						.moveByOffset(0, -200)
						.release()
						.perform();
			}
		}

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
		System.out.println("Sync button pressed");
        
        test.log(Status.INFO, "Syncing web data");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	// Access the Settings module
	public void access_settings() {
        //WebElement settings = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"\uE672, Settings\"]"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        //WebElement settings = driver.findElement(By.xpath("(//android.widget.TextView[@text=\"Settings\"])[2]"));
		//WebElement settings = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//android.widget.TextView[@text=\"Settings\"])[2]")));
		WebElement settings = driver.findElement(AppiumBy.accessibilityId(", Settings"));
		settings.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Entering the Setting module");
		swipeLeft(driver);
	}



	public static void swipeRight(AppiumDriver driver) {
		WebElement screenElement = driver.findElement(By.xpath("//android.widget.FrameLayout"));
		String elementId = ((RemoteWebElement) screenElement).getId();
		// Define the swipe gesture using Gestures Plugin
		// Now you can use the elementId for actions like swipe
		HashMap<String, Object> swipeParams = new HashMap<>();
		swipeParams.put("elementId", elementId);
		swipeParams.put("direction", "right");
		swipeParams.put("percentage", 0.8);
		driver.executeScript("gesture: swipe", swipeParams);
	}
	public void swipeLeft(AppiumDriver driver){
		WebElement screenElement = driver.findElement(By.xpath("//android.widget.FrameLayout"));
		String elementId = ((RemoteWebElement) screenElement).getId();
		// Define the swipe gesture using Gestures Plugin
		// Now you can use the elementId for actions like swipe
		HashMap<String, Object> swipeParams = new HashMap<>();
		swipeParams.put("elementId", elementId);
		swipeParams.put("direction", "left");
		swipeParams.put("percentage", 0.8);
		driver.executeScript("gesture: swipe", swipeParams);
	}

	public static void scrolluntilElementViewed(){
		//WebElement screenElement = driver.findElement(By.xpath(xpathData));
		//String elementId = ((RemoteWebElement) screenElement).getId();

		// Define the scroll gesture using the Appium driver
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(45)")));
		/*RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" +
																																		".scrollIntoView(new UiSelector.className(\"android.view.ViewGroup\")")));*/


		driver.executeScript("gesture: scrollElementIntoView", Map.of("scrollableView", scrollView.getId(),
				"strategy", "androidUIAutomator",
				"selector", "new UiSelector().text(\"10 Seconds\")",
				"percentage", 80,
				"direction", "up",
				"maxCount", 3));



		// Execute the script to scroll the element into view
	}



	// Select a Line
	public void select_line() throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


		//WebElement line = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text=\"Root\"]")));
		WebElement line = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text=\"4A Yarn Dyeing Ltd\"]")));
    	line.click();
		try {
			WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.ScrollView")));

			// 3. Verify if the dropdown is visible.
			if (dropdown.isDisplayed()) {
				System.out.println("Dropdown is visible");

				// 4. Now, interact with one of the options in the dropdown.
				String firstDropdownButtonFromTestData = (String) DataReader.getTestData("firstDropdownButton");
				String xpathExpression = String.format("//android.widget.TextView[@text='%s']",firstDropdownButtonFromTestData);
				WebElement firstDropDownButton = driver.findElement(By.xpath(xpathExpression));
				//WebElement testButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text=\"4A Yarn Dyeing Ltd\"]")));
				firstDropDownButton.click();
				System.out.println("1st option selected");
			} else {
				System.out.println("Dropdown is NOT visible");
			}

		} catch (TimeoutException e) {
			System.out.println("Dropdown did not appear within the expected time");
		}

    	// Scroll to the desired Line
        //driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Test\"));"));
		// Clicking the floor button to open the dropdown
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		try {

			WebElement secondDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text=\"4A-1 1ST\"]")));
			if (secondDropdown.isDisplayed()) {
				System.out.println("Second Dropdown is visible");
				//swipeUp(driver);
				String secondDropdownButtonFromTestData = (String) DataReader.getTestData("secondDropdownButton");
				String xpathExpression = String.format("//android.widget.TextView[@text='%s']",secondDropdownButtonFromTestData);
				WebElement secondDropDownButton = driver.findElement(By.xpath(xpathExpression));
				//WebElement seconddropdownButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"4A-1 1ST\"]"));
				//seconddropdownButton.click();
				secondDropDownButton.click();

			} else {
				System.out.println("Dropdown is NOT visible");
			}

		} catch (TimeoutException e) {
			System.out.println("Second Dropdown did not appear within the expected time");
		}

		//Again scroll from the line dropdown


		// Select the desired Line
		/*WebDriverWait secondWait = new WebDriverWait(driver, Duration.ofSeconds(5));
		secondWait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text='4A-1-02']")));

		WebElement selectedLine = driver.findElement(By.xpath("//android.widget.TextView[@text=\"4A-1-02\"]"));
        selectedLine.click();*/
        System.out.println("The desired line got selected");
        Thread.sleep(1000);
        test.log(Status.INFO, "Selected a Line");
	}




	
	// Select an Input Delay
	/*public void select_input_delay() throws InterruptedException, IOException, ParseException {
		// Expand the Input Delay drop-down
        WebElement delay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"5 Seconds\"]"));
        delay.click();
		String inputDelayNumber = DataReader.getTestData("input-delay-number");
		String xpathExpression = String.format("//android.widget.TextView[@text=\"'%s' Seconds\"]", inputDelayNumber);
		new WebDriverWait(driver, Duration.ofSeconds(2));
		WebElement selectedDelay = driver.findElement(By.xpath(xpathExpression));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        selectedDelay.click();
        Thread.sleep(1000);
        test.log(Status.INFO, "Selected an Input Delay");
	}*/

	public void select_input_delay() throws InterruptedException, IOException, ParseException {
		// Read the input delay number from test data
		String inputDelayNumber = (String) DataReader.getTestData("inputDelayNumber");
		//String xpathExpression = String.format("//android.widget.TextView[@text='%s Seconds']", inputDelayNumber);
		String xpathExpression = String.format("//android.view.ViewGroup[@content-desc=\"%s Seconds\"]", inputDelayNumber);

		// Try to find the element directly
		WebElement selectedDelay = null;
		WebElement delay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"5 Seconds\"]"));
		delay.click();
		try {
			selectedDelay = driver.findElement(By.xpath(xpathExpression));
			// If the element is found, click it
			selectedDelay.click();
			test.log(Status.INFO, "Selected an Input Delay without scrolling");
		} catch (NoSuchElementException e) {
			// If the element is not found, log and attempt to scroll until it's in view
			test.log(Status.INFO, "Element not found, attempting to scroll into view");
			System.out.println("Element not found, attempting to scroll into view");

			// Call the scrollUntilElementViewed function to ensure the element is scrolled into view
			String xpathForDropdownContainer = "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[5]/android.widget.ScrollView";
			scrolluntilElementViewed();
			// Now find the element after scrolling
			selectedDelay = driver.findElement(By.xpath(xpathExpression));
			selectedDelay.click();
			test.log(Status.INFO, "Selected an Input Delay after scrolling");
		}

		// Add a small delay after the action
		Thread.sleep(1000);
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
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Wait for the main content to appear
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"End table check, (Sewing), \"]")));
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
	public void select_buyer() throws IOException, ParseException {
        //Scrolling to a specific buyer
		String buyerName = (String) DataReader.getTestData("buyer");
		String xpathExpression = String.format("//android.view.ViewGroup[@content-desc='%s']/android.view.ViewGroup", buyerName);
		WebElement selectedBuyer = driver.findElement(By.xpath(xpathExpression));
        selectedBuyer.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Selected a Buyer");
	}
	
	// Select a Style
	public void select_style() throws IOException, ParseException{
		String styleName = (String) DataReader.getTestData("style");
		String xpathExpression = String.format("//android.widget.Button[@content-desc='%s']", styleName);
		WebElement selectedStyle = driver.findElement(By.xpath(xpathExpression));
        selectedStyle.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Selected a Style");
	}
	
	// Select an Order
	public void select_order() throws IOException,ParseException{
		String orderName = (String) DataReader.getTestData("order");
		String xpathExpression = String.format("//android.widget.Button[@content-desc='%s']", orderName);
        WebElement selectedOrder = driver.findElement(By.xpath(xpathExpression));
		selectedOrder.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Selected an Order");
	}
	
	// Set the Buyer, the Style, and the Order
	public void set_bso() throws IOException, ParseException{
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
	public void choose_color() throws InterruptedException, IOException, ParseException {
		WebElement color = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Choose a Color\"]"));
		color.click();
		//For logic
		String colorName = (String) DataReader.getTestData("color");
		String xpathExpression = String.format("//android.widget.TextView[@text='%s']", colorName);
		WebElement selectedColor = driver.findElement(By.xpath(xpathExpression));
		selectedColor.click();

		Thread.sleep(2000);
		test.log(Status.INFO, "Chose a Color");
	}
	
	// Choose a Size
	public void choose_size() throws InterruptedException, IOException, ParseException {
		WebElement size = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Choose a Size\"]"));
		size.click();
		String sizeName = (String) DataReader.getTestData("size");
		String xpathExpression = String.format("//android.widget.TextView[@text='%s']", sizeName);
		WebElement selectedSize = driver.findElement(By.xpath(xpathExpression));
		selectedSize.click();
		
		Thread.sleep(2000);
		test.log(Status.INFO, "Chose a Size");
	}
	/*public void executeFunction(String functionName) throws InterruptedException, IOException, ParseException {
		if ("specificFunction".equals(functionName)) {
			// Wait for a few seconds before executing the specific function
			System.out.println("Waiting before executing " + functionName);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DataReader.getIntTestData("inputDelayNumber")));
		} else {
			System.out.println("No wait for " + functionName);
		}

		// Call the corresponding function
		switch (functionName) {
			case "specificFunction":
				functionName();
				break;
			case "anotherFunction":
				anotherFunction();
				break;
			default:
				System.out.println("No matching function for " + functionName);
				break;
		}
		switch (functionName) {
			case "specificFunction":
				functionName();
				break;
			case "anotherFunction":
				anotherFunction();
				break;
			default:
				System.out.println("No matching function for " + functionName);
				break;
		}
	}*/

	
	// Provide a Pass entry
	public void pass_action() throws InterruptedException, IOException, ParseException {
		try{
			Thread.sleep(DataReader.getLongTestData("inputDelayNumber") * 1000L);
			int currentHour = LocalTime.now().getHour(); // Get the current system hour in 24-hour format

			if (currentHour >= 13 && currentHour < 14) { // 1 PM to 2 PM
				System.out.println("Lunch hour detected (1 PM - 2 PM). Skipping Pass button action.");
				return; // Do not attempt to click the button
			}
			// Input Pass data from the Production entry page
			try{
				WebElement pass = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Pass\"]"));
				pass.click();
				System.out.println("Pass button clicked successfully!");
				//Thread.sleep(((inputDelay+2))*1000);
				//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DataReader.getIntTestData("inputDelayNumber")));
				test.log(Status.INFO, "Pass");
			}catch(Exception e){
				System.out.println("Could not click Pass button: " + e.getMessage());
			}



		} catch (Exception e) {
			//throw new RuntimeException(e);
			System.out.println("Couldn't give pass");
			System.out.println(e.getMessage());
		}
	}


	
	// Provide an Alter entry
	public void alter_action() throws InterruptedException, IOException, ParseException {
		Thread.sleep(DataReader.getLongTestData("inputDelayNumber") * 1000L);
		// Input Alter data from the Production entry page
		WebElement alter = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Alter\"]"));
		alter.click();

		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		test.log(Status.INFO, "Entering the Defect entry page");
		
		// Select a Position, an Operation, and a Defect from the Defect entry page
		enter_defect();
		
		//Thread.sleep((inputDelay+2)*1000);
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DataReader.getIntTestData("inputDelayNumber")));
		test.log(Status.INFO, "Alter");
		//Thread.sleep(Duration.ofSeconds(DataReader.getIntTestData("inputDelayNumber")));
		Thread.sleep(DataReader.getLongTestData("inputDelayNumber") * 1000L);

	}
	
	// Provide a Reject entry
	public void reject_action() throws InterruptedException, IOException, ParseException {
		Thread.sleep(DataReader.getLongTestData("inputDelayNumber") * 1000L);
		// Input Reject data from the Production entry page
		WebElement reject = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Reject\"]"));
		reject.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		test.log(Status.INFO, "Entering the Defect entry page");
		
		// Select a Position, an Operation, and a Defect from the Defect entry page
		enter_defect();
		
		//Thread.sleep((inputDelay+2)*1000);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DataReader.getIntTestData("inputDelayNumber")));
		test.log(Status.INFO, "Reject");
		//Thread.sleep(Duration.ofSeconds(DataReader.getIntTestData("inputDelayNumber")));
		Thread.sleep(DataReader.getLongTestData("inputDelayNumber") * 1000L);
	}
	
	// Select a Position, an Operation, and a Defect for an Alter/Reject
	public void enter_defect() throws InterruptedException, IOException, ParseException {
		try {
			// Select a Position on the Sketch
			select_position();
				
			// Select an Operation
			//select_operation();
		
		} catch(Exception e) {
			// Without selecting a Position, select an Operation
			//select_operation();
		}
	
		// Select a Defect
		select_defect();
				
		// Click on the Okay button to continue with the selected Position, Operation, and Defect
		//WebElement okay = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup"));
		WebElement okay = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\uE632\"]"));

		okay.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		test.log(Status.INFO, "Continuing with the selected Position, Operation, and Defect");
	}

	//Select a specific position at the image with specific coordinates
	public void clickOnImageAtSpecificLocation(WebElement imageElement) {
		Rectangle rect = imageElement.getRect();
		int imageX = rect.getX();
		int imageY = rect.getY();
		int imageWidth = rect.getWidth();
		int imageHeight = rect.getHeight();

	// Calculate the specific point to touch (e.g., center of the image)
		int touchX = imageX + (imageWidth / 2);  // Adjust these for your specific point
		int touchY = imageY + (imageHeight / 2);

	// Perform the tap action
		HashMap<String, Object> params = new HashMap<>();
		params.put("action", "tap");
		params.put("x", touchX);
		params.put("y", touchY);
		driver.executeScript("mobile: gesture", params);

		System.out.println("Tapped at point: (" + touchX + ", " + touchY + ")");

	}

	public void goBack(){
		driver.navigate().back();
	}

	// Select a Position on the Sketch
	/*public void select_position() throws InterruptedException {
		//Done by Yeasin
		//WebElement selectedSketch = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/com.horcrux.svg.SvgView"));
		WebElement selectedSketch = driver.findElement(By.xpath("//com.horcrux.svg.SvgView"));
		WebElement sketch = driver.findElement(By.xpath("//com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[3]"));

		if (selectedSketch.isDisplayed()){
			selectedSketch.click();
		}else {
			System.out.println("Couldn't select the position");
		}

		Thread.sleep(5000);
		test.log(Status.INFO, "Selected a Position on the Sketch");
	}*/

	public void select_position() throws InterruptedException, IOException, ParseException{
		//String imageposition = DataReader.getTestData("imagePositionXpath");
		//String xpathExpression = String.format()
		//WebElement sketch = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.GroupView"));
		//WebElement selectedSketch = driver.findElement(By.className("com.horcrux.svg.GroupView"));
		WebElement selectedSketch = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(21)"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(21)")));
		//RemoteWebElement scrollView = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("Swipe-screen")));
		RemoteWebElement sketch = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(0)")));
		if (selectedSketch.isDisplayed()){
			//selectedSketch.click();
			//clickOnImageAtSpecificLocation(sketch);
			driver.executeScript("mobile:clickGesture", Map.of(
					"elementId", sketch.getId(), // ID of the image element
					"x", 324, // X-coordinate offset within the element
					"y", 460 // Y-coordinate offset within the element
			));
			System.out.println("Clicked on the image");
		}else {
			System.out.println("Image couldn't be selected");
		}
	}
	
	// Select an Operation
	/*public void select_operation() throws InterruptedException, IOException, ParseException {
		String operationName = (String) DataReader.getTestData("operation");
		String xpathExpression = String.format("//android.widget.TextView[@text='%s']",operationName);
		try {
			WebElement operation = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Operation:\"]"));
			operation.click();
			Thread.sleep(2000);
			
		} catch (Exception e) {
			// No sketch available
			test.log(Status.INFO, "Selecting an Operation without selecting a Position");
		}

		try{
			//WebElement selectedOperation = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"KHADIZA\"]/android.view.ViewGroup"));
			WebElement selectedOperation = driver.findElement(By.xpath(xpathExpression));
			if(selectedOperation.isDisplayed()){
				selectedOperation.click();
				System.out.println("Operation selected");
			}
		}catch (NoSuchElementException e){
				System.out.println("No such operation found");
				select_defect();
				WebElement button_to_confirm_alter = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]"));
				button_to_confirm_alter.click();
		}


		Thread.sleep(2000);
		test.log(Status.INFO, "Selected an Operation");
	}*/
	
	// Select a Defect
	public void select_defect() throws InterruptedException, IOException, ParseException {
		String defectName = (String) DataReader.getTestData("defect");
		String xpathExpression = String.format("//android.widget.TextView[@text='%s']",defectName);
		WebElement defect = driver.findElement(By.xpath(xpathExpression));
		defect.click();
		Thread.sleep(2000);
		test.log(Status.INFO, "Selected an Defect");
	}
	
	// Sync the Pass/Alter/Reject entries to the web
	public void force_sync() throws InterruptedException {
		// Press the Force Sync button
		//WebElement fsButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\"]"));
		WebElement fsButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\uF0EE\"]"));
		fsButton.click();
		
		Thread.sleep(5000);
		test.log(Status.INFO, "Syncing data to web");
	}
	
	// Perform an Undo after a Pass/Alter/Reject
	public void undo_action() throws InterruptedException {
		// Press the Undo button
		WebElement undoButton = driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(17)"));
		//WebElement undoButton = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup"));
		undoButton.click();
		
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		test.log(Status.INFO, "Clicked on the Undo button");
			
		// Click on the pop-up message to confirm the Undo 
		//WebElement undoConfirm = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
		WebElement undoConfirm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]")));
		undoConfirm.click();
		
		Thread.sleep(2000);
		test.log(Status.INFO, "Confirmed the Undo from the pop-up");
	}
	
	// Perform an undo after a Pass
	public void pass_undo() throws InterruptedException, IOException, ParseException {
		try{
			//inputDelay = 1;
			pass_action();
			test.log(Status.INFO, "Pass");
			//getIntDelayNumber()
			//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(getIntDelayNumber()));
			undo_action();
			test.log(Status.INFO, "Undone Pass");
			//Thread.sleep(Duration.ofSeconds(DataReader.getIntTestData("inputDelayNumber")));
			Thread.sleep(DataReader.getLongTestData("inputDelayNumber") * 1000L);
			//inputDelay = 5;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
		
	// Perform an Undo after an Alter
	public void alter_undo() throws InterruptedException, IOException, ParseException {
		//inputDelay = 1;
		alter_action();
		test.log(Status.INFO, "Alter");
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(getIntDelayNumber()));
		undo_action();
		test.log(Status.INFO, "Undone Alter");
		//inputDelay = 5;
	}
			
	// Perform an Undo after a Reject
	public void reject_undo() throws InterruptedException, IOException, ParseException {
		//inputDelay = 1;
		reject_action();
		test.log(Status.INFO, "Reject");
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(getIntDelayNumber()));
		undo_action();
		test.log(Status.INFO, "Undone Reject");
		//inputDelay = 5;
		//Thread.sleep(Duration.ofSeconds(DataReader.getIntTestData("inputDelayNumber")));
	}

	// Turn on the Repair mode
	public void repair_mode_on() throws InterruptedException {
		// Toggle the Repair button to turn on the Repair mode
		//WebElement repairOn = driver.findElement(By.xpath("//android.widget.Switch[@text=\"OFF\"]"));
		WebElement repairOn = driver.findElement(By.xpath("//android.widget.Switch"));
		repairOn.click();
		
		Thread.sleep(2000);
		test.log(Status.INFO, "Turned on the Repair mode");
	}
		
	// Turn off the Repair mode
	public void repair_mode_off() throws InterruptedException {
		// Toggle the Repair button to turn off the Repair mode
		WebElement repairOff = driver.findElement(By.xpath("//android.widget.Switch"));
		//WebElement repairOff = driver.findElement(By.xpath("//android.widget.Switch[@text=\"ON\"]"));
		repairOff.click();
		
		Thread.sleep(2000);
		test.log(Status.INFO, "Turned off the Repair mode");
	}
	
	// Log out of the app
	public void log_out() throws InterruptedException {
		//expand_side_menu();
		//WebElement side_menu_button = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\uF0C9\"]"));
		//side_menu_button.click();
		swipeRight(driver);
		// Click on Logout from the menu
		WebElement logout = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"\uE9BA, logout\"]"));
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

