package csc.hooks;

import com.codeborne.selenide.Selenide;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import io.qameta.allure.Allure;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

//@Epic("UI Verification")
//@Feature("Verifying UI Elements")
public class VisualsValidate {


    @Test
    @Story("Compare with Template")
    public void compareWithTemplate(String name) throws IOException {
        assertScreen(name);
    }

    public void assertScreen(String name) throws IOException {
        String expectedFileName = name + ".png";
        String expectedScreenDir = "src/test/resources/testData/screenElements/";

        File actualScreenshot = Selenide.screenshot(OutputType.FILE);
        File expectedScreenshot = new File(expectedScreenDir + expectedFileName);

        if (!expectedScreenshot.exists()) {
            Allure.addAttachment("actual", Files.newInputStream(Objects.requireNonNull(actualScreenshot).toPath()));
            throw new IllegalArgumentException("Can't assert image, because there is no reference." +
                    "Actual screen can be downloaded from allure");
        }
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedScreenDir + expectedFileName);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualScreenshot.toPath().toString());

        File resultDestination = new File("build/diffs/diff_" + expectedFileName);
        ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage, resultDestination);
        ImageComparisonResult result = imageComparison.compareImages();

        if (!result.getImageComparisonState().equals(ImageComparisonState.MATCH)) {
            Allure.addAttachment("actual", Files.newInputStream(Objects.requireNonNull(actualScreenshot).toPath()));
            Allure.addAttachment("expected", Files.newInputStream(Objects.requireNonNull(expectedScreenshot).toPath()));
            Allure.addAttachment("diff", Files.newInputStream(Objects.requireNonNull(resultDestination).toPath()));

        }
        Assertions.assertEquals(ImageComparisonState.MATCH, result.getImageComparisonState());
    }

}
