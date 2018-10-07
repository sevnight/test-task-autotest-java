import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class MainTest {

    private Search s;

    @BeforeSuite
    public void setUp(){
        Driver.init();
        s = new Search(Driver.driver);
    }
    @AfterSuite
    public void close(){
        Driver.finish();
    }
/*
    @Test
    public void fillStartCost(){
        s.FillStartCost("0");
    }

    @Test
    public void fillCheck223(){ s.FillCheck223(); }

    @Test
    public void fillCheckCommercialPurchase(){ s.FillCheckCommercialPurchase(); }

    @Test
    public void searchLot(){ s.SelectSearch(); }

    @Test
    public void set_ListSize(){
        //$("td[dir=\"ltr\"] > select[class=\"ui-pg-selbox\"] > option[value=\"100\"]").click();
        s.setListSize();
    }

    @Test
    public void checkRow(){
        System.out.println(s.focusRow()); //s.focusRow();
    }
    */
    @Test
    public void MainTest(){
        s.FillStartCost("0");
        s.FillCheck223();
        s.FillCheckCommercialPurchase();
        s.SelectSearch();
        s.setListSize();
        System.out.println(s.focusRow());
    }
}
