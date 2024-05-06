package com.yeasin.appium_qms;

import java.net.MalformedURLException;

import org.testng.annotations.Test;

public class QmsOfflineRepair extends QmsMain {
	@Test(priority = 1)
	public static void setup_call() throws MalformedURLException {
		setup();
	}
	
	@Test(priority = 2)
	public static void login_call() throws MalformedURLException {
		login();
	}
	
	@Test(priority = 3)
	public static void side_menu_call() {
		side_menu();
	}
	
	@Test(priority = 4)
	public static void sync_web_call() {
		sync_web();
	}
	
	@Test(priority = 5)
	public static void set_line_call() throws InterruptedException {
		set_line();
	}
	
	@Test(priority = 6)
	public static void select_order_call() {
		select_order();
	}
	
	@Test(priority = 7)
	public static void choose_variance_call() throws InterruptedException {
		choose_variance();
		driver.toggleWifi();
	}
	
	@Test(priority = 8)
	public static void repair_mode_on_call() throws InterruptedException {
		repair_mode_on();
	}
	
	@Test(priority = 9)
	public static void pass_call() throws InterruptedException {
		pass();
	}
	
	@Test(priority = 10)
	public static void alter_call() throws InterruptedException {
		alter();
	}
	
	@Test(priority = 11)
	public static void reject_call() throws InterruptedException {
		reject();
	}
	
	@Test(priority = 12)
	public static void repair_mode_off_call() throws InterruptedException {
		repair_mode_off();
	}
	
	@Test(priority = 13)
	public static void force_sync_call() throws InterruptedException {
		driver.toggleWifi();
		force_sync();
	}
	
	@Test(priority = 14)
	public static void logout_call() throws InterruptedException {
		side_menu();
		logout();
	}
	
	@Test(priority = 15)
	public static void close_app_call() throws InterruptedException {
		close_app();
	}
}
