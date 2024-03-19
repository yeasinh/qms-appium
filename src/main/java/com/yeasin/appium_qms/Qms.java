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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class Qms {
	public static AndroidDriver driver;

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
        setup();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        login();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
        
        side_menu();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        sync_web();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        
        set_line();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        side_menu();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        select_order();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        for(int i = 0; i < 5; i++) {
        	pass();
        	Thread.sleep(3000);
        }
        
        force_sync();
        Thread.sleep(5000);
        
        alter();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        force_sync();
        Thread.sleep(5000);
        
        reject();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        force_sync();
        Thread.sleep(5000);
        
        close_app();
	}
	
	public static void setup() throws MalformedURLException {
		// set the device and the app
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("intellier-tab");
		options.setApp("D:\\APK\\qms-24.03.11.apk");
			    
		// set the driver
		driver = new AndroidDriver(new URL("http://172.17.8.14:4723"), options);
	}
	
	public static void login() {
		// log in to qms app with username = Test and password = test
		WebElement username = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Username\"]"));
		username.sendKeys("Test");
		WebElement password = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Password\"]"));
		password.sendKeys("test");
		WebElement login = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Login\"]"));
		login.click();
	}
	
	public static void side_menu() {
		// click on the side menu
        WebElement menu = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
        menu.click();
	}
	
	public static void sync_web() {
		// sync web data
        WebElement sync = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Sync\"]"));
        sync.click();
        System.out.println(sync.getText());
	}
	
	public static void set_line() {
		// access settings module
        WebElement settings = driver.findElement(By.xpath("//android.widget.Button[@content-desc=', Settings']"));
        settings.click();
        System.out.println(settings.getText());
    	
        // select line
        WebElement line = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\", Root\"]/android.view.ViewGroup"));
        line.click(); 
        WebElement selectedLine = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"App Test 101\"]/android.view.ViewGroup"));
        selectedLine.click();
        
        // select input delay
        WebElement delay = driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"5 Seconds\"])[1]"));
        delay.click();
        WebElement selectedDelay = driver.findElement(By.xpath("//android.widget.TextView[@text=\"1 Second\"]"));
        selectedDelay.click();
        
        // continue with selected line and input delay
        WebElement next = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Next\"]"));
        next.click();
	}
	
	public static void select_order() {
		// access home module
        WebElement home = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\", Home\"]"));
        home.click();
        System.out.println(home.getText());
        
        // access qc lunch pad
        WebElement endTable = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"End table check, (Sewing), \"]"));
        System.out.println(endTable.getText());
        endTable.click();
        
        // select buyer, style, and order
        WebElement selectedBuyer = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"AWG\"]/android.view.ViewGroup"));
        selectedBuyer.click();
        WebElement selectedStyle = driver.findElement(By.xpath("(//android.widget.Button[@content-desc=\"158850\"])[1]"));
        selectedStyle.click();
        WebElement selectedOrder = driver.findElement(By.xpath("(//android.widget.Button[@content-desc=\"158850\"])[2]"));
        selectedOrder.click();
        WebElement orderOkay = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"\"]"));
        orderOkay.click();
	}
	
	public static void pass() {
		// input pass data from production entry page
		WebElement pass = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Pass\"]"));
		pass.click();
	}
	
	public static void alter() {
		// input alter data from production entry page
		WebElement alter = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Alter\"]"));
		alter.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		WebElement alterImage = driver.findElement(By.xpath("//com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[3]"));
		alterImage.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		WebElement alterDefect = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Defect 1\"]"));
		alterDefect.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
		WebElement alterOkay = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\"]"));
		alterOkay.click();
	}
	
	public static void reject() {
		// input reject data from production entry page
		WebElement reject = driver.findElement(By.id("//android.widget.TextView[@text=\"Reject\"]"));
		reject.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		WebElement rejectImage = driver.findElement(By.xpath("//com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[3]"));
		rejectImage.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		WebElement rejectDefect = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Defect 3\"]"));
		rejectDefect.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
		WebElement rejectOkay = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\"]"));
		rejectOkay.click();
	}
	
	public static void force_sync() {
		// press force sync button
		WebElement fsButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"\"]"));
		fsButton.click();
	}
	
	public static void close_app() {
		// close the app
        driver.quit();
	}
}
