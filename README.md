## тестовое задание по написанию автотеста java #SolarLab

<h1>Используются:</h1>
<ul>
  <li><b>Java</b> version "1.8.0_181"</li>
  <li><b>Selenium</b> version "2.53.1"</li>
  <li><b>Maven</b> version "3.1"</li>
</ul>

# Задание:
Кейс сбора данных:
<ul>
<li>Дата начала и конца публикации извещения (Дата подтягивается из конфига)</li>
<li>Закупка в соответствии с нормами 223-ФЗ - чекаем</li>
<li>Коммерческая закупка - чекаем</li>
<li>Начальная цена от 0</li>
<li>производим поиск</li>
<li>Собираем в Все записи в которых есть Номер в ЕИС получаем из них сумму и вычитаем отмененные (закладка отменена).</li>
<li>В консоль выводим значения в котором пишем кол-во лотов и их сумму, также создаем файл с текстом полученного результата.</li>
</ul>
