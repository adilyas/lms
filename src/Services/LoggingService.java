package Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LoggingService {

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    private PrintWriter printWriter;

    public LoggingService(String fileLocation) throws FileNotFoundException {
        printWriter = new PrintWriter(new File(fileLocation));
    }

    public void logString(String log){
        printWriter.println(log);
    }
}
