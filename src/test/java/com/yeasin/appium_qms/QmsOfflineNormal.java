package com.yeasin.appium_qms;

import java.net.MalformedURLException;

import org.testng.annotations.Test;

public class QmsOfflineNormal extends QmsMain {
	static QmsMain offNor = new QmsMain();
	
	@Test(priority = 1)
	public void set_up_test() throws MalformedURLException {
		offNor.set_up();
	}
	
	@Test(priority = 2)
	public void login_test() throws MalformedURLException {
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
	public void pass_action_test() throws InterruptedException {
		offNor.pass_action();
	}
	
	@Test(priority = 9)
	public void alter_action_test() throws InterruptedException {
		offNor.alter_action();
	}
	
	@Test(priority = 10)
	public void reject_action_test() throws InterruptedException {
		offNor.reject_action();
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
