package phonebook.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import phonebook.backend.FilterFunctions;
import phonebook.backend.Request;
import phonebook.backend.RequestGet;
import phonebook.backend.Response;
import phonebook.entity.Contact;

import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

public class AllMenu extends Menu{

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public AllMenu(){
        LOGGER.debug("Created a new display all menu.");
        this.in = new Scanner(System.in);
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public Request run() {
        LOGGER.debug("Executing run method.");
        return new RequestGet(
                new BiFunction<List<Contact>, String, List<Contact>>() {
                    @Override
                    public List<Contact> apply(List<Contact> contacts, String s) {
                        return FilterFunctions.searchBySurname(contacts,"");
                    }
                }
        );
    }

    @Override
    public void executeResponse(Response response) throws JsonProcessingException {
        LOGGER.debug("Executing a response.");
        String json = response.getBody();
        List<Contact> contacts = objectMapper.readValue(json, new TypeReference<List<Contact>>() {});
        for(int i=0;i<contacts.size();i++){
            System.out.printf("[%d] %s\n\n", i+1, contacts.get(i));
        }
    }
}
