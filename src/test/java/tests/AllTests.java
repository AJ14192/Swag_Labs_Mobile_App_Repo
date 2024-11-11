package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;

public class AllTests {
    static AppiumDriver driver;

    @Before
    public void openMobile(){


        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appium:deviceName", "motorola edge 40");
        caps.setCapability("appium:udid", "ZD222CJ8CL");
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:platformVersion", "14");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:appPackage", "com.swaglabsmobileapp");
        caps.setCapability("appium:appActivity", "com.swaglabsmobileapp.MainActivity");

        try {
            driver = new AndroidDriver(getUrl(), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static URL getUrl() throws MalformedURLException {
        return new URL("http://127.0.0.1:4723");
    }

    @Test
    public void Login(){
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        WebElement loginButton = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));
        Point location = loginButton.getLocation();
        Dimension size = loginButton.getSize();

        Point centerOflocation = getCenterOfLocation(location, size);
        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),centerOflocation))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1,Duration.ofMillis(200)))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));

    }

    public Point getCenterOfLocation(Point location, Dimension size){
        return new Point(location.getX()+ size.getWidth()/2, location.getY()+size.getHeight()/2);

    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

    }

}
