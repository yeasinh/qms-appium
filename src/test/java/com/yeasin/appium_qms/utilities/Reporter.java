package com.yeasin.appium_qms.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reporter {
	static ExtentReports extent;
	
	public static ExtentReports getReporter()
	{
		// Create an ExtentSparkReporter instance and specify the path to the report
		ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\java\\com\\yeasin\\appium_qms\\reports\\qms_test_report.html");
		reporter.config().setReportName("QMS Automation Results");
		reporter.config().setDocumentTitle("QMS Test Results");
		
		// Create an ExtentReports instance and attach the ExtentSparkReporter instance to it
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "M. Yeasin Hossain");
		return extent;
	}
}