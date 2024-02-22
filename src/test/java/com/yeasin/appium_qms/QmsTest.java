package com.yeasin.appium_qms;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class QmsTest extends BaseTest {
	@Test
	public void basic_test() throws MalformedURLException {
		configure_appium();
		close_appium();
	}
}
