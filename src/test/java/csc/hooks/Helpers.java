package csc.hooks;

import com.codeborne.selenide.SelenideElement;

import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

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

    public static int getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMdd");
        LocalDateTime now = LocalDateTime.now();
        return Integer.parseInt(dtf.format(now));
    }

    public static boolean equalLists(List<String> a, List<String> b){
        if (a == null && b == null) return true;

        if ((a == null && b!= null) || (a != null && b== null) || (a.size() != b.size()))
        {
            return false;
        }
        // Sort and compare two lists
        Collections.sort(a);
        Collections.sort(b);
        return a.equals(b);
    }

}
