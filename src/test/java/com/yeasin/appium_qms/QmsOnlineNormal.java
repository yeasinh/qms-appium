package com.yeasin.appium_qms;

import java.io.IOException;
import java.time.Duration;

import com.yeasin.appium_qms.utilities.DataReader;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	public void set_bso_test() throws IOException, ParseException {
		onNor.set_bso();
	}
	
	@Test(priority = 5)
	public void choose_variance_test() throws InterruptedException {
		onNor.choose_variance();
	}
	
	//@Test(priority = 6, invocationCount = 20)
	/*public static void pass_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.pass_action();
		}
	}*/
	@Test(priority = 6, invocationCount = 40)
	public static void pass_action_test() throws InterruptedException, IOException, ParseException {
			onNor.pass_action();
	}

	@Test(priority = 6)
	public static void pass_and_undo_test() throws InterruptedException{

		try{
				onNor.pass_undo();
			//for(int i = 0; i < iteration; i++) onNor.pass_undo();
//			for (int i = 0; i <iteration;i++) onNor.undo_action();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Test(priority = 7)
	public void alter_action_test() throws InterruptedException, IOException, ParseException{
			onNor.alter_action();
	}

	@Test(priority = 7, invocationCount = 2)
	public void alter_and_undo_test() throws InterruptedException{
		try{
				onNor.alter_undo();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/*@Test(priority = 7)
	public void alter_action_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.alter_action();
		}
	}*/
	
	@Test(priority = 8)
	public void reject_action_test() throws InterruptedException, IOException, ParseException {
		for(int i = 0; i < iteration; i++) {
			onNor.reject_action();
		}
	}

	@Test(priority = 8)
	public void reject_and_undo() throws InterruptedException {
		try{
			for (int i = 0;i < iteration; i++) {
				onNor.reject_undo();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/*public static void pass_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.pass_undo();
		}
	}*/
	//@Test(priority = 6, invocationCount = 20)


	
	/*@Test(priority = 7, invocationCount = 10)
	public static void alter_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.alter_undo();
		}
	}*/
	/*
	@Test(priority = 8)
	public static void reject_undo_test() throws InterruptedException {
		for(int i = 0; i < iteration; i++) {
			onNor.reject_undo();
		}
	}*/
	
	@Test(priority = 9)
	public void force_sync_test() throws InterruptedException {
		onNor.force_sync();
	}
	
	@AfterTest
	public void log_out_test() throws InterruptedException {
		//onNor.expand_side_menu();
		onNor.log_out();
	}
}
