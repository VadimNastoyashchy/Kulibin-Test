package kulibinTest.pages;

import kulibinTest.driverutill.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Vadym Nastoiashchyi
 */

public class KulibinResultsPage {


    public KulibinResultsPage() {
        DriverFactory.getWebDriverInstance();
        PageFactory.initElements(DriverFactory.driver, this);
    }


    public KulibinResultsPage checkPriceRandomDrills(int numberOfDrills) {
        WebElement drill = DriverFactory.driver.findElement(By
                .xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/div/div[1]/ul/li[3]/a"));
        String drillsLink = drill.getAttribute("href");
        DriverFactory.driver.navigate().to(drillsLink);
        List<String> drillLinkList = new LinkedList<>();
        WebElement drills = DriverFactory.driver.findElement(By.
                xpath("//*[@class='catalog catalog-full js-catalog']"));
        List<WebElement> drillList = drills.findElements(By.
                xpath("//h4[@class='s_title']"));
        for (WebElement we : drillList) {
            WebElement l = we.findElement(By.tagName("a"));
            drillLinkList.add(l.getAttribute("href"));
        }
        for (int i = 0; i < numberOfDrills; i++) {
            int randomLink = (int) (Math.random() * drillLinkList.size());
            DriverFactory.driver.navigate().to(drillLinkList.get(randomLink));
            WebElement price = DriverFactory.driver.findElement(By.xpath("//span[@class='item_old_price old-price']"));
            String promotionalPrice = price.getAttribute("innerHTML");
            Assert.assertTrue(promotionalPrice.length() > 0);
        }
        return new KulibinResultsPage();
    }

    public KulibinResultsPage checkPricePunchers(int numberOfPages) {
        WebElement puncher = DriverFactory.driver.findElement(By
                .xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/div/div[1]/ul/li[12]/a"));
        String punchersLink = puncher.getAttribute("href");
        DriverFactory.driver.navigate().to(punchersLink);
        List<String> puncherLinkList = new LinkedList<>();
        int currentPage = 1;
        endIteration:
        for (int i = 1; i < numberOfPages; i++, currentPage++) {
            int linkCount = 1;
            puncherLinkList.clear();
            WebElement punchers = DriverFactory.driver.findElement(By.
                    xpath("//*[@class='catalog catalog-full js-catalog']"));
            List<WebElement> puncherPriceList = punchers.findElements(By.
                    xpath("//span[@class='price']"));
            for (WebElement we : puncherPriceList) {
                String promotionalPrice = we.getAttribute("innerHTML");
                puncherLinkList.add(promotionalPrice);
            }

            for (int j = 0; j < puncherLinkList.size(); j++) {
                if (puncherLinkList.get(j).length() <= 0) {
                    Assert.assertTrue(puncherLinkList.get(j).length() <= 0);
                    break endIteration;
                }
                if (linkCount == puncherLinkList.size()) {
                    DriverFactory.driver.findElement(By.linkText("Следующая")).click();
                    linkCount = 1;
                }
                linkCount++;
            }
            Assert.assertNotEquals(currentPage, numberOfPages);

        }
        return new KulibinResultsPage();
    }

}



