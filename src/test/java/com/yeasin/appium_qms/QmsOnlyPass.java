package com.yeasin.appium_qms;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.yeasin.appium_qms.QmsOnlineNormal.onNor;

public class QmsOnlyPass extends QmsBaseTest {
    static QmsBaseTest onNor = new QmsBaseTest();

    @Test(priority = 1, invocationCount = 200)
    public static void pass_action_test() throws InterruptedException, IOException, ParseException {
        onNor.pass_action();
    }
}
