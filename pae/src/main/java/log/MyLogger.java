package log;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {

  static private FileHandler fileHTML;
  static private Formatter formatterHTML;
  static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  static public void setup() throws IOException {

    // suppress the logging output to the console
    Logger rootLogger = Logger.getLogger("");
    Handler[] handlers = rootLogger.getHandlers();
    if (handlers[0] instanceof ConsoleHandler) {
      rootLogger.removeHandler(handlers[0]);
    }

    logger.setLevel(Level.ALL);
    fileHTML = new FileHandler("src/main/java/logs_date/Logging_" + LocalDate.now() + ".html");



    // create an HTML formatter
    formatterHTML = new MyFormatter();
    fileHTML.setFormatter(formatterHTML);
    logger.addHandler(fileHTML);
  }

  public static Logger getLogger() {
    return logger;
  }

}
