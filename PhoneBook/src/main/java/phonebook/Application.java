package phonebook;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import phonebook.backend.*;
import phonebook.entity.Contact;
import phonebook.frontend.*;
import phonebook.frontend.constants.Messages;

import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Application {

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public Application(Database database){
        LOGGER.debug("Application class created.");
        this.database = database;
        this.in = new Scanner(System.in);
    }

    public void run() throws JsonProcessingException {
        LOGGER.debug("run method started.");
        Menu[] options = {
                new SearchMenu(),
                new AddMenu(),
                new DeleteMenu(),
                new AllMenu()
        };
        int choice;
        do {
            displayActionChoice();
            choice = getActionChoice();

            if(choice==0){
                System.exit(0);
            }
            else{
                Menu currentMenu = options[choice-1];
                Request request = getRequest(currentMenu);
                Response response = database.executeRequest(request);
                currentMenu.executeResponse(response);
            }
        }
        while (true);
    }

    private Request getRequest(Menu currentMenu) throws JsonProcessingException {
        LOGGER.debug("getRequest method started.");
        if(currentMenu instanceof AddMenu){
            return currentMenu.run();
        }
        else if(currentMenu instanceof DeleteMenu){

            RequestGet requestGet = new RequestGet(
                    new BiFunction<List<Contact>, String, List<Contact>>() {
                        @Override
                        public List<Contact> apply(List<Contact> contacts, String s) {
                            return FilterFunctions.searchBySurname(contacts,"");
                        }
                    }
            );
            Response response = this.database.executeRequest(requestGet);
            ((DeleteMenu) currentMenu).updateDeleteOptions(response);
            return currentMenu.run();
        }
        else if(currentMenu instanceof SearchMenu){
            return currentMenu.run();
        }
        else if(currentMenu instanceof AllMenu){
            return currentMenu.run();
        }
        throw new IllegalArgumentException();
    }

    /**
     * Parses user input in the main menu section and returns a chosen option
     * as a number.
     * @return the number of the chosen option.
     */
    public int getActionChoice(){
        LOGGER.debug("getActionChoice method started.");
        String input = in.nextLine();
        while(!input.matches("[0-4]")){
            System.out.println(Messages.MENU_INPUT_WARNING);
            input = in.nextLine();
        }
        return Integer.parseInt(input);
    }

    /**
     * Displays main menu options for the user among such are
     * Searching for a contact using one of the filters.
     * Adding a new contact.
     * Deleting an existing contact.
     * Displaying all contacts.
     */
    public void displayActionChoice(){
        System.out.println(Messages.MAIN_MENU);
    }

    private final Scanner in;
    private final Database database;
}
