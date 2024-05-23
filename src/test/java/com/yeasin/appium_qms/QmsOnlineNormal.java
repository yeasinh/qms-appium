package com.yeasin.appium_qms;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class QmsOnlineNormal extends QmsBaseTest {
	static QmsBaseTest onNor = new QmsBaseTest();
	
	@BeforeTest
	public void log_in_test() throws IOException, ParseException {
		onNor.log_in();
	}
	
	@Test(priority = 1)
	public void expand_side_menu_test() {
		onNor.expand_side_menu();
	}
	
	@Test(priority = 2)
	public void sync_web_test() {
		onNor.sync_web();
	}
	
	@Test(priority = 3)
	public void set_lid_test() throws InterruptedException {
		onNor.set_lid();
	}
	
	@Test(priority = 4)
	public void set_bso_test() {
		onNor.set_bso();
	}
	
	@Test(priority = 5)
	public void choose_variance_test() throws InterruptedException {
		onNor.choose_variance();
	}
	
	@Test(priority = 6)
	public static void pass_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.pass_action();
		}
	}
	
	@Test(priority = 7)
	public void alter_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.alter_action();
		}
	}
	
	@Test(priority = 8)
	public void reject_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.reject_action();
		}
	}
	
	@Test(priority = 6)
	public static void pass_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.pass_undo();
		}
	}
	
	@Test(priority = 7)
	public static void alter_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.alter_undo();
		}
	}
	
	@Test(priority = 8)
	public static void reject_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.reject_undo();
		}
	}
	
	@Test(priority = 9)
	public void force_sync_test() throws InterruptedException {
		onNor.force_sync();
	}
	
	@AfterTest
	public void log_out_test() throws InterruptedException {
		onNor.expand_side_menu();
		onNor.log_out();
	}
}
