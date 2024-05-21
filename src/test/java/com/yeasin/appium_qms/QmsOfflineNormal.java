package com.yeasin.appium_qms;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.yeasin.appium_qms.utilities.QmsMain;

public class QmsOfflineNormal extends QmsMain {
	static QmsMain offNor = new QmsMain();
	
	@Test(priority = 1)
	public void set_up_test() throws IOException {
		offNor.set_up();
	}
	
	@Test(priority = 2)
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
	public void set_line_test() throws InterruptedException {
		offNor.set_line();
	}
	
	@Test(priority = 6)
	public void select_order_test() {
		offNor.select_order();
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
	
	@Test(priority = 12)
	public void log_out_test() throws InterruptedException {
		offNor.expand_side_menu();
		offNor.log_out();
	}
	
	@Test(priority = 13)
	public void close_down_test() throws InterruptedException {
		offNor.close_down();
	}
}
