package Day;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class lab001 {


    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java/4.27.0");
    }
}
