package kulibinTest.pages;

import kulibinTest.driverutill.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
        DriverFactory.driver.navigate().to(drill.getAttribute("href"));
        List<String> drillLinkList = new LinkedList<>();
        List<WebElement> drillList = DriverFactory.driver.findElements(By.
                xpath("//h4[@class='s_title']"));
        for (WebElement we : drillList) {
            WebElement l = we.findElement(By.tagName("a"));
            drillLinkList.add(l.getAttribute("href"));
        }
        for (int i = 0; i < numberOfDrills; i++) {
            int randomLink = (int) (Math.random() * drillLinkList.size());
            DriverFactory.driver.navigate().to(drillLinkList.get(randomLink));
            WebElement price = DriverFactory.driver.findElement(By.xpath("//span[@class='old-price']"));
            String promotionalPrice = price.getAttribute("innerHTML");
            Assert.assertTrue(promotionalPrice.length() > 0);
        }
        return new KulibinResultsPage();
    }

    public KulibinResultsPage checkPricePunchers(int numberOfPages) {
        WebElement puncher = DriverFactory.driver.findElement(By
                .xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/div/div[1]/ul/li[12]/a"));
        DriverFactory.driver.navigate().to(puncher.getAttribute("href"));
        List<String> puncherLinkList = new LinkedList<>();
        int currentPage = 1;
        endIteration:
        for (int i = 1; i < numberOfPages; i++, currentPage++) {
            int linkCount = 1;
            puncherLinkList.clear();
            List<WebElement> puncherPriceList = DriverFactory.driver.findElements(By.
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

    public KulibinResultsPage checkFlagIconScrewdrivers(int numberOfPages) {
        WebElement screwdriver = DriverFactory.driver.findElement(By
                .xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/div/div[1]/ul/li[24]/a"));
        DriverFactory.driver.navigate().to(screwdriver.getAttribute("href"));
        int currentPage = 1;
        for (int i = 1; i < numberOfPages; i++, currentPage++) {
            int linkCount = 1;
            List<WebElement> screwdriversList = DriverFactory.driver.findElements(By.
                    xpath("//li[@class='col-xs-4 js-product']"));
            for (WebElement webElement : screwdriversList) {
                WebElement flag = webElement.findElement(By.cssSelector(".item-brand-country"));
                WebElement flagCountryElement = flag.findElement(By.tagName("img"));
                String flagCountry = flagCountryElement.getAttribute("src");
                if (flagCountry.contains("United_states")) {
                    WebElement title = webElement.findElement(By.cssSelector(".s_title"));
                    String titleText = title.findElement(By.tagName("a")).getAttribute("title");
                    System.out.println(titleText);
                }
                if (linkCount == screwdriversList.size()) {
                    DriverFactory.driver.findElement(By.linkText("Следующая")).click();
                    linkCount = 1;
                }
                linkCount++;
            }
        }
        return new KulibinResultsPage();
    }

    public KulibinResultsPage calculationOfThePromotionalPriceOfTheGrinder(int numberOfGoods) {
        WebElement grinder = DriverFactory.driver.findElement(By
                .xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/div/div[1]/ul/li[1]/a"));
        DriverFactory.driver.navigate().to(grinder.getAttribute("href"));
        int currentPage = 1;
        int numberGoodsCount = 0;
        endIteration:
        for (int i = 1; i < 5; i++, currentPage++) {
            int linkCount = 1;
            List<WebElement> grindersList = DriverFactory.driver.findElements(By.
                    xpath("//li[@class='col-xs-4 js-product']"));
            for (WebElement webElement : grindersList) {
                if (numberGoodsCount >= numberOfGoods) {
                    break endIteration;
                }
                try {
                    WebElement oldPriceElem = webElement.findElement(By.cssSelector(".old-price"));
                    String oldTempPrice = oldPriceElem.getAttribute("innerHTML");
                    int oldPrice = Integer.parseInt(oldTempPrice.substring(0, oldTempPrice.indexOf('<')).replaceAll(" ", ""));
                    if (oldTempPrice.length() > 0) {
                        WebElement newPriceElem = webElement.findElement(By.cssSelector(".price"));
                        String newTempPrice = newPriceElem.getAttribute("innerHTML");
                        int newPrice = Integer.parseInt(newTempPrice.substring(0, newTempPrice.indexOf('<')).replaceAll(" ", ""));
                        WebElement discountElement = webElement.findElement(By.tagName("span"));
                        String tempDiscount = discountElement.getAttribute("innerHTML");
                        int discount = Integer.parseInt(tempDiscount.substring(1, tempDiscount.indexOf('%')).replaceAll(" ", ""));
                        WebElement title = webElement.findElement(By.cssSelector(".s_title"));
                        String titleText = title.findElement(By.tagName("a")).getAttribute("title");
                        int result = oldPrice - ((oldPrice * discount) / 100);
                        numberGoodsCount++;
                        Assert.assertEquals(newPrice, result, titleText + " " + result + " " + newPrice);
                    }
                } catch (NoSuchElementException e) {
                }
                if (linkCount == grindersList.size()) {
                    DriverFactory.driver.findElement(By.linkText("Следующая")).click();
                    linkCount = 1;
                }
                linkCount++;
            }
        }
        return new KulibinResultsPage();
    }


}






