package Day;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

class lab001 {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            driver.get("https://patinformed.wipo.int/");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='margin-right']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='green']"))).click();

            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='searchField']")));
            searchBox.sendKeys("ropinirole hydrochloride" + Keys.ENTER);

            driver.findElement(By.xpath("//div[@class='title cropper']")).click();

            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul > li img")));

            List<WebElement> languageItems = driver.findElements(By.cssSelector("ul > li img"));
            for (WebElement img : languageItems) {
                String title = img.getAttribute("title");
                System.out.println("Title and/or abstract available in: " + title);
            }

            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement hiddenDiv = wait3.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@style, 'display: none') and contains(text(), 'cc')]")
            ));

            String rawHtml = hiddenDiv.getAttribute("innerHTML");
            String jsonLike = rawHtml
                    .replace("&nbsp;", " ")
                    .replace("(", "")
                    .replace(")", "")
                    .replaceAll("^[^\\{]*", "") 
                    .replaceAll(",\\s*\" freeText\"", "\"freeText\"") 
                    .replaceAll(",\\s*\\)", "") 
                    .replaceAll("\\)\\s*$", "") 
                    .trim();

            JSONObject json = new JSONObject(jsonLike);

            String jurisdiction = json.optString("cc", "N/A");
            String publicationNumber = json.getJSONObject("numbers").optString("publication", "N/A");
            String filingDate = json.getJSONObject("dates").optString("filing", "N/A");

            System.out.println("Jurisdiction: " + jurisdiction);
            System.out.println("Publication Number: " + publicationNumber);
            System.out.println("Filing Date: " + filingDate);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
