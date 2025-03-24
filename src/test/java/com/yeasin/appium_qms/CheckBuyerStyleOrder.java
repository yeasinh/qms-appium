package com.yeasin.appium_qms;

import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckBuyerStyleOrder extends QmsBaseTest{
    static QmsBaseTest checkBSO = new QmsBaseTest();

    @BeforeTest
    public void log_in_test() throws IOException, ParseException {
        checkBSO.log_in();
    }

    @Test(priority = 1)
    public void expand_side_menu_test() {
        checkBSO.expand_side_menu();
    }

    @Test(priority = 2)
    public void sync_web_test() {
        checkBSO.sync_web();
    }

    @Test(priority = 3)
    public void set_lid_test() throws InterruptedException {
        checkBSO.set_lid();
    }

    @Test(priority = 4)
    public void set_bso_test() throws IOException, ParseException {
        checkBSO.set_bso();
    }
    @AfterTest
    public void go_back_test() {
        checkBSO.goBack();
    }
}
