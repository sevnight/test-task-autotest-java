import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

public class Search {
    private WebDriver driver;

    @FindBy(id = "BaseMainContent_MainContent_btnSearch")
    private WebElement buttonSearch = null;

    @FindBy(id= "BaseMainContent_MainContent_txtNumber_txtText")
    private WebElement numberLot = null;

    Search(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    void SelectSearch(){
        buttonSearch.click();
    }
    void FillNumLot(String str){ numberLot.sendKeys(str); }
}
