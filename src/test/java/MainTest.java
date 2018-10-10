import org.javatuples.Pair;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.text.MessageFormat;


public class MainTest {

    private Search s;

    @BeforeSuite
    public void setUp(){
        Driver.log = new Logger();
        Driver.log.init("src\\result\\log" + Driver.log.getDate() + ".txt");
        Driver.init();
        s = new Search(Driver.driver);
    }
    @AfterSuite
    public void close(){
        Driver.finish();
    }
    @Test
    public void MainTest(){
        Driver.log.writeLog("Выставление начальной цены '0'");
        s.FillStartCost("0");
        Driver.log.writeLog("Чек 'Закупка в соответствии с нормами 223-ФЗ (за исключением норм статьи 3.2 223-ФЗ)'");
        s.FillCheck223();
        Driver.log.writeLog("Чек 'Коммерческая закупка'");
        s.FillCheckCommercialPurchase();
        Driver.log.writeLog("'Дата публикации извещения' на сегодня");
        s.FillDate();
        Driver.log.writeLog("Начало поиска");
        s.SelectSearch();
        Driver.log.writeLog("Ожидание загрузки списка для последующего его форматирования");
        s.WaitLoad();
        Driver.log.writeLog("Выставление размера списка на '100'");
        s.setListSize100();
        Driver.log.writeLog("Циклический расчет суммы");
        int ids = 0; double sum = 0;
        for (int i =0; i < s.getSumPage(); i++, s.setNextPage()){
            Pair<Integer,Double> newSum = s.focusRow();
            ids += newSum.getValue0();
            sum += newSum.getValue1();
        }
        Driver.log.writeLog(String.format("Найденно закупок: %d  Сумма = %.3f рублей.", ids,sum));
        System.out.println("Found purchases: "+ids+"\nThe sum: "+sum+" rub.");
    }
}
