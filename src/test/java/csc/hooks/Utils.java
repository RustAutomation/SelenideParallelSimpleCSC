package csc.hooks;

import com.codeborne.selenide.Selenide;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Utils {

    /**
     * Метод вызывает остановку потока на указанное количество миллисекунд
     *
     * @param millis длительность остановки в миллисекундах
     */
    public static void freeze(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException e) {
            throw new AutotestError("Слип сломался", e);
        }
    }

    /**
     * Метод позволяет выполнить произвольный JS код над элементом
     *
     * @param script  Javascript, например "arguments[0].innerHTML = 'Abracadabra'"
     * @param element WebElement
     * @return результат выполнения скрипта
     */
    public static Object executeJS(final String script, final WebElement element) {
        return ((JavascriptExecutor) Selenide.webdriver().driver().getWebDriver()).executeScript(script, element);
    }

    public static Object executeJS(final String script) {
        return ((JavascriptExecutor) Selenide.webdriver().driver().getWebDriver()).executeScript(script);
    }

    /**
     * Конвертор строки в булево значение. Вернёт true если в него передать "да" или "true"
     * в любом регистре, иначе false
     *
     * @param state логическое состояние в виде строки
     * @return булево значение
     */
    public static boolean getBoolean(final String state) {
        final boolean isStateValid = "да".equalsIgnoreCase(state)
                || "true".equalsIgnoreCase(state)
                || "нет".equalsIgnoreCase(state)
                || "false".equalsIgnoreCase(state);
        Assert.assertTrue("Значение должно быть Да, TRUE, Нет, FALSE в любом регистре", isStateValid);
        return "да".equalsIgnoreCase(state) || "true".equalsIgnoreCase(state);
    }

    public static boolean empty(final String value) {
        return value == null || value.isEmpty();
    }


    public static Integer getNumber(final String param) {
        boolean isInteger = true;
        for (final char s : param.toCharArray()) {
            if (((int) s < 48 || (int) s > 57) && s != '-') {
                isInteger = false;
                break;
            }
        }
        return isInteger ? Integer.valueOf(param) : null;
    }

    /**
     * Метод вынимает целочисленные значения из текстовой строки по его порядковому номеру в этой строке
     * Например в строке: Ещё 95 очков до 6-го уровня, всего 245 очков опыта
     * по порядковому номеру "3" будет получено значение "245"
     *
     * @param stringWithNumber текстовая строка содержащая числовые значения в произвольном месте
     * @param numberPosition порядковый номер числового значения
     * @return Integer значение числа
     */
    public static int extractNumberFromString(final String stringWithNumber, final int numberPosition) {
        final String[] parts = stringWithNumber.split("[a-zA-Zа-яёА-ЯЁ\\s{}\\[\\]!@#$%^&*().,<>/|\\\\'\"?]+");
        final String value = Stream.of(parts)
                .map(v -> v.replaceAll("\\D*$", ""))
                .filter(v -> v.matches("[-+]*\\d+"))
                .collect(Collectors.toList())
                .get(numberPosition - 1);
        return Integer.parseInt(value);
    }
}