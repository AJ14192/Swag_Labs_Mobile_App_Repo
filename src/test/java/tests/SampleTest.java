package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.Actions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Arrays;

public class SampleTest {

    private AndroidDriver driver;

    @BeforeEach
    public void setUp() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:deviceName", "motorola edge 40");
        capabilities.setCapability("appium:udid", "ZD222CJ8CL");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "14");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:appPackage", "com.swaglabsmobileapp");
        capabilities.setCapability("appium:appActivity", "com.swaglabsmobileapp.MainActivity");
        capabilities.setCapability("appium:ensureWebviewsHavePages", true);
        capabilities.setCapability("appium:nativeWebScreenshot", true);
        capabilities.setCapability("appium:newCommandTimeout", 3600);
        capabilities.setCapability("appium:connectHardwareKeyboard", true);

        try {
            driver = new AndroidDriver(getUrl(), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private URL getUrl() throws MalformedURLException {
        return new URL("http://127.0.0.1:4723");
    }

    @Test
    public void sampleTest() {
        // Reusable finger action setup
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        driver.findElement(AppiumBy.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")).click();
        driver.findElement(AppiumBy.accessibilityId("test-Cart")).click();
        driver.findElement(AppiumBy.accessibilityId("test-CHECKOUT")).click();

        // Filling in form fields
        driver.findElement(AppiumBy.accessibilityId("test-First Name")).sendKeys("James");
        driver.findElement(AppiumBy.accessibilityId("test-Last Name")).sendKeys("Cameron");
        driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code")).sendKeys("789654");

        // More actions
        performTap(finger, new Point(369, 1459));  // Continue Button

        // Swipe Action
        swipeAction(finger, new Point(533, 1154), new Point(535, 927));

        // Final tap action
        performTap(finger, new Point(533, 2203));  // Last Button
        performTap(finger, new Point(585, 1645));  // Final Button
    }

    // Helper method to perform tap actions
    private void performTap(PointerInput finger, Point tapPoint) {
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), tapPoint.x, tapPoint.y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(50)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }

    // Helper method to perform swipe actions
    private void swipeAction(PointerInput finger, Point start, Point end) {
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
