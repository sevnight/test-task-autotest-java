package steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.javatuples.Pair;
import pages.CommonPage;

public class CommonStepDefinitions extends AbstractStepDefinitions {
    private CommonPage commonPage = new CommonPage();

    @Когда("^Пользователь ставить минимальную стоимость ноль$")
    public void FillStartPrice(){
        logger.info("В поле \"начальная цена\" устанавливается значение \"0\"");
        commonPage.FillStartCost("0");
    }

    @И("^Чекает нормы 223$")
    public void Check223(){
        logger.info("Тип закупки \"Закупка в соответствии с нормами 223-ФЗ (за исключением норм статьи 3.2 223-ФЗ)\"");
        commonPage.FillCheck223Norm();
    }

    @И("^Чекает коммерческие закупки$")
    public void CheckCommercialPurchase(){
        logger.info("Тип закупки: \"Коммерческая закупка\"");
        commonPage.FillCheckCommercialPurchase();
    }

    @И("^Устанавливает дату публикации на сегодня$")
    public void FillTodayPublicationDate(){
        logger.info("Дата публикации - сегодня");
        commonPage.fillDate();
    }

    @Тогда("^Запускает поиск$")
    public void StartSearch(){
        logger.info("Начало поиска");
        commonPage.StartSearch();
    }

    @И("^Выставлен размер списка 100$")
    public void FillListSize(){
        logger.info("Размез отображаемого списка - 100");
        commonPage.setSizePurchaseListIs100();
    }
    @Тогда("^Посчитать сумму не отменных закупок с номерами в ЕИС$")
    public void CountSumPurchases(){
        logger.info("Начало обработки результатов поиска");
        int ids = 0;
        double sum = 0;
        for (int i = 0; i < commonPage.getSumPages(); i++, commonPage.setNextPage()){
            logger.info(String.format("Обработка %d-страницы",i+1));
            Pair<Integer,Double> newSum = commonPage.countAllFoundRows();
            logger.info(String.format(
                    "На %d-странице найденно %d закупок, на сумму %.3f рублей.",
                    i+1, newSum.getValue0(),newSum.getValue1())
            );
            ids += newSum.getValue0();
            sum += newSum.getValue1();
        }
        logger.info(String.format("Всего найденно закупок: %d.  На сумма = %.3f рублей.", ids,sum));
    }
}
