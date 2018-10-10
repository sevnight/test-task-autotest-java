import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Logger {

    private PrintWriter log = null;
    private String path;
    private Desktop desktop = null;

    /**
     * Инициализация логгера
     * @param pathLog путь в директорию лога и его название
     */
    void init(String pathLog){
        try {
            path = pathLog;
            desktop = Desktop.getDesktop();
            log  = new PrintWriter(pathLog);
            writeLog("Создан лог-файл.");
        } catch (FileNotFoundException e) {
            System.out.println(getDate() +"     Не удалось создать лог-файл.");
        }
    }

    /**
     * Получение текущей даты и времени
     * @return текующая дата и время
     */
    String getDate(){
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date=new Date();
        return format.format(date);
    }

    /**
     * Печать лога в формате
     * @param s сообщение для печати
     */
    void writeLog(String s){
        log.println(getDate() + "       " + s);
    }

    /**
     * Завершение работы с логом
     */
    void finishLog(){
        try {
            Driver.log.desktop.open(new File(Driver.log.path));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        log.close();
    }

}
