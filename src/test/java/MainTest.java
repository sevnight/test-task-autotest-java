import cucumber.api.java.ru.*;
import org.javatuples.Pair;

public class MainTest {

    private Search s;

    @cucumber.api.java.Before
    public void setUp(){
        Driver.log = new Logger();
        Driver.log.init("src\\result\\log" + Driver.log.getDate() + ".txt");
        try{
            Driver.init();
        }
        catch (Exception e){
            Driver.log.writeLog("[ERROR] не удалость инициализировать драйвер");
        }

        s = new Search(Driver.driver);
    }

    @Когда("^Пользователь ставить минимальную стоимость ноль$")
    public void FillStartPrice(){
        try{
            s.FillStartCost("0");
            Driver.log.writeLog("Выставление начальной цены '0'");
        }
        catch (Exception e){
            Driver.log.writeLog("[ERROR]Не получилось выставить начальную цену");
        }
    }

    @И("^Чекает нормы 223$")
    public void Check223(){
        try{
            s.FillCheck223();
            Driver.log.writeLog("Чек 'Закупка в соответствии с нормами 223-ФЗ (за исключением норм статьи 3.2 223-ФЗ)'");
        }
        catch (Exception e){
            Driver.log.writeLog("[ERROR]Не получилось выставить соответствие нормам 223");
        }
    }

    @И("^Чекает коммерческие закупки$")
    public void CheckCommercialPurchase(){
        try{
            s.FillCheckCommercialPurchase();
            Driver.log.writeLog("Чек 'Коммерческая закупка'");
        }
        catch (Exception e){
            Driver.log.writeLog("[ERROR]Не получилось выставить чек на коммерческие закупки");
        }
    }

    @И("^Устанавливает дату публикации на сегодня$")
    public void FillTodayPublicationDate(){
        try{
            s.FillDate();
            Driver.log.writeLog("'Дата публикации извещения' на сегодня");
        }
        catch (Exception e){
            Driver.log.writeLog("[ERROR]Не получилось выставить дату публикации");
        }
    }

    @Тогда("^Запускает поиск$")
    public void StartSearch(){
        try{
            s.SelectSearch();
            Driver.log.writeLog("Начало поиска");
        }
        catch (Exception e){
            Driver.log.writeLog("[ERROR]Не получилось запустить поиск");
        }
    }

    @Когда("^Появился искомый список$")
    public void WaitLoadList(){
        try{
            s.WaitLoad();
            Driver.log.writeLog("Ожидание загрузки списка для последующего его форматирования");
        }
        catch (Exception e){
            Driver.log.writeLog("[ERROR] Проблема с прогрузкой списка закупок");
        }
    }

    @И("^Выставлен размер списка 100$")
    public void FillListSize(){
        try{
            s.setListSize100();
            Driver.log.writeLog("Выставление размера списка на '100'");
        }
        catch (Exception e){
            Driver.log.writeLog("[ERROR] Не получилось изменить размер отображаемого списка закупок");
        }
    }
    @Тогда("^Посчитать сумму не отменных закупок с номерами в ЕИС$")
    public void CountSumPurchases(){
        try{
            int ids = 0; double sum = 0;
            for (int i =0; i < s.getSumPage(); i++, s.setNextPage()){
                Pair<Integer,Double> newSum = s.focusRow();
                ids += newSum.getValue0();
                sum += newSum.getValue1();
            }
            Driver.log.writeLog(String.format("Найденно закупок: %d  Сумма = %.3f рублей.", ids,sum));
            System.out.println("Found purchases: "+ids+"\nThe sum: "+sum+" rub.");
        }
        catch (Exception e){
            Driver.log.writeLog("[ERROR] Не удаётся обработать список закупок");
        }
    }

    @cucumber.api.java.After
    public void close(){
        Driver.finish();
    }
}
