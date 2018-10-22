package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Содержит статический экземпляр WebDriver для явного обращения к нему (если не используем Selenide).
 * Created by Vladimir V. Klochkov on 20.04.2016.
 */
public class WebDriverContainer {
    /*******************************************************************************************************************
     * Поля класса.
     ******************************************************************************************************************/
    // Ссылка на основное окно приложения в браузере
    private static String mainWindowHandle;
    public static final Logger logger = LogManager.getLogger(WebDriverContainer.class);

    /*******************************************************************************************************************
     *********** Методы класса ***************
     ******************************************************************************************************************/
    /**
     * Возвращает статический экземпляр этого класса (если класс еще не имеет экземпляра, то создает новый экземпляр).
     *
     * @return Статический экземпляр этого класса
     */
    public static synchronized WebDriverContainer getInstance() {
         return new WebDriverContainer();
    }

    /**
     * Возвращает экземпляр WebDriver (инициализирует его если он еще не инициализирован).
     *
     * @return экземпляр Selenium WebDriver
     */
    public WebDriver getWebDriver() {
        return WebDriverRunner.getWebDriver();
    }

    /**
     * Инициализирует статический экземпляр WebDriver.
     */
    public void setWebDriver() throws MalformedURLException, UnknownHostException {
        //switch (ConfigContainer.getInstance().getConfigParameter("DriverType")) {
        //    case "Chrome":
                this.setChromeDriver();
        //        break;
        //}
    }

    private void setChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary(new File("src/test/resources/drivers/chromium/chrome.exe"));
        //options.addExtensions(new File("src/test/resources/drivers/CryptoProExt.crx"));
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        Configuration.selectorMode = Configuration.SelectorMode.Sizzle;
        Configuration.pollingInterval = 50;
        WebDriverRunner.setWebDriver(driver);
    }

