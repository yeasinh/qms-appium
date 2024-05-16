package com.yeasin.appium_qms;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.yeasin.appium_qms.utilities.QmsMain;

public class QmsOfflineRepair extends QmsMain {
	static QmsMain offRep = new QmsMain();
	
	@Test(priority = 1)
	public void set_up_test() throws MalformedURLException {
		offRep.set_up();
	}
	
	@Test(priority = 2)
	public void log_in_test() throws IOException, ParseException {
		offRep.log_in();
	}
	
	@Test(priority = 3)
	public void expand_side_menu_test() {
		offRep.expand_side_menu();
	}
	
	@Test(priority = 4)
	public void sync_web_test() {
		offRep.sync_web();
	}
	
	@Test(priority = 5)
	public void set_line_test() throws InterruptedException {
		offRep.set_line();
	}
	
	@Test(priority = 6)
	public void select_order_test() {
		offRep.select_order();
	}
	
	@Test(priority = 7)
	public void choose_variance_test() throws InterruptedException {
		offRep.choose_variance();
		driver.toggleWifi();
	}
	
	@Test(priority = 8)
	public void repair_mode_on_test() throws InterruptedException {
		offRep.repair_mode_on();
	}
	
	@Test(priority = 9)
	public static void pass_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offRep.pass_action();
		}
	}
	
	@Test(priority = 10)
	public void alter_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offRep.alter_action();
		}
	}
	
	@Test(priority = 11)
	public void reject_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offRep.reject_action();
		}
	}
	/*
	@Test(priority = 9)
	public static void pass_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offRep.pass_undo();
		}
	}
	
	@Test(priority = 10)
	public static void alter_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offRep.alter_undo();
		}
	}
	
	@Test(priority = 11)
	public static void reject_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			offRep.reject_undo();
		}
	}
	*/
	@Test(priority = 12)
	public void repair_mode_off_test() throws InterruptedException {
		offRep.repair_mode_off();
	}
	
	@Test(priority = 13)
	public void force_sync_test() throws InterruptedException {
		driver.toggleWifi();
		offRep.force_sync();
	}
	
	@Test(priority = 14)
	public void log_out_test() throws InterruptedException {
		offRep.expand_side_menu();
		offRep.log_out();
	}
	
	@Test(priority = 15)
	public void close_down_test() throws InterruptedException {
		offRep.close_down();
	}
}
