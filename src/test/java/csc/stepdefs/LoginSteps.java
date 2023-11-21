package csc.stepdefs;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import csc.hooks.VisualsValidate;
import csc.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class LoginSteps {
    public WebDriverRunner driverSelenide;
    public static ThreadLocal<WebDriverRunner> tdriverSelenide = new ThreadLocal<WebDriverRunner>();
    public String url;

    public static synchronized WebDriverRunner getSeleideDriver() {
        return tdriverSelenide.get();
    }

    public WebDriverRunner initialize_driver_selenium_gitlab(String browser) {
        System.out.println("\n Gitlab configuration launched in browser " + browser);
        if (browser.toLowerCase().contains("firefox")) {
            Configuration.browser = "firefox";

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--disable-dev-shm-usage");
            firefoxOptions.addArguments("--no-sandbox");
            firefoxOptions.addArguments("--headless");
            firefoxOptions.addArguments("--port=4444");
            firefoxOptions.addArguments("--whitelisted-ips");
            firefoxOptions.addArguments("--allowed-origins=*");
            firefoxOptions.addArguments("--window-size=1920,1080");

            firefoxOptions.addArguments("webdriver.gecko.driver", "geckodriver");
//            FirefoxDriverManager.firefoxdriver().arch64().setup();
//            WebDriver driver = new FirefoxDriver(firefoxOptions);//for del
            WebDriverRunner.setWebDriver(new FirefoxDriver(firefoxOptions));
            driverSelenide = new WebDriverRunner();
            tdriverSelenide.set(driverSelenide);
        } else if (browser.toLowerCase().contains("chrome")) {
//            System.setProperty("webdriver.chrome.whitelistedIps", "");
            Configuration.browser = "chromium-browser";

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--port=4444");
            chromeOptions.addArguments("--whitelisted-ips");
            chromeOptions.addArguments("--allowed-origins=*");
            chromeOptions.addArguments("--window-size=1920,1080");

            chromeOptions.addArguments("chromedriverExecutable", "chromedriver");
//            WebDriver driver = new ChromeDriver(chromeOptions);
            WebDriverRunner.setWebDriver(new ChromeDriver(chromeOptions));
            driverSelenide = new WebDriverRunner();
            tdriverSelenide.set(driverSelenide);
        }

        return getSeleideDriver();
    }

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        Selenide.sleep(2000);//needs to stabilize driver initialization
        this.url = url;
        open(url);
        Selenide.webdriver().driver().getWebDriver().manage().window().maximize();
    }

    @And("^Я жду \"(.*)\" мсек$")
    public void waitMin(int sec) throws InterruptedException {
        Thread.sleep(sec);
    }

    @And("I login with the username {string} and password {string}")
    public void iLogin(String username, String password) throws InterruptedException, IOException {
        while (!LoginPage.loginField().isDisplayed()) {
            Thread.sleep(500);
        }
        Thread.sleep(5000);
        LoginPage.loginField().sendKeys(username);
        LoginPage.passwordField().sendKeys(password);

    }

    @And("I click Submit")
    public void iPressSubmit() {
        LoginPage.submitButton().scrollTo();
        LoginPage.submitButton().click();
    }

    @Then("I should be see the message {string}")
    public void iShouldSee(String result) {
        try {
            LoginPage.message(result).should(Condition.exist);
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + result + "\" not available in results");
        }
    }

    @Тогда("Я проверяю верстку {string}")
    public void яПроверяюВерстку(String name) {
        VisualsValidate visualsValidate = new VisualsValidate();
        visualsValidate.compareWithTemplate(name);
    }

    @Когда("Я захожу на {string}")
    public void яЗахожуНа(String url) {
        open(url);
    }

    @И("Я захожу с пользователем {string} и паролем {string}")
    public void яЗахожуСПользователемИПаролем(String username, String password) {

        $(byId("username")).sendKeys(username);
        $(byId("password")).sendKeys(password);
    }

    @И("Я нажимаю Submit")
    public void яНажимаюSubmit() {
        $(byCssSelector("button")).scrollTo();
        $(byCssSelector("button")).click();
    }

    @Затем("Я вижу сообщение {string}")
    public void яВижуСообщение(String result) {
        try {
            $(byXpath("//*[contains(text(), '" + result + "')]")).should(Condition.exist);
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + result + "\" not available in results");
        }
    }

}

