package csc.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public static SelenideElement loginField() {
        return $(byId("username"));
    }

    public static SelenideElement passwordField() {
        return $(byId("password"));
    }

    public static SelenideElement submitButton() {
        return $(byCssSelector("button"));
    }

    public static SelenideElement message(String result) {
        return $(byXpath("//*[contains(text(), '" + result + "')]"));
    }


}
