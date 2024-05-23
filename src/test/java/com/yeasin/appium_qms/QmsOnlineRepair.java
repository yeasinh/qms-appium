package com.yeasin.appium_qms;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class QmsOnlineRepair extends QmsBaseTest {
	static QmsBaseTest onRep = new QmsBaseTest();
	
	@BeforeTest
	public void log_in_test() throws IOException, ParseException {
		onRep.log_in();
	}
	
	@Test(priority = 3)
	public void expand_side_menu_test() {
		onRep.expand_side_menu();
	}
	
	@Test(priority = 4)
	public void sync_web_test() {
		onRep.sync_web();
	}
	
	@Test(priority = 5)
	public void set_lid_test() throws InterruptedException {
		onRep.set_lid();
	}
	
	@Test(priority = 6)
	public void set_bso_test() {
		onRep.set_bso();
	}
	
	@Test(priority = 7)
	public void choose_variance_test() throws InterruptedException {
		onRep.choose_variance();
	}
	
	@Test(priority = 8)
	public void repair_mode_on_test() throws InterruptedException {
		onRep.repair_mode_on();
	}
	
	@Test(priority = 9)
	public static void pass_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onRep.pass_action();
		}
	}
	
	@Test(priority = 10)
	public void alter_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onRep.alter_action();
		}
	}
	
	@Test(priority = 11)
	public void reject_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onRep.reject_action();
		}
	}
	
	@Test(priority = 9)
	public static void pass_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onRep.pass_undo();
		}
	}
	
	@Test(priority = 10)
	public static void alter_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onRep.alter_undo();
		}
	}
	
	@Test(priority = 11)
	public static void reject_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onRep.reject_undo();
		}
	}
	
	@Test(priority = 12)
	public void repair_mode_off_test() throws InterruptedException {
		onRep.repair_mode_off();
	}
	
	@Test(priority = 13)
	public void force_sync_test() throws InterruptedException {
		onRep.force_sync();
	}
	
	@AfterTest
	public void log_out_test() throws InterruptedException {
		onRep.expand_side_menu();
		onRep.log_out();
	}
}
