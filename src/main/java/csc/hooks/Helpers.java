package csc.hooks;

import com.codeborne.selenide.SelenideElement;

import java.text.DateFormatSymbols;

public class Helpers {

    public static String getMonthForInt(int num) {
        num = num - 1;
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    public static void waitForElementDisappear(SelenideElement element) throws InterruptedException {
        while (element.isDisplayed()) {
            Thread.sleep(2000);
        }
    }

    public static void waitForElementAppear(SelenideElement element) throws InterruptedException {
        while (!element.isDisplayed()) {
            Thread.sleep(2000);
        }
    }

}
