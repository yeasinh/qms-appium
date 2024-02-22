package com.yeasin.appium_qms;

public class EndTest extends BaseTest {
	public void close_appium() {
		driver.quit();
		service.stop();
	}
}
