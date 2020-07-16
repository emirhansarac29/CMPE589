import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.appium.java_client.TouchAction;
import javafx.stage.Screen;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.plaf.synth.Region;

@GraphWalker(value = "random(edge_coverage(100))", start = "v_WelcomePage_1")
//@GraphWalker(value = "a_star(reached_vertex(v_MyMusicRecent))", start = "v_WelcomePage_1")
public class Genius_Part_Three extends ExecutionContext implements genius_part3 {

    private AndroidDriver<WebElement> driver;

    @Override
    public void v_WelcomePage_1() {
        System.out.println("PAGE AT v_WelcomePage_1");
        Assert.assertTrue(driver.findElementById("com.genius.android:id/title").getText().equals("Welcome to Genius."));
    }
    @Override
    public void v_WelcomePage_2() {
        System.out.println("PAGE AT v_WelcomePage_2");
        Assert.assertTrue(driver.findElementById("com.genius.android:id/title").getText().equals("Tap highlighted lines to read annotations"));
    }
    @Override
    public void v_WelcomePage_3() {
        System.out.println("PAGE AT v_WelcomePage_3");
        boolean t1 = driver.findElementById("com.genius.android:id/text1").isDisplayed();
        boolean t2 = driver.findElementById("com.genius.android:id/text2").isDisplayed();
        boolean t3 = driver.findElementById("com.genius.android:id/text3").isDisplayed();
        Assert.assertTrue(t1 && t2 && t3);
    }
    @Override
    public void v_WelcomePage_4() {
        System.out.println("PAGE AT v_WelcomePage_4");
        Assert.assertTrue(driver.findElementById("com.genius.android:id/title").getText().equals("Welcome"));
    }
    @Override
    public void v_SignUpPage() {
        System.out.println("PAGE AT v_SignUpPage");
        Assert.assertTrue(driver.findElementById("com.genius.android:id/username").getText().equals("Username"));
        Assert.assertTrue(driver.findElementById("com.genius.android:id/email").getText().equals("Email"));
        Assert.assertTrue(driver.findElementById("com.genius.android:id/password").getText().equals("Password"));
    }
    @Override
    public void v_LoginPage() {
        System.out.println("PAGE AT v_LoginPage");
        Assert.assertTrue(driver.findElementById("com.genius.android:id/username").getText().equals("Username"));
        Assert.assertTrue(driver.findElementById("com.genius.android:id/password").getText().equals("Password"));
    }
    @Override
    public void v_AccountStreamPage() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PAGE AT v_AccountStreamPage");
        WebElement el = driver.findElementByAccessibilityId("Navigate up");
        Assert.assertTrue(driver.findElementByAccessibilityId("Navigate up").isDisplayed());

    }

    @Override
    public void e_swipe_right_1() {
        WebElement scrollable = driver.findElementByClassName("androidx.viewpager.widget.ViewPager");
        Point value = scrollable.getLocation();
        int height = scrollable.getSize().getHeight();
        int width = scrollable.getSize().getWidth();
        TouchAction action = new TouchAction(driver);
        action.press(value.x + width - 1 ,(value.y + height)/2).waitAction(1000).moveTo(value.x + 1,(value.y + height)/2).release().perform();
    }
    @Override
    public void e_swipe_right_2() {
        WebElement scrollable = driver.findElementByClassName("androidx.viewpager.widget.ViewPager");
        Point value = scrollable.getLocation();
        int height = scrollable.getSize().getHeight();
        int width = scrollable.getSize().getWidth();
        TouchAction action = new TouchAction(driver);
        action.press(value.x + width - 1 ,(value.y + height)/2).waitAction(1000).moveTo(value.x + 1,(value.y + height)/2).release().perform();
    }
    @Override
    public void e_swipe_right_3() {
        WebElement scrollable = driver.findElementByClassName("androidx.viewpager.widget.ViewPager");
        Point value = scrollable.getLocation();
        int height = scrollable.getSize().getHeight();
        int width = scrollable.getSize().getWidth();
        TouchAction action = new TouchAction(driver);
        action.press(value.x + width - 1 ,(value.y + height)/2).waitAction(1000).moveTo(value.x + 1,(value.y + height)/2).release().perform();
    }
    @Override
    public void e_sign_up() {
        driver.findElementById("com.genius.android:id/onboarding_sign_up").click();

    }
    @Override
    public void e_have_account() {
        driver.findElementById("com.genius.android:id/switch_state").click();
    }
    @Override
    public void e_enter_credentials() {
        driver.findElementById("com.genius.android:id/username").sendKeys("eses2929");
        driver.findElementById("com.genius.android:id/password").sendKeys("123qweasd");
        driver.findElementById("com.genius.android:id/submit").click();
    }

    @BeforeExecution
    public void setup() {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "src/main/resources");
        File app = new File(appDir, "genius.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Nexus_4_API_23");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.genius.android");
        capabilities.setCapability("appActivity", ".MainActivity");
        try {
            driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @AfterExecution
    public void tearDown() {
        driver.quit();
    }
}