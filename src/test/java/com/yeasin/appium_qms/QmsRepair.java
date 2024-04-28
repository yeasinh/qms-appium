package com.yeasin.appium_qms;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class QmsRepair extends QmsTest {
	@Test
	public static void basic_prep() throws MalformedURLException, InterruptedException {
        login();
        side_menu();
        sync_web();
        set_line();
        side_menu();
        select_order();
        choose_variance();
	}
	
	@Test
	public static void repair_mode_on() throws InterruptedException {
		// toggle the repair button to turn on repair mode
		WebElement repairOn = driver.findElement(By.xpath("//android.widget.Switch[@text=\"OFF\"]"));
		repairOn.click();
	}
	
	@Test
	public static void repair_mode_off() throws InterruptedException {
		// toggle the repair button to turn off repair
		WebElement repairOff = driver.findElement(By.xpath("//android.widget.Switch[@text=\"ON\"]"));
		repairOff.click();
	}
}
