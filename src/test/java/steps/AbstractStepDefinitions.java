package steps;

//import helpers.ConfigContainer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Абстрактный класс, от которого наследуются все реальные классы определения шагов для тестов.
 * Created by Vladimir V. Klochkov on 19.08.2016.
 */
public abstract class AbstractStepDefinitions
{
    //protected ConfigContainer config = ConfigContainer.getInstance();
    public static final Logger logger = LogManager.getLogger(AbstractStepDefinitions.class);

    /*******************************************************************************************************************
     *
     *                                              Поля класса.
     *
     ******************************************************************************************************************/

    private Integer repeatNumber; // Длина строки-разделителя в символах
    private String delimiter;     // Символ, из которого состоит строка-разделитель

    /*******************************************************************************************************************
     *
     *                         Конструктор класса. Отвечает за инициализацию всех полей класса
     *
     ******************************************************************************************************************/

    public AbstractStepDefinitions()
    {
        this.repeatNumber = 85;
        this.delimiter = ".";
    }

    /*******************************************************************************************************************
     *
     *                                               Методы класса
     *
     ******************************************************************************************************************/

    /**
     * Возвращает строку-разделитель.
     * @return строка-разделитель
     */
    protected String getDelimiterString()
    {
        return StringUtils.repeat(delimiter, repeatNumber);
    }
}
