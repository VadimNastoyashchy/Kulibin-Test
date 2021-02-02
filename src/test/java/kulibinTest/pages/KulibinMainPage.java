package kulibinTest.pages;

import kulibinTest.driverutill.DriverFactory;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Vadym Nastoiashchyi
 */

public class KulibinMainPage {


    public KulibinMainPage() {
        DriverFactory.getWebDriverInstance();
        PageFactory.initElements(DriverFactory.driver, this);
    }

    public KulibinResultsPage openKulibinPage() {
        String url = System.getProperty("kulibin", "https://kulibin.com.ua");
        DriverFactory.driver.get(url);
        return new KulibinResultsPage();
    }


}
