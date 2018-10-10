import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Драйвер Chrome для реализации приложения
 */
public class Driver {

    static WebDriver driver = null;
    private static String URL =  "https://223.rts-tender.ru/supplier/auction/Trade/Search.aspx";
    static Logger log;

    /**
     * Инициализация
     */
    static void init(){
        log.writeLog("Инициализация драйвера Chrome");
        driver = new ChromeDriver();
        log.writeLog("Перевод по "+Driver.URL);
        driver.get(URL);

    }

    /**
     * Конец теста
     */
    static void finish(){
        log.writeLog("Завершение теста");
        Driver.log.finishLog();
        //driver.close(); //разкоментировать чтобы браузер закрывался после теста
    }
}
