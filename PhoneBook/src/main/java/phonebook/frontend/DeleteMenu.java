package phonebook.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import phonebook.backend.Request;
import phonebook.backend.RequestDelete;
import phonebook.backend.Response;
import phonebook.entity.Contact;
import phonebook.frontend.constants.Messages;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DeleteMenu extends Menu {

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    private void displayDeleteOptions(){

            LOGGER.debug("Displaying delete options.");
            System.out.println(OPTIONS);
            List<Contact> contacts = deleteOptions.get();
            for(int i=0;i<contacts.size();i++){
                System.out.printf("[%d] %s\n",
                        i,contacts.get(i).toString());
            }
    }

    private Scanner in;

    public DeleteMenu(){
        LOGGER.debug("Creating a delete menu object.");
        this.objectMapper = new ObjectMapper();
        this.in = new Scanner(System.in);
    }


    /**
     * Retrieves the index of the contact to be deleted by the user
     * though the console input.
     * @return the index of the contact to be deleted.
     */
    private int getDeleteId(){
        LOGGER.debug("Retrieving id of a contact to delete.");
        System.out.println(DEMAND_ID);
        String choice;
        do{
            choice = in.nextLine();
        }
        while(!choiceChecker(choice));

        return Integer.parseInt(choice);
    }


    /**
     * Checks the user input for the correct input.
     * @param choice user input.
     * @return true if the input is of a correct format, false otherwise.
     */
    private boolean choiceChecker(String choice){
        LOGGER.debug("Checking choice for the correct format: "+ choice);
        if(choice.matches("\\d+")){
            int choiceInt = Integer.parseInt(choice);
            if(choiceInt>=0&&choiceInt<deleteOptions.get().size()){
                return  true;
            }
        }
        System.out.println(Messages.PATTERN_INPUT_WARNING);
        return false;
    }

    public void updateDeleteOptions(Response optionsResponse) throws JsonProcessingException {
        LOGGER.debug("Adding delete options.");
        String json  = optionsResponse.getBody();
        List<Contact> contacts = objectMapper.readValue(json, new TypeReference<List<Contact>>() {});
        deleteOptions = Optional.of(contacts);
    }
    private Optional<List<Contact>> deleteOptions = Optional.empty();


    @Override
    public Request run() {
        LOGGER.debug("Running user interaction.");
        if(deleteOptions.isEmpty() || deleteOptions.get().isEmpty()){
            LOGGER.debug("No contacts can be deleted.");
            return new RequestDelete();
        }
        displayDeleteOptions();
        int deleteId = getDeleteId();
        Contact contact = deleteOptions.get().get(deleteId);
        this.deleteOptions = Optional.empty();
        return new RequestDelete(contact);
    }

    @Override
    public void executeResponse(Response response) {
        LOGGER.debug("Executing database response.");
        if(response.isAccepted()){
            System.out.println(SUCCESS_MESSAGE);
        }
        else{
            System.out.println(FAILURE_MESSAGE);
        }
    }

    private static final String SUCCESS_MESSAGE ="Deleted successfully.";
    private static final String FAILURE_MESSAGE = "Failed to delete a contact.";
    private static final String OPTIONS = "Which of the following contacts would you like to delete?";
    private static final String DEMAND_ID = "Enter the index of the contact you would like to delete.";

}
