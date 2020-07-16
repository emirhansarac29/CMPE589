import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.appium.java_client.TouchAction;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

@GraphWalker(value = "random(edge_coverage(100))", start = "v_WelcomePage")
//@GraphWalker(value = "a_star(reached_vertex(v_MyMusicRecent))", start = "v_WelcomePage_1")
public class Genius_Part_Two extends ExecutionContext implements genius_part2 {

    private AndroidDriver<WebElement> driver;

    @Override
    public void v_StreamPage(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PAGE AT v_StreamPage");
        Assert.assertTrue(driver.findElementById("com.genius.android:id/summary").getText().contains("See recently"));
        System.out.println(driver.findElementById("com.genius.android:id/summary").getText());
    }
    @Override
    public void v_SideBarPage(){
        System.out.println("PAGE AT v_SideBarPage");
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/android.widget.ListView/android.view.ViewGroup/android.widget.LinearLayout/android.widget.TextView";
        Assert.assertTrue(driver.findElementByXPath(xpath).getText().equals("Tap to Sign Up for Genius"));
    }
    @Override
    public void v_SignUpPage(){
        System.out.println("PAGE AT v_SignUpPage");
        Assert.assertTrue(driver.findElementById("com.genius.android:id/username").getText().equals("Username"));
        Assert.assertTrue(driver.findElementById("com.genius.android:id/email").getText().equals("Email"));
        Assert.assertTrue(driver.findElementById("com.genius.android:id/password").getText().equals("Password"));
    }
    @Override
    public void v_WelcomePage(){
        System.out.println("PAGE AT v_WelcomePage");
        Assert.assertTrue(driver.findElementById("com.genius.android:id/title").getText().equals("Welcome to Genius."));
    }
    @Override
    public void v_LoginPage(){
        System.out.println("PAGE AT v_LoginPage");
        Assert.assertTrue(driver.findElementById("com.genius.android:id/username").getText().equals("Username"));
        Assert.assertTrue(driver.findElementById("com.genius.android:id/password").getText().equals("Password"));
    }
    @Override
    public void v_StreamPageLogged(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PAGE AT v_StreamPageLogged");
        Assert.assertTrue(driver.findElementByAccessibilityId("Navigate up").isDisplayed());
    }
    @Override
    public void v_SideBarPage_LOGGED(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PAGE AT v_SideBarPage_LOGGED");
        //String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/android.widget.ListView/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView";
        //Assert.assertTrue(driver.findElementByXPath(xpath).getText().equals("eses2929"));
    }
    @Override
    public void v_SearchPage(){
        System.out.println("PAGE AT v_SearchPage");
        driver.hideKeyboard();
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[1]/android.widget.RelativeLayout/android.widget.EditText";
        Assert.assertEquals(driver.findElementByXPath(xpath).getText(), "Search Genius");
    }

    @Override
    public void e_SKIP(){
        driver.findElementById("com.genius.android:id/onboarding_skip").click();
    }
    @Override
    public void e_enter_credentials(){
        driver.hideKeyboard();
        driver.findElementById("com.genius.android:id/username").sendKeys("eses2929");
        driver.findElementById("com.genius.android:id/password").sendKeys("123qweasd");
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[4]/android.widget.TextView[2]";
        driver.findElementByXPath(xpath).click();
    }
    @Override
    public void e_sign_up(){
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[5]/android.widget.TextView";
        driver.findElementByXPath(xpath).click();
    }
    @Override
    public void e_side_navigation(){
        String xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]";
        driver.findElementByXPath(xpath).click();
    }
    @Override
    public void e_have_account(){
        driver.findElementById("com.genius.android:id/switch_state").click();
    }
    @Override
    public void e_side_navigation_logged(){
        String xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]";
        driver.findElementByXPath(xpath).click();
    }
    @Override
    public void e_side_navigation_logged_1(){
        String xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]";
        driver.findElementByXPath(xpath).click();
    }
    @Override
    public void e_search_click(){
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.TextView";
        driver.findElementByXPath(xpath).click();
    }
    @Override
    public void e_search(){
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[1]/android.widget.RelativeLayout/android.widget.EditText";
        driver.findElementByXPath(xpath).sendKeys("Rains of Castamere");
        driver.hideKeyboard();
    }
    @Override
    public void v_SearchResultPage(){
        System.out.println("PAGE AT v_SearchResultPage");
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[1]/android.widget.TextView";
        Assert.assertTrue(driver.findElementByXPath(xpath).getText().equals("Top Result"));
    }
    @Override
    public void v_SongPage(){
        System.out.println("PAGE AT v_SongPage");
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.RelativeLayout/android.widget.RelativeLayout[1]/android.widget.TextView[1]";
        Assert.assertTrue(driver.findElementByXPath(xpath).getText().equals("Ramin Djawadi & Serj Tankian"));
    }
    @Override
    public void e_go_to_song(){
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[7]/android.widget.FrameLayout/android.widget.ImageView";
        driver.findElementByXPath(xpath).click();
    }
    @Override
    public void e_like(){
        driver.findElementById("com.genius.android:id/action_save").click();
        driver.findElementByXPath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]").click();
    }
    @Override
    public void v_nav1(){
        System.out.println("PAGE AT v_nav1");
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[1]/android.widget.TextView";
        Assert.assertTrue(driver.findElementByXPath(xpath).getText().equals("Top Result"));
    }
    @Override
    public void e_back1(){
        driver.findElementByXPath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]").click();
        driver.hideKeyboard();
    }
    @Override
    public void v_nav2(){

    }
    @Override
    public void e_back2(){
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.TextView";
        driver.findElementByXPath(xpath).click();
    }

    @Override
    public void e_my_music_click(){
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[5]/android.widget.TextView";
        driver.findElementByXPath(xpath).click();
    }
    @Override
    public void v_LikedMusicsPage(){
        System.out.println("PAGE AT v_LikedMusicsPage");
        System.out.println("COUTNER :::");
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView";
        int numberOfLikedSongs = driver.findElementByXPath(xpath).findElements(By.className("android.widget.LinearLayout")).size();
        Assert.assertEquals(1,numberOfLikedSongs);
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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterExecution
    public void tearDown() {
        driver.quit();
    }
}