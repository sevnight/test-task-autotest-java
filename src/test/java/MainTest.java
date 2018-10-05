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
}
