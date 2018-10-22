package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
//import helpers.Timer;
import helpers.WebDriverContainer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.javatuples.Pair;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.url;

/**
 * Класс описывающий общую логику для работы со страницами
 * Created by Evgeniy Glushko on 19.05.2016.
 */
public class CommonPage extends AbstractPage {
    //protected ConfigContainer config = ConfigContainer.getInstance();
    //private Timer timer = new Timer();
    public static final Logger logger = LogManager.getLogger(WebDriverContainer.class);

    /*******************************************************************************************************************
     * Конструктор класса.
     ******************************************************************************************************************/
    public CommonPage() {
        super();
        PropertyConfigurator.configure("src/main/resources/log4j2.properties");
    }

    /*******************************************************************************************************************
     *                                             Локаторы элементов страницы.
     ******************************************************************************************************************/
    private static final String buttonStartSearch = "#BaseMainContent_MainContent_btnSearch";
    private static final String checkButtonNorm223 = "#BaseMainContent_MainContent_chkPurchaseType_0";
    private static final String checkButtonCommercialPurchase = "#BaseMainContent_MainContent_chkPurchaseType_1";
    private static final String fieldStartCost = "#BaseMainContent_MainContent_txtStartPrice_txtRangeFrom";
    private static final String sizePurchaseList100 = "td[dir=\"ltr\"] > select[class=\"ui-pg-selbox\"] > option[value=\"100\"]";
    private static final String lot = "tbody > tr[role=\"row\"][tabindex=\"-1\"]";
    private static final String fieldDateStart = "input[id=\"BaseMainContent_MainContent_txtPublicationDate_txtDateFrom\"]";
    private static final String fieldDateEnd = "input[id=\"BaseMainContent_MainContent_txtPublicationDate_txtDateTo\"]";
    private static final String dateToday = "td.ui-datepicker-today";
    private static final String sumOfSearchPages = "#sp_1_BaseMainContent_MainContent_jqgTrade_toppager";
    private static final String buttonNextPage = "#next_t_BaseMainContent_MainContent_jqgTrade_toppager";
    private static final String LoadingWindow = "#load_BaseMainContent_MainContent_jqgTrade";
    private static final String fieldNumberEIS = "td[aria-describedby=\"BaseMainContent_MainContent_jqgTrade_OosNumber\"]";
    private static final String fieldLotStatus = "td[aria-describedby=\"BaseMainContent_MainContent_jqgTrade_LotStateString\"]";
    private static final String costLot = "td[aria-describedby=\"BaseMainContent_MainContent_jqgTrade_StartPrice\"]";

    /*******************************************************************************************************************
     *
     *                                        Методы страницы
     *
     ******************************************************************************************************************/
    /**
     * Запуск поиска
     */
    public void StartSearch(){
        $(buttonStartSearch).click();
        waitForLoadingList();
    }

    public void FillCheck223Norm(){
        $(checkButtonNorm223).click();
    }

    public void FillCheckCommercialPurchase(){
        $(checkButtonCommercialPurchase).click();
    }

    public void FillStartCost(String cost){
        $(fieldStartCost).setValue(cost);
    }

    /**
     * Метод, ожидающий исчезновения спиннеров на текущей странице
     */
    private void waitForLoadingList(){
        SelenideElement element = $(LoadingWindow);
        if(element.isDisplayed()){
            element.waitUntil(Condition.hidden, delayTimeMs, pollingIntervalMs);
        }
    }

    public void setSizePurchaseListIs100(){
        $(sizePurchaseList100).click();
        waitForLoadingList();
    }

    public Pair<Integer,Double> countAllFoundRows(){
        int ids = 0;
        double sum = 0;
        waitForLoadingList();
        for (SelenideElement row : $$(lot)) {
            boolean IsNotEmptyNumberEIS = !row.find(fieldNumberEIS).getText().equals(" ") ;
            boolean IsNotcanceledLot =  !row.find(fieldLotStatus).getText().equals("Отменено");
            if ( IsNotEmptyNumberEIS && IsNotcanceledLot){
                ids++;
                sum += Double.parseDouble(row.find(costLot).getText().replaceAll("[^\\.0123456789]",""));
            }
        }
        return Pair.with(ids,sum);
    }

    public void fillDate(){
        $(fieldDateStart).click();
        $(dateToday).click();
        $(fieldDateEnd).click();
        $(dateToday).click();
    }

    public Integer getSumPages(){
        return Integer.parseInt($(sumOfSearchPages).getText());
    }

    public void setNextPage(){
        $(buttonNextPage).click();
    }
}