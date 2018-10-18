import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

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
     * Инициализация Chrome драйвера для запуска браузера
     * (Рекомендация: хранить драйвера в проекте)
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
        driver.close(); //если закоментировать то браузер не закроется
                        // и останется процесс в диспетчере
    }
}