//    /**
//     * Закрывает браузер (если он открыт) и делает повторную инициализацию статического экземпляра Selenium WebDriver.
//     */
//    public void restartWebDriver() throws Exception {
//        // Закрываем текущий экземпляр браузера
//        WebDriverRunner.getWebDriver().quit();
//        // Задержки необходимы, иначе перезапущенный браузер может работать нестабильно
//        TimeUnit.SECONDS.sleep(5);
//        // Запускаем новый экземпляр браузера (инициализируем новую сессию Id != null)
//        this.setWebDriver();
//        // Задержки необходимы, иначе перезапущенный браузер может работать нестабильно
//        TimeUnit.SECONDS.sleep(5);
//    }

    /**
     * Нажимает на кнопку [Yes] в модальном диалоговом окне [Security Alert].
     */
    public void acceptSecurityAlert() throws Exception {
        WebDriverRunner.getWebDriver().switchTo().alert().accept();
        TimeUnit.SECONDS.sleep(15);
    }

    /**
     * Запоминает ссылку на основное (главное) окно приложения в браузере.
     */
    public void setMainWindowHandle() throws Exception {
        mainWindowHandle = WebDriverRunner.getWebDriver().getWindowHandle();
        Assert.assertTrue("[Ошибка]: mainWindowHandle равна NULL!", mainWindowHandle != null);
         logger.info("  произведено сохранение указателя на главное окно: " +
                "[" + mainWindowHandle + "]");
    }

    /**
     * Запоминает ссылку на промежуточное окно приложения в браузере:
     */
    public void setIntermediateWindowHandle() throws Exception {
        String intermediateWindowHandle = WebDriverRunner.getWebDriver().getWindowHandle();
         logger.info("  произведено сохранение указателя на промежуточное окно: " +
                "[" + intermediateWindowHandle + "]");
    }

    public void switchToNewWindowInBrowse() throws Throwable {
        Boolean switchNew = false;
        String winHandleBefore = WebDriverRunner.getWebDriver().getWindowHandle();
        String winHandleNew = "";
        for (String winHandle : WebDriverRunner.getWebDriver().getWindowHandles()) {
            if (!winHandle.equals(winHandleBefore)) {
                winHandleNew = winHandle;
                switchNew = true;
                break;
            }
        }
        if (switchNew) WebDriverRunner.getWebDriver().switchTo().window(winHandleNew);
        else WebDriverRunner.getWebDriver().switchTo().window(winHandleBefore);
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("window.focus();");
    }

    public void switchToNewWindowInBrowseURL(String type, String value) throws Exception {
        WebDriver popup;
        for (String windowHandle : WebDriverRunner.getWebDriver().getWindowHandles()) {
            popup = WebDriverRunner.getWebDriver().switchTo().window(windowHandle);
            switch (type) {
                case "title":
                    if (popup.getTitle().contains(value)) break;
                    break;
                case "url":
                    if (popup.getCurrentUrl().contains(value)) break;
                    break;
                default:
                    throw new AssertionError("[Ошибка]: в метод передан некорректный параметр:" +
                            " [type: " + type + "]");
            }
        }
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("window.focus();");
    }

    /**
     * Переключает фокус на новое окно и закрывает старые
     */
    public void switchToNewWindow(String oldWindowHandle) throws Exception {
        Set<String> handles = WebDriverRunner.getWebDriver().getWindowHandles();
        for (String handle : handles
                ) {
            if (!handle.equals(oldWindowHandle)) {
                WebDriverRunner.getWebDriver().switchTo().window(handle);
                setMainWindowHandle();
                closeActiveWindowsSwitchToMainWindowInBrowser();
            }
        }
    }

    /**
     * Получаем и выводим указатель на окно
     */
    public void getWindowHandle(String nameWindow) throws Exception {
         logger.info("  произведено получение указателя на окно: [" + nameWindow + "] "
                + WebDriverRunner.getWebDriver().getWindowHandle());

    }

    /**
     * Закрывает все открытые вкладки кроме первой и переключается на нее.
     */
    public void closeActiveWindowsAndSwitchToMainWindowInBrowser() throws Exception {
        if (mainWindowHandle == null) return;
        for (String handle : WebDriverRunner.getWebDriver().getWindowHandles())
            if (!handle.equals(mainWindowHandle)) {
                WebDriverRunner.getWebDriver().switchTo().window(handle);
                WebDriverRunner.getWebDriver().close();
            }
        WebDriverRunner.getWebDriver().switchTo().window(mainWindowHandle);
         logger.info("  произведено закрытие окон, кроме окна: " + mainWindowHandle);
    }

    public void closeActiveWindowsSwitchToMainWindowInBrowser() throws Exception {
        // Получаем все идентификаторы открытых на данный момент окон приложения (включая главное окно)
        Set<String> windowHandles = WebDriverRunner.getWebDriver().getWindowHandles();
         logger.info("  текущее количество открытых окон в приложении ["
                + windowHandles.size() + "" + "]");
        // если некуда переключаться (идентификатор главного окна отсутствует)
        if (mainWindowHandle == null) {
             logger.info(" [ОШИБКА]: Текущим идентификатором главного окна является NULL");
            return;
        }
        // если незачем переключаться (открыто только одно главное окно)
        if (windowHandles.size() == 1) return;
        // если некуда переключаться (передан не существующий идентификатор окна)
        if (!windowHandles.contains(mainWindowHandle)) {
             logger.info(" [ОШИБКА]: передан не существующий идентификатор главного окна: ["
                    + mainWindowHandle + "]");
            return;
        }
        // Закрываем все открытые окна кроме главного
        for (String handle : windowHandles)
            if (!handle.equals(mainWindowHandle)) {
                WebDriverRunner.getWebDriver().switchTo().window(handle);
                WebDriverRunner.getWebDriver().close();
                 logger.info("  произведено закрытия окна в приложении");
            }
        // Переключаемся в главное окно
        WebDriverRunner.getWebDriver().switchTo().window(mainWindowHandle);
         logger.info("  переключаемся в главное окно");
    }

    /**
     * Настраивает профиль и поведение нового экземпляра WebDriver.
     * Чистит все, что может очистить чтобы избежать общего состояния между выполнением тестов.
     */
//    private void setFirefoxWebDriver() {
//        // Настройка профиля браузера
//        FirefoxProfile profile = new ProfilesIni().getProfile("WebDriver");
//        // Настройка поведения WebDriver
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.BOTTOM);
//        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//        capabilities.setBrowserName(BrowserType.FIREFOX);
//        // Создание и настройка нового экземпляра WebDriver с указанными выше профилем и поведением
//        driver = new FirefoxDriver(null, profile, capabilities);
//        driver.manage().window().maximize();
//        driver.manage().deleteAllCookies();
//        // Инициализируем Selenide нашим экземпляром WebDriver
//        WebDriverRunner.setWebDriver(driver);
//         logger.info("  the static instance of Selenium WebDriver was initialized");
//        // Инициализируем время ожидания по умолчанию в миллисекундах для Selenide
//        timeout = 100000;
//    }

    /**
     * Настраивает профиль и поведение нового экземпляра WebDriver.
     * Чистит все, что может очистить чтобы избежать общего состояния между выполнением тестов.
     */
