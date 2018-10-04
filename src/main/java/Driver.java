import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/*Драйвер Chrome для реализации приложения*/
public class Driver {

    static WebDriver driver = null;
    private static String URL =  "https://223.rts-tender.ru/supplier/auction/Trade/Search.aspx";

    /*инициализация*/
    static void init(){
        driver = new ChromeDriver();
        driver.get(URL);

    }
    /*завершение*/
    static void finish(){ driver.close(); }
}
