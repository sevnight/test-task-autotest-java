import org.javatuples.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Search {

    private WebDriver driver;

    @FindBy(id = "BaseMainContent_MainContent_btnSearch")
    private WebElement buttonSearch = null;

    @FindBy(id= "BaseMainContent_MainContent_chkPurchaseType_0")
    private WebElement checkNorm223 = null;

    @FindBy(id= "BaseMainContent_MainContent_chkPurchaseType_1")
    private WebElement checkCommercialPurchase = null;

    @FindBy(id= "BaseMainContent_MainContent_txtStartPrice_txtRangeFrom")
    private WebElement startCost = null;

    @FindBy(css="td[dir=\"ltr\"] > select[class=\"ui-pg-selbox\"] > option[value=\"100\"]")
    private WebElement selectListSize = null;

    @FindAll({
            @FindBy(css="tbody > tr[role=\"row\"][tabindex=\"-1\"]")
    })
    private List<WebElement> rowInList = null;

    Search(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    void SelectSearch(){
        buttonSearch.click();
    }
    void FillCheck223(){ checkNorm223.click(); }
    void FillCheckCommercialPurchase(){ checkCommercialPurchase.click(); }
    void FillStartCost(String str){ startCost.sendKeys(str); }
    void setListSize(){
        (new WebDriverWait(driver,10)).until(
                ExpectedConditions.invisibilityOfElementLocated(By
                        .cssSelector("#load_BaseMainContent_MainContent_jqgTrade"))
        );
        selectListSize.click();
    }
    Pair<Double, Double> focusRow(){
        double ids=0;
        double allsum=0;
        (new WebDriverWait(driver,20)).until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver d) {
                return rowInList.size() == 100;
            }
        });
        for (WebElement row: rowInList) {
            if (!(row.findElement(By
                    .cssSelector("td[aria-describedby=\"BaseMainContent_MainContent_jqgTrade_OosNumber\"]"))
                    .getText()
                    .equals("&nbsp;"))
                    //!= "&nbsp;"
            ){
                ids++;
                allsum += Double.parseDouble( row.findElement(By
                        .cssSelector("td[aria-describedby=\"BaseMainContent_MainContent_jqgTrade_StartPrice\"]"))
                        .getText().replaceAll("[^\\.0123456789]","")
                );
            }
        }
        return Pair.with(ids , allsum);
    }
}
