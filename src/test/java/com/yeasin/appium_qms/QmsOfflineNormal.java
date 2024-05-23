package com.yeasin.appium_qms;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class QmsOfflineNormal extends QmsBaseTest {
	static QmsBaseTest offNor = new QmsBaseTest();
	
	@BeforeTest
	public void log_in_test() throws IOException, ParseException {
		offNor.log_in();
	}
	
	@Test(priority = 3)
	public void expand_side_menu_test() {
		offNor.expand_side_menu();
	}
	
	@Test(priority = 4)
	public void sync_web_test() {
		offNor.sync_web();
	}
	
	@Test(priority = 5)
	public void set_lid_test() throws InterruptedException {
		offNor.set_lid();
	}
	
	@Test(priority = 6)
	public void set_bso_test() {
		offNor.set_bso();
	}
	
	@Test(priority = 7)
	public void choose_variance_test() throws InterruptedException {
		offNor.choose_variance();
		driver.toggleWifi();
	}
	
	@Test(priority = 8)
	public static void pass_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offNor.pass_action();
		}
	}
	
	@Test(priority = 9)
	public void alter_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offNor.alter_action();
		}
	}
	
	@Test(priority = 10)
	public void reject_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offNor.reject_action();
		}
	}
	
	@Test(priority = 8)
	public static void pass_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offNor.pass_undo();
		}
	}
	
	@Test(priority = 9)
	public static void alter_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offNor.alter_undo();
		}
	}
	
	@Test(priority = 10)
	public static void reject_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offNor.reject_undo();
		}
	}
	
	@Test(priority = 11)
	public void force_sync_test() throws InterruptedException {
		driver.toggleWifi();
		offNor.force_sync();
	}
	
	@AfterTest
	public void log_out_test() throws InterruptedException {
		offNor.expand_side_menu();
		offNor.log_out();
	}
}
