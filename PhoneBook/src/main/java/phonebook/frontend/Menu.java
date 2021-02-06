package phonebook.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import phonebook.backend.Request;
import phonebook.backend.Response;

import java.util.Scanner;

public abstract class Menu {

    /**
     * Interacts with the user displaying and demanding class object relevant information.
     * @return request to the database based on the user input.
     * @throws JsonProcessingException
     */
    public abstract Request run() throws JsonProcessingException;


    /**
     * Displays the data received from the database response.
     * @param response database response.
     * @throws JsonProcessingException
     */
    public abstract void executeResponse(Response response) throws JsonProcessingException;
    protected Scanner in;
    protected ObjectMapper objectMapper;
}
