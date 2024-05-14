package com.yeasin.appium_qms;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.yeasin.appium_qms.utilities.QmsMain;

public class QmsOnlineNormal extends QmsMain {
	static QmsMain onNor = new QmsMain();
	
	@Test(priority = 1)
	public void set_up_test() throws MalformedURLException {
		onNor.set_up();
	}
	
	@Test(priority = 2)
	public void log_in_test() throws IOException, ParseException {
		onNor.log_in();
	}
	
	@Test(priority = 3)
	public void expand_side_menu_test() {
		onNor.expand_side_menu();
	}
	
	@Test(priority = 4)
	public void sync_web_test() {
		onNor.sync_web();
	}
	
	@Test(priority = 5)
	public void set_line_test() throws InterruptedException {
		onNor.set_line();
	}
	
	@Test(priority = 6)
	public void select_order_test() {
		onNor.select_order();
	}
	
	@Test(priority = 7)
	public void choose_variance_test() throws InterruptedException {
		onNor.choose_variance();
	}
	
	@Test(priority = 8)
	public static void pass_action_test() throws InterruptedException {
		onNor.pass_action();
	}
	
	@Test(priority = 9)
	public void alter_action_test() throws InterruptedException {
		onNor.alter_action();
	}
	
	@Test(priority = 10)
	public void reject_action_test() throws InterruptedException {
		onNor.reject_action();
	}
	
	@Test(priority = 11)
	public void force_sync_test() throws InterruptedException {
		onNor.force_sync();
	}
	
	@Test(priority = 12)
	public void log_out_test() throws InterruptedException {
		onNor.expand_side_menu();
		onNor.log_out();
	}
	
	@Test(priority = 13)
	public void close_down_test() throws InterruptedException {
		onNor.close_down();
	}
}
