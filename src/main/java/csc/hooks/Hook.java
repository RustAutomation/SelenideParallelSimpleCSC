package csc.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.*;
import io.qameta.allure.Allure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Hook {

    @Before
    public void setUp() {
        Configuration.browserSize = "1920x1080";
        System.setProperty("chromeoptions.args", "--remote-allow-origins=*");
//        Configuration.headless = true;
    }

    @AfterStep
    public void addScreenshotOnFailed(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                Allure.addAttachment("Failed step", new FileInputStream(Objects.requireNonNull(Screenshots.takeScreenShotAsFile())));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("Fail add screenShot: " + fileNotFoundException.getMessage());
            }
        }
    }

    @After
    public void tearDown() {
        WebDriverRunner.getWebDriver().close();
    }
}
