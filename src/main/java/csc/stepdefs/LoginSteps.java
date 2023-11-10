package csc.stepdefs;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class LoginSteps {

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        open(url);
        Selenide.webdriver().driver().getWebDriver().manage().window().maximize();
    }

    @And("^Я жду \"(.*)\" мсек$")
    public void waitMin(int sec) throws InterruptedException {
        Thread.sleep(sec);
    }

    @And("I login with the username {string} and password {string}")
    public void iLogin(String username, String password) {

        $(byId("username")).sendKeys(username);
        $(byId("password")).sendKeys(password);
    }

    @And("I click Submit")
    public void iPressSubmit() {
        $(byCssSelector("button")).scrollTo();
        $(byCssSelector("button")).click();
    }

    @Then("I should be see the message {string}")
    public void iShouldSee(String result) {
        try {
            $(byXpath("//*[contains(text(), '" + result + "')]")).should(Condition.exist);
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + result + "\" not available in results");
//        } finally {
//                getWebDriver().quit();
        }
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
//        } finally {
//                getWebDriver().quit();
        }
    }
}

