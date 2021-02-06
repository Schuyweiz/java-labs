package phonebook.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import phonebook.Application;
import phonebook.backend.Database;

public class Main {
    private static final Logger LOGGER = Logger.getLogger("GLOBAL");
    public static void main(String[] args){
        org.apache.log4j.BasicConfigurator.configure();
        LOGGER.debug("Program started running.");
        Database db = new Database();
        Application application = new Application(db);
        try {
            application.run();
        } catch (JsonProcessingException e) {
            System.out.println("Data file has been tampered with.\n" +
                    "The program will now shut down.");
            LOGGER.trace(e.getMessage());
        }
    }
}
