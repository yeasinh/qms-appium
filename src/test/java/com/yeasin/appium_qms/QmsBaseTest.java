package com.yeasin.appium_qms;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.yeasin.appium_qms.utilities.QmsMain;

public class QmsBaseTest extends QmsMain {
	static QmsMain qmsBase = new QmsMain();
	
	@BeforeSuite
	public void set_up_test() throws IOException {
		qmsBase.start_server();
		qmsBase.set_up();
	}
	
	@AfterSuite
	public void close_down_test() throws InterruptedException {
		qmsBase.close_down();
	}
}
