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

    @FindBy(css="input[id=\"BaseMainContent_MainContent_txtPublicationDate_txtDateFrom\"]")
    private WebElement dateFormYesterday;

    @FindBy(css="td.ui-datepicker-today")
    private WebElement yesterdayDate = null;

    @FindBy(css="input[id=\"BaseMainContent_MainContent_txtPublicationDate_txtDateTo\"]")
    private WebElement dateFormToday;

    @FindBy(css="td.ui-datepicker-today")
    private WebElement todayDate = null;

    @FindBy(id = "sp_1_BaseMainContent_MainContent_jqgTrade_toppager")
    private WebElement sumPage = null;

    @FindBy(id="next_t_BaseMainContent_MainContent_jqgTrade_toppager")
    private WebElement nextPage = null;

    Search(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Нажатие кнопки "Поиск"
     */
    void SelectSearch(){
        buttonSearch.click();
    }

    /**
     * Выставление чека на "Закупка в соответствии с нормами 223-ФЗ (за исключением норм статьи 3.2 223-ФЗ)"
     */
    void FillCheck223(){ checkNorm223.click(); }

    /**
     * Выставление чека на "Коммерческая закупка"
     */
    void FillCheckCommercialPurchase(){ checkCommercialPurchase.click(); }

    /**
     * Выставление минимальной цены
     * @param str
     */
    void FillStartCost(String str){ startCost.sendKeys(str); }

    /**
     * Ожидание загрузки списка закупок
     */
    void WaitLoad(){
        int WAITING_TIME_LIST = 20;
        (new WebDriverWait(driver,WAITING_TIME_LIST)).until(
                ExpectedConditions.invisibilityOfElementLocated(By
                        .cssSelector("#load_BaseMainContent_MainContent_jqgTrade"))
        );
    }

    /**
     * Выставление размерности отображаемого списка закупок равным "100"
     */
    void setListSize100(){
        selectListSize.click();
        WaitLoad();
    }

    /**
     * Подсчет количества закупок имеющих номер в ЕИС и не отмененных
     * @return передаётся пара <"количество закупок", "их общая стоимость">
     */
    Pair<Integer, Double> focusRow(){
        int ids=0;
        double sum=0;
        WaitLoad();
        for (WebElement row: rowInList) {
            if (!(row.findElement(By
                    .cssSelector("td[aria-describedby=\"BaseMainContent_MainContent_jqgTrade_OosNumber\"]"))
                    .getText()
                    .equals(" "))
                    //!= "&nbsp;"
                && (!
                row.findElement((By
                    .cssSelector("td[aria-describedby=\"BaseMainContent_MainContent_jqgTrade_LotStateString\"]")))
                    .getText()
                    .equals("Отменена")
                )
            ){
                ids++;
                sum += Double.parseDouble( row.findElement(By
                        .cssSelector("td[aria-describedby=\"BaseMainContent_MainContent_jqgTrade_StartPrice\"]"))
                        .getText().replaceAll("[^\\.0123456789]","")
                );
            }
        }
        return Pair.with(ids , sum);
    }

    /**
     * Задание в "Дата публикации извещения" сегодняшней даты
     */
    void FillDate(){
        dateFormYesterday.click();
        yesterdayDate.click();
        dateFormToday.click();
        todayDate.click();

    }

    /**
     * Просмотр количества найденных страниц
     * @return значение количества страниц для длины цикла их прохода
     */
    Integer getSumPage(){
        return Integer.parseInt(sumPage.getText());
    }

    /**
     * Переключение на следующую страницу
     */
    void setNextPage(){
        if (!nextPage  //ненужная проверка, но пусть будет
                .getAttribute("class").equals("ui-pg-button ui-corner-all ui-state-disabled")){
            nextPage.click();
        }
    }
}
