package ru.popova;
/**
 * Подключение логирования (в файл и консоль) к классу обработки файлов
 */
import java.io.IOException;
import java.util.logging.*;

public class Log {

    private Logger logger = Logger.getLogger("Files.class");

    Log() throws IOException {
        Handler consoleHandler = new ConsoleHandler();
        Handler fileHandler = new FileHandler("myJavaLog.txt");
        consoleHandler.setFormatter(new MyFormatter());
        fileHandler.setFormatter(new MyFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);
        logger.addHandler(fileHandler);
    }

    public void addLog (String msg)  {
        logger.info(msg);
    }

    static class MyFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return record.getLevel()+ record.getMessage()+ System.getProperty("line.separator");
        }
    }
}
