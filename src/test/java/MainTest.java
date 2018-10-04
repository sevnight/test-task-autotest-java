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
    public void fillNumLot(){
        s.FillNumLot("2");
    }

    @Test
    public void searchLot(){
        s.SelectSearch();
    }
}
