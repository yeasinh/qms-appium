package com.yeasin.appium_qms;

import java.net.MalformedURLException;

import org.testng.annotations.Test;

public class QmsOnlineNormal extends QmsMain {
	@Test(priority = 1)
	public static void set_up_test() throws MalformedURLException {
		set_up();
	}
	
	@Test(priority = 2)
	public static void login_test() throws MalformedURLException {
		log_in();
	}
	
	@Test(priority = 3)
	public static void expand_side_menu_test() {
		expand_side_menu();
	}
	
	@Test(priority = 4)
	public static void sync_web_test() {
		sync_web();
	}
	
	@Test(priority = 5)
	public static void set_line_test() throws InterruptedException {
		set_line();
	}
	
	@Test(priority = 6)
	public static void select_order_test() {
		select_order();
	}
	
	@Test(priority = 7)
	public static void choose_variance_test() throws InterruptedException {
		choose_variance();
	}
	
	@Test(priority = 8)
	public static void pass_action_test() throws InterruptedException {
		pass_action();
	}
	
	@Test(priority = 9)
	public static void alter_action_test() throws InterruptedException {
		alter_action();
	}
	
	@Test(priority = 10)
	public static void reject_action_test() throws InterruptedException {
		reject_action();
	}
	
	@Test(priority = 11)
	public static void force_sync_test() throws InterruptedException {
		force_sync();
	}
	
	@Test(priority = 12)
	public static void log_out_test() throws InterruptedException {
		expand_side_menu();
		log_out();
	}
	
	@Test(priority = 13)
	public static void close_down_test() throws InterruptedException {
		close_down();
	}
}
