package pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.codeborne.selenide.Selenide.$;

/**
 * Абстрактная страница, от которой наследуются все реальные страницы приложения.
 * Created by Evgeniy Glushko on 26.04.2016.
 */
public abstract class AbstractPage {
    /*******************************************************************************************************************
     *                                             Поля класса
     ******************************************************************************************************************/
    //protected ConfigContainer config;
    public static final Logger logger = LogManager.getLogger(AbstractPage.class);
    protected int delayTimeMs = 100000;
    protected int delayTime300 = 300000; //30000
    protected long pollingIntervalMs = 50;
    protected int timeForIsDisplay1 = 5;
    protected int timeForIsDisplay2 = 15; //30

    /*******************************************************************************************************************
     * Конструктор класса. Отвечает за инициализацию всех полей класса
     ******************************************************************************************************************/

    /*public AbstractPage() {
        this.config = ConfigContainer.getInstance();
    }
*/
    /*******************************************************************************************************************
     * Методы класса
     ******************************************************************************************************************/


}