import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/*Драйвер Chrome для реализации приложения*/
public class Driver {

    static WebDriver driver = null;
    private static String URL =  "https://223.rts-tender.ru/supplier/auction/Trade/Search.aspx";
    static Logger log;

    /*инициализация*/
    static void init(){
        log.writeLog("Инициализация драйвера Chrome");
        driver = new ChromeDriver();
        log.writeLog("Переъод по "+Driver.URL);
        driver.get(URL);

    }
    /*завершение*/
    static void finish(){
        log.writeLog("Завершение теста");
        Driver.log.finishLog();
        /*driver.close();*/
    }
}