//    private void setInternetExplorerDriver() throws MalformedURLException, UnknownHostException {
//        // Настройка поведения WebDriver
//        DesiredCapabilities capabilitiesIE = new DesiredCapabilities();
//        capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//        capabilitiesIE.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.BOTTOM);
//        capabilitiesIE.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        capabilitiesIE.setCapability("ignoreProtectedModeSettings", true);
//        capabilitiesIE.setCapability("ignore-certificate-error", true);
//        capabilitiesIE.setCapability("ensureCleanSession", true);
//        capabilitiesIE.setCapability("ignoreZoomSetting", true);
//        capabilitiesIE.setCapability("browser.download.folderList", 2);
//        capabilitiesIE.setCapability("browser.download.dir", System.getProperty("user.dir") + "\\temp");
//        capabilitiesIE.setCapability("browser.helperApps.neverAsk.saveToDisk", "application/msword, " +
//                "application/csv, application/ris, text/csv, image/png, application/pdf, text/html, " +
//                "text/plain, application/zip, application/x-zip, application/x-zip-compressed, " +
//                "application/download, application/octet-stream");
//        capabilitiesIE.setCapability("browser.download.manager.showWhenStarting", false);
//        capabilitiesIE.setCapability("browser.download.manager.focusWhenStarting", false);
//        capabilitiesIE.setCapability("browser.download.useDownloadDir", true);
//        capabilitiesIE.setCapability("browser.helperApps.alwaysAsk.force", false);
//        capabilitiesIE.setCapability("browser.download.manager.alertOnEXEOpen", false);
//        capabilitiesIE.setCapability("browser.download.manager.closeWhenDone", true);
//        capabilitiesIE.setCapability("browser.download.manager.showAlertOnComplete", false);
//        capabilitiesIE.setCapability("browser.download.manager.useWindow", false);
//        capabilitiesIE.setCapability("services.sync.prefs.sync.browser.download.manager.showWhenStarting",
//                false);
//        capabilitiesIE.setCapability("pdfjs.disabled", true);
//
//        /***************************************************************************************************************
//         * Строчку кода ниже не удалять ! Она напрямую связана с крашами Internet Explorer !
//         **************************************************************************************************************/
//        capabilitiesIE.setCapability("nativeEvents", false);
//        /**************************************************************************************************************/
//        capabilitiesIE.setBrowserName(BrowserType.IE);
//        // Установка свойства "webdriver.ie.driver", необходимого для корректного запуска Internet Explorer
//        String path = System.getProperty("user.dir") +
//                ConfigContainer.getInstance().getConfigParameter("ExternalDriverPath");
//        String ieDriverExe = "IEDriverServer.exe";
//        System.setProperty("webdriver.ie.driver", path + ieDriverExe);
//         logger.info("  путь к IEDriverServer файлу: " + path + ieDriverExe);
//        //..............................................................................................................
//        // Создание и настройка нового экземпляра WebDriver с указанными выше профилем и поведением
//        driver = new InternetExplorerDriver(capabilitiesIE);
//        driver.manage().window().maximize();
//        // Инициализируем Selenide нашим экземпляром WebDriver
//        WebDriverRunner.setWebDriver(driver);
//        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
//        String browserName = cap.getBrowserName().toUpperCase();
//        String browserVersion = cap.getVersion();
//         logger.info("  имя браузера: " + browserName + " версия: " + browserVersion);
//        // Инициализируем время ожидания по умолчанию в миллисекундах для Selenide
//        timeout = 100000;
//        // Выводим дополнительную информацию о компьютере, на котором выполняются тесты
//         logger.info("  Computer name is : " + InetAddress.getLocalHost().getHostName());
//        String hostAddress = InetAddress.getLocalHost().getHostAddress();
//         logger.info("  IP address is    : " + hostAddress);
//        switch (hostAddress) {
//            case "172.18.130.249":
//                 logger.info("  Agent name is    : " + "MASTER");
//                break;
//            case "172.18.130.247":
//                 logger.info("  Agent name is    : " + "SLAVE-AGENT-1");
//                break;
//            case "172.18.130.26":
//                 logger.info("  Agent name is    : " + "SLAVE-AGENT-2");
//                break;
//        }
//         logger.info("  OS name is       : " + System.getProperty("os.name"));
//         logger.info("  OS version is    : " + System.getProperty("os.version"));
//         logger.info("  User logged in as: " + System.getProperty("user.name"));
//    }
}