import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;

/**
 * Драйвер Chrome для реализации приложения
 */
public class Driver {

    private static ChromeDriverService service;
    static WebDriver driver = null;
    private static String URL =  "https://223.rts-tender.ru/supplier/auction/Trade/Search.aspx";
    static Logger log;

    /**
     * Инициализация
     */
    static void init(){
        log.writeLog("Инициализация драйвера Chrome");
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        driver = new ChromeDriver(service);
        log.writeLog("Переход по "+Driver.URL);
        driver.get(URL);
    }

    /**
     * Конец теста
     */
    static void finish(){
        log.writeLog("Завершение теста");
        Driver.log.finishLog();
        //driver.close();    /*тута закрываетается браузер, закоменть чтобы увидеть*/
    }
}
