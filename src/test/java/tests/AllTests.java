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
import java.util.Arrays;
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
    public void login(){
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

    @Test
    public void buyProduct(){

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        driver.findElement(AppiumBy.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")).click();
        driver.findElement(AppiumBy.accessibilityId("test-Cart")).click();
        driver.findElement(AppiumBy.accessibilityId("test-CHECKOUT")).click();
        driver.findElement(AppiumBy.accessibilityId("test-First Name")).sendKeys("John");
        driver.findElement(AppiumBy.accessibilityId("test-Last Name")).sendKeys("Walt");
        driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code")).sendKeys("256891");
        driver.findElement(AppiumBy.accessibilityId("test-CONTINUE")).click();

        swipeAction(finger, new Point(533, 1154), new Point(535, 927));


        performTap(finger, new Point(533, 2203));  // Last Button
        performTap(finger, new Point(585, 1645));  // Final Button
    }

    @Test
    public void logout(){
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
        driver.findElement(AppiumBy.accessibilityId("test-LOGOUT")).click();
    }


    public Point getCenterOfLocation(Point location, Dimension size){
        return new Point(location.getX()+ size.getWidth()/2, location.getY()+size.getHeight()/2);

    }

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


    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

    }

}
