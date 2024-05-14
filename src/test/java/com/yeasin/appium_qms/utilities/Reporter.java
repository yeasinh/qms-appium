package com.yeasin.appium_qms.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reporter {
	static ExtentReports extent;
	
	public static ExtentReports getReporter()
	{
		// String path = System.getProperty("user.dir")+"//reports//test_report.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\java\\com\\yeasin\\appium_qms\\reports\\qms_test_report.html");
		reporter.config().setReportName("QMS Automation Results");
		reporter.config().setDocumentTitle("QMS Test Results");
		
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "M. Yeasin Hossain");
		return extent;
	}
}