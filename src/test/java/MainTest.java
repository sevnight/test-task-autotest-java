import cucumber.api.java.ru.*;
import org.javatuples.Pair;

public class MainTest {

    private Search s;

    @cucumber.api.java.Before
    public void setUp(){
        Driver.log = new Logger();
        Driver.log.init("src\\result\\log" + Driver.log.getDate() + ".txt");
        Driver.init();
        s = new Search(Driver.driver);
    }

    @Когда("^Пользователь ставить минимальную стоимость ноль$")
    public void FillStartPrice(){
        Driver.log.writeLog("Выставление начальной цены '0'");
        s.FillStartCost("0");
    }
    @И("^Чекает нормы 223$")
    public void Check223(){
        Driver.log.writeLog("Чек 'Закупка в соответствии с нормами 223-ФЗ (за исключением норм статьи 3.2 223-ФЗ)'");
        s.FillCheck223();
    }
    @И("^Чекает коммерческие закупки$")
    public void CheckCommercialPurchase(){
        Driver.log.writeLog("Чек 'Коммерческая закупка'");
        s.FillCheckCommercialPurchase();
    }
    @И("^Устанавливает дату публикации на сегодня$")
    public void FillTodayPublicationDate(){
        Driver.log.writeLog("'Дата публикации извещения' на сегодня");
        s.FillDate();
    }
    @Тогда("^Запускает поиск$")
    public void StartSearch(){
        Driver.log.writeLog("Начало поиска");
        s.SelectSearch();
    }
    @Когда("^Появился искомый список$")
    public void WaitLoadList(){
        Driver.log.writeLog("Ожидание загрузки списка для последующего его форматирования");
        s.WaitLoad();
    }
    @И("^Выставлен размер списка 100$")
    public void FillListSize(){
        Driver.log.writeLog("Выставление размера списка на '100'");
        s.setListSize100();
    }
    @Тогда("^Посчитать сумму не отменных закупок с номерами в ЕИС$")
    public void CountSumPurchases(){
        int ids = 0; double sum = 0;
        for (int i =0; i < s.getSumPage(); i++, s.setNextPage()){
            Pair<Integer,Double> newSum = s.focusRow();
            ids += newSum.getValue0();
            sum += newSum.getValue1();
        }
        Driver.log.writeLog(String.format("Найденно закупок: %d  Сумма = %.3f рублей.", ids,sum));
        System.out.println("Found purchases: "+ids+"\nThe sum: "+sum+" rub.");
    }

    @cucumber.api.java.After
    public void close(){
        Driver.finish();
    }
}
