package csc.hooks;

import com.codeborne.selenide.Selenide;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import io.qameta.allure.Attachment;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Epic("UI Verification")
@Feature("Verifying UI Elements")
public class VisualsValidate {


    @Test
    @Story("Compare with Template")
    public void compareWithTemplate(String name) {
        assertScreen(name);
    }

    public void assertScreen(String name) {
        String expectedFileName = name + ".png";
        String expectedScreenDir = "src/test/resources/testData/screenElements/";

        File actualScreenshot = Selenide.screenshot(OutputType.FILE);
        File expectedScreenshot = new File(expectedScreenDir + expectedFileName);

        if(!expectedScreenshot.exists()) {
            addImgToAllure("actual", actualScreenshot);
            throw new IllegalArgumentException("Can't assert image, because there is no reference." +
                    "Actual screen can be downloaded from allure");
        }
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedScreenDir + expectedFileName);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualScreenshot.toPath().toString());

        File resultDestination = new File("build/diffs/diff_" + expectedFileName);
        ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage, resultDestination);
        ImageComparisonResult result = imageComparison.compareImages();

        if(!result.getImageComparisonState().equals(ImageComparisonState.MATCH)) {
            addImgToAllure("actual", actualScreenshot);
            addImgToAllure("expected", expectedScreenshot);
            addImgToAllure("diff", resultDestination);
        }
        Assertions.assertEquals(ImageComparisonState.MATCH, result.getImageComparisonState());
    }

    private void addImgToAllure(String name, File file) {
        try {
            byte[] image = Files.readAllBytes(file.toPath());
            saveScreenshot(name, image);
        } catch (IOException e) {
            throw new RuntimeException("Can't read bytes");
        }
    }

    @Attachment(value = "{name}", type = "image/png")
    private static byte[] saveScreenshot(String name, byte[] image) {
        return image;
    }

}
