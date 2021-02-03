package kulibinTest;

import kulibinTest.driverutill.DriverFactory;
import kulibinTest.pages.KulibinMainPage;
import kulibinTest.pages.KulibinResultsPage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 * @author Vadym Nastoiashchyi
 */

public class KulibinTest {

    @Test
    public void checkingTheAvailabilityOfPromotionalAndOldPrices() {
        KulibinResultsPage kulibinResultsPage =
                new KulibinMainPage()
                        .openKulibinPage()
                        .checkPriceRandomDrills(3);
    }

    @Test
    public void checkingProductPricesOnPages() {
        KulibinResultsPage kulibinResultsPage =
                new KulibinMainPage()
                        .openKulibinPage()
                        .checkPricePunchers(2);

    }

    @Test
    public void checkFlagIcon(){
        KulibinResultsPage kulibinResultsPage =
                new KulibinMainPage()
                        .openKulibinPage()
                        .checkFlagIconScrewdrivers(3);
    }

    @Test
    public void calculationOfThePromotionalPrice(){
        KulibinResultsPage kulibinResultsPage =
                new KulibinMainPage()
                        .openKulibinPage()
                        .calculationOfThePromotionalPriceOfTheGrinder(10);
    }


    @AfterTest
    public void quit() {
        DriverFactory.quit();
    }
}
