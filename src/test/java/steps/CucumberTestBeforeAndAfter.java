package steps;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit.BrowserStrategy;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
//import helpers.CertificateGenerator;
import helpers.WebDriverContainer;
import org.junit.ClassRule;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;

/**
 * Код, который выполняется до и после каждого сценария (Hooks).
 * Created by Vladimir V. Klochkov on 20.04.2016.
 */

public class CucumberTestBeforeAndAfter {
    /******************************************************************************************************************
     *
     *                                          Методы класса
     *
     *****************************************************************************************************************/

    /**
     * Рестарт браузера после выполнения каждого тест класса (добавлено по совету А. Солнцева) test
     */
    @ClassRule
    public static BrowserStrategy perClass = new BrowserStrategy();

    /**
     * Код, который выполняется до каждого сценария.
     */
    @Before
    public void setUp() throws MalformedURLException, UnknownHostException {
        // Загружаем настройки тестовой среды из файла [config.properties]
        //ConfigContainer.getInstance().loadConfig();

        // Инициализируем статический экземпляр WebDriver
        WebDriverContainer.getInstance().setWebDriver();
        open("https://223.rts-tender.ru/supplier/auction/Trade/Search.aspx");

    }

    @Attachment(value = "TXT Attachment", type = "text/plain")
    private static byte[] saveLogFile(String pathToFile) throws Exception {
        return fileToBytes(pathToFile);
    }

    private static byte[] fileToBytes(String fileName) throws Exception
    {
        File file = new File(fileName);
        return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
    }

    /**
     * Код, который выполняется после каждого сценария.
     */
    @After
    public void tearDown(Scenario scenario) throws Exception {
        // Получаем текущий экземпляр WebDriver
        WebDriver driver = WebDriverContainer.getInstance().getWebDriver();

        // Делаем скриншот в случае аварийного завершения теста
        if (scenario.isFailed()) {
            try {
                scenario.write("Current Page URL is " + WebDriverRunner.getWebDriver().getCurrentUrl());
                byte[] screenshot = ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
                Screenshot failedPageScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
                ImageIO.write(failedPageScreenshot.getImage(), "PNG", new File("target/site/FPScreen.png"));
            } catch (WebDriverException somePlatformsNotSupportScreenshots) {
                System.err.println(somePlatformsNotSupportScreenshots.getMessage());
            }
        }
/*
        // Извлекаем диск виртуального носителя
        CertificateGenerator.getInstance().unMountDisk();

        // Если тип теста: аккредитация (был сгенерирован сертификат) то удаляем сертификат Пользователя
        String testType = ConfigContainer.getInstance().getParameter("param_test_type");
        if (testType != null && testType.equals("Accreditation")) {
            CertificateGenerator.getInstance().
                    deleteCertificate(ConfigContainer.getInstance().getParameter("CertificateName"));
        }

        if (testType != null && testType.equals("supplier-sso")) {

            if (!ConfigContainer.getInstance().getParameter("NewCertificateName").equals("0000000000000000000")) {
                CertificateGenerator.getInstance().
                        deleteCertificate(ConfigContainer.getInstance().getParameter("NewCertificateName"));
            }
            CertificateGenerator.getInstance().
                    deleteCertificate(ConfigContainer.getInstance().getParameter("CertificateName"));
        }
*/
        // Закрываем браузер по завершению теста

        driver.quit();

        saveLogFile("target/test-classes/logging.txt");
        TimeUnit.SECONDS.sleep(1);

        // TODO Доделать этот вид отчета тоже. Пока не работает, так как на момент вызова входной файл имеет 0 длину.
        // Генерируем результирующий отчет по тестам
        // CucumberResultsOverview results = new CucumberResultsOverview();
        // results.setSourceFile("./target/cucumber.json");
        // results.setOutputDirectory("target");
        // results.setOutputName("cucumber-results");
        // results.validateParameters();
        // results.execute();
        //ConfigContainer.getInstance().clearParameters();
    }
}
