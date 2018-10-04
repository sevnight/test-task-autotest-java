import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

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
}
